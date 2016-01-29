package org.freakz.hokan_ng_springboot.bot.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Petri Airio on 29.1.2016.
 * -
 */
public class ParsePDFTest {


  public void parsePDFTest() throws IOException {
    URL url =
        new URL( "http://pasca.undiksha.ac.id/e-journal/index.php/jurnal_bahasa/article/view/500" );

    URLConnection connection = url.openConnection();

    InputStream input = connection.getInputStream();

    Document doc = Jsoup.parse(input);
    System.out.println(doc.toString());
  }

}
