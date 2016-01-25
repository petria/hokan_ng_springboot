package org.freakz.hokan_ng_springboot.bot.panel;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;

/**
 * Created by Petri Airio on 24.3.2015.
 *
 */
@Slf4j
public class ActionPanel extends Panel {

  private NetworksPanel parent;

  public ActionPanel(NetworksPanel parent, String id, IModel<Network> model) {
    super(id, model);
    this.parent = parent;
    SubmitLink saveLink = new SubmitLink("save", parent.form) {
      @Override
      public void onSubmit() {
        Network network = (Network) ActionPanel.this.getDefaultModelObject();
        network = Services.getNetworkService().save(network);
        info("Saved network " + network);
        log.debug("Saved: {}", network);
      }
    };
    saveLink.setDefaultFormProcessing(false);
    add(saveLink);


    SubmitLink removeLink = new SubmitLink("remove", parent.form) {
      @Override
      public void onSubmit() {
        Network network = (Network) ActionPanel.this.getDefaultModelObject();
        Services.getNetworkService().delete(network);
        info("Remove network " + network);
        log.debug("remove");
      }
    };
    removeLink.setDefaultFormProcessing(false);
    add(removeLink);
  }

}
