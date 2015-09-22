package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import org.freakz.hokan_ng_springboot.bot.jpa.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 15.4.2015.
 *
 */
public interface UrlRepository extends JpaRepository<Url, Long> {

  Url findFirstByUrlLikeOrUrlTitleLikeOrderByCreatedDesc(String url, String title);

  List<Url> findByUrlLikeOrUrlTitleLikeOrderByCreatedDesc(String url, String title);

  List<Url> findByUrlLikeOrUrlTitleLikeAndSenderInOrderByCreatedDesc(String url, String title, List<String> senders);

  List<Url> findByCreatedBetweenAndChannel(Date start, Date end, String channel);

  @Query("SELECT url, count(url) FROM Url url GROUP BY url.sender ORDER BY 2 DESC")
  List findTopSender();

  @Query("SELECT url, count(url) FROM Url url WHERE url.channel = ?1 GROUP BY url.sender ORDER BY 2 DESC")
  List findTopSenderByChannel(String channel);

}
