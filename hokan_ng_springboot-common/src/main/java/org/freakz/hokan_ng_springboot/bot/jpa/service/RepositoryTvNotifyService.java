package org.freakz.hokan_ng_springboot.bot.jpa.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.TvNotify;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.TvNotifyRepository;
import org.freakz.hokan_ng_springboot.bot.models.TelkkuProgram;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 26.4.2015.
 *
 */
@Service
@Slf4j
public class RepositoryTvNotifyService implements TvNotifyService {

  private TvNotifyRepository repository;

  @Override
  public TvNotify addTvNotify(Channel channel, String pattern, String owner) {
    return null;
  }

  @Override
  public List<TvNotify> getTvNotifies(Channel channel) {
    return null;
  }

  @Override
  public TvNotify getTvNotify(Channel channel, String pattern) {
    return null;
  }

  @Override
  public TvNotify getTvNotifyById(long id) {
    return repository.findOne(id);
  }

  @Override
  public int delTvNotifies(Channel channel) {
    return 0;
  }

  @Override
  public void delTvNotify(TvNotify notify) {
    repository.delete(notify);
  }

  @Override
  public List<TelkkuProgram> getChannelDailyNotifiedPrograms(Channel channel, Date day) {
    return null;
  }
}
