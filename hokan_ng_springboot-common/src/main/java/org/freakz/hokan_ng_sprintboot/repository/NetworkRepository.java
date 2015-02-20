package org.freakz.hokan_ng_sprintboot.repository;

import org.freakz.hokan_ng_sprintboot.entity.Network;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Petri Airio on 19.2.2015.
 */
public interface NetworkRepository extends JpaRepository<Network, Long> {

  Network findByNetworkName(String networkName);

}
