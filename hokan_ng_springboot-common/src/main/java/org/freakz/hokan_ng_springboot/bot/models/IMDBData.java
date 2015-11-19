package org.freakz.hokan_ng_springboot.bot.models;

import com.omertron.omdbapi.model.OmdbVideoBasic;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Petri Airio on 18.11.2015.
 * -
 */
public class IMDBData implements Serializable {


  private List<OmdbVideoBasic> searchResults;

  public IMDBData() {
  }

  public IMDBData(List<OmdbVideoBasic> searchResults) {
    this.searchResults = searchResults;
  }

  public List<OmdbVideoBasic> getSearchResults() {
    return searchResults;
  }

  public void setSearchResults(List<OmdbVideoBasic> searchResults) {
    this.searchResults = searchResults;
  }
}
