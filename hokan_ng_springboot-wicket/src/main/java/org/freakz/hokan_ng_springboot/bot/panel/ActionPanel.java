package org.freakz.hokan_ng_springboot.bot.panel;

import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;

/**
 * Created by Petri Airio on 24.3.2015.
 */
public class ActionPanel extends Panel {

  private NetworksPanel parent;

  public ActionPanel(NetworksPanel parent, String id, IModel<Network> model) {
    super(id, model);
    this.parent = parent;
    add(new Link("select") {
      @Override
      public void onClick() {
        parent.setSelected((Network) ActionPanel.this.getDefaultModelObject());
      }
    });

    SubmitLink removeLink = new SubmitLink("remove", parent.form) {
      @Override
      public void onSubmit() {
        Network network = (Network) ActionPanel.this.getDefaultModelObject();
        info("Remove network " + network);
      }
    };
    removeLink.setDefaultFormProcessing(false);
    add(removeLink);
  }

}
