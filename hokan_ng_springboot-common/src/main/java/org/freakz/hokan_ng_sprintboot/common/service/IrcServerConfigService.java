package org.freakz.hokan_ng_sprintboot.common.service;

import java.util.List;

import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfig;

/**
 *
 * Created by petria on 10.2.2015.
 */
public interface IrcServerConfigService {

  List<IrcServerConfig> findAll();

}
