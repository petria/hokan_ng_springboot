package org.freakz.hokan_ng_springboot.bot.jms;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.enums.HokanModule;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsServiceMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by petria on 5.2.2015.
 *
 */
@Component
@Slf4j
public class VaadinJmsReceiver extends SpringJmsReceiver {

  private  UI ui;

  @Autowired
  private JmsSender jmsSender;

//  @Autowired
  private JmsServiceMessageHandler jmsServiceMessageHandler;


  @Override
  public String getDestinationName() {
    return HokanModule.HokanVaadin.getQueueName();
  }

  @Override
  public void handleJmsEnvelope(JmsEnvelope envelope) throws Exception {
    String msg = (String) envelope.getMessageIn().getPayLoadObject("TEXT");
    Notification.show(msg);
  }


}
