package org.freakz.hokan_ng_springboot.bot.panel.urls;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Url;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 16.4.2015.
 *
 */
public class UrlsPanel extends Panel {

  public UrlsPanel(String id) {
    super(id);
    SortableUrlsDataProvider dp = new SortableUrlsDataProvider();
    final DataView<Url> dataView = new DataView<Url>("sorting", dp) {

      @Override
      protected void populateItem(Item<Url> item) {
        Url url = item.getModelObject();
        item.add(new Label("id", String.valueOf(url.getId())));
        item.add(new Label("url", url.getUrl()));
        item.add(new Label("urlTitle", url.getUrlTitle()));
        item.add(new Label("channel", url.getChannel()));
        item.add(new Label("sender", url.getSender()));
        item.add(new Label("created", url.getCreated().toString()));
        item.add(new Label("wanhaCount", url.getWanhaCount()));

        item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
          private static final long serialVersionUID = 1L;
          @Override
          public String getObject() {
            return (item.getIndex() % 2 == 1) ? "even" : "odd";
          }
        }));

      }
    };
    dataView.setItemsPerPage(8L);

    add(new OrderByBorder("orderById", "id", dp) {
      private static final long serialVersionUID = 1L;
      @Override
      protected void onSortChanged() {
        dataView.setCurrentPage(0);
      }
    });
    add(new OrderByBorder("orderByUrl", "url", dp) {
      private static final long serialVersionUID = 1L;

      @Override
      protected void onSortChanged() {
        dataView.setCurrentPage(0);
      }
    });

    add(dataView);
    add(new PagingNavigator("navigator", dataView));

  }

}
