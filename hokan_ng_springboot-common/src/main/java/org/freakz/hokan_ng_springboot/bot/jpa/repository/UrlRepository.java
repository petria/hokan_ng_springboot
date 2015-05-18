package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 15.4.2015.
 *
 */
public interface UrlRepository extends JpaRepository<Url, Long> {

  Url findFirstByUrlLikeOrUrlTitleLikeOrderByCreatedDesc(String url, String title);

  List<Url> findByUrlLikeOrUrlTitleLikeOrderByCreatedDesc(String url, String title);

  List<Url> findByUrlLikeOrUrlTitleLikeAndSenderInOrderByCreatedDesc(String url, String title, List<String> senders);

}
