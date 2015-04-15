package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 15.4.2015.
 *
 */
public interface UrlRepository extends JpaRepository<Url, Long> {

  Url findByUrlLikeOrUrlTitleLikeOrderByCreatedDesc(String url, String title);

}
