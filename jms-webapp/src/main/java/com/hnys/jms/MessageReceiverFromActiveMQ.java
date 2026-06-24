package com.hnys.jms;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType",propertyValue = "jakarta.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destinationLookup",propertyValue = "activemqTopic"),
        @ActivationConfigProperty(propertyName = "destination",propertyValue = "activemqTopic"),
        @ActivationConfigProperty(propertyName = "resourceAdapter",propertyValue = "activemq-rar-6.2.6")
})
public class MessageReceiverFromActiveMQ implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("Message : " + message.getBody(String.class));
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}

