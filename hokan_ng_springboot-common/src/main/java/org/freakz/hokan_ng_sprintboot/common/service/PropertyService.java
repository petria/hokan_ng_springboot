package org.freakz.hokan_ng_sprintboot.common.service;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.ChannelProperty;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Property;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.PropertyName;

import java.util.List;

/**
 * User: petria
 * Date: 11/7/13
 * Time: 10:50 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface PropertyService {

  List<Property> getProperties();

  Property findProperty(PropertyName name) throws HokanException;

  Property setProperty(PropertyName name, String value) throws HokanException;

  Property saveProperty(Property property) throws HokanException;

  ChannelProperty saveChannelProperty(ChannelProperty property) throws HokanException;

  List<ChannelProperty> getChannelProperties(Channel... channel);

  List<Channel> getChannelsWithProperty(PropertyName propertyName);

  ChannelProperty findChannelProperty(Channel channel, PropertyName name) throws HokanException;

  ChannelProperty setChannelProperty(Channel channel, PropertyName property, String value) throws HokanDAOException;
}
