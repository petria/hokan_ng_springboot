package org.freakz.hokan_ng_springboot.bot.panel;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.ChannelState;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;
import org.wicketstuff.egrid.EditableGrid;
import org.wicketstuff.egrid.column.AbstractEditablePropertyColumn;
import org.wicketstuff.egrid.column.EditableCellPanel;
import org.wicketstuff.egrid.column.EditableRequiredDropDownCellPanel;
import org.wicketstuff.egrid.column.RequiredEditableTextFieldColumn;
import org.wicketstuff.egrid.provider.EditableListDataProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Petri Airio on 25.3.2015.
 *
 */
@Slf4j
public class EditableChannelsPanel extends Panel {

  private FeedbackPanel feedbackPanel;

  public EditableChannelsPanel(String panelId) {
    this(panelId, Services.getNetworkService().findAll().get(0));
  }

  public EditableChannelsPanel(String id, final Network network) {
    super(id);

    feedbackPanel = new FeedbackPanel("feedBack");
    feedbackPanel.setOutputMarkupPlaceholderTag(true);
    add(feedbackPanel);

    add(new Label("networkName", network));

    EditableListDataProvider<Channel, String> provider = new EditableListDataProvider<>(Services.getChannelService().findByNetwork(network));
    EditableGrid editableGrid = new EditableGrid<Channel, String>("channelGrid", getColumns(), provider, 10, Channel.class) {
      private static final long serialVersionUID = 1L;

      @Override
      protected void onAdd(AjaxRequestTarget target, Channel newRow) {
        Channel channel = Services.getChannelService().create(newRow);
        newRow.setId(channel.getId());

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
//        Services.getChannelService().delete(rowModel.getObject());
        log.debug("deleted");
        target.add(feedbackPanel);
      }

      @Override
      protected void onSave(AjaxRequestTarget target, IModel<Channel> rowModel) {
        log.debug("save");
//        Services.getChannelService().save(rowModel.getObject());
        target.add(feedbackPanel);
      }
    };
    add(editableGrid);

  }

  private List<AbstractEditablePropertyColumn<Channel, String>> getColumns() {
    List<AbstractEditablePropertyColumn<Channel, String>> columns = new ArrayList<>();
    columns.add(new RequiredEditableTextFieldColumn<>(new Model<>("ID"), "id", false));
    columns.add(new RequiredEditableTextFieldColumn<>(new Model<>("Channel name"), "channelName"));
    columns.add(new AbstractEditablePropertyColumn<Channel, String>(new Model<String>("Channel state"), "channelState") {
      private static final long serialVersionUID = 1L;

      public EditableCellPanel getEditableCellPanel(String componentId) {
        return new EditableRequiredDropDownCellPanel<Channel, String>(componentId, this, Arrays.asList(ChannelState.values()));
      }
    });
    return columns;
  }


}
