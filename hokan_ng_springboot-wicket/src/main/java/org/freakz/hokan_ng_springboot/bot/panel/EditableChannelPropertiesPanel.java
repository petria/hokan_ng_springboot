package org.freakz.hokan_ng_springboot.bot.panel;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Channel;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.ChannelPropertyEntity;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;
import org.wicketstuff.egrid.EditableGrid;
import org.wicketstuff.egrid.column.AbstractEditablePropertyColumn;
import org.wicketstuff.egrid.column.EditableCellPanel;
import org.wicketstuff.egrid.column.EditableRequiredDropDownCellPanel;
import org.wicketstuff.egrid.column.RequiredEditableTextFieldColumn;
import org.wicketstuff.egrid.provider.EditableListDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri Airio on 26.3.2015.
 *
 */
@Slf4j
public class EditableChannelPropertiesPanel extends Panel {

  final private FeedbackPanel feedbackPanel;

  public EditableChannelPropertiesPanel(String id, final Channel channel) {
    super(id);


    feedbackPanel = new FeedbackPanel("feedBack");
    feedbackPanel.setOutputMarkupPlaceholderTag(true);
    add(feedbackPanel);

    EditableListDataProvider<ChannelPropertyEntity, String> provider = new EditableListDataProvider<>(Services.getChannelPropertyService().findByChannel(channel));
    EditableGrid editableGrid = new EditableGrid<ChannelPropertyEntity, String>("channelPropertiesGrid", getColumns(), provider, 10, ChannelPropertyEntity.class) {
      private static final long serialVersionUID = 1L;

      @Override
      protected void onAdd(AjaxRequestTarget target, ChannelPropertyEntity newRow) {
        newRow.setChannel(channel);
        ChannelPropertyEntity saved = Services.getChannelPropertyService().save(newRow);
        newRow.setId(saved.getId());
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
      protected void onDelete(AjaxRequestTarget target, IModel<ChannelPropertyEntity> rowModel) {
        Services.getChannelPropertyService().delete(rowModel.getObject());
        log.debug("deleted");
        target.add(feedbackPanel);
      }

      @Override
      protected void onSave(AjaxRequestTarget target, IModel<ChannelPropertyEntity> rowModel) {
        log.debug("save");
        Services.getChannelPropertyService().save(rowModel.getObject());
        target.add(feedbackPanel);
      }
    };
    add(editableGrid);

  }

  private List<? extends IColumn<ChannelPropertyEntity, String>> getColumns() {
    List<AbstractEditablePropertyColumn<ChannelPropertyEntity, String>> columns = new ArrayList<>();
    columns.add(new AbstractEditablePropertyColumn<ChannelPropertyEntity, String>(new Model<String>("PropertyEntity name"), "propertyName") {
      private static final long serialVersionUID = 1L;

      public EditableCellPanel getEditableCellPanel(String componentId) {
        return new EditableRequiredDropDownCellPanel<ChannelPropertyEntity, String>(componentId, this, PropertyName.getValuesLike("channel.*"));
      }
    });
    columns.add(new RequiredEditableTextFieldColumn<>(new Model<>("value"), "value", false));
    return columns;


  }

}
