package org.freakz.hokan_ng_springboot.bot.panel;

import org.apache.wicket.model.LoadableDetachableModel;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;

/**
 * Created by Petri Airio on 24.3.2015.
 */

public class DetachableNetworkModel extends LoadableDetachableModel<Network> {

  private final long id;

  public DetachableNetworkModel(Network network) {
    this(network.getId());
  }

  public DetachableNetworkModel(long id) {
    this.id = id;
  }

  @Override
  protected Network load() {
    return Services.getNetworkService().findOne(this.id);
  }

}
