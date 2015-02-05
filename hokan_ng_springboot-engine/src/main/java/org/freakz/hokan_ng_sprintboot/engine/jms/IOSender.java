/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.freakz.hokan_ng_sprintboot.engine.jms;

import lombok.extern.slf4j.Slf4j;
import org.freakz.hokan_ng_sprintboot.common.jms.JmsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
@Slf4j
public class IOSender {

  @Autowired
  private JmsTemplate jmsTemplate;


  public ObjectMessage sendAndGetReply(String destination, String key, String msg) {
    log.debug("{}: {} -> {}", destination, key, msg);
    Message reply = this.jmsTemplate.sendAndReceive(destination, new MessageCreator() {
          @Override
          public Message createMessage(Session session) throws JMSException {
            ObjectMessage objectMessage = session.createObjectMessage();
            JmsMessage jmsMessage = new JmsMessage();
            jmsMessage.addPayLoadObject(key, msg);
            objectMessage.setObject(jmsMessage);
            return objectMessage;
          }
        }
    );
    return (ObjectMessage) reply;
  }

  public void send(String destination, String key, String msg) {
    log.debug("{}: {} -> {}", destination, key, msg);
    this.jmsTemplate.send(destination, new MessageCreator() {
          @Override
          public Message createMessage(Session session) throws JMSException {
            ObjectMessage objectMessage = session.createObjectMessage();
            JmsMessage jmsMessage = new JmsMessage();
            jmsMessage.addPayLoadObject(key, msg);
            objectMessage.setObject(jmsMessage);
            return objectMessage;
          }
        }
    );
  }

  public void send(Destination destination, String key, String msg) {
    log.debug("{}: {} -> {}", destination, key, msg);
    this.jmsTemplate.send(destination, new MessageCreator() {
      @Override
      public Message createMessage(Session session) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage();
        JmsMessage jmsMessage = new JmsMessage();
        jmsMessage.addPayLoadObject(key, msg);
        objectMessage.setObject(jmsMessage);
        return objectMessage;
      }
    });
  }

}
