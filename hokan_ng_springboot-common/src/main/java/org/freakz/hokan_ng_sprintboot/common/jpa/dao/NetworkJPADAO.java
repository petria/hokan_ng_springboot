package org.freakz.hokan_ng_sprintboot.common.jpa.dao;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Network;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * User: petria
 * Date: 11/7/13
 * Time: 11:23 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Repository(value = "Network")
@Transactional
public class NetworkJPADAO implements NetworkDAO {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Network getNetwork(String name) throws HokanDAOException {
    Network network = entityManager.find(Network.class, name);
    if (network == null) {
      throw new HokanDAOException("Network not found: " + name);
    }
    return network;
  }

  @Override
  public List<Network> getNetworks() throws HokanDAOException {
    try {
      TypedQuery<Network> query = entityManager.createQuery("SELECT n FROM Network n ORDER BY n.networkName", Network.class);
      return query.getResultList();
    } catch (Exception e) {
      throw new HokanDAOException(e.getMessage());
    }
  }

  @Override
  public Network createNetwork(String name) throws HokanDAOException {
    try {
      Network network = new Network(name);
      entityManager.persist(network);
      return network;
    } catch (Exception e) {
      throw new HokanDAOException(e.getMessage());
    }
  }

  @Override
  public Network updateNetwork(Network network) throws HokanDAOException {
    try {
      return entityManager.merge(network);
    } catch (Exception e) {
      throw new HokanDAOException(e.getMessage());
    }
  }

}
