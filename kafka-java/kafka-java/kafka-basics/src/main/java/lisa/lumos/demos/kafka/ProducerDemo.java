package lisa.lumos.demos.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemo {
    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());
    public static void main(String[] args) {
        log.info("Hello world!");

        // create producer properties
        Properties properties = new Properties();
        // properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        // can also use below instead:
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // create a producer record
        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>("demo_java", "hello world");

        // send data - async operation, so need to flush, otherwise code complete and not yet send
        producer.send(producerRecord);

        // flush (synchronous) and close the producer
        producer.flush(); // wait until data is sent to the producer
        producer.close(); // note the close method also calls flush for you,
        // But flush method is available to you if needed in the future.
    }
}
