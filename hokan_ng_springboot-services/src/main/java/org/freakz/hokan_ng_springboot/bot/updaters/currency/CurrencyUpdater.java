package org.freakz.hokan_ng_springboot.bot.updaters.currency;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * PircBotUser: petria
 * Date: 12/12/13
 * Time: 10:20 PM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Slf4j
public class CurrencyUpdater {  //extends Updater {

  private Map<String, CurrencyValue> values = new HashMap<>();

//  @Override
  public void doUpdateData() throws Exception {
    //To change body of implemented methods use File | Settings | File Templates.
  }

//  @Override
  public Object doGetData(Object... args) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
