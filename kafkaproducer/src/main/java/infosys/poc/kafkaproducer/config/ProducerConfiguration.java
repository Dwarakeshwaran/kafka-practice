package infosys.poc.kafkaproducer.config;

import infosys.poc.kafkaproducer.controllers.ProducerController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class ProducerConfiguration {

    @Bean
    public Properties getKafkaProperties() throws IOException {

        Properties props = new Properties();

        InputStream stream = ProducerController.class.getClassLoader().getResourceAsStream("application.properties");

        props.load(stream);

        return props;
    }

    @Bean
    public ProducerFactory producerFactory() throws IOException {

        return new DefaultKafkaProducerFactory(getKafkaProperties());

    }

    @Bean
    public KafkaTemplate kafkaTemplate() throws IOException {

        return new KafkaTemplate(producerFactory());
    }


}
