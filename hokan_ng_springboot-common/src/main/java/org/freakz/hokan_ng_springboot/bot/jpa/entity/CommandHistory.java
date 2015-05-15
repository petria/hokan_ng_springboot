package org.freakz.hokan_ng_springboot.bot.jpa.entity;

import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Petri Airio on 15.5.2015.
 *
 */
@Entity
@Table(name = "COMMAND_HISTORY")
public class CommandHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private long id;

  private Date startTime;

  private Date endTime;

  private HokanModule hokanModule;

  private long pid;

  private String args;

  private String runnable;

  public CommandHistory() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public long getPid() {
    return pid;
  }

  public void setPid(long pid) {
    this.pid = pid;
  }

  public String getArgs() {
    return args;
  }

  public void setArgs(String args) {
    this.args = args;
  }

  public String getRunnable() {
    return runnable;
  }

  public void setRunnable(String runnable) {
    this.runnable = runnable;
  }
}
