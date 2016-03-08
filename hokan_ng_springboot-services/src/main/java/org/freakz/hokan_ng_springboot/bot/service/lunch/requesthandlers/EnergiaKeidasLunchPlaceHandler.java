package org.freakz.hokan_ng_springboot.bot.service.lunch.requesthandlers;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.freakz.hokan_ng_springboot.bot.enums.LunchDay;
import org.freakz.hokan_ng_springboot.bot.enums.LunchPlace;
import org.freakz.hokan_ng_springboot.bot.models.LunchData;
import org.freakz.hokan_ng_springboot.bot.models.LunchMenu;
import org.freakz.hokan_ng_springboot.bot.service.annotation.LunchPlaceHandler;
import org.freakz.hokan_ng_springboot.bot.service.lunch.LunchRequestHandler;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Petri Airio on 8.3.2016.
 * -
 */
@Component
@Slf4j
public class EnergiaKeidasLunchPlaceHandler implements LunchRequestHandler {

  private static final String[] DAYS = {"MAANANTAI", "TIISTAI", "KESKIVIIKKO", "TORSTAI", "PERJANTAI", "Annos:"};

  public EnergiaKeidasLunchPlaceHandler() {
  }

  private String readMenu(String menuUrl) {
    Parser parser = new AutoDetectParser();
    Metadata metadata = new Metadata();
    ParseContext context = new ParseContext();
    BodyContentHandler handler = new BodyContentHandler(1000000);
    InputStream is;
    try {
      URL oracle = new URL(menuUrl);
      is = oracle.openStream();
      parser.parse(is, handler, metadata, context);
      is.close();
      return handler.toString();

    } catch (SAXException e) {
      e.printStackTrace();
    } catch (TikaException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;

  }

  @Override
  @LunchPlaceHandler(LunchPlace = LunchPlace.LOUNAS_INFO_ENERGIA_KEIDAS)
  public void handleLunchPlace(LunchPlace lunchPlaceRequest, LunchData response, DateTime day) {
    response.setLunchPlace(lunchPlaceRequest);
    String menuText = readMenu(lunchPlaceRequest.getUrl());
    parseMenu(menuText, response);
  }

  private void parseMenu(String menuText, LunchData response) {
    String[] days = DAYS;
    String[] lines = menuText.split("\n");
    int dayIdx = 0;
    int lineIdx = 0;
    while (lineIdx < lines.length) {
      String line = lines[lineIdx];
      lineIdx++;
      if (line.startsWith(days[dayIdx])) {
        LunchDay lunchDay = LunchDay.getFromWeekdayString(days[dayIdx]);
        dayIdx++;
        if (dayIdx == 6) {
          break;
        }
        String lunchForDay = "";
        while (true) {
          line = lines[lineIdx].trim();
          lineIdx++;
          if (line.contains(days[dayIdx])) {
            LunchMenu lunchMenu = new LunchMenu(lunchForDay);
            response.getMenu().put(lunchDay, lunchMenu);
            lineIdx--;
            break;
          } else {
            if (line.length() > 0) {
              lunchForDay += line + "  ";
            }
          }
        }
      }
    }
  }

}
