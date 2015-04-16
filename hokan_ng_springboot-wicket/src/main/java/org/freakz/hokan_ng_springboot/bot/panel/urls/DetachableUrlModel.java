package org.freakz.hokan_ng_springboot.bot.panel.urls;

import org.apache.wicket.model.LoadableDetachableModel;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Url;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 16.4.2015.
 *
 */
public class DetachableUrlModel extends LoadableDetachableModel<Url> {

  private final long id;

  public DetachableUrlModel(Url object) {
    this(object.getId());
  }

  public DetachableUrlModel(long id) {
    this.id = id;
  }

  @Override
  protected Url load() {
    return Services.getUrlLoggerService().findOne(this.id);
  }

}
