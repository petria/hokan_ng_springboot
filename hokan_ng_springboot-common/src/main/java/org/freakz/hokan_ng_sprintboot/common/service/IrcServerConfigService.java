package org.freakz.hokan_ng_sprintboot.common.service;

import java.util.List;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfig;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfigState;

/**
 *
 * Created by petria on 10.2.2015.
 */
public interface IrcServerConfigService {

    List<IrcServerConfig> getIrcServerConfigs();

    IrcServerConfig createIrcServerConfig(String network, String server, int port, String password, boolean useThrottle,
            IrcServerConfigState disconnected) throws HokanException;

    IrcServerConfig updateIrcServerConfig(IrcServerConfig ircServerConfig);


}
