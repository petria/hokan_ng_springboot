package org.freakz.hokan_ng_springboot.bot.jpa.entity;

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

  @Column(name = "SESSION_ID")
  private long sessionId;


  @Column(name = "START_TIME")
  private Date startTime;

  @Column(name = "END_TIME")
  private Date endTime;

  @Column(name = "HOKAN_MODULE")
  private String hokanModule;

  @Column(name = "PID")
  private long pid;

  @Column(name = "ARGS")
  private String args;

  @Column(name = "RUNNABLE")
  private String runnable;

  @Column(name = "COMMAND_STATUS", nullable = false)
  @Enumerated(EnumType.STRING)
  private CommandStatus status;

  @Column(name = "STARTED_BY")
  private String startedBy;

  @Column(name = "ERROR_EXCEPTION", length = 4096)
  private String errorException;


  public CommandHistory() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getSessionId() {
    return sessionId;
  }

  public void setSessionId(long sessionId) {
    this.sessionId = sessionId;
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

  public String getHokanModule() {
    return hokanModule;
  }

  public void setHokanModule(String hokanModule) {
    this.hokanModule = hokanModule;
  }

  public CommandStatus getStatus() {
    return status;
  }

  public void setStatus(CommandStatus status) {
    this.status = status;
  }

  public String getStartedBy() {
    return startedBy;
  }

  public void setStartedBy(String startedBy) {
    this.startedBy = startedBy;
  }

  public String getErrorException() {
    return errorException;
  }

  public void setErrorException(String errorException) {
    this.errorException = errorException;
  }
}
