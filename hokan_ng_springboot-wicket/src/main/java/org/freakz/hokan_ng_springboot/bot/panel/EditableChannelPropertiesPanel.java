package org.freakz.hokan_ng_springboot.bot.panel;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.ChannelProperty;
import org.wicketstuff.egrid.EditableGrid;
import org.wicketstuff.egrid.provider.EditableListDataProvider;

import java.util.List;

/**
 * Created by Petri Airio on 26.3.2015.
 */
@Slf4j
public class EditableChannelPropertiesPanel extends Panel {

  final private FeedbackPanel feedbackPanel;

  public EditableChannelPropertiesPanel(String id, final Channel channel) {
    super(id);


    feedbackPanel = new FeedbackPanel("feedBack");
    feedbackPanel.setOutputMarkupPlaceholderTag(true);
    add(feedbackPanel);

    EditableListDataProvider<ChannelProperty, String> provider = new EditableListDataProvider<>(Services.getChannelPropertyService().findByChannel(channel));
    EditableGrid editableGrid = new EditableGrid<ChannelProperty, String>("channelPropertiesGrid", getColumns(), provider, 10, ChannelProperty.class) {
      private static final long serialVersionUID = 1L;

      @Override
      protected void onAdd(AjaxRequestTarget target, ChannelProperty newRow) {
//        newRow.setNetwork(network);
//        Channel channel = Services.getChannelService().create(newRow);
//        newRow.setId(channel.getId());
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
      protected void onDelete(AjaxRequestTarget target, IModel<ChannelProperty> rowModel) {
//        Services.getChannelService().delete(rowModel.getObject());
        log.debug("deleted");
        target.add(feedbackPanel);
      }

      @Override
      protected void onSave(AjaxRequestTarget target, IModel<ChannelProperty> rowModel) {
        log.debug("save");
//        Services.getChannelService().save(rowModel.getObject());
        target.add(feedbackPanel);
      }
    };
    add(editableGrid);

  }

  private List<? extends IColumn<ChannelProperty, String>> getColumns() {
    return null;
  }

}
