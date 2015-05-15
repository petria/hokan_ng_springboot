package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.CommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Petri Airio on 15.5.2015.
 *
 */
public interface CommandHistoryRepository extends JpaRepository<CommandHistory, Long> {
}
