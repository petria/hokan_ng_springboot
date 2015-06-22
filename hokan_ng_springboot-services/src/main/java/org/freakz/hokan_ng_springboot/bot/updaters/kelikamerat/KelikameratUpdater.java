package org.freakz.hokan_ng_springboot.bot.updaters.kelikamerat;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.models.KelikameratUrl;
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
    int urlCount = 0;
    for (String url : KELIKAMERAT_URLS) {
      Document doc = Jsoup.connect(url).get();
      Elements elements = doc.getElementsByClass("road-camera");
      for (int xx = 0 ; xx < elements.size(); xx++) {
        Element div = elements.get(xx);
        Element href =     div.child(0);
        String hrefUrl = BASE_ULR + href.attributes().get("href");
        KelikameratUrl kelikameratUrl = new KelikameratUrl(url, hrefUrl);
        stationUrls.add(kelikameratUrl);
        log.debug("{} url: {}", ++urlCount, hrefUrl);
      }
    }
  }

  public List<KelikameratUrl> getStationUrls() {
    return stationUrls;
  }
}
