package org.freakz.hokan_ng_springboot.bot.panel;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;
import org.freakz.hokan_ng_springboot.bot.jpa.repository.service.NetworkService;

import java.util.Iterator;

/**
 * Created by Petri Airio on 24.3.2015.
 */
public class ConfigPanel extends Panel {

  @SpringBean
  private NetworkService networkService;

  private Network selectedNetwork;

  final Form<?> form;

  public ConfigPanel(String id) {
    super(id);

    form = new Form("form");
    add(form);

    RefreshingView<Network> refreshingView = new RefreshingView<Network>("simple") {

      @Override
      protected Iterator<IModel<Network>> getItemModels() {
        Iterator<Network> networks = networkService.findAll().iterator();

        return new ModelIteratorAdapter<Network>(networks) {

          @Override
          protected IModel<Network> model(Network network) {
            return new CompoundPropertyModel<Network>(new DetachableNetworkModel(network));
          }
        };
      }

      @Override
      protected void populateItem(Item<Network> item) {
        IModel<Network> network = item.getModel();
//        item.add(item.add(new ActionPanel("actions", network)));
        item.add(new TextField<>("id"));
        item.add(new TextField<>("networkName"));
      }

      @Override
      protected Item<Network> newItem(String id, int index, IModel<Network> model) {
        // this item sets markup class attribute to either 'odd' or
        // 'even' for decoration
        return new OddEvenItem<>(id, index, model);
      }
    };

    refreshingView.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
    form.add(refreshingView);

  }

  //--

  public class ActionPanel extends Panel {
    public ActionPanel(String id, IModel<Network> model) {
      super(id, model);
      add(new Link("select") {
        @Override
        public void onClick() {
          setSelected((Network) ActionPanel.this.getDefaultModelObject());
        }
      });

      SubmitLink removeLink = new SubmitLink("remove", form) {
        @Override
        public void onSubmit() {
          Network network = (Network) ActionPanel.this.getDefaultModelObject();
          info("Remove network " + network);
        }

      };

    }
  }

  private void setSelected(Network network) {
    this.selectedNetwork = network;
  }

  public Network getSelectedNetwork() {
    return selectedNetwork;
  }

}
