package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.IrcServerConfig;

import java.util.List;

/**
 *
 * Created by EXTAirioP on 20.2.2015.
 */
public interface IrcServerConfigService {

  List<IrcServerConfig> findAll();

  void delete(IrcServerConfig object);

  IrcServerConfig save(IrcServerConfig object);
}
