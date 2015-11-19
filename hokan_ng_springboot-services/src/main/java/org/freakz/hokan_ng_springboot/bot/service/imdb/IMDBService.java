package org.freakz.hokan_ng_springboot.bot.service.imdb;

import org.freakz.hokan_ng_springboot.bot.models.IMDBData;

/**
 * Created by Petri Airio on 17.11.2015.
 * -
 */
public interface IMDBService {

  IMDBData findByTitle(String title);

}
