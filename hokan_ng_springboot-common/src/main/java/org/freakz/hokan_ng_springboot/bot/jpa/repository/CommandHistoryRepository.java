package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.CommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Petri Airio on 15.5.2015.
 *
 */
public interface CommandHistoryRepository extends JpaRepository<CommandHistory, Long> {

  List<CommandHistory> findByHokanModule(String module);

}
