package org.freakz.hokan_ng_springboot.bot.models;

import com.omertron.omdbapi.model.OmdbVideoFull;

import java.io.Serializable;

/**
 * Created by Petri Airio on 19.11.2015.
 * -
 */
public class IMDBDetails implements Serializable {

  private OmdbVideoFull details;

  public IMDBDetails() {
  }

  public IMDBDetails(OmdbVideoFull details) {
    this.details = details;
  }

  public OmdbVideoFull getDetails() {
    return details;
  }

  public void setDetails(OmdbVideoFull details) {
    this.details = details;
  }
}
