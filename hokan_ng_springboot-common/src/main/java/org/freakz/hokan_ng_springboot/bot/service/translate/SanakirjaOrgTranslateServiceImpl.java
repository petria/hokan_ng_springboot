package org.freakz.hokan_ng_springboot.bot.service.translate;

import org.freakz.hokan_ng_springboot.bot.models.TranslateData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri Airio on 12.11.2015.
 * -
 */
@Service
public class SanakirjaOrgTranslateServiceImpl implements SanakirjaOrgTranslateService {


  private static final String BASE_URL = "http://www.sanakirja.org/search.php";
  private static final int L_FINNISH = 17;
  private static final int L_ENGLISH = 3;

  // http://www.sanakirja.org/search.php?q=hei+hei+vaan+mit%C3%A4+kuuluu&l=17&l2=3
// http://www.sanakirja.org/search.php?q=gay&l=3&l2=17

  private List<TranslateData> getTranslations(Elements elements) {
    List<TranslateData> results = new ArrayList<>();
    for (int i = 0; i < elements.size(); i++) {
      Element t = elements.get(i);
      String number = t.child(0).text();
      if (number.endsWith(".")) {
        String translation = t.child(1).text();
        String context = t.child(2).text();
        if (translation != null && translation.length() > 0) {
          results.add(new TranslateData(translation, context));
        }
      }
    }
    return results;
  }

  private  List<TranslateData> getTranslationsFromUrl(String url) {
    try {
      Document doc = Jsoup.connect(url).get();
      Elements row1 = doc.getElementsByClass("sk-row1");
      Elements row2 = doc.getElementsByClass("sk-row2");
      List<TranslateData> results = getTranslations(row1);
      results.addAll(getTranslations(row2));
      return results;
    } catch (Exception e) {
      //
    }
    return null;

  }


  @Override
  public List<TranslateData> translateFiEng(String keyword) {
    // String url = "http://www.sanakirja.org/search.php?l=17&l2=3&q=" + keyword;
    String url = "http://www.sanakirja.org/search.php?l=17&l2=3&q=" + keyword;
    return getTranslationsFromUrl(url);
  }

  @Override
  public List<TranslateData> translateEngFi(String keyword) {
    //     String url = "http://www.sanakirja.org/search.php?l=3&l2=17&q=" + keyword;
    String url = "http://www.sanakirja.org/search.php?l=3&l2=17&q=" + keyword;
    return getTranslationsFromUrl(url);
  }

}
