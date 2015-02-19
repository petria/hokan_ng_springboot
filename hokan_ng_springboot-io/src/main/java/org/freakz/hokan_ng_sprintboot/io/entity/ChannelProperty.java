package org.freakz.hokan_ng_sprintboot.io.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * User: petria
 * Date: 12/10/13
 * Time: 2:04 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Entity
@Table(name = "CHANNELPROPERTIES")
public class ChannelProperty extends PropertyBase implements Serializable {

  @ManyToOne
  @JoinColumn(name = "CHANNEL", referencedColumnName = "ID", nullable = false)
  private Channel channel;

  public ChannelProperty() {
  }

  public ChannelProperty(Channel channel, PropertyName property, String value, String flags) {
    super(property, value, flags);
    this.channel = channel;
  }

  public Channel getChannel() {
    return channel;
  }

  public void setChannel(Channel channel) {
    this.channel = channel;
  }

  public String toString() {
    return String.format("[%s] %s = %s", channel.getChannelName(), getProperty().toString(), getValue());
  }

}
