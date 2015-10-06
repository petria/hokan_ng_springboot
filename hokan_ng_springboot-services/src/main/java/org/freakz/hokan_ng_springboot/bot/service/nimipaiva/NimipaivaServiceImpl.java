package org.freakz.hokan_ng_springboot.bot.service.nimipaiva;

/**
 * Created by Petri Airio on 5.10.2015.
 *
 */

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.util.FileUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class NimipaivaServiceImpl implements NimipaivaService {

  private static final String NIMIPAIVAT_TXT = "/Nimipaivat.txt";

  @PostConstruct
  public void loadNames() {
    FileUtil fileUtil = new FileUtil();
    StringBuilder contents = new StringBuilder();
    try {
      fileUtil.copyResourceToTmpFile(NIMIPAIVAT_TXT, contents);
      String[] rows = contents.toString().split("\n");
      for (String row : rows) {
        String[] split1 = row.split("\\. ");
        if (split1.length == 2) {
          String[] date = split1[0].split("\\.");
          DateTime dateTime = DateTime.now().withDayOfMonth(Integer.parseInt(date[0])).withMonthOfYear(Integer.parseInt(date[1]));
          String[] names = split1[1].split(",");
          int id = 0;
        }


      }
    } catch (IOException e) {
      log.error("Can't load {}", NIMIPAIVAT_TXT);
    }
  }

  @Override
  public List<String> getNamesForDay(DateTime day) {
    return null;
  }

  @Override
  public DateTime findDayForName(String name) {
    return null;
  }
}
