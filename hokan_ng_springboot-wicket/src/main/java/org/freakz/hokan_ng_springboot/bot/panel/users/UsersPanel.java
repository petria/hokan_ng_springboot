package org.freakz.hokan_ng_springboot.bot.panel.users;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;
import org.freakz.hokan_ng_springboot.bot.panel.usereditor.UserEditorPanel;

/**
 * Created by Petri Airio on 17.4.2015.
 *
 */
@Slf4j
public class UsersPanel extends Panel {

  public UsersPanel(String id) {
    super(id);

    final ModalWindow modal2;
    add(modal2 = new ModalWindow("modal2"));

    modal2.setContent(new UserEditorPanel(modal2.getContentId(), modal2, null));
    modal2.setTitle("User editor.");
    modal2.setCookieName("modal-2");

    modal2.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {
      @Override
      public boolean onCloseButtonClicked(AjaxRequestTarget target) {
        log.debug("onCloseButtonClicked");
        target.add(UsersPanel.this);
        return true;
      }
    });


    SortableUsersDataProvider dp = new SortableUsersDataProvider();
    final DataView<User> dataView = new DataView<User>("sorting", dp) {

      @Override
      protected void populateItem(Item<User> item) {
        User user = item.getModelObject();
        AjaxLink<Void> userEditorLink = new AjaxLink<Void>("userEditorLink") {
          @Override
          public void onClick(AjaxRequestTarget target) {
            modal2.show(target);
          }
        };
        item.add(new Link("editLink", item.getModel()) {

          @Override
          public void onClick() {
            User user = (User) getParent().getDefaultModelObject();
            log.debug("clicked: {}", user);
          }
        });
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

/*  class ActionPanel extends Panel
  {
    public ActionPanel(String id, IModel<User> model)
    {
      super(id, model);
      add(new Link("select")
      {
        @Override
        public void onClick()
        {
          selected = (User)getParent().getDefaultModelObject();
        }
      });
    }
  }
  */
}
