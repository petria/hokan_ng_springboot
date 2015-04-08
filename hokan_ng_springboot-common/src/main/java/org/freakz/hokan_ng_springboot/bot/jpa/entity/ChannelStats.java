package org.freakz.hokan_ng_springboot.bot.jpa.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Petri Airio on 8.4.2015.
 */
@Entity
@Table(name = "CHANNEL_STATS")
public class ChannelStats {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private long id;

  @OneToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "CHANNEL", referencedColumnName = "ID", nullable = false)
  private Channel channel;

  @Column(name = "FIRST_JOINED")
  private Date firstJoined;

  @Column(name = "MAX_USER_COUNT")
  private int maxUserCount;

  @Column(name = "MAX_USER_COUNT_DATE")
  private Date maxUserCountDate;

  @Column(name = "LINES_SENT")
  private int linesSent;

  @Column(name = "LINES_RECEIVED")
  private int linesReceived;

  @Column(name = "LAST_ACTIVE")
  private Date lastActive;

  @Column(name = "LAST_MESSAGE")
  private String lastMessage;

  @Column(name = "LAST_WRITER")
  private String lastWriter;

  @Column(name = "LAST_WRITER_SPREE")
  private int lastWriterSpree;

  @Column(name = "WRITER_SPREE_RECORD")
  private int writerSpreeRecord;

  @Column(name = "WRITER_SPREE_OWNER")
  private String writerSpreeOwner;

  @Column(name = "COMMANDS_HANDLED")
  private int commandsHandled;

  @Column(name = "TOPIC_KEEP")
  private String topicKeep;

  @Column(name = "TOPIC_SET")
  private String topicSet;

  @Column(name = "TOPIC_SET_BY")
  private String topicSetBy;

  @Column(name = "TOPIC_SET_DATE")
  private Date topicSetDate;

  public ChannelStats() {
    Date d = new Date();
    this.firstJoined = d;
    this.maxUserCount = 0;
    this.maxUserCountDate = d;
    this.linesSent = 0;
    this.linesReceived = 0;
    this.lastActive = d;
    this.lastMessage = "";
    this.lastWriter = "";
    this.lastWriterSpree = 0;
    this.writerSpreeRecord = 0;
    this.commandsHandled = 0;
    this.topicKeep = "";
    this.topicSet = "";
    this.topicSetBy = "";
    this.topicSetDate = d;

  }

  public ChannelStats(Channel channel) {
    this();
    this.channel = channel;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Channel getChannel() {
    return channel;
  }

  public void setChannel(Channel channel) {
    this.channel = channel;
  }

  public Date getFirstJoined() {
    return firstJoined;
  }

  public void setFirstJoined(Date firstJoined) {
    this.firstJoined = firstJoined;
  }

  public int getMaxUserCount() {
    return maxUserCount;
  }

  public void setMaxUserCount(int maxUserCount) {
    this.maxUserCount = maxUserCount;
  }

  public Date getMaxUserCountDate() {
    return maxUserCountDate;
  }

  public void setMaxUserCountDate(Date maxUserCountDate) {
    this.maxUserCountDate = maxUserCountDate;
  }

  public int getLinesSent() {
    return linesSent;
  }

  public void setLinesSent(int linesSent) {
    this.linesSent = linesSent;
  }

  public int getLinesReceived() {
    return linesReceived;
  }

  public void setLinesReceived(int linesReceived) {
    this.linesReceived = linesReceived;
  }

  public Date getLastActive() {
    return lastActive;
  }

  public void setLastActive(Date lastActive) {
    this.lastActive = lastActive;
  }

  public String getLastMessage() {
    return lastMessage;
  }

  public void setLastMessage(String lastMessage) {
    this.lastMessage = lastMessage;
  }

  public String getLastWriter() {
    return lastWriter;
  }

  public void setLastWriter(String lastWriter) {
    this.lastWriter = lastWriter;
  }

  public int getLastWriterSpree() {
    return lastWriterSpree;
  }

  public void setLastWriterSpree(int lastWriterSpree) {
    this.lastWriterSpree = lastWriterSpree;
  }

  public int getWriterSpreeRecord() {
    return writerSpreeRecord;
  }

  public void setWriterSpreeRecord(int writerSpreeRecord) {
    this.writerSpreeRecord = writerSpreeRecord;
  }

  public String getWriterSpreeOwner() {
    return writerSpreeOwner;
  }

  public void setWriterSpreeOwner(String writerSpreeOwner) {
    this.writerSpreeOwner = writerSpreeOwner;
  }

  public int getCommandsHandled() {
    return commandsHandled;
  }

  public void setCommandsHandled(int commandsHandled) {
    this.commandsHandled = commandsHandled;
  }

  public String getTopicKeep() {
    return topicKeep;
  }

  public void setTopicKeep(String topicKeep) {
    this.topicKeep = topicKeep;
  }

  public String getTopicSet() {
    return topicSet;
  }

  public void setTopicSet(String topicSet) {
    this.topicSet = topicSet;
  }

  public String getTopicSetBy() {
    return topicSetBy;
  }

  public void setTopicSetBy(String topicSetBy) {
    this.topicSetBy = topicSetBy;
  }

  public Date getTopicSetDate() {
    return topicSetDate;
  }

  public void setTopicSetDate(Date topicSetDate) {
    this.topicSetDate = topicSetDate;
  }

  public void addToLinesReceived(int i) {
    this.linesReceived += i;
  }


}
