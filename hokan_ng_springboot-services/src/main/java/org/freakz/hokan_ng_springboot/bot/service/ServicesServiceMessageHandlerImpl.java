package org.freakz.hokan_ng_springboot.bot.service;

import com.google.api.translate.Language;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceResponse;
import org.freakz.hokan_ng_springboot.bot.jms.JmsEnvelope;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsServiceMessageHandler;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.models.*;
import org.freakz.hokan_ng_springboot.bot.service.metar.MetarDataService;
import org.freakz.hokan_ng_springboot.bot.updaters.DataUpdater;
import org.freakz.hokan_ng_springboot.bot.updaters.UpdaterData;
import org.freakz.hokan_ng_springboot.bot.updaters.UpdaterManagerService;
import org.freakz.hokan_ng_springboot.bot.updaters.horo.HoroUpdater;
import org.freakz.hokan_ng_springboot.bot.updaters.telkku.TelkkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Petri Airio on 10.2.2015.
 *
 *
 */
@Controller
@Slf4j
@SuppressWarnings("unchecked")
public class ServicesServiceMessageHandlerImpl implements JmsServiceMessageHandler {

  @Autowired
  private MetarDataService metarDataService;

  @Autowired
  private TelkkuService telkkuService;

  @Autowired
  private GoogleTranslatorService translatorService;

  @Autowired
  private UpdaterManagerService updaterManagerService;

  @Override
  public void handleJmsEnvelope(JmsEnvelope envelope) throws Exception {
    log.debug("Handling envelope");
    ServiceRequest request = envelope.getMessageIn().getServiceRequest();
    ServiceResponse response = new ServiceResponse();
    UpdaterData updaterData;
    switch (request.getType()) {
      case HORO_REQUEST:
        HoroUpdater horoUpdater = (HoroUpdater) updaterManagerService.getUpdater("horoUpdater");
        updaterData = new UpdaterData();
        horoUpdater.getData(updaterData, request.getParameters());
        HoroHolder hh = (HoroHolder) updaterData.getData();
        response.setResponseData("HORO_DATA", hh);
        break;
      case METAR_REQUEST:
        List<MetarData> data = metarDataService.getMetarData(request.getParameters());
        response.setResponseData("METAR_DATA", data);
        break;
      case TV_DAY_REQUEST:
        Channel channel = (Channel) request.getParameters()[0];
        Date date = (Date) request.getParameters()[1];
        List<TelkkuProgram> tvDayData = telkkuService.getChannelDailyNotifiedPrograms(channel, date);
        response.setResponseData("TV_DAY_DATA", tvDayData);
        break;
      case TV_INFO_REQUEST:
        int id = (int) request.getParameters()[0];
        TelkkuProgram program = telkkuService.findProgramById(id);
        response.setResponseData("TV_INFO_DATA", program);
        break;
      case TV_NOW_REQUEST:
        TvNowData tvNowData = telkkuService.getTvNowData();
        response.setResponseData("TV_NOW_DATA", tvNowData);
        break;
      case TRANSLATE_REQUEST:
        String translated = translatorService.getTranslation(request.getParameters(), Language.AUTO_DETECT, Language.FINNISH);
        response.setResponseData("TRANSLATE_RESPONSE", translated);
        break;
      case UPDATERS_LIST:
        List<DataUpdaterModel> modelList = updaterManagerService.getDataUpdaterModelList();
        response.setResponseData("UPDATER_LIST_RESPONSE", modelList);
        break;
      case UPDATERS_START:
        List<DataUpdaterModel> startedUpdaters = new ArrayList<>();
        for (Object toStart : request.getParameters()) {
          String updater = (String) toStart;
          DataUpdaterModel model = updaterManagerService.startUpdaterByName(updater);
          if (model != null) {
            startedUpdaters.add(model);
          }
        }
        response.setResponseData("START_UPDATER_LIST_RESPONSE", startedUpdaters);
        break;
      case WEATHER_REQUEST:
        DataUpdater weatherUpdater = updaterManagerService.getUpdater("weatherUpdater");
        updaterData = new UpdaterData();
        weatherUpdater.getData(updaterData);
        List<WeatherData> datas = (List<WeatherData>) updaterData.getData();
        response.setResponseData("WEATHER_DATA", datas);
        break;
    }
    envelope.getMessageOut().addPayLoadObject("SERVICE_RESPONSE", response);
  }

}
