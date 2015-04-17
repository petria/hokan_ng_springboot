package org.freakz.hokan_ng_springboot.bot.panel.users;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;

/**
 * Created by Petri Airio on 17.4.2015.
 *
 */
public class UsersPanel extends Panel {

  public UsersPanel(String id) {
    super(id);
    SortableUsersDataProvider dp = new SortableUsersDataProvider();
    final DataView<User> dataView = new DataView<User>("sorting", dp) {

      @Override
      protected void populateItem(Item<User> item) {
        User user = item.getModelObject();
        item.add(new Label("id", String.valueOf(user.getId())));
        item.add(new Label("nick", user.getNick()));
        item.add(new Label("fullName", user.getFullName()));
        item.add(new Label("email", user.getEmail()));
        item.add(new Label("phone", user.getPhone()));
        item.add(new Label("flags", user.getFlags()));
        item.add(new Label("mask", user.getMask()));

        item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
          private static final long serialVersionUID = 1L;

          @Override
          public String getObject() {
            return (item.getIndex() % 2 == 1) ? "even" : "odd";
          }
        }));

      }
    };
    dataView.setItemsPerPage(15L);

    add(new OrderByBorder("orderById", "id", dp) {
      private static final long serialVersionUID = 1L;
      @Override
      protected void onSortChanged() {
        dataView.setCurrentPage(0);
      }
    });
    add(new OrderByBorder("orderByNick", "nick", dp) {
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
