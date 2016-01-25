package org.freakz.hokan_ng_springboot.bot.models;

import java.io.Serializable;

/**
 * Created by Petri Airio on 3.11.2015.
 * -
 */
public class SystemScriptResult implements Serializable {

  private String[] originalOutput;

  private String formattedOutput;

  public SystemScriptResult() {
  }

  public String[] getOriginalOutput() {
    return originalOutput;
  }

  public void setOriginalOutput(String[] originalOutput) {
    this.originalOutput = originalOutput;
  }

  public String getFormattedOutput() {
    return formattedOutput;
  }

  public void setFormattedOutput(String formattedOutput) {
    this.formattedOutput = formattedOutput;
  }
}
