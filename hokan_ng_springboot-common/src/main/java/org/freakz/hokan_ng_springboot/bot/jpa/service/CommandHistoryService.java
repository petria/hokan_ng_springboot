package org.freakz.hokan_ng_springboot.bot.jpa.service;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.CommandHistory;

import java.util.List;

/**
 * Created by Petri Airio on 15.5.2015.
 *
 */
public interface CommandHistoryService {
  CommandHistory save(CommandHistory history);

  List<CommandHistory> findByHokanModule(String module);
}