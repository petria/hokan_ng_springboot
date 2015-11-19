package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequest;
import org.freakz.hokan_ng_springboot.bot.events.ServiceRequestType;
import org.freakz.hokan_ng_springboot.bot.events.ServiceResponse;
import org.freakz.hokan_ng_springboot.bot.jms.JmsEnvelope;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsServiceMessageHandler;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.models.*;
import org.freakz.hokan_ng_springboot.bot.service.annotation.ServiceMessageHandler;
import org.freakz.hokan_ng_springboot.bot.service.currency.CurrencyService;
import org.freakz.hokan_ng_springboot.bot.service.imdb.IMDBService;
import org.freakz.hokan_ng_springboot.bot.service.metar.MetarDataService;
import org.freakz.hokan_ng_springboot.bot.service.nimipaiva.NimipaivaService;
import org.freakz.hokan_ng_springboot.bot.service.translate.SanakirjaOrgTranslateService;
import org.freakz.hokan_ng_springboot.bot.service.urls.UrlCatchService;
import org.freakz.hokan_ng_springboot.bot.updaters.DataUpdater;
import org.freakz.hokan_ng_springboot.bot.updaters.UpdaterData;
import org.freakz.hokan_ng_springboot.bot.updaters.UpdaterManagerService;
import org.freakz.hokan_ng_springboot.bot.updaters.horo.HoroUpdater;
import org.freakz.hokan_ng_springboot.bot.updaters.telkku.TelkkuService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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
  private ApplicationContext applicationContext;

  @Autowired
  private CurrencyService currencyService;

  @Autowired
  private IMDBService imdbService;

  @Autowired
  private MetarDataService metarDataService;

  @Autowired
  private NimipaivaService nimipaivaService;

  @Autowired
  private TelkkuService telkkuService;

  @Autowired
  private SanakirjaOrgTranslateService translateService;

  @Autowired
  private UpdaterManagerService updaterManagerService;

  @Autowired
  private UrlCatchService urlCatchService;

  private boolean findHandlersMethod(ServiceRequest request, ServiceResponse response) {
    String[] names = applicationContext.getBeanDefinitionNames();
    for (String beanName : names) {
      Object obj = applicationContext.getBean(beanName);
      Class<?> objClz = obj.getClass();
      if (org.springframework.aop.support.AopUtils.isAopProxy(obj)) {
        objClz = org.springframework.aop.support.AopUtils.getTargetClass(obj);
      }
      for (Method m : objClz.getDeclaredMethods()) {
        if (m.isAnnotationPresent(ServiceMessageHandler.class)) {
          Annotation annotation = m.getAnnotation(ServiceMessageHandler.class);
          ServiceMessageHandler serviceMessageHandler = (ServiceMessageHandler) annotation;
          ServiceRequestType requestType = serviceMessageHandler.ServiceRequestType();
          if (requestType == request.getType()) {
            try {
              log.debug("Method: {} -> {}", m, requestType);
              m.invoke(obj, request, response);
              return true;
            } catch (Exception e) {
              log.error("Could not call service handler for: {}", request);
              return false;
            }
          }
        }
      }
    }
    return false;
  }

  @ServiceMessageHandler(ServiceRequestType = ServiceRequestType.WEATHER_REQUEST)
  public void handleWeatherRequest(ServiceRequest request, ServiceResponse response) {
    DataUpdater weatherUpdater = updaterManagerService.getUpdater("kelikameratUpdater");
    UpdaterData updaterData = new UpdaterData();
    weatherUpdater.getData(updaterData);
    List<KelikameratWeatherData> datas = (List<KelikameratWeatherData>) updaterData.getData();
    response.setResponseData("WEATHER_DATA", datas);
  }


  @ServiceMessageHandler(ServiceRequestType = ServiceRequestType.IMDB_TITLE_REQUEST)
  public void handleIMDBTitleRequest(ServiceRequest request, ServiceResponse response) {
    String title = (String) request.getParameters()[0];
    IMDBData imdbData = imdbService.findByTitle(title);
    response.setResponseData("IMDB_TITLE_DATA", imdbData);
  }

  @Override
  public void handleJmsEnvelope(JmsEnvelope envelope) throws Exception {
    ServiceRequest request = envelope.getMessageIn().getServiceRequest();
    ServiceResponse response = new ServiceResponse();

    boolean handleDone = findHandlersMethod(request, response);

    if (!handleDone) {
      log.debug("Doing service request old way: {}", request);
      UpdaterData updaterData;
      switch (request.getType()) {
        case CATCH_URLS_REQUEST:
          urlCatchService.catchUrls(request.getIrcMessageEvent());
          break;
        case CURRENCY_CONVERT_REQUEST:
          String amount = (String) request.getParameters()[0];
          String from = (String) request.getParameters()[1];
          String to = (String) request.getParameters()[2];
          String currencyConvert = currencyService.googleConvert(amount, from, to);
          response.setResponseData("CURRENCY_CONVERT_RESPONSE", currencyConvert);
          break;
        case CURRENCY_LIST_REQUEST:
          List<GoogleCurrency> currencyList = currencyService.getGoogleCurrencies();
          response.setResponseData("CURRENCY_LIST_RESPONSE", currencyList); // TODO
          break;
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
        case NIMIPAIVA_DAY:
          DateTime day = (DateTime) request.getParameters()[0];
          NimipaivaData nimipaivaData = nimipaivaService.getNamesForDay(day);
          response.setResponseData("NIMIPAIVA_DAY_RESPONSE", nimipaivaData);
          break;
        case NIMIPAIVA_NAME:
          String nameStr = (String) request.getParameters()[0];
          NimipaivaData theDay = nimipaivaService.findDayForName(nameStr);
          response.setResponseData("NIMIPAIVA_NAME_RESPONSE", theDay);
          // TODO
          break;
        case TV_FIND_REQUEST:
          String programs = (String) request.getParameters()[0];
          List<TelkkuProgram> programList = telkkuService.findPrograms(programs);
          response.setResponseData("TV_FIND_DATA", programList);
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
          String originalText = (String) request.getParameters()[0];
          TranslateResponse translateResponse = translateService.translateText(originalText);
          response.setResponseData("TRANSLATE_RESPONSE", translateResponse);
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
        default:
          log.error("Service request NOT handled!!!!");
      }
    }
    envelope.getMessageOut().addPayLoadObject("SERVICE_RESPONSE", response);
  }



}
