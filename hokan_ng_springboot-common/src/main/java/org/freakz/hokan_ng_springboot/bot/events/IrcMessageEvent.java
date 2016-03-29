package org.freakz.hokan_ng_springboot.bot.events;

import java.io.Serializable;

/**
 * PircBotUser: petria
 * Date: 11/15/13
 * Time: 12:34 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public class IrcMessageEvent extends IrcEvent implements Serializable, Cloneable {

  private static final long serialVersionUID = 1L;

  private String message;
  private boolean isPrivate;
  private boolean isToMe;
  private boolean isBotOp;

  private String outputPrefix;
  private String outputPostfix;

	public byte[] getOriginal() {
		return original;
	}

	public void setOriginal(byte[] original) {
		this.original = original;
	}

	private byte[] original;

  public IrcMessageEvent() {
    super();
  }

  public IrcMessageEvent(String botNick, String network, String channel, String sender, String login, String hostname, String message) {
    super(botNick, network, channel, sender, login, hostname);
    this.message = message;
  }

  public IrcMessageEvent(IrcMessageEvent toClone) {
    super(toClone);
    this.message = toClone.message;
  }

  @Override
  public Object clone() {
    IrcMessageEvent cloned = new IrcMessageEvent(this);
    return cloned;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isToMe() {
    return isToMe;
  }

  public void setToMe(boolean toMe) {
    isToMe = toMe;
  }

  public boolean isPrivate() {
    return isPrivate;
  }

  public void setPrivate(boolean aPrivate) {
    isPrivate = aPrivate;
  }

  public boolean isBotOp() {
    return isBotOp;
  }

  public void setBotOp(boolean isBotOp) {
    this.isBotOp = isBotOp;
  }

  public String getOutputPrefix() {
    return outputPrefix;
  }

  public void setOutputPrefix(String outputPrefix) {
    this.outputPrefix = outputPrefix;
  }

  public String getOutputPostfix() {
    return outputPostfix;
  }

  public void setOutputPostfix(String outputPostfix) {
    this.outputPostfix = outputPostfix;
  }
}
