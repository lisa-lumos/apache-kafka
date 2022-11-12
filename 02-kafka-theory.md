# 2. Kafka Theory

## Kafka Topics
A `topic` is a particular stream of data, there's no limit in num of topics. A topic is identified by its `name`. Topic can contain any message format. The sequence of messages is called a `data stream`. 

You cannot query topics, instead, use `Kafka Producers` to send data and `Kafka Consumers` to read the data. 

Data in kept in Kafka only for a limited time (default is 1 week - configurable).

## Partitions and offsets
Topics are split into `partitions` (e.g.: 3 partitions, there's no limit on its num), the messages in each partition are `ordered`, with each message within a partition gets an incremental id, called `offset`. 

Offset only have a meaning for a specific partition. Offsets are not reused even if previous messages have been deleted. 

Order is guaranteed only within a partition (not across partitions). Data is assigned randomly to a partition unless a key is provided. 

Kafka topics are `immutable`: Once data is written to a partition, it cannot be changes. 

## Producers
- `Producers` write data to topics (which are made of partitions)
- Producers know to which partition to write to, and which Kafka broker has it
- In case of Kafka broker failures, Producers will recover automatically.

### Message keys
Producers can choose to send a `key` with the message (string, number, binary, etc)
- If `key=null`, then data is sent with round robin (partition 0, then 1, then 2, ...)
- if `key!=null`, then all messages for that key will always end into one partition (hashing)

A key is typically sent when you need `message ordering` for a specific field (e.g: truck_id)

### What is in a Kafka message
- Key
- Value (Message content)
- Compression type
- Header (optional)
- Partition and offset
- Time stamp

## Kafka Message Serializer
Kafka only accepts bytes as an input from producers and sends bytes out as an output to consumers. `Message serialization` means transform objects/data into bytes. Serializer is only used on the value an key of the message. 

Common serializers provided by Kafka:
- String (including JSON)
- Int, Float
- Avro
- Protobuf
- ...

## Consumers
`Consumers` read data from a topic (identified by name) via the `pull model`, they automatically know which broker to read from, and in case of broker failures, consumers know how to recover. Data is read `in order` from low to high offset `within each partitions`. 

## Consumer Deserializer
`Deserializers` transform bites into objects/data, they are bundled with Kafka and can be use by consumers. 

`The serialization/deserialization type must not change during a topic lifecycle`, otherwise need to create a new topic instead. 

## Consumer Groups
All the consumers in an app read data as one `consumer group`. Each consumer within a group reads from `exclusive partitions` - in this way, a group is reading the Kafka topic as a whole. 

If you have more consumers than num of partitions, then some consumers will be inactive. 

There could be multiple consumer groups on the same Kafka topic. 

## Consumer Offsets
Kafka `stores the offsets` at which  a `consumer group` has been reading. The offsets committed are in Kafka topic named `__consumer_offsets`, and when a consumer in a group has processed data received from Kafka, it should be `periodically` committing the offsets, so the Kafka broker  will write to `__consumer_offsets` in the topic. If a consumer dies, it will be able to read back from where it left off thanks to offsets. 

## Delivery semantics for consumers
By default, Java Consumers  will automatically commit offsets (At least once mode). There are 3 delivery semantics if you choose to commit manually:

### 1. At least once (usually prefered)
- Offsets are committed after the message is processed
- If the processing goes wrong, the message will be read again
- May result in duplicate processing of messages, so need to make sure your processing is `idempotent` (processing it again will not impact your systems)

### 2. At most once
- Offsets are committed as soon as messages are received
- If the processing goes wrong, some messages will be lost (they won't be read again)

### 3. Exactly once
- Kafka workflows: use the Transactional API (easy with Kafka Streams API)
- External system workflows: use an idempotent consumer. 

## Kafka Brokers
A Kafka cluster is composed of multiple `brokers` (servers), each broker is identified with it `ID`, which is an `integer`. 

Each broker contains certain topic partitions. e.g., if you have 3 brokers that contains 2 topics in total, assume topic 1 has 3 partitions, and topic 2 has 2 partitions, then broker 1 could have topic 1 partition 1, and topic 2 partition 1, while broker 3 could have topic 1 partition 3 only. The partitions are spread out across all brokers, in any order, this is what makes Kafka scale (Horizontal Scaling). 

After connecting to any broker, you will be connected to the entire cluster, because each broker has all the metadata information of the Kafka cluster. Every Kafka broker is also called a `bootstrap server`. 

A good number to get started is 3 brokers, but some big clusters have over 100 brokers. 

## Topic Replication
Usually in production, you should have a replication factor > 1 (usually 2 or 3). A replication factor of 2 means that you have one replica. In this way, if a broker is down, another broker can have a copy of the data to serve and receive. 

At any time, only one broker can be a leader for a given partition, and producers can only send data to the broker that is leader of a partition, while the other brokers will replicate the data. Therefore, each partition has one leader and multiple in-sync replica (ISR). 

Kafka consumers by default will read from the leader broker of a partition (so it is faster and cheaper). Since Kafka 2.4, it is possible to configure consumers to read from the closest replica (Kafka Consumer Replica Fetching). 

## Producer Acknowledgements
When producers send data to brokers, they can choose to receive acknowledgement of data writes, to have confirmation from the brokers that the write did successfully happen. There are three settings: 
- ack = 0. Producer won't wait for acknowledgement (possible data loss)
- ack = 1. Producer will wait for leader acknowledgement (limited data loss)
- ack = all. Leader and replicas acknowledgement (no data loss) 

## Kafka Topic Durability
For a topic replication factor of 3, topic data durability can withstand 2 brokers loss. As a general rule, for a replication factor of N, you can permanently lose up to N-1 brokers and still recover your data. 

## Zookeeper
Zookeeper has been there since the beginning of Kafka, and is slowly disappearing and will be replaced. Zookeeper is a software that manages Kafka brokers. Zookeeper can help with electing a new leader for partitions in case a broker is down. It also sends notifications to Kafka in case of changes (e.g.: new topic, broker dies, broker comes back, delete topics, etc...). It has a lot of Kafka meta data. 

Now since Kafka 3.X, it can work without Zookeeper, using Kafka Raft (Kraft) instead (KIP-500). Kafka 4.X will not have Zookeeper, but it is not production-ready yet (as of 2022). 

Zookeeper by design operates with an odd number of servers. (1, 3, 5, 7, ...). It has a leader (writes) and the rest of the servers are followers (reads). Zookeeper does NOT store consumer offsets since v0.10. 

With Kafka Clients, over time, the Kafka clients and CLI have been migrated to leverage the brokers as a connection endpoint instead of Zookeeper. 

Therefore, as a modern-day Kafka developer, you should never use Zookeeper as a configuration in your Kafka clients, and other programs tha connect to Kafka.  






















