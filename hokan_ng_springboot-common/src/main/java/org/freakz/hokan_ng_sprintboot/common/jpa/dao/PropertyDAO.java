package org.freakz.hokan_ng_sprintboot.common.jpa.dao;


import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.ChannelProperty;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Property;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.PropertyName;

import java.util.List;

/**
 * User: petria
 * Date: 11/7/13
 * Time: 10:51 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface PropertyDAO {

  List<Property> getProperties();

  Property findProperty(PropertyName name) throws HokanDAOException;

  Property setProperty(PropertyName name, String value) throws HokanDAOException;

  Property saveProperty(Property property) throws HokanDAOException;

  List<Channel> getChannelsWithProperty(PropertyName propertyName) throws HokanDAOException;

  List<ChannelProperty> getChannelProperties(Channel... channel) throws HokanDAOException;

  ChannelProperty findChannelProperty(Channel channel, PropertyName name) throws HokanDAOException;

  ChannelProperty setChannelProperty(Channel channel, PropertyName name, String value) throws HokanDAOException;

  ChannelProperty saveChannelProperty(ChannelProperty property) throws HokanDAOException;

}
