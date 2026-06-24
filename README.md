# JMS Practical Project

This workspace contains a small Java JMS learning project built around Payara/GlassFish and Jakarta Messaging. It includes two Java client modules and a Jakarta EE web application module that demonstrate both topic-based publish/subscribe messaging and queue-based messaging.

## Project Overview

The project is organized as three Maven modules:

- JMS-FirstClient: a JMS topic subscriber example
- JMS-SecondClient: a JMS topic publisher and queue sender example
- jms-webapp: a simple Jakarta EE web application with a message-driven bean receiver

## Modules

### 1. JMS-FirstClient

Purpose:
- Demonstrates a topic subscriber that listens for messages on the JMS topic named myTopic.

Main classes:
- com.hnys.jms.App: creates a topic connection, subscribes to myTopic, and uses a MessageListener to print incoming messages.
- com.hnys.jms.DefaultConnection: contains a separate example that uses the default connection factory.

### 2. JMS-SecondClient

Purpose:
- Demonstrates a topic publisher and a queue sender.

Main classes:
- com.hnys.jms.App: reads console input and publishes each line as a text message to myTopic.
- com.hnys.jms.QueueSender: sends a series of text messages to the queue named myQueue.

### 3. jms-webapp

Purpose:
- Demonstrates a Jakarta EE web application with a message-driven bean receiver.

Main classes:
- com.hnys.jms.MessageReceiver: an MDB that receives messages from myTopic.
- src/main/webapp/index.jsp: the default JSP page for the web application.

## Technical Stack

- Java 17
- Maven
- Jakarta JMS
- Jakarta EE 10
- Payara / GlassFish-compatible runtime

## Prerequisites

Before running the examples, make sure you have:

- Java 17 or newer installed
- Maven 3.6+ installed
- A Payara server running and configured with the JMS resources used by the code

## Required JMS Resources

The examples rely on JNDI resources such as:

- myTopicConnectionFactory
- myTopic
- myQueueConnectionFactory
- myQueue

These should be configured in the Payara server environment before executing the clients.

## Build the Project

From each module directory, run:

```bash
mvn clean install
```

Example:

```bash
cd JMS-FirstClient
mvn clean install
```

## Run the Examples

### Run the topic subscriber

```bash
cd JMS-FirstClient
mvn exec:java -Dexec.mainClass="com.hnys.jms.App"
```

### Run the topic publisher

Open a second terminal:

```bash
cd JMS-SecondClient
mvn exec:java -Dexec.mainClass="com.hnys.jms.App"
```

Type messages in the publisher console. The subscriber should print them when the topic is reached.

### Run the queue sender

```bash
cd JMS-SecondClient
mvn exec:java -Dexec.mainClass="com.hnys.jms.QueueSender"
```

### Deploy and test the web application

Deploy the jms-webapp module to Payara and observe the MDB logs for incoming messages.

## Project Structure

```text
JMS/
├── README.md
├── JMS-FirstClient/
│   └── src/main/java/com/hnys/jms/
├── JMS-SecondClient/
│   └── src/main/java/com/hnys/jms/
└── jms-webapp/
    └── src/main/java/com/hnys/jms/
```

## Notes

- The current examples are intended for learning and experimentation with JMS.
- The web module is a basic Jakarta EE example and can be extended with a richer UI or additional messaging logic.
- If a lookup fails, verify that the corresponding JMS resource exists in the Payara server configuration.

## Last Updated

2026-06-24
