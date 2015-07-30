package org.freakz.hokan_ng_springboot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.models.KelikameratUrl;
import org.freakz.hokan_ng_springboot.bot.models.KelikameratWeatherData;
import org.freakz.hokan_ng_springboot.bot.updaters.kelikamerat.KelikameratUpdater;
import org.freakz.hokan_ng_springboot.bot.util.StringStuff;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
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
@Slf4j
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
    Elements elements = doc.getElementsByClass("weather-details");
    Element title = doc.getElementsByTag("title").get(0);
    String titleText = doc.getElementsByTag("title").get(0).text();
    titleText = titleText.replaceFirst("Kelikamerat - ", "").replaceFirst("\\| Kelikamerat", "").trim();
    Element div = elements.get(0);
    Element table = div.child(0);
    Element tbody = table.child(0);
    String air = tbody.child(0).child(1).text();;
    String road = tbody.child(1).child(1).text();;
    String ground = tbody.child(2).child(1).text();;
    String humidity = tbody.child(3).child(1).text();;
    String dewPoint = tbody.child(4).child(1).text();;

    Elements elements2 = doc.getElementsByClass("date-time");
    String timestamp = elements2.get(0).text().substring(12);
    String pattern = "dd.MM.yyyy HH:mm:ss";
    DateTime dateTime  = DateTime.parse(timestamp, DateTimeFormat.forPattern(pattern));
    int x = 0;
  }

  @Test
  public void testUpdateUrl() throws IOException {
    KelikameratUpdater updater = new KelikameratUpdater();
    KelikameratUrl url = new KelikameratUrl();
    url.setStationUrl("http://www.kelikamerat.info/kelikamerat/Varsinais-Suomi/Turku/tie-40/kt40_Ravattula");
    KelikameratWeatherData data= updater.updateKelikameratWeatherData(url);
    int xx = 0;
  }

//  @Test
  public void testUpdater() throws IOException {
    KelikameratUpdater updater = new KelikameratUpdater();
    updater.updateStations();
    List<KelikameratUrl> urlList = updater.getStationUrls();
    for (KelikameratUrl url : urlList) {
      KelikameratWeatherData d = updater.updateKelikameratWeatherData(url);
      log.debug("{}", StringStuff.formatWeather(d));
    }
    int x = 0;
  }

}
