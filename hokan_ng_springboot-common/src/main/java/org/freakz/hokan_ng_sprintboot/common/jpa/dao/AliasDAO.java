package org.freakz.hokan_ng_sprintboot.common.jpa.dao;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Alias;

import java.util.List;

/**
 * User: petria
 * Date: 1/14/14
 * Time: 11:22 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface AliasDAO {

  Alias createAlias(String alias, String command);

  Alias findAlias(String alias) throws HokanDAOException;

  List<Alias> findAliases() throws HokanDAOException;

  int removeAlias(String alias) throws HokanDAOException;

  Alias updateAlias(Alias alias) throws HokanDAOException;

}
