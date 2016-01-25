package org.freakz.hokan_ng_springboot.bot.jpa.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.TvNotify;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.TvNotifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 26.4.2015.
 *
 */
@Service
@Slf4j
public class RepositoryTvNotifyService implements TvNotifyService {

  @Autowired
  private TvNotifyRepository repository;

  @Override
  public TvNotify addTvNotify(Channel channel, String pattern, String owner) {
    TvNotify notify = new TvNotify(pattern, owner, channel, false);
    notify.setNotifyDescription("");
    return repository.save(notify);
  }

  @Override
  public List<TvNotify> getTvNotifies(Channel channel) {
    return repository.findByChannel(channel);
  }

  @Override
  public TvNotify getTvNotify(Channel channel, String notifyPattern) {
    return repository.findFirstByChannelAndNotifyPattern(channel, notifyPattern);
  }

  @Override
  public TvNotify getTvNotifyById(long id) {
    return repository.findOne(id);
  }

  @Override
  public int delTvNotifies(Channel channel) {
    repository.delete(getTvNotifies(channel));
    return 0;
  }

  @Override
  public void delTvNotify(TvNotify notify) {
    repository.delete(notify);
  }

/*  @Override
  public List<TelkkuProgram> getChannelDailyNotifiedPrograms(Channel channel, Date day) {
    return null;
  }*/

}
