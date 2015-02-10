package org.freakz.hokan_ng_sprintboot.common.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.dao.AliasDAO;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Alias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: petria
 * Date: 1/14/14
 * Time: 1:36 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Service
@Slf4j
public class AliasServiceImpl implements AliasService {

  @Autowired
  private AliasDAO aliasDAO;

  public AliasServiceImpl() {
  }

  @Override
  public Alias createAlias(String alias, String command) {
    return aliasDAO.createAlias(alias, command);
  }

  @Override
  public Alias findAlias(String alias) {
    try {
      return aliasDAO.findAlias(alias);
    } catch (HokanDAOException e) {
      log.error("Alias", e);
    }
    return null;
  }

  @Override
  public List<Alias> findAliases() {
    try {
      return aliasDAO.findAliases();
    } catch (HokanDAOException e) {
      log.error("Alias", e);
    }
    return null;
  }

  @Override
  public int removeAlias(String alias) {
    try {
      return aliasDAO.removeAlias(alias);
    } catch (HokanDAOException e) {
      log.error("Alias", e);
    }
    return -1;
  }

  @Override
  public Alias updateAlias(Alias alias) {
    try {
      return aliasDAO.updateAlias(alias);
    } catch (HokanDAOException e) {
      log.error("Alias", e);
    }
    return null;
  }
}
