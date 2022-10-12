# 4. Kafka CLI

## Kafka topics cli
### Create Kafka topics
To create a topic with 1 partition, with no replications by default: 
```
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --create
```

To create a topic with 3 partitions, with no replications by default:
```
kafka-topics.sh --bootstrap-server localhost:9092 --topic second_topic --create --partitions 3
```

To create a topic with 3 partitions and replication factor of 2 on a Kafka cluster:
```
kafka-topics.sh --bootstrap-server localhost:9092 --topic third_topic --create --partitions 3 --replication-factor 2
```

### List Kafka topics
```
kafka-topics.sh --bootstrap-server localhost:9092 --list
```

### Describe Kafka topics
```
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --describe
kafka-topics.sh --bootstrap-server localhost:9092 --topic second_topic --describe
kafka-topics.sh --bootstrap-server localhost:9092 --describe
```
<img src="images/describe-topic.png">

### Delete Kafka topic
```
kafka-topics.sh --bootstrap-server localhost:9092 --topic third_topic --delete
```





























