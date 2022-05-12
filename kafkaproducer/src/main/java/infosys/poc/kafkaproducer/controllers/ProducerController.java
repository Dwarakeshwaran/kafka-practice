package infosys.poc.kafkaproducer.controllers;


import infosys.poc.kafkaproducer.model.Match;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RestController
@RequestMapping("kafka")
public class ProducerController {

    @Autowired
    private KafkaTemplate<String, Match> kafkaTemplate;

    private String kafkaTopic = "match-results";

    @GetMapping("/test")
    public String test() {
        return "test";
    }



    @PostMapping("/publish/")
    public String publishMessage(@RequestBody Match match) {

        ProducerRecord record = new ProducerRecord(kafkaTopic,match);

        kafkaTemplate.send(record);


        return match + " - This Message has been Published";

    }

}
