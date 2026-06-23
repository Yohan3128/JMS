# JMS Practical Project

A Java Message Service (JMS) practical project demonstrating pub/sub messaging pattern with two client applications communicating through a shared topic.

## Project Overview

This is a multi-module Maven project that implements a **Publisher-Subscriber (Pub/Sub)** messaging pattern using JMS with Payara application server.

### Architecture

The project consists of two independent Maven modules:

```
JMS (Parent Project)
├── JMS-FirstClient   (Subscriber/Consumer)
└── JMS-SecondClient  (Publisher/Producer)
```

## Modules

### 1. JMS-FirstClient (Subscriber)

**Purpose:** Listens to messages published on a JMS topic and processes them.

**Key Features:**
- Subscribes to `myTopic`
- Uses `MessageListener` for asynchronous message processing
- Receives and displays messages in real-time
- Maintains an infinite loop to keep listening

**Main Class:** `com.hnys.jms.App`

```
Location: JMS-FirstClient/src/main/java/com/hnys/jms/App.java
```

**Functionality:**
- Establishes a TopicConnection using JNDI lookup
- Creates a TopicSession with AUTO_ACKNOWLEDGE mode
- Sets up a MessageListener callback for incoming messages
- Prints received messages to the console

### 2. JMS-SecondClient (Publisher)

**Purpose:** Publishes messages to a JMS topic for subscribers to consume.

**Key Features:**
- Publishes messages to `myTopic`
- Interactive console input for sending messages
- Type "exit" to terminate the publisher
- Sends TextMessages to all subscribers

**Main Class:** `com.hnys.jms.App`

```
Location: JMS-SecondClient/src/main/java/com/hnys/jms/App.java
```

**Functionality:**
- Establishes a TopicConnection using JNDI lookup
- Creates a TopicSession with AUTO_ACKNOWLEDGE mode
- Sets up a TopicPublisher
- Accepts user input via Scanner
- Publishes each line as a TextMessage to the topic

## Technical Stack

- **Language:** Java 17
- **Build Tool:** Maven
- **JMS Provider:** Payara Embedded (Version: 6.2025.11)
- **JMS API:** Jakarta JMS
- **Connection Method:** JNDI Lookup

## Prerequisites

- Java 17 or higher installed
- Maven 3.6.0 or higher
- Payara Application Server or Embedded Payara instance

## Configuration

### JNDI Resources

The project relies on the following JNDI resources that must be configured in the application server:

| Resource Name | Type | Description |
|---------------|------|-------------|
| `myTopicConnectionFactory` | TopicConnectionFactory | Factory for creating topic connections |
| `myTopic` | Topic | The JMS topic for pub/sub communication |

These resources need to be configured in Payara's JNDI provider (typically via `glassfish-resources.xml` or admin console).

## Building the Project

### Build All Modules

```bash
mvn clean install
```

### Build Individual Module

```bash
# First Client (Subscriber)
cd JMS-FirstClient
mvn clean install

# Second Client (Publisher)
cd JMS-SecondClient
mvn clean install
```

## Running the Application

### Step 1: Start Payara Server

Ensure Payara server is running and configured with the required JMS resources (myTopic and myTopicConnectionFactory).

### Step 2: Start the Subscriber (FirstClient)

```bash
cd JMS-FirstClient
mvn exec:java -Dexec.mainClass="com.hnys.jms.App"
```

You should see output indicating it's listening for messages.

### Step 3: Start the Publisher (SecondClient)

In another terminal:

```bash
cd JMS-SecondClient
mvn exec:java -Dexec.mainClass="com.hnys.jms.App"
```

### Step 4: Send Messages

In the Publisher terminal, type messages and press Enter. Each message will be:
- Published to the topic
- Received by the subscriber
- Printed in the subscriber's console

Example:

**Publisher Console:**
```
Enter Message or type 'exit' to exit
Hello World
Test Message 123
exit
```

**Subscriber Console:**
```
Hello World
Test Message 123
```

## Project Structure

```
JMS/
├── README.md                          # This file
├── JMS-FirstClient/
│   ├── pom.xml                        # Maven configuration
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/hnys/jms/
│       │   │       └── App.java       # Subscriber implementation
│       │   └── resources/
│       └── test/
│           └── java/
│
└── JMS-SecondClient/
    ├── pom.xml                        # Maven configuration
    └── src/
        ├── main/
        │   ├── java/
        │   │   └── com/hnys/jms/
        │   │       └── App.java       # Publisher implementation
        │   └── resources/
        └── test/
            └── java/
```

## Message Flow Diagram

```
┌─────────────────┐
│ JMS-SecondClient│ (Publisher)
│   (App.java)    │
└────────┬────────┘
         │
         │ Publishes TextMessages
         │
         ▼
    ┌─────────────┐
    │   myTopic   │ (JMS Topic)
    └─────────────┘
         │
         │ Routes Messages
         │
         ▼
┌─────────────────────────┐
│  JMS-FirstClient        │ (Subscriber)
│  (App.java)             │
│  MessageListener Setup  │
└─────────────────────────┘
```

## Key Concepts Used

### 1. **Topic (Pub/Sub Pattern)**
- Multiple subscribers can receive the same message
- Publishers don't need to know about subscribers
- Messages are distributed to all active subscribers at the time of publishing

### 2. **JNDI (Java Naming and Directory Interface)**
- Decouples applications from resource configurations
- Resources are looked up by name rather than hardcoded

### 3. **MessageListener**
- Asynchronous message consumption
- Callback-based approach for handling incoming messages
- Non-blocking subscriber

### 4. **Session Acknowledgment Mode**
- `AUTO_ACKNOWLEDGE`: Messages are automatically acknowledged upon successful delivery

### 5. **TopicConnection & TopicSession**
- TopicConnection: Establishes connection to the JMS provider
- TopicSession: Creates a context for producing/consuming messages

## Troubleshooting

### Connection Issues
- Verify Payara server is running
- Check that JNDI resources (myTopicConnectionFactory, myTopic) are properly configured
- Ensure the server can be accessed from the client

### Resource Not Found
```
javax.naming.NameNotFoundException: myTopic
```
Solution: Configure the JMS resources in Payara Admin Console or create `glassfish-resources.xml`

### No Messages Received
- Ensure subscriber is running before publisher sends messages
- Verify both clients are connecting to the same topic
- Check Payara server logs for errors

## Future Enhancements

- [ ] Add queue-based messaging (Point-to-Point pattern)
- [ ] Implement message filtering/selectors
- [ ] Add persistence layer for messages
- [ ] Create web UI for monitoring
- [ ] Add message transformation/serialization
- [ ] Implement error handling and retry logic
- [ ] Add unit and integration tests
- [ ] Configuration externalization (properties file)

## Dependencies

### JMS-FirstClient / JMS-SecondClient

```xml
<dependency>
    <groupId>fish.payara.extras</groupId>
    <artifactId>payara-embedded-all</artifactId>
    <version>6.2025.11</version>
</dependency>
```

## License

This is a practical/educational project. No specific license applied.

## Author

Created as a Java Message Service practical learning project.

---

**Last Updated:** 2026-06-23
