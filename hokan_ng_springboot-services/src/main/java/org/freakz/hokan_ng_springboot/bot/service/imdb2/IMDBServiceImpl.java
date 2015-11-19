package org.freakz.hokan_ng_springboot.bot.service.imdb2;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.SearchResults;
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
      if (part.toLowerCase().matches("\\d\\d\\d\\d|s\\d\\d?e\\d\\d?|\\d\\d?x\\d\\d?|\\d\\d\\d\\d?p")) {
        return name;
      }
      if (name.length() > 0) {
        name += " ";
      }
      name += part;
    }
    return null;
  }

  public IMDBData findByTitle(String title) {
    try {
      String parsed = parseSceneMovieName(title);
      if (parsed != null) {
        log.debug("Using parsed name: {}", parsed);
        title = parsed;
      }
      SearchResults results = omdb.search(title);
      return new IMDBData(results.getResults());
    } catch (OMDBException e) {
      e.printStackTrace();
    }
    return null;
  }


}
