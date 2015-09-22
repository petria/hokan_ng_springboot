package org.freakz.hokan_ng_springboot.bot.jpa.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Url;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.UrlRepository;
import org.freakz.hokan_ng_springboot.bot.models.StartAndEndTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 15.4.2015.
 *
 */
@Service
@Slf4j
public class UrlRepositoryLoggerService implements UrlLoggerService {

  @Autowired
  private UrlRepository repository;

  @Override
  public List<Url> findByUrl(String url) {
    String likeUrl = "%" + url + "%";
    return repository.findByUrlLikeOrUrlTitleLikeOrderByCreatedDesc(likeUrl, likeUrl);
  }

  @Override
  public List<Url> findByUrlAndNicks(String url, String... nicks) {
    List<String> nickList = Arrays.asList(nicks);
    String likeUrl = "%" + url + "%";
    return repository.findByUrlLikeOrUrlTitleLikeAndSenderInOrderByCreatedDesc(likeUrl, likeUrl, nickList);
  }

  @Override
  public List<Url> findAll() {
    return repository.findAll();
  }

  @Override
  public Url findOne(long id) {
    return repository.findOne(id);
  }

  @Override
  public List findTopSender() {
    return repository.findTopSender();
  }

  @Override
  public List findTopSenderByChannel(String channel) {
    return repository.findTopSenderByChannel(channel);
  }

  @Override
  public List<Url> findByCreatedBetweenAndChannel(StartAndEndTime saet, String channel) {
    return repository.findByCreatedBetweenAndChannel(saet.getStartTime().toDate(), saet.getEndTime().toDate(), channel);
  }

}
