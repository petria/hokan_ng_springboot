package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.util.StaticStrings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 1.9.2015.
 *
 */
public class HoroUpdaterTest {

  @Test
  public void testUpdateHoroJsoup() throws IOException {

    Document doc = Jsoup.connect("http://www.iltalehti.fi/viihde/horoskooppi1_ho.shtml").userAgent(StaticStrings.HTTP_USER_AGENT).get();
    Elements horot = doc.getElementsByClass("valiotsikko");
    Element p = horot.get(0);
    List<DataNode> nodes = doc.dataNodes();
    int foo = 0;
  }

}
