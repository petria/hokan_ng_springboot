package org.freakz.hokan_ng_springboot.bot.service;


import com.google.api.GoogleAPI;
import com.google.api.GoogleAPIException;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Property;
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
  public String getTranslation(String[] text, Language from, Language to) {
    GoogleAPI.setHttpReferrer("https://github.com/petria/hokan_ng_springboot");

    // Set the Google Translate API key
    // See: http://code.google.com/apis/language/translate/v2/getting_started.html
    Property apikey = propertyService.findFirstByPropertyName(PropertyName.PROP_SYS_GOOGLE_API_KEY);
    log.debug("GoogleAPI key: {}", apikey.getValue());
    GoogleAPI.setKey(apikey.getValue());
    //Translate.DEFAULT.
    String translatedText = null;
    StringBuilder sb = new StringBuilder();
    try {
      for (String textLine : text) {
        translatedText = Translate.DEFAULT.execute(textLine, from, to);
        sb.append(translatedText);
      }
    } catch (GoogleAPIException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }
}
