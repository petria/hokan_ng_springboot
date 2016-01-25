package org.freakz.hokan_ng_springboot.bot.panel.users;

import org.apache.wicket.model.LoadableDetachableModel;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 16.4.2015.
 *
 */
public class DetachableUserModel extends LoadableDetachableModel<User> {

  private final long id;

  public DetachableUserModel(User object) {
    this(object.getId());
  }

  public DetachableUserModel(long id) {
    this.id = id;
  }

  @Override
  protected User load() {
    return Services.getUserService().findById(this.id);
  }

}
