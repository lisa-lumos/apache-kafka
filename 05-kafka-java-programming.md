# 5. Kafka Java Programming
SDK List of Kafka: The official SDK for Apache Kafka is the Java SDK. For other languages, it's community supported: Scala, C, C++, Golang, Python, Javascript, .NET, C#, Rust, Kotlin, Haskell, Ruby...

Kafka project setup:
- IntelliJ Community IDEA
- Java 11 JDK installed
- Will set up the project using Gradle. 

## Creating Kafka Project
New Project -> Name: kafka-java, Location: (custom), Language: Java, Build system: Gradle, JDK: 11, GroupId: lumos.lisa, ArtifactId: kafka-java -> Create. After the project opens, delete the src folder. 

Right click on the kafka-java project folder, New -> Module..., Name: kafka-basics, Language: Java, Build system: Gradle, JDK: 11 -> Create. 

Google search for "kafka maven" to go to `https://mvnrepository.com/artifact/org.apache.kafka`, click on the hyperlink kafka-clients, click on latest version (3.3.1) hyperlink, click on the Gradle(Short) tab, copy the contents in the box below, and paste it inside dependencies block in the build.gradle file under the kafka-basics folder: 
```
dependencies {
    // https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
    implementation 'org.apache.kafka:kafka-clients:3.3.1'

    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
}
```

Next, in the search bar on top, search for "slf4j api", then click on the "slf4j api" hyperlink, chose the latest non-beta one, copy the contents in the box below the Gradle (Short) tab, and paste it there too:
```
dependencies {
    // https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
    implementation 'org.apache.kafka:kafka-clients:3.3.1'

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation 'org.slf4j:slf4j-api:2.0.5'

    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
}
```

Next, in the search bar on top, search for "slf4j simple", then click on the "slf4j simple" hyperlink, chose the latest non-beta one, copy the contents in the box below the Gradle (Short) tab, and paste it there too:
```
dependencies {
    // https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
    implementation 'org.apache.kafka:kafka-clients:3.3.1'

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation 'org.slf4j:slf4j-api:2.0.5'

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    testImplementation 'org.slf4j:slf4j-simple:2.0.5'

    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
}
```

Remove the test implementations inside the block, and change the "testImplementation" to "implementation" in the third one:
```
dependencies {
    // https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
    implementation 'org.apache.kafka:kafka-clients:3.3.1'

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation 'org.slf4j:slf4j-api:2.0.5'

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation 'org.slf4j:slf4j-simple:2.0.5'
}
```

To pull the dependencies in Gradle, click the "Load Gradle changes" icon in the upper right corner of the editor. 

Right click on the kafka-basics/src/main/java folder, New -> JavaClass, Name: lisa.lumos.demos.kafka.ProducerDemo, and enter. Then a folder named lumos.demos.kafka is created, with ProducerDemo.java file inside. Create a main function inside the class ProducerDemo, and to test whether the code works:
```java
package lisa.lumos.demos.kafka;

public class ProducerDemo {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
```

Run it, and see the output. 

IntelliJ IDEA -> Settings -> Build, Execution, Deployment -> Build Tools -> Gradle -> Choose: Build and run using: IntelliJ IDEA -> Apply -> OK. 

Run the code again, and get a cleaner output window. 