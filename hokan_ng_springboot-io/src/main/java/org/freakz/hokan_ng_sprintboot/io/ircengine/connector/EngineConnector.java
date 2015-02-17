package org.freakz.hokan_ng_sprintboot.io.ircengine.connector;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfig;
import org.freakz.hokan_ng_sprintboot.io.ircengine.HokanCore;

/**
 * User: petria
 * Date: 11/4/13
 * Time: 7:17 PM
 *
 * @author Petri Airio (petri.j.airio@gmail.com)
 */
public interface EngineConnector {

	void engineConnectorGotOnline(Connector connector, HokanCore engine) throws HokanException;

	void engineConnectorTooManyConnectAttempts(Connector connector, IrcServerConfig configuredServer);

	void engineConnectorTooManyConnections(Connector connector, IrcServerConfig configuredServer);

	void engineConnectorDisconnected(HokanCore engine);

	void engineConnectorPingTimeout(HokanCore hokanCore);

	void engineConnectorExcessFlood(HokanCore hokanCore);

}
