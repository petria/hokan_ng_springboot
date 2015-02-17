package org.freakz.hokan_ng_sprintboot.common.service;

import java.util.List;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanException;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Network;

/**
 * User: petria
 * Date: 11/7/13
 * Time: 11:43 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
public interface NetworkService {

	Network getNetwork(String name);

	List<Network> getNetworks() throws HokanException;

	Network createNetwork(String name) throws HokanException;

	Network updateNetwork(Network network);

}
