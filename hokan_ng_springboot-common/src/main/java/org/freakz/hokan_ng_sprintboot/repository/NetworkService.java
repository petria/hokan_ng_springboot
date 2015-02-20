package org.freakz.hokan_ng_sprintboot.repository;

import org.freakz.hokan_ng_sprintboot.entity.Network;

/**
 * Created by Petri Airio on 10.2.2015.
 */
public interface NetworkService {

  Network create(String networkName);

  Network getNetwork(String networkName);

  void updateNetwork(Network network);
}
