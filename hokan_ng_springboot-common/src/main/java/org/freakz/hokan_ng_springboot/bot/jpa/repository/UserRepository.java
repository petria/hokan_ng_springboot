package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Petri Airio on 11.3.2015.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
