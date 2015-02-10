package org.freakz.hokan_ng_sprintboot.common.jpa.dao;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfig;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.IrcServerConfigState;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Network;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Date: 3.6.2013
 * Time: 11:45
 *
 * @author Petri Airio (petri.j.airio@gmail.com)
 */
@Repository(value = "IrcServerConfig")
@Slf4j
@Transactional
public class IrcServerConfigJPADAO implements IrcServerConfigDAO {

  @PersistenceContext
  private EntityManager entityManager;

  public IrcServerConfigJPADAO() {
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<IrcServerConfig> getIrcServerConfigs() throws HokanDAOException {
    try {
      Query query = getEntityManager().createNativeQuery("select * from IrcServerConfig", IrcServerConfig.class);
      return query.getResultList();

    } catch (Exception e) {
//      log.error(e.getMessage(), e);
      throw new HokanDAOException(e.getMessage());

    }
  }

  @Override
  public IrcServerConfig createIrcServerConfig(Network network,
                                               String server,
                                               int port,
                                               String password,
                                               boolean useThrottle,
                                               IrcServerConfigState state) throws HokanDAOException {
    try {
      IrcServerConfig ircServerConfig = new IrcServerConfig();
      ircServerConfig.setNetwork(network);
      ircServerConfig.setServer(server);
      ircServerConfig.setPort(port);
      ircServerConfig.setServerPassword(password);
      ircServerConfig.setUseThrottle(useThrottle ? 1 : 0);

      ircServerConfig.setIrcServerConfigState(state);
      entityManager.persist(ircServerConfig);
      return ircServerConfig;
    } catch (Exception e) {
//      log.error(e.getMessage(), e);
      throw new HokanDAOException(e.getMessage());
    }
  }

  @Override
  public IrcServerConfig updateIrcServerConfig(IrcServerConfig ircServerConfig) throws HokanDAOException {
    try {
      return entityManager.merge(ircServerConfig);
    } catch (Exception e) {
      throw new HokanDAOException(e.getMessage());
    }
  }

}
