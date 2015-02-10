package org.freakz.hokan_ng_sprintboot.common.jpa.dao;


import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Network;

import java.util.List;

/**
 * User: petria
 * Date: 11/7/13
 * Time: 11:22 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface NetworkDAO {

  Network getNetwork(String name) throws HokanDAOException;

  List<Network> getNetworks() throws HokanDAOException;

  Network createNetwork(String name) throws HokanDAOException;

  Network updateNetwork(Network network) throws HokanDAOException;
}
