# 🔔 Alert POC Project: Comprehensive Setup & Run Guide

Welcome to the Alert POC Notification System! This guide is written strictly step-by-step to help any developer (even starting from scratch) get this system successfully running on their local computer.

---

## 📖 1. What Are We Building? (Project Overview)
This project is an **event-driven Notification System** using Apache Kafka. It has two main pieces:
1. **Producer (`Producer_Setup`)**: It acts like a watcher. Every 10 seconds, it checks its built-in database (H2) for any records marked as `PENDING`. If it finds one, it sends it to Kafka and marks it as `PUBLISHED`.
2. **Consumer (`notification-consumer`)**: It constantly listens to Kafka. Whenever a message arrives, it dynamically reads the data, looks up how to format it (using rules in MySQL), and generates a ready-to-send SMS or Email payload.

---

## 🛠️ 2. Prerequisites: What You Need Installed
Before you run any setup commands, ensure you have the following installed on your computer. 
*Open your terminal (Command Prompt/PowerShell on Windows, or Terminal on Mac) and type these commands to verify:*

1. **Java SDK (17 to 21)**
   - *Test command:* `java -version`
2. **Maven** (For building the Java applications)
   - *Test command:* `mvn -v`
3. **Docker** (Needs to be running in the background)
   - *Test command:* `docker --version`
4. **MySQL Server** (Running locally on your machine)
   - *Test command:* `mysql --version` (or ensure it's running via MySQL Workbench)

*(If any of these throw an error "command not found", you must download and install them first).*

---

## 🚀 3. Step-by-Step Execution Guide

### Step 1: Start Kafka Infrastructure using Docker
We need Kafka running so the Producer and Consumer have a message broker to talk through.
1. Open a terminal window.
2. Navigate to the `Docker` folder inside the project.
   ```bash
   cd path/to/alertpocbank/Docker
   ```
   *(Please replace `path/to/alertpocbank` with the actual path to the project folder on your machine).*
3. Start the Kafka container in the background:
   ```bash
   docker compose up -d
   ```
   *Wait about 15-30 seconds for Kafka to fully start.*
4. **Create the Kafka Topic** (this is the tunnel the messages are sent into):
   ```bash
   docker exec -it kafka-broker kafka-topics --bootstrap-server localhost:9092 --create --topic notifications.events --partitions 8 --replication-factor 1
   ```
5. *(Optional)* Verify Kafka is running by visiting the Kafka UI dashboard in your web browser: http://localhost:8081 

---

### Step 2: Set Up the Consumer's MySQL Database
The Consumer needs a database to look up predefined notification templates.
1. Open your MySQL client (e.g., **MySQL Workbench**, **DBeaver**, or terminal).
2. Connect to your local MySQL (Host: `localhost`, Port: `3306`).
3. Execute the following SQL query to create the database:
   ```sql
   CREATE DATABASE POCALERT;
   ```
4. Tell your client to use the newly created database:
   ```sql
   USE POCALERT;
   ```
5. The Java application expects your database **Username to be: `root`** and **Password to be: `root1234`**. 
   *(⚠️ If your local MySQL password is different, you MUST update lines 10 & 11 in the file [notification-consumer/src/main/resources/application.yml](file:///Users/manaspeeyushpandey/Desktop/alertpocbank/notification-consumer/src/main/resources/application.yml) before proceeding).*
6. Open the script files located in the project folder and run them in your MySQL client to create the layout tables:
   - File 1: [notification-consumer/src/main/resources/Scripts/routing_key_config.sql](file:///Users/manaspeeyushpandey/Desktop/alertpocbank/notification-consumer/src/main/resources/Scripts/routing_key_config.sql)
   - File 2: [notification-consumer/src/main/resources/Scripts/template_master.sql](file:///Users/manaspeeyushpandey/Desktop/alertpocbank/notification-consumer/src/main/resources/Scripts/template_master.sql)

---

### Step 3: Start the Notification Consumer
Now we will turn on the consumer which will sit back and listen for messages.
1. Open a **New Terminal Window**.
2. Navigate into the Consumer folder:
   ```bash
   cd path/to/alertpocbank/notification-consumer
   ```
3. Run the application using Maven:
   ```bash
   mvn clean spring-boot:run
   ```
4. **What to expect:** Lots of logs will scroll by. Eventually, near the bottom, you should see `Tomcat started on port 8083` and `Started NotificationConsumerApplication`. 
   Leave this terminal open and running.

---

### Step 4: Start the Notification Producer
Now we will start the producer which will look for events to send.
1. Open **Another New Terminal Window** (You should now have your Consumer terminal running in the back, and this new one).
2. Navigate into the Producer folder:
   ```bash
   cd path/to/alertpocbank/Producer_Setup
   ```
3. Run the application:
   ```bash
   mvn clean spring-boot:run
   ```
4. **What to expect:** You will see logs, and eventually `Started ProducerSetupApplication`. Because it has a scheduler running, you will begin to see polling logs every 10 seconds checking for records. 
   Leave this terminal running too.

---

### Step 5: The Grand Test (End-to-End Trigger)
Let's actually trigger a notification! We do this by inserting a mock record manually into the Producer's built-in H2 database.
1. **Open your web browser** and go to this link: http://localhost:8080/h2-console
2. You will see a web login screen. Fill it out EXACTLY like this:
   - **JDBC URL:** `jdbc:h2:mem:alertdb`
   - **User Name:** `sa`
   - **Password:** *(Leave this completely blank/empty)*
3. Click the **Connect** button.
4. You are now inside the H2 database. There is a large white text box for running SQL commands. Paste the following SQL command to insert a new `PENDING` request:
   ```sql
   INSERT INTO payload_mst (payload_type,payload,topic_status,is_active,created_by) 
   VALUES ('ACCOUNT_CREATED','{"customFieldDetails":{"MessageType":"SMS","alertType":"ACCOUNT_CREATED","customer_name":"TestUser"}}','PENDING',TRUE,'SYSTEM');
   ```
   *(Note: The [data.sql](file:///Users/manaspeeyushpandey/Desktop/alertpocbank/Producer_Setup/target/classes/data.sql) script also pre-inserts a giant payload but flags it as `PUBLISHED`. You could also just run `UPDATE payload_mst SET topic_status = 'PENDING';` to rerun existing ones).*
5. Click **Run** in the H2 console interface.
6. **Watch the Magic Happen:**
   - Look at your **Producer Terminal**: Within 10 seconds, it will detect the `PENDING` event, say it published to Kafka, and update it to `PUBLISHED`!
   - Look at your **Consumer Terminal**: It will instantly intercept the event from Kafka and log out a formatted notification resolving the template matching via the headers!

---

## 🛑 4. How to Shut Down Cleanly
When you are done testing the system:
1. Go to your **Consumer Terminal** and press `CTRL + C` on your keyboard to stop the app.
2. Go to your **Producer Terminal** and press `CTRL + C` to stop the app.
3. Stop your Docker containers to free up computer memory:
   ```bash
   cd path/to/alertpocbank/Docker
   docker compose down -v
   ```
