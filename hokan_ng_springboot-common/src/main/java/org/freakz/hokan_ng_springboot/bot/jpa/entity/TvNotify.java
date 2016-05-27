package org.freakz.hokan_ng_springboot.bot.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: petria
 * Date: 2/24/12
 * Time: 5:53 PM
 */
@Entity
@Table(name = "TV_NOTIFY")
public class TvNotify implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private long Id;

  @ManyToOne
  @JoinColumn(name = "CHANNEL_ID_FK", referencedColumnName = "ID", nullable = false)
  private Channel channel;

  @Column(name = "NOTIFY_PATTERN")
  private String notifyPattern;

  @Column(name = "NOTIFY_DESCRIPTION")
  private String notifyDescription;

  @Column(name = "NOTIFY_OWNER")
  private String notifyOwner;

  @Column(name = "ONLY_ONCE")
  private int onlyOnce;

  public TvNotify() {
  }

  public TvNotify(String notifyPattern, String notifyOwner, Channel channel, boolean once) {
    this.notifyPattern = notifyPattern;
    this.notifyOwner = notifyOwner;
    this.channel = channel;
    if (once) {
      this.onlyOnce = 1;
    } else {
      this.onlyOnce = 0;
    }
  }

  public long getId() {
    return Id;
  }

  public void setId(long id) {
    Id = id;
  }

  public Channel getChannel() {
    return channel;
  }

  public void setChannel(Channel channel) {
    this.channel = channel;
  }

  public String getNotifyPattern() {
    return notifyPattern;
  }

  public void setNotifyPattern(String notifyPattern) {
    this.notifyPattern = notifyPattern;
  }

  public String getNotifyDescription() {
    return notifyDescription;
  }

  public void setNotifyDescription(String notifyDescription) {
    this.notifyDescription = notifyDescription;
  }

  public String getNotifyOwner() {
    return notifyOwner;
  }

  public void setNotifyOwner(String notifyOwner) {
    this.notifyOwner = notifyOwner;
  }

  public int getOnlyOnce() {
    return onlyOnce;
  }

  public void setOnlyOnce(int onlyOnce) {
    this.onlyOnce = onlyOnce;
  }

}
