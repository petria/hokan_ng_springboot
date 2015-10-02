package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.util.FileUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_NIMI_OR_PVM;

/**
 * User: petria
 * Date: 1/13/14
 * Time: 12:36 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Scope("prototype")
@Slf4j
public class NimipaivaCmd extends Cmd {

  private static final String NIMIPAIVAT_TXT = "/Nimipaivat.txt";
  private static final int PVM_MODE = 0;
  private static final int NAME_MODE = 1;

  private List<String> nimiPvmList = new ArrayList<>();

  public NimipaivaCmd() {
    super();
    setHelp("Nimipäivät");

    UnflaggedOption flg = new UnflaggedOption(ARG_NIMI_OR_PVM)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(flg);

  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {

    if (nimiPvmList == null || nimiPvmList.size() == 0) {
      FileUtil fileUtil = new FileUtil();
      StringBuilder contents = new StringBuilder();
      try {
        fileUtil.copyResourceToTmpFile(NIMIPAIVAT_TXT, contents);
        this.nimiPvmList = Arrays.asList(contents.toString().split("\n"));


        String nimiOrPvm = results.getString(ARG_NIMI_OR_PVM);
        int mode;
        if (nimiOrPvm.matches("\\d+\\.\\d+\\.")) {
          mode = PVM_MODE;
        } else {
          mode = NAME_MODE;
        }
        log.debug("Mode: {}", mode);
        for (String nimiPvm : nimiPvmList) {
          if (mode == NAME_MODE) {
            if (nimiPvm.toLowerCase().contains(nimiOrPvm.toLowerCase())) {
              response.addResponse("%s\n", nimiPvm);
            }
          } else {
            if (nimiPvm.startsWith(nimiOrPvm)) {
              response.addResponse("%s\n", nimiPvm);
            }
          }
        }
      } catch (IOException e) {
        throw new HokanException("Nimipaivat.txt", e);
      }
    }
  }

}
