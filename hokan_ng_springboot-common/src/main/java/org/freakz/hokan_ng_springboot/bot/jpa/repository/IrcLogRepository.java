package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.IrcLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Petri Airio on 21.8.2015.
 *
 */
public interface IrcLogRepository extends JpaRepository<IrcLog, Long> {

}
