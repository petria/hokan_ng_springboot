package org.freakz.hokan_ng_springboot.bot.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsMessageHandler;
import org.freakz.hokan_ng_springboot.bot.jms.api.JmsSender;
import org.freakz.hokan_ng_springboot.bot.service.UptimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 *
 * Created by petria on 6.2.2015.
 */
@Component
@Slf4j
public abstract class SpringJmsReceiver implements JmsMessageHandler {

  @Autowired
  private ConnectionFactory connectionFactory;

  @Autowired
  private UptimeService uptimeService;

  @Autowired
  private JmsSender jmsSender;

  @Bean
  public ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor() {
    return new ScheduledAnnotationBeanPostProcessor();
  }

  @Bean
  public DefaultMessageListenerContainer messageListener() {
    DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
    container.setConnectionFactory(this.connectionFactory);
    log.info("Setting Destination Name: {}", getDestinationName());
    container.setDestinationName(getDestinationName());
    container.setMessageListener((MessageListener) message -> {
      try {
        ObjectMessage objectMessage = (ObjectMessage) message;
        JmsMessage messageIn = (JmsMessage) objectMessage.getObject();
        JmsMessage messageOut = new JmsMessage();
        JmsEnvelope envelope = new JmsEnvelope(objectMessage, messageIn, messageOut);

        String command = messageIn.getCommand();
        if (command.equals("PING")) {

          PingResponse pingResponse = new PingResponse();
          pingResponse.setUptime(uptimeService.getUptime());

//          JarNixScriptExecutor cmdExecutor = new JarNixScriptExecutor("/hostinfo.sh", "UTF-8");
//          String[] hostinfo = cmdExecutor.executeJarScript();
//          if (hostinfo != null && hostinfo.length > 0) {
//            pingResponse.setHostinfo(hostinfo[0]);
//          } else {
//            pingResponse.setHostinfo("N/A");
//          }

          messageOut.addPayLoadObject("PING_RESPONSE", pingResponse);

        } else {
          try {
            handleJmsEnvelope(envelope);
          } catch (Exception e) {
            log.error("Exception", e);
          }
        }
        Destination replyTo = message.getJMSReplyTo();
        if (replyTo != null) {
          jmsSender.sendJmsMessage(replyTo, messageOut);
        }

      } catch (JMSException ex) {
        log.error("Jms error", ex);
      }
    });
    return container;
  }


}

