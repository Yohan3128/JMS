package com.hnys.jms;

import jakarta.annotation.Resource;
import jakarta.jms.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;

@WebServlet("/test")
public class Test extends HttpServlet {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory factory;

    @Resource(lookup = "myTopic")
    private Topic topic;


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
//            InitialContext context = new InitialContext();
//            ConnectionFactory factory = (ConnectionFactory) context.lookup("jms/__defaultConnectionFactory");

            Connection connection = factory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

//            Topic topic = (Topic) context.lookup("myTopic");

//            Topic topic = session.createTopic("myTopic");

            MessageProducer producer = session.createProducer(topic);

            for (int i = 1; i <= 10; i++) {
                TextMessage message = session.createTextMessage();
                message.setText("Hello World" + i);
                producer.send(message);
            }

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
