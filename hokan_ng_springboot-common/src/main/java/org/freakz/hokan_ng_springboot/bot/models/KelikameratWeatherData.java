package org.freakz.hokan_ng_springboot.bot.models;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Created by Petri Airio on 23.6.2015.
 *
 */
public class KelikameratWeatherData implements Serializable {

  private DateTime time;

  private KelikameratUrl url;

  private String place;

  private float air;
  private float road;
  private float ground;

  private float humidity;
  private float devPoint;

  public KelikameratWeatherData() {
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public DateTime getTime() {
    return time;
  }

  public void setTime(DateTime time) {
    this.time = time;
  }

  public KelikameratUrl getUrl() {
    return url;
  }

  public void setUrl(KelikameratUrl url) {
    this.url = url;
  }

  public float getAir() {
    return air;
  }

  public void setAir(float air) {
    this.air = air;
  }

  public float getRoad() {
    return road;
  }

  public void setRoad(float road) {
    this.road = road;
  }

  public float getGround() {
    return ground;
  }

  public void setGround(float ground) {
    this.ground = ground;
  }

  public float getHumidity() {
    return humidity;
  }

  public void setHumidity(float humidity) {
    this.humidity = humidity;
  }

  public float getDewPoint() {
    return devPoint;
  }

  public void setDewPoint(float devPoint) {
    this.devPoint = devPoint;
  }

  /*

     String air = tbody.child(0).child(1).text();;
    String road = tbody.child(1).child(1).text();;
    String ground = tbody.child(2).child(1).text();;
    String humidity = tbody.child(3).child(1).text();;
    String dewPoint = tbody.child(4).child(1).text();;

    Elements elements2 = doc.getElementsByClass("map-top-bar");
    String timestamp = elements2.get(0).child(0).text();
   */
}
