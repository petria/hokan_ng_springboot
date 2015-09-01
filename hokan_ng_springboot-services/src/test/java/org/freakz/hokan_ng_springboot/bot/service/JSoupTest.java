package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.util.StaticStrings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 1.9.2015.
 *
 */
public class JSoupTest {

//  @Test
  public void testUpdateHoroJsoup() throws IOException {

    Document doc = Jsoup.connect("http://www.iltalehti.fi/viihde/horoskooppi1_ho.shtml").userAgent(StaticStrings.HTTP_USER_AGENT).get();
    Elements horot = doc.getElementsByAttributeValue("id", "container_keski");
    Elements pees = horot.select("p");
    for (int horo = 1; horo < 25; horo += 2) {
      Element ee1 = pees.get(horo);
      Element ee2 = pees.get(horo + 1);
      System.out.printf("%s -> %s\n", ee1.text(), ee2.text());
      int foo = 2;
    }
//    List<Element> testt = horot2.subList(13, 36);
    int foo = 0;

  }

  @Test
  public void testMetarFetchJsoup() throws IOException {
    //
    String stationID = "KCOS";
    String urlStr = "ftp://tgftp.nws.noaa.gov/data/observations/metar/decoded/" + stationID + ".TXT";
    URL url = new URL(urlStr);
    URLConnection conn = url.openConnection();
    InputStream in = conn.getInputStream();
    InputStreamReader isr = new InputStreamReader(in);
    BufferedReader br =
        new BufferedReader(isr);
    String l = null;
    StringBuilder htmlBuffer = new StringBuilder();
    do {
      try {
        l = br.readLine();
      } catch (IOException ex) {
//        log.info("read line failed", ex);
      }
      if (l != null) {
        htmlBuffer.append(l);
        htmlBuffer.append("\n");
      }
    } while (l != null);
    int foo = 0;
  }

}
