package org.freakz.hokan_ng_springboot.bot.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Petri Airio on 18.6.2015.
 *
 */
public class KelikameratWeatherTest {

  @Test
  public void testWeatherParse() throws IOException {

    Document doc = Jsoup.connect("http://www.kelikamerat.info/kelikamerat/Keski-Suomi/Muurame/tie-9/vt9_Muurame").get();
    Elements elements = doc.getElementsByClass("air-temp-on-image");
    Element test = elements.get(0);
    test = test.getElementById("air-temp-image");
    String air = test.text();
    int x = 0;
  }



}
