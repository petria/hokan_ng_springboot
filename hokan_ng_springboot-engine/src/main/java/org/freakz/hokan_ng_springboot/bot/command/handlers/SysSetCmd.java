package org.freakz.hokan_ng_springboot.bot.command.handlers;

import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;
import org.freakz.hokan_ng_springboot.bot.events.EngineResponse;
import org.freakz.hokan_ng_springboot.bot.events.InternalRequest;
import org.freakz.hokan_ng_springboot.bot.exception.HokanException;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyEntity;
import org.freakz.hokan_ng_springboot.bot.jpa.entity.PropertyName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.freakz.hokan_ng_springboot.bot.util.StaticStrings.ARG_PROPERTY;

/**
 * Created by Petri Airio (petri.j.airio@gmail.com) on 26.8.2015.
 *
 */
@Component
@Scope("prototype")
public class SysSetCmd extends Cmd {

  public SysSetCmd() {
    super();
    setHelp("Sets system properties.");
    addToHelpGroup(HelpGroup.PROPERTIES, this);

    UnflaggedOption flg = new UnflaggedOption(ARG_PROPERTY)
        .setRequired(true)
        .setGreedy(false);
    registerParameter(flg);

    setAdminUserOnly(true);
  }

  @Override
  public void handleRequest(InternalRequest request, EngineResponse response, JSAPResult results) throws HokanException {
    String[] split = results.getString(ARG_PROPERTY).split("=");
    if (split.length != 2) {
      response.addResponse("Syntax error, usage: %s <PropertyName>=<Value>", getName());
      return;
    }
//    PropertyName test = PropertyName.valueOf(split[0]);
    List<PropertyName> propertyNameList = PropertyName.getValuesLike(split[0]);
    if (propertyNameList.size() != 1) {
      response.addResponse("Invalid property: %s", split[0]);
      return;
    }
    PropertyEntity property = propertyService.findFirstByPropertyName(propertyNameList.get(0));
    if (property == null) {
      property = new PropertyEntity(propertyNameList.get(0), split[1], "");
    } else {
      property.setValue(split[1]);
    }
    propertyService.save(property);
    response.addResponse("System property set: [%d] %s=%s", property.getId(), property.getPropertyName(), property.getValue());
  }

}
