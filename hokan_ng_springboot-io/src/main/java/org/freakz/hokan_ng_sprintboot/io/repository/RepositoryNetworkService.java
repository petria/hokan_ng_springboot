package org.freakz.hokan_ng_sprintboot.io.repository;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.io.entity.Network;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by JohnDoe on 19.2.2015.
 */
@Service
@Slf4j
public class RepositoryNetworkService implements NetworkService {

  @Resource
  private NetworkRepository networkRepository;


  @Override
  public Network create() {
    Network network = new Network();
    network.setName("Test");
    return networkRepository.save(network);
  }
}
