package org.freakz.hokan_ng_sprintboot.common.jpa.dao;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Channel;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.JoinedUser;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Network;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * User: petria
 * Date: 11/13/13
 * Time: 3:04 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Repository("ChannelUsers")
@Transactional
@Slf4j
public class JoinedUsersJPADAO implements JoinedUsersDAO {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public JoinedUser createJoinedUser(Channel channel, User user, String userModes) throws HokanDAOException {
    try {
      JoinedUser joinedUser = new JoinedUser(channel, user, userModes);
      this.entityManager.persist(joinedUser);
      return joinedUser;
    } catch (Exception e) {
      throw new HokanDAOException(e);
    }
  }

  @Override
  public JoinedUser getJoinedUser(Channel channel, User user) throws HokanDAOException {
    try {
      TypedQuery<JoinedUser> query = this.entityManager.createQuery("SELECT cu FROM JoinedUser cu WHERE cu.channel = :channel AND cu.user = :user", JoinedUser.class);
      query.setParameter("channel", channel);
      query.setParameter("user", user);
      return query.getSingleResult();
    } catch (Exception e) {
      throw new HokanDAOException(e);
    }
  }

  @Override
  public JoinedUser getJoinedUser(Channel channel, String nick) throws HokanDAOException {
    try {
      TypedQuery<JoinedUser> query = this.entityManager.createQuery("SELECT cu FROM JoinedUser cu WHERE cu.channel = :channel AND cu.user.nick = :nick", JoinedUser.class);
      query.setParameter("channel", channel);
      query.setParameter("nick", nick);
      return query.getSingleResult();
    } catch (Exception e) {
      throw new HokanDAOException(e);
    }
  }


  @Override
  public void removeJoinedUser(Channel channel, User user) throws HokanDAOException {
    try {
      Query query = this.entityManager.createQuery("DELETE FROM JoinedUser cu WHERE cu.channel = :channel AND cu.user = :user");
      query.setParameter("channel", channel);
      query.setParameter("user", user);
      int res = query.executeUpdate();
      JoinedUsersJPADAO.log.info("Removed {} JoinedUser", res);
    } catch (Exception e) {
      throw new HokanDAOException(e);
    }
  }

  @Override
  public void removeJoinedUser(User user) throws HokanDAOException {
    try {
      Query query = this.entityManager.createQuery("DELETE FROM JoinedUser cu WHERE cu.user = :user");
      query.setParameter("user", user);
      int res = query.executeUpdate();
      JoinedUsersJPADAO.log.info("Removed {} JoinedUser", res);
    } catch (Exception e) {
      throw new HokanDAOException(e);
    }
  }

  @Override
  public List<JoinedUser> findJoinedUsers(Channel channel) throws HokanDAOException {
    try {
      TypedQuery<JoinedUser> query = this.entityManager.createQuery("SELECT cu FROM JoinedUser cu WHERE cu.channel = :channel", JoinedUser.class);
      query.setParameter("channel", channel);
      return query.getResultList();
    } catch (Exception e) {
      throw new HokanDAOException(e);
    }
  }

  @Override
  public List<JoinedUser> findJoinedUsers(Network network) throws HokanDAOException {
    try {
      TypedQuery<JoinedUser> query = this.entityManager.createQuery("SELECT cu FROM JoinedUser cu WHERE cu.channel.network = :network", JoinedUser.class);
      query.setParameter("network", network);
      return query.getResultList();
    } catch (Exception e) {
      throw new HokanDAOException(e);
    }
  }

  @Override
  public void clearJoinedUsers(Channel channel) throws HokanDAOException {
    try {
      Query query = this.entityManager.createQuery("DELETE FROM JoinedUser cu WHERE cu.channel = :channel");
      query.setParameter("channel", channel);
      int res = query.executeUpdate();
      JoinedUsersJPADAO.log.info("Removed {} JoinedUser", res);
    } catch (Exception e) {
      throw new HokanDAOException(e);
    }
  }

  @Override
  public void clearJoinedUsers() throws HokanDAOException {
    try {
      Query query = this.entityManager.createQuery("DELETE FROM JoinedUser cu");
      int res = query.executeUpdate();
      JoinedUsersJPADAO.log.info("Removed {} JoinedUser", res);
    } catch (Exception e) {
      throw new HokanDAOException(e);
    }
  }

}
