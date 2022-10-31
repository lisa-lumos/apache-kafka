# 4. Kafka CLI

## Kafka topics CLI
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

## Kafka Console Producer CLI
If produce without keys, then the data will be distributed across all partitions; if produce with key, then the data with the same key always go to the same partition. `kafka-console-producer.sh`. 

### producing
```
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic 
>Hello World
>My name is lisa
>I love Kafka
>^C  (<- Ctrl + C is used to exit the producer)
```

### producing with properties
```
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic --producer-property acks=all
>some message that is acked
>just for fun
>fun learning!
```

### producing to a non existing topic
If you produce to a non existing topic, Kafka will create that topic for you by default, but you will see a few retriable warnings before it is ready. 
```
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic new_topic
>hello world!
```
Our auto created topic only has 1 partition. But you can edit the default settings by editing the config/server.properties or config/kraft/server.properties, and set `num.partitions=3`.

Overall, please create topics before producing to them!

### produce with keys
The message format is `key:value` pairs. If you did not enter the whole pair, there will be an error. 
```
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic --property parse.key=true --property key.separator=:
>example key:example value
>name:lisa
```

## Kafka Console Consumer CLI
`kafka-console-consumer.sh` 

### consuming
The consumer by default will not read messages that are produced before it is started - it will only read new messages. 
```
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic
```

### consuming from beginning
To override the default: 
```
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic --from-beginning
```
Notice that the messages are not displayed in the order that I have created them. This is because we have 3 partitions, and the messages are only ordered within each partition, not across partitions. If you have only one partition, then all the messages will be in order, but then you will lose the scaling aspects of Apache Kafka, with only one partition and only one consumer. 

### display key, values and timestamp in consumer
```
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic --formatter kafka.tools.DefaultMessageFormatter --property print.timestamp=true --property print.key=true --property print.value=true --from-beginning
```
And the return:
```
CreateTime:1666232367197        null    Hello World
CreateTime:1666232371266        null    My name is lisa
CreateTime:1666232374956        null    I love Kafka
CreateTime:1666233633661        null    round
CreateTime:1666233668382        null    Boo
CreateTime:1666234321568        name    lisa
CreateTime:1666232594753        null    a
CreateTime:1666232595476        null    b
CreateTime:1666232597905        null    c
CreateTime:1666234315792        example key     example value
```

## Kafka Consumers in Group
The Kafka topic needs to have more than one topics to start this part. 
### Start one producer and start producing
```
kafka-console-producer.sh --bootstrap-server localhost:9092 --topic first_topic
```

### Start one consumer with a group name
```
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic --group my-first-application
```

### Start another consumer of the same group
See messages being spread:
```
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic --group my-first-application
```
If start more consumers than num of partitions, the additional consumers will stay idle. 

### Start another consumer of different group from beginning
If start another consumer from a different group, then both group receive new messages. Is use from-beginning with first topic, will produce nothing because it has already moved the offset. 
```
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic --group my-second-application --from-beginning
```






















