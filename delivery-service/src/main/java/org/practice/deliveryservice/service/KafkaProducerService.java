package org.practice.deliveryservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.practice.deliveryservice.dto.DeliveryEventDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String,String> kafkaTemplate;

    public void sendDeliveryEvent(DeliveryEventDto deliveryEventDto){
        try{
        ObjectMapper mapper = new ObjectMapper();
        String message=mapper.writeValueAsString(deliveryEventDto);
//        String json=convertToJson(deliveryEventDto);
        kafkaTemplate.send("delivery-events",message);}
        catch (Exception e){
            System.out.println("failed to send delivery event ");
        }
    }

//    private String convertToJson(DeliveryEventDto deliveryEventDto) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.writeValueAsString(deliveryEventDto);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Failed to convert event to JSON", e);
//        }
//    }

}
