package org.freakz.hokan_ng_springboot.bot.updaters.kelikamerat;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.models.KelikameratUrl;
import org.freakz.hokan_ng_springboot.bot.models.KelikameratWeatherData;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri Airio on 22.6.2015.
 *
 */
@Component
//@Scope("prototype")
@Slf4j
public class KelikameratUpdater {

  private static final String BASE_ULR = "http://www.kelikamerat.info";

  private static final String[] KELIKAMERAT_URLS =
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

  private List<KelikameratUrl> stationUrls = new ArrayList<>();

  public void updateStations() throws IOException {
    stationUrls.clear();
    for (String url : KELIKAMERAT_URLS) {
      Document doc = Jsoup.connect(url).get();
      Elements elements = doc.getElementsByClass("road-camera");
      for (int xx = 0 ; xx < elements.size(); xx++) {
        Element div = elements.get(xx);
        Element href =     div.child(0);
        String hrefUrl = BASE_ULR + href.attributes().get("href");
        KelikameratUrl kelikameratUrl = new KelikameratUrl(url, hrefUrl);
        stationUrls.add(kelikameratUrl);
      }
    }
  }

  private float parseFloat(String str) {
    String f = str.split(" ")[0];
    if (!f.equals("-")) {
      return Float.parseFloat(f);
    } else {
      return Float.NaN;
    }
  }

  private int parseInt(String str) {
    String f = str.split(" ")[0];
    if (!f.equals("-")) {
      return Integer.parseInt(f);
    } else {
      return -1;
    }
  }

  public KelikameratWeatherData updateKelikameratWeatherData(KelikameratUrl url) {
    Document doc = null;
    try {
      doc = Jsoup.connect(url.getStationUrl()).get();
    } catch (IOException e) {
      log.error("Can't update data: {}", url);
      return null;
    }
    String titleText = doc.getElementsByTag("title").get(0).text();
    titleText = titleText.replaceFirst("Kelikamerat - ", "").replaceFirst("\\| Kelikamerat", "").trim();

    Elements elements = doc.getElementsByClass("weather-details");
    Element div = elements.get(0);
    Element table = div.child(0);
    Element tbody = table.child(0);

    KelikameratWeatherData data = new KelikameratWeatherData();
    data.setPlace(titleText);
    data.setUrl(url);

    String air = tbody.child(0).child(1).text();
    data.setAir(parseFloat(air));

    String road = tbody.child(1).child(1).text();
    data.setRoad(parseFloat(road));

    String ground = tbody.child(2).child(1).text();
    data.setGround(parseFloat(ground));

    String humidity = tbody.child(3).child(1).text();
    data.setHumidity(parseFloat(humidity));

    String dewPoint = tbody.child(4).child(1).text();
    data.setDewPoint(parseFloat(dewPoint));

    Elements elements2 = doc.getElementsByClass("date-time");
    if (elements2.size() > 0) {
      String timestamp = elements2.get(0).text().substring(12);
      String pattern = "dd.MM.yyyy hh:mm:ss";
      DateTime dateTime  = DateTime.parse(timestamp, DateTimeFormat.forPattern(pattern));
      data.setTime(dateTime);
    }

    return data;
  }

  public List<KelikameratUrl> getStationUrls() {
    return stationUrls;
  }
}
