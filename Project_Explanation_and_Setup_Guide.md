# 🔔 Alert POC Project Documentation & Setup Guide

## 📖 1. Project Overview (As-Is State)
This project is an end-to-end event-driven **Notification System** built around Apache Kafka. It is designed to decouple event production from notification delivery. 
It consists of two main components:
1. **Notification Producer Service** (`Producer_Setup`): A scheduler-based application that picks up pending events from an internal database and publishes them to a Kafka topic.
2. **Notification Consumer Service** (`notification-consumer`): A configurable consumer that reads events from Kafka, dynamically maps payloads to message templates stored in a database, and processes SMS/Email notifications based on the metadata.

### 🏗️ Architecture Flow
1. **Data Source (H2)**: The Producer relies on records being inserted into an in-memory SQL database (H2) with a status of `PENDING`.
2. **Producer**: Runs a scheduler (every 10 seconds) ➔ Fetches PENDING events ➔ Publishes them to a Kafka topic (`notifications.events`) ➔ Updates their status in H2 to `PUBLISHED`.
3. **Kafka Broker**: Acts as the message broker mediating events asynchronously locally on port `9092`.
4. **Consumer**: Constantly polls `notifications.events`. It extracts properties (like `event-type` and `alert-type` in the Kafka Headers) to query the setup logically.
5. **Database (MySQL)**: Contains routing configurations and notification templates (`template_master`, `routing_key_config`, `app_config`).
6. **Processing**: Constructs a dynamic `NotificationRequest` combining predefined template rules and recursive payload JSON search for parameter extraction, ready to be sent over SMS or Email endpoints.

---

## 🛠️ 2. Tech Stack & Prerequisites
* **Java SDK**: Java 17 up to Java 21
* **Maven**: For dependecy installation natively
* **Docker Engine & Docker-Compose**: For spinning up Kafka & Kafka UI locally
* **MySQL Server**: Running physically or practically mapped to `localhost:3306`

---

## 🚀 3. Step-by-Step Setup Guide

### Step 1: Start Infrastructure (Kafka Cluster)
Navigate to the `Docker` directory and spin up Kafka and Kafka UI:
```bash
cd Docker
docker compose up -d
```
*Wait a few seconds for Kafka to initialize properly.*

**Create the required Kafka Topic:**
```bash
docker exec -it kafka-broker kafka-topics \
  --bootstrap-server localhost:9092 \
  --create \
  --topic notifications.events \
  --partitions 8 \
  --replication-factor 1
```

*(You can verify your Kafka setup by opening Kafka UI at: http://localhost:8081)*

### Step 2: Configure the Consumer Database (MySQL)
The Consumer requires a running MySQL database to map events functionally to actual physical strings and messaging routing configs.
1. Run your local MySQL instance. Create a database/schema uniquely named `alert`.
2. Ensure you have user `root` with the password `root1234` or adjust these directly via [notification-consumer/src/main/resources/application.yml](file:///Users/manaspeeyushpandey/Desktop/alertpocbank/notification-consumer/src/main/resources/application.yml) respectively.
3. Open MySQL Workbench/Client and execute your initial table structures using the scripts located in:
   - [notification-consumer/src/main/resources/Scripts/routing_key_config.sql](file:///Users/manaspeeyushpandey/Desktop/alertpocbank/notification-consumer/src/main/resources/Scripts/routing_key_config.sql)
   - [notification-consumer/src/main/resources/Scripts/template_master.sql](file:///Users/manaspeeyushpandey/Desktop/alertpocbank/notification-consumer/src/main/resources/Scripts/template_master.sql)
4. Make sure that valid template definitions and keys exist here before executing requests heavily.

### Step 3: Run the Notification Consumer
Navigate to the consumer directory and boot it up via Maven Wrapper:
```bash
cd notification-consumer
mvn clean spring-boot:run
```
*The Consumer will start successfully on port `8083` and connect synchronously to your Kafka broker (port 9092) and MySQL (port 3306).*

### Step 4: Run the Notification Producer
Open a new terminal shell session, navigate to the producer directory, and run the service.
```bash
cd Producer_Setup
mvn clean spring-boot:run
```
*The Producer will launch successfully on standard port `8080`, spawning up the in-memory H2 database interface.*

### Step 5: End-to-End Trigger & Testing
Since the Producer iteratively checks for `PENDING` records in its H2 database, you have to add manual triggers manually.
1. Access the Producer's integrated H2 Database Console inside your web browser: `http://localhost:8080/h2-console`
2. **Login Details:**
   - **JDBC URL:** `jdbc:h2:mem:alertdb`
   - **User Name:** `sa`
   - **Password:** *[Leave Empty]*
3. Insert a mock insert query record providing a status `'PENDING'` appropriately inside your payload events table.
4. Wait for about 10 seconds.
5. In your terminals, you should observe the Producer logs actively fetch the record, push that safely to Kafka (`PUBLISHED`), and consecutively, the Consumer logs should intercept from `notifications.events` and format the target messages securely!

---

## 🧹 4. Teardown / Clean Cleanup
When finished with the demo environments, tear down the containers fully securely:
```bash
cd Docker
# Remove the container instances and local mounted volumes safely
docker compose down -v
```
