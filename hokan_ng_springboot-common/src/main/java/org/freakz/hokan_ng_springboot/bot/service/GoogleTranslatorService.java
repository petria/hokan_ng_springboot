package org.freakz.hokan_ng_springboot.bot.service;

import com.google.api.translate.Language;

/**
 * Created by Petri Airio on 29.4.2015.
 *
 */
public interface GoogleTranslatorService {

  String getTranslation(String text, Language from, Language to);

}
