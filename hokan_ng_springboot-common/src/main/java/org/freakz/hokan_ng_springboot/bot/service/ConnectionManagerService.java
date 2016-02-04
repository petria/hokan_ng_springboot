package org.freakz.hokan_ng_springboot.bot.service;

import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.NotifyRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanServiceException;

/**
 * Date: 3.6.2013
 * Time: 10:39
 *
 * @author Petri Airio (petri.j.airio@gmail.com)
 */
public interface ConnectionManagerService {

  void joinChannels(String network) throws HokanServiceException;

  void connect(String network) throws HokanServiceException;

  void disconnect(String network) throws HokanServiceException;

  void disconnectAll();

  //  Collection<Connector> getConnectors();

  void updateServers();

  void handleEngineResponse(EngineResponse response);

  void handleTvNotifyRequest(NotifyRequest notifyRequest);

  void handleStatsNotifyRequest(NotifyRequest notifyRequest);

  void handleNotifyRequest(NotifyRequest notifyRequest);


}
