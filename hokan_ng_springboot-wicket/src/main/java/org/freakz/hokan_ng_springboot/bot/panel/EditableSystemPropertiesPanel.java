package org.freakz.hokan_ng_springboot.bot.panel;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Property;
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
 * Created by Petri Airio on 27.3.2015.
 *
 */
@Slf4j
public class EditableSystemPropertiesPanel extends Panel {

  final private FeedbackPanel feedbackPanel;

  public EditableSystemPropertiesPanel(String id) {
    super(id);

    feedbackPanel = new FeedbackPanel("feedBack");
    feedbackPanel.setOutputMarkupPlaceholderTag(true);
    add(feedbackPanel);
    EditableListDataProvider<Property, String> provider = new EditableListDataProvider<>(Services.getPropertyService().findAll());
    EditableGrid editableGrid = new EditableGrid<Property, String>("systemPropertiesGrid", getColumns(), provider, 10, Property.class) {
      private static final long serialVersionUID = 1L;

      @Override
      protected void onAdd(AjaxRequestTarget target, Property newRow) {
        Property saved = Services.getPropertyService().save(newRow);
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
      protected void onDelete(AjaxRequestTarget target, IModel<Property> rowModel) {
        Services.getPropertyService().delete(rowModel.getObject());
        log.debug("deleted");
        target.add(feedbackPanel);
      }

      @Override
      protected void onSave(AjaxRequestTarget target, IModel<Property> rowModel) {
        log.debug("save");
        Services.getPropertyService().save(rowModel.getObject());
        target.add(feedbackPanel);
      }
    };
    add(editableGrid);

  }

  private List<? extends IColumn<Property, String>> getColumns() {
    List<AbstractEditablePropertyColumn<Property, String>> columns = new ArrayList<>();
    columns.add(new AbstractEditablePropertyColumn<Property, String>(new Model<String>("Property name"), "property") {
      private static final long serialVersionUID = 1L;

      public EditableCellPanel getEditableCellPanel(String componentId) {
        return new EditableRequiredDropDownCellPanel<Property, String>(componentId, this, PropertyName.getValuesLike("sys.*"));
      }
    });
    columns.add(new RequiredEditableTextFieldColumn<>(new Model<>("value"), "value", false));
    return columns;


  }


}
