package org.freakz.hokan_ng_springboot.bot.jpa.repository;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *
 * Created by Petri Airio on 19.2.2015.
 */
@Service
@Slf4j
public class RepositoryNetworkService implements NetworkService {

  @Resource
  private NetworkRepository networkRepository;

  @Override
  @Transactional
  public Network create(String networkName) {
    Network network = new Network(networkName);
    return networkRepository.save(network);
  }

  @Override
  @Transactional(readOnly = true)
  public Network getNetwork(String networkName) {
    return networkRepository.findByNetworkName(networkName);
  }


  @Override
  @Transactional
  public void updateNetwork(Network network) {
    Network saved = networkRepository.save(network);
  }

}
