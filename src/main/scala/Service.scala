import org.apache.kafka.streams._
import org.apache.kafka.streams.scala._
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.Serdes._
import java.util.Properties

class Service {
  def start(): Unit = {
    val builder = new StreamsBuilder()

    // Define your Kafka Streams topology
    // For example, you can create a simple word count application
    val textLines: KStream[String, String] = builder.stream("input-topic")
    val wordCounts: KTable[String, Long] = textLines
      .flatMapValues(textLine => textLine.toLowerCase.split("\\W+"))
      .groupBy((_, word) => word)
      .count()

    wordCounts.toStream.to("output-topic")

    val streams: KafkaStreams = new KafkaStreams(builder.build(), ConfigService.getStreamsConfig())
    streams.start()

    // Gracefully shutdown the Kafka Streams application on JVM exit
    sys.addShutdownHook {
      streams.close()
    }
  }
}