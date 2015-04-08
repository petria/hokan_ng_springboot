package org.freakz.hokan_ng_springboot.bot.panel;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.freakz.hokan_ng_springboot.bot.Services;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.IrcServerConfig;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.Network;
import org.wicketstuff.egrid.EditableGrid;
import org.wicketstuff.egrid.column.AbstractEditablePropertyColumn;
import org.wicketstuff.egrid.column.EditableCellPanel;
import org.wicketstuff.egrid.column.EditableRequiredDropDownCellPanel;
import org.wicketstuff.egrid.column.RequiredEditableTextFieldColumn;
import org.wicketstuff.egrid.provider.EditableListDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petri Airio on 8.4.2015.
 */
@Slf4j
public class EditableIrcServerConfigPanel extends Panel {

  private FeedbackPanel feedbackPanel;

  public EditableIrcServerConfigPanel(String id) {
    super(id);
    setOutputMarkupId(true);

    feedbackPanel = new FeedbackPanel("feedBack");
    feedbackPanel.setOutputMarkupPlaceholderTag(true);
    add(feedbackPanel);

    EditableListDataProvider<IrcServerConfig, String> provider = new EditableListDataProvider<>(Services.getIrcServerConfigService().findAll());
    EditableGrid<IrcServerConfig, String> editableGrid = new EditableGrid<IrcServerConfig, String>("ircServerConfigsGrid", getColumns(), provider, 10, IrcServerConfig.class) {
      private static final long serialVersionUID = 1L;

      @Override
      protected void onAdd(AjaxRequestTarget target, IrcServerConfig newRow) {
        IrcServerConfig config = Services.getIrcServerConfigService().save(newRow);
        newRow.setId(config.getId());
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
      protected void onDelete(AjaxRequestTarget target, IModel<IrcServerConfig> rowModel) {
        Services.getIrcServerConfigService().delete(rowModel.getObject());
        log.debug("deleted");
        target.add(feedbackPanel);
      }

      @Override
      protected void onSave(AjaxRequestTarget target, IModel<IrcServerConfig> rowModel) {
        log.debug("save");
        Services.getIrcServerConfigService().save(rowModel.getObject());
        target.add(feedbackPanel);
      }
    };
    add(editableGrid);

  }

  private List<? extends IColumn<IrcServerConfig, String>> getColumns() {
    List<AbstractEditablePropertyColumn<IrcServerConfig, String>> columns = new ArrayList<>();

    columns.add(new AbstractEditablePropertyColumn(new Model<String>("Network"), "network") {
      private static final long serialVersionUID = 1L;

      public EditableCellPanel getEditableCellPanel(String componentId) {
        return new EditableRequiredDropDownCellPanel<Network, String>(componentId, this, Services.getNetworkService().findAll());
      }
    });


    columns.add(new RequiredEditableTextFieldColumn<>(new Model<>("Server address"), "server", true));
    RequiredEditableTextFieldColumn column = new RequiredEditableTextFieldColumn(new Model<>("Server password"), "serverPassword", true) {
      @Override
      protected void addBehaviors(FormComponent editorComponent) {
        editorComponent.setRequired(false);
      }
    };
    columns.add(column);
    columns.add(new RequiredEditableTextFieldColumn<>(new Model<>("Server port"), "port", true));
    column = new RequiredEditableTextFieldColumn(new Model<>("Local address"), "localAddress", true) {
      @Override
      protected void addBehaviors(FormComponent editorComponent) {
        editorComponent.setRequired(false);
      }
    };
    columns.add(column);
    columns.add(new RequiredEditableTextFieldColumn<>(new Model<>("Use throttle"), "useThrottle", true));

    return columns;


  }

}
