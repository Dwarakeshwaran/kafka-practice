package infosys.poc.kafkaproducer.controllers;


import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("kafka")
public class ProducerController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private String kafkaTopic = "cricket-results";

    @GetMapping("/test")
    public String test() {
        return "test";
    }



    @PostMapping("/publish/")
    public String publishMessage(@RequestBody String match) {

        ProducerRecord record = new ProducerRecord(kafkaTopic,match);

        kafkaTemplate.send(record);


        return match + " - This Message has been Published";

    }

}
