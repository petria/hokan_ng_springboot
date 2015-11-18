package org.freakz.hokan_ng_springboot.bot.models;

import com.omertron.omdbapi.model.OmdbVideoFull;

import java.io.Serializable;

/**
 * Created by Petri Airio on 18.11.2015.
 * -
 */
public class IMDBData implements Serializable {

  private OmdbVideoFull omdbVideoFull;

  public IMDBData() {
  }

  public IMDBData(OmdbVideoFull omdbVideoFull) {
    this.omdbVideoFull = omdbVideoFull;
  }

  public OmdbVideoFull getOmdbVideoFull() {
    return omdbVideoFull;
  }

  public void setOmdbVideoFull(OmdbVideoFull omdbVideoFull) {
    this.omdbVideoFull = omdbVideoFull;
  }

}
