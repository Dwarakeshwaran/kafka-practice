package infosys.poc.kafkaconsumer.controllers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehose;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseClientBuilder;
import com.amazonaws.services.kinesisfirehose.model.PutRecordRequest;
import com.amazonaws.services.kinesisfirehose.model.Record;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;

@Service
@AllArgsConstructor
public class ConsumerController {

    @Value("${firehose.delivery-stream.name}")
    private String deliveryStreamName;

    public ConsumerController() {
    }

    @KafkaListener(topics = "cricket-results", groupId = "cricket", containerFactory = "kafkaListenerContainerFactory")
    public void listener(String data){

        data = data + "\n";

        System.out.println(data);
        AmazonKinesisFirehose firehoseClient = getFirehoseClient();

        PutRecordRequest putRecordRequest = new PutRecordRequest();
        putRecordRequest.setDeliveryStreamName(deliveryStreamName);

        Record record = new Record().withData(ByteBuffer.wrap(data.getBytes()));
        putRecordRequest.setRecord(record);

        firehoseClient.putRecord(putRecordRequest);

        System.out.println("Data Sent!");


    }

    public AmazonKinesisFirehose getFirehoseClient(){

        return AmazonKinesisFirehoseClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .build();
    }


}
