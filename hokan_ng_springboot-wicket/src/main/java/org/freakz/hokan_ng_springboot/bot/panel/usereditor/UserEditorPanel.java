package org.freakz.hokan_ng_springboot.bot.panel.usereditor;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;

/**
 * Created by Petri Airio on 27.3.2015.
 *
 */
public class UserEditorPanel extends Panel {

  public UserEditorPanel(String id, final User user) {
    super(id);
    add(new Label("editUserLabel", user.toString()));

  }

}
