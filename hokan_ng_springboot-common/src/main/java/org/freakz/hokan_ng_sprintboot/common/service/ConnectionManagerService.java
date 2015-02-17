package org.freakz.hokan_ng_sprintboot.common.service;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanServiceException;

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

	//  void handleCoreRequest(CoreRequest request);

}
