package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.JoinedUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Petri Airio on 1.4.2015.
 */
public interface JoinedUserRepository extends JpaRepository<JoinedUser, Long> {

}
