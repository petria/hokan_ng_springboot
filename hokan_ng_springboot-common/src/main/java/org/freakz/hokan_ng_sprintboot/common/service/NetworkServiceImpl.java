package org.freakz.hokan_ng_sprintboot.common.service;

import java.util.List;

import org.freakz.hokan_ng_sprintboot.common.exception.HokanDAOException;
import org.freakz.hokan_ng_sprintboot.common.exception.HokanException;
import org.freakz.hokan_ng_sprintboot.common.jpa.dao.NetworkDAO;
import org.freakz.hokan_ng_sprintboot.common.jpa.entity.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * User: petria
 * Date: 11/7/13
 * Time: 11:44 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Service
@Slf4j
public class NetworkServiceImpl implements NetworkService {

	@Autowired private NetworkDAO networkDAO;

	public NetworkServiceImpl() {
	}

	@Override
	public Network getNetwork(String name) {
		try {
			return networkDAO.getNetwork(name);
		} catch (HokanDAOException e) {
			log.error("Network not found error: {}", name);
		}
		return null;
	}

	@Override
	public List<Network> getNetworks() throws HokanException {
		return networkDAO.getNetworks();
	}

	@Override
	public Network createNetwork(String name) throws HokanException {
		return networkDAO.createNetwork(name);
	}

	@Override
	public Network updateNetwork(Network network) {
		try {
			return networkDAO.updateNetwork(network);
		} catch (HokanDAOException e) {
			log.error("Network update error", e);
		}
		return null;
	}

}
