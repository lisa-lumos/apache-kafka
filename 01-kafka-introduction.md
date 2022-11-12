# 1. Kafka Introduction

## Brief intro of Kafka
When companies start, and want to move data from one `Source System` to one `Target System`, at first, it is simple - Extract, transform, and load. After a while, as company evolves, it has many `Source Systems` and many `target Systems`, and each source system needs to sent to data to all target systems, there are a lot of integration. e.g., if you have 4 source systems and 6 target systems, you need to write 24 integrations. 

Here are the difficulties involved:
- Protocol - How the data is transported (TCP, HTTP, REST, FTP, JDBC...)
- Data format - How the data is parsed (Binary, CSV, JSON, Avron, Protobuf... )
- Data schema & evolution - How the data is shaped and may change later on
- Each source system will have an increased load from the connections.

## Solution to the problem
Solution: decouple the data streams and systems. 

Now the `source systems` are responsible for sending/producing data into Apache Kafka. Now Apache Kafka is going to have a `data stream` of all of your data from all of your data systems. And if the `target systems` need to receive data, they will tap into the data of Apache Kafka, because it is responsible for `receive and send data`. 

Benefits: more scalable. 

E.g.: Source systems can be website events, pricing data, financial transactions, user interactions; and Target systems can be databases, analytics, email system, and audit. 

##  Why Apache Kafka
- Open source
- Distributed resilient architecture, fault tolerant
- Horizontal scalability: can scale to 100s of brokers, millions of msg/sec
- High performance - real time - latency less than 10ms
- Widely used 

## Apache Kafka Use Cases
- Messaging system
- Activity tracking
- Gather metrics from many different locations
- Application logs gathering
- Stream processing (e.g.: with Kafka Streams API)
- Decoupling of system dependencies
- Integration with Spark, Flink, Storm, Hadoop, and many other Big Data technologies
- Micro-services pub/sub



















