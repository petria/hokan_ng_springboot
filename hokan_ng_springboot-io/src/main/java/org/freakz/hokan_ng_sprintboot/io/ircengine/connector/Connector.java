package org.freakz.hokan_ng_sprintboot.io.ircengine.connector;

import org.freakz.hokan_ng_sprintboot.entity.IrcServerConfig;

/**
 * User: petria
 * Date: 11/4/13
 * Time: 7:14 PM
 *
 * @author Petri Airio (petri.j.airio@gmail.com)
 */
public interface Connector {

	void connect(String nick, EngineConnector engineConnector, IrcServerConfig configuredServer);

	void abortConnect();

}
