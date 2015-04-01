package org.freakz.hokan_ng_springboot.bot.panel.usereditor;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.User;

/**
 * Created by Petri Airio on 27.3.2015.
 *
 */
@Slf4j
public class UserEditorPanel extends Panel {

  public UserEditorPanel(String id, ModalWindow window, final User user) {
    super(id);
    add(new Label("editUserLabel", user.toString()));
    Form<User> form = new Form<>("form", new CompoundPropertyModel<>(user));
    form.add(new TextField<String>("fullName").setRequired(true).setLabel(new Model<>("String")));
    form.add(new TextField<String>("email").setRequired(true).setLabel(new Model<>("String")));
    form.add(new TextField<String>("phone").setRequired(true).setLabel(new Model<>("String")));
    form.add(new TextField<String>("mask").setRequired(true).setLabel(new Model<>("String")));
    form.add(new TextField<String>("flags").setRequired(true).setLabel(new Model<>("String")));

    form.add(new AjaxButton("save-button", form) {
      @Override
      protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
        User user = (User) form.getModelObject();
        user = Services.getUserService().save(user);
        log.debug("Saved user: {}", user);
        window.close(target);
      }
    });
    form.add(new AjaxButton("cancel-button", form) {
      @Override
      protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
        log.debug("cancel");
        window.close(target);
      }
    });
    add(form);
  }

}
