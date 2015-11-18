package org.freakz.hokan_ng_springboot.bot.service.imdb;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.omertron.omdbapi.tools.OmdbBuilder;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.models.IMDBData;
import org.springframework.stereotype.Service;

/**
 * Created by Petri Airio on 17.11.2015.
 * -
 */
@Service
@Slf4j
public class IMDBServiceImpl implements IMDBService {

  private static final OmdbApi omdb = new OmdbApi();


  public String parseSceneMovieName(String sceneName) {
    if (sceneName == null) {
      return null;
    }
    String[] parts = sceneName.split("\\.");
    String name = "";
    for (String part : parts) {
      if (name.length() > 0) {
        name += " ";
      }
      if (part.toLowerCase().matches("\\d\\d\\d\\d|s\\d\\d?e\\d\\d?|\\d\\d?x\\d\\d?|\\d\\d\\d\\d?p")) {
        break;
      }
      name += part;
    }
    return name;
  }

  public IMDBData findByTitle(String title) {
    try {
      OmdbVideoFull result = omdb.getInfo(new OmdbBuilder().setTitle(title).build());
      return new IMDBData(result);
    } catch (OMDBException e) {
      e.printStackTrace();
    }
    return null;
  }


}
