package org.freakz.hokan_ng_springboot.bot.panel;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.wicketstuff.egrid.EditableGrid;
import org.wicketstuff.egrid.column.AbstractEditablePropertyColumn;
import org.wicketstuff.egrid.column.RequiredEditableTextFieldColumn;
import org.wicketstuff.egrid.provider.EditableListDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri Airio on 25.3.2015.
 */
@Slf4j
public class EditableChannelsPanel extends Panel {

  private FeedbackPanel feedbackPanel;

  public EditableChannelsPanel(String id) {
    super(id);
    setOutputMarkupId(true);

    feedbackPanel = new FeedbackPanel("feedBack");
    feedbackPanel.setOutputMarkupPlaceholderTag(true);
    add(feedbackPanel);

    EditableListDataProvider<Channel, String> provider = new EditableListDataProvider<>(Services.getChannelService().findAll());
    EditableGrid editableGrid = new EditableGrid<Channel, String>("grid", getColumns(), provider, 10, Channel.class) {
      private static final long serialVersionUID = 1L;

      @Override
      protected void onAdd(AjaxRequestTarget target, Channel newRow) {
        Channel channel = Services.getChannelService().create(newRow);
        newRow.setChannelId(channel.getChannelId());

        info("Added!");
        target.add(feedbackPanel);
      }

      @Override
      protected void onError(AjaxRequestTarget target) {
        log.debug("error");
        target.add(feedbackPanel);
      }

      @Override
      protected void onCancel(AjaxRequestTarget target) {
        log.debug("cancel");
        target.add(feedbackPanel);
      }

      @Override
      protected void onDelete(AjaxRequestTarget target, IModel<Channel> rowModel) {
        Services.getChannelService().delete(rowModel.getObject());
        log.debug("deleted");
        target.add(feedbackPanel);
      }

      @Override
      protected void onSave(AjaxRequestTarget target, IModel<Channel> rowModel) {
        log.debug("save");
        Services.getChannelService().save(rowModel.getObject());
        target.add(feedbackPanel);
      }
    };
    add(editableGrid);

  }

  private List<AbstractEditablePropertyColumn<Channel, String>> getColumns() {
    List<AbstractEditablePropertyColumn<Channel, String>> columns = new ArrayList<>();
    columns.add(new RequiredEditableTextFieldColumn<>(new Model<>("ID"), "channelId"));
    columns.add(new RequiredEditableTextFieldColumn<>(new Model<>("Channel name"), "channelName"));
    return columns;
  }


}
