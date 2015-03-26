package org.freakz.hokan_ng_springboot.bot.model;

import lombok.Getter;

import java.io.Serializable;

/**
 * Created by Petri Airio on 22.3.2015.
 *
 */
public class UrlLink implements Serializable {

  private static final long serialVersionUID = 1L;

  @Getter
  private String urlTarget;

  @Getter
  private String urlText;

  public UrlLink() {
  }

  public UrlLink(String urlTarget, String urlText) {
    this.urlTarget = urlTarget;
    this.urlText = urlText;
  }
}
