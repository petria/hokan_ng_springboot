package org.freakz.hokan_ng_springboot.bot.models;

import java.io.Serializable;

/**
 * Created by Petri Airio on 24.8.2015.
 *
 */
public class StatsData implements Serializable {

  private String nick;
  private int words;
  private int lines;


  public StatsData(String nickUpper) {
    this.nick = nickUpper;
    this.words = 0;
    this.lines = 0;
  }

  public String getNick() {
    return nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

  public int getWords() {
    return words;
  }

  public void setWords(int words) {
    this.words = words;
  }

  public int getLines() {
    return lines;
  }

  public void setLines(int lines) {
    this.lines = lines;
  }
}
