package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.TvNotify;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 26.4.2015.
 *
 */
public interface TvNotifyRepository extends JpaRepository<TvNotify, Long> {
}
