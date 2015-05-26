package org.freakz.hokan_ng_springboot.bot.service;


import com.google.api.GoogleAPI;
import com.google.api.GoogleAPIException;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyEntity;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;
import org.freakz.hokan_ng_springboot.bot.jpa.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 29.4.2015.
 *
 */
@Service
@Slf4j
public class GoogleTranslatorServiceImpl implements GoogleTranslatorService {

  @Autowired
  private PropertyService propertyService;

  @Override
  public String getTranslation(Object[] text, Language from, Language to) {
    GoogleAPI.setHttpReferrer("https://github.com/petria/hokan_ng_springboot/");

    PropertyEntity apikey = propertyService.findFirstByPropertyName(PropertyName.PROP_SYS_GOOGLE_API_KEY);
    if (apikey == null) {
      log.error("GoogleAPI key missing");
      return "GoogleAPI key missing";
    }
//    log.debug("GoogleAPI key: {}", apikey.getValue());
    GoogleAPI.setKey(apikey.getValue());

    StringBuilder sb = new StringBuilder();
    try {
      for (Object textLine : text) {
        String translatedText = Translate.DEFAULT.execute(textLine.toString(), from, to);
        sb.append(translatedText);
      }
    } catch (GoogleAPIException e) {
      log.error("GoogleAPI", e);
      return e.getMessage();
    }
    return sb.toString();
  }
}
