package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.models.KelikameratUrl;
import org.freakz.hokan_ng_springboot.bot.updaters.kelikamerat.KelikameratUpdater;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by Petri Airio on 18.6.2015.
 *
 */
public class KelikameratWeatherTest {

  String[] urls =
      {
          "http://www.kelikamerat.info/kelikamerat/Etel%C3%A4-Karjala",
          "http://www.kelikamerat.info/kelikamerat/Etel%C3%A4-Pohjanmaa",
          "http://www.kelikamerat.info/kelikamerat/Etel%C3%A4-Savo",
          "http://www.kelikamerat.info/kelikamerat/Kainuu",
          "http://www.kelikamerat.info/kelikamerat/Kanta-H%C3%A4me",
          "http://www.kelikamerat.info/kelikamerat/Keski-Pohjanmaa",
          "http://www.kelikamerat.info/kelikamerat/Keski-Suomi",
          "http://www.kelikamerat.info/kelikamerat/Kymenlaakso",
          "http://www.kelikamerat.info/kelikamerat/Lappi",
          "http://www.kelikamerat.info/kelikamerat/P%C3%A4ij%C3%A4t-H%C3%A4me",
          "http://www.kelikamerat.info/kelikamerat/Pirkanmaa",
          "http://www.kelikamerat.info/kelikamerat/Pohjanmaa",
          "http://www.kelikamerat.info/kelikamerat/Pohjois-Karjala",
          "http://www.kelikamerat.info/kelikamerat/Pohjois-Pohjanmaa",
          "http://www.kelikamerat.info/kelikamerat/Pohjois-Savo",
          "http://www.kelikamerat.info/kelikamerat/Satakunta",
          "http://www.kelikamerat.info/kelikamerat/Uusimaa",
          "http://www.kelikamerat.info/kelikamerat/Varsinais-Suomi"
      };

  @Test
  public void testWeatherParse() throws IOException {

    Document doc = Jsoup.connect("http://www.kelikamerat.info/kelikamerat/Varsinais-Suomi/Turku/tie-40/kt40_Ravattula").get();
    Elements elements = doc.getElementsByClass("air-temp-on-image");
    Element test = elements.get(0);
    test = test.getElementById("air-temp-image");
    String air = test.text();
    int x = 0;
  }

  @Test
  public void testParseLocations() throws IOException {
    Document doc = Jsoup.connect("http://www.kelikamerat.info/kelikamerat/Etel%C3%A4-Savo").get();
    Elements elements = doc.getElementsByClass("road-camera");
    Element div = elements.get(0);
    Element href =     div.child(0);
    String url = href.baseUri() + href.attributes().get("href");
  }

  @Test
  public void testUpdater() throws IOException {
    KelikameratUpdater updater = new KelikameratUpdater();
    updater.updateStations();
    List<KelikameratUrl> urlList = updater.getStationUrls();
    int x = 0;
  }

}