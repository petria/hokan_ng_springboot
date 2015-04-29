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


  public static void main(String[] args) {
    GoogleAPI.setHttpReferrer("https://github.com/petria/hokan_ng_springboot");

    // Set the Google Translate API key
    // See: http://code.google.com/apis/language/translate/v2/getting_started.html
    GoogleAPI.setKey("");
    String textii = "If you believe that your key has been compromised—for instance, if you notice suspicious activity in your Console traffic reports—then Google recommends generating a new key by clicking the \"Generate new key\" button to the right of the key. Upon doing this, you can decide whether or not to allow up to 24 hours to phase out your old key, during which time both keys are active. Using a phased deactivation gives you time to fully deploy your new key. If, instead, you want to invalidate the old key immediately, then click the \"Delete key\" button to the right of the key.";
    String translatedText = null;
    try {
//      for (String word : textii.split(" ")) {
        translatedText = Translate.DEFAULT.execute(textii, Language.AUTO_DETECT, Language.FINNISH);
        System.out.println(" --> " + translatedText);
//      }
    } catch (GoogleAPIException e) {
      e.printStackTrace();
    }
    System.out.println(translatedText);
  }

  @Override
  public String getTranslation(String text, Language from, Language to) {
    GoogleAPI.setHttpReferrer("https://github.com/petria/hokan_ng_springboot");

    // Set the Google Translate API key
    // See: http://code.google.com/apis/language/translate/v2/getting_started.html
    Property apikey = propertyService.findFirstByPropertyName(PropertyName.PROP_SYS_GOOGLE_API_KEY);
    log.debug("GoogleAPI key: {}", apikey.getValue());
    GoogleAPI.setKey(apikey.getValue());
    //Translate.DEFAULT.
    String translatedText = null;
    try {
      translatedText = Translate.DEFAULT.execute("Bonjour le monde", null, Language.FINNISH);
    } catch (GoogleAPIException e) {
      e.printStackTrace();
    }

    return null;
  }
}
