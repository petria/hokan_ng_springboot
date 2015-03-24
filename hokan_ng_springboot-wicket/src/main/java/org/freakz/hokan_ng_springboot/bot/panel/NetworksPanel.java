package org.freakz.hokan_ng_springboot.bot.panel;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;

import java.util.Iterator;

/**
 * Created by Petri Airio on 24.3.2015.
 */
@Slf4j
public class NetworksPanel extends Panel {

  private Network selectedNetwork;

  final Form<?> form;

  public NetworksPanel(String id) {
    super(id);

    form = new Form("form");
    add(form);

    RefreshingView<Network> refreshingView = new RefreshingView<Network>("simple") {

      @Override
      protected Iterator<IModel<Network>> getItemModels() {
        Iterator<Network> networks = Services.getNetworkService().findAll().iterator();

        return new ModelIteratorAdapter<Network>(networks) {

          @Override
          protected IModel<Network> model(Network network) {
            return new CompoundPropertyModel<Network>(new DetachableNetworkModel(network));
          }
        };
      }

      @Override
      protected void onModelChanged() {
        super.onModelChanged();
        log.info("Changed!!!");
      }

      @Override
      protected void populateItem(Item<Network> item) {
        IModel<Network> network = item.getModel();
        item.add(new ActionPanel(NetworksPanel.this, "actions", network));
        item.add(new TextField<>("id"));
        item.add(new TextField<>("networkName"));
      }

      @Override
      protected Item<Network> newItem(String id, int index, IModel<Network> model) {
        return new OddEvenItem<>(id, index, model);
      }

    };

    refreshingView.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());

    form.add(refreshingView);

  }

  //--

  protected void setSelected(Network network) {
    this.selectedNetwork = network;
  }

  public Network getSelectedNetwork() {
    return selectedNetwork;
  }

}
