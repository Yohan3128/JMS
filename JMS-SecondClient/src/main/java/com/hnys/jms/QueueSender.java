package com.hnys.jms;

import jakarta.jms.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class QueueSender {
    public static void main(String[] args) {
        try {
            InitialContext context = new InitialContext();
            QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("jms/myQueueConnectionFactory");

            QueueConnection connection = factory.createQueueConnection();
            connection.start();

            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = (Queue) context.lookup("myQueue");

            jakarta.jms.QueueSender sender = session.createSender(queue);

            for (int i = 1; i <= 100; i++) {
                TextMessage message = session.createTextMessage();
                message.setText("Hello World"+i);
                sender.send(message);

                Thread.sleep(500);
            }

//            Scanner  scanner = new Scanner(System.in);
//            while (true) {
//                String line = scanner.nextLine();
//                if (line.equalsIgnoreCase("exit")) {
//                    break;
//                }
//                TextMessage message = session.createTextMessage(line);
//                sender.send(message);
//            }

        } catch (NamingException e) {
            throw new RuntimeException(e);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
