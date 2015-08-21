package org.freakz.hokan_ng_springboot.bot.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Petri Airio on 21.8.2015.
 *
 */
@Entity
@Table(name = "IRC_LOG")
public class IrcLog  implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private long id;

  @Column(name = "TIME_STAMP")
  private Date timeStamp;

  @Column(name = "SENDER")
  private String sender;

  @Column(name = "TARGET")
  private String target;

  @Column(name = "MESSAGE", length = 1024)
  private String message;

  public IrcLog() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
