package org.freakz.hokan_ng_sprintboot.common.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.User;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.UserChannel;
import org.springframework.stereotype.Repository;

/**
 * User: petria
 * Date: 11/9/13
 * Time: 4:05 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Repository("UserChannel")
//@Transactional
public class UserChannelJPADAO implements UserChannelDAO {

  @PersistenceContext
  private EntityManager entityManager;

  public UserChannelJPADAO() {
  }

  @Override
  public UserChannel createUserChannel(User user, Channel channel) throws HokanDAOException {
    try {
      UserChannel userChannel = new UserChannel(user, channel);
      entityManager.persist(userChannel);
      return userChannel;
    } catch (Exception e) {
      throw new HokanDAOException(e);
    }
  }

  @Override
  public UserChannel getUserChannel(User user, Channel channel) throws HokanDAOException {
    try {
      TypedQuery<UserChannel> query
          = entityManager.createQuery("SELECT userChannel FROM UserChannel userChannel WHERE userChannel.user = :user AND userChannel.channel = :channel", UserChannel.class);
      query.setParameter("user", user);
      query.setParameter("channel", channel);
      return query.getSingleResult();
    } catch (Exception e) {
      throw new HokanDAOException(e);
    }
  }

  @Override
  public List<UserChannel> findUserChannels(User user) throws HokanDAOException {
    try {
      TypedQuery<UserChannel> query
          = entityManager.createQuery("SELECT userChannel FROM UserChannel userChannel WHERE userChannel.user = :user ORDER BY userChannel.lastMessageTime DESC", UserChannel.class);
      query.setParameter("user", user);
      return query.getResultList();
    } catch (Exception e) {
      throw new HokanDAOException(e);
    }
  }

  @Override
  public UserChannel storeUserChannel(UserChannel userChannel) {
    return entityManager.merge(userChannel);
  }

}
