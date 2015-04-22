package org.freakz.hokan_ng_springboot.bot.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: petria
 * Date: 11/25/13
 * Time: 4:27 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Slf4j
public class JarScriptExecutor {

  private static final String SHELL = "/bin/sh";
  private final String scriptName;
  private final String charset;

  public JarScriptExecutor(String scriptName, String charset) {
    this.scriptName = scriptName;
    this.charset = charset;
  }

  public String[] executeJarScript(String... args) {
    log.info("Executing {} with args: {}", scriptName, StringStuff.arrayToString(args, ","));
    InputStream inputStream = this.getClass().getResourceAsStream(scriptName);
    if (inputStream == null) {
      log.error("Couldn't get InputStream for {}", this.scriptName);
      return null;
    }
    try {
      File tmpFile = File.createTempFile(scriptName, "");

      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
      BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile));
      String line;
      while (true) {
        line = br.readLine();
        if (line == null) {
          break;
        }
        bw.write(line + "\n");
      }
      br.close();
      bw.flush();
      bw.close();

      String tmpScriptName = tmpFile.getAbsolutePath();
      String[] cmdArray = new String[2 + args.length];
      cmdArray[0] = SHELL;
      cmdArray[1] = tmpScriptName;
      System.arraycopy(args, 0, cmdArray, 2, args.length);


      Process p = Runtime.getRuntime().exec(cmdArray);
      int ret = p.waitFor();
      log.info("Process {} ended: {}", p, ret);

      br = new BufferedReader(new InputStreamReader(p.getInputStream(), this.charset));

      List<String> output = new ArrayList<>();
      String l;
      do {
        l = br.readLine();
        if (l != null) {
          output.add(l);
        }

      } while (l != null);
      p.destroy();
      log.info("Deleting file: {} -> {}", tmpFile, tmpFile.delete());

      return output.toArray(new String[output.size()]);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return new String[0];
  }

}
