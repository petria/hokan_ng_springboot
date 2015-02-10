package org.freakz.hokan_ng_sprintboot.common.jpa.dao;


import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.TvNotify;

import java.util.List;

/**
 * User: petria
 * Date: 12/11/13
 * Time: 6:27 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface TvNotifyDAO {

  TvNotify addTvNotify(Channel channel, String pattern, String owner);

  List<TvNotify> getTvNotifies(Channel channel) throws HokanDAOException;

  TvNotify getTvNotify(Channel channel, String pattern) throws HokanDAOException;

  TvNotify getTvNotifyById(long id);

  int delTvNotifies(Channel channel);

  void delTvNotify(TvNotify notify);
}
