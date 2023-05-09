package ru.spring.shop.service.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.api.product.dto.ProductDto;
import ru.spring.shop.config.jms.JmsConfig;

@Component
@RequiredArgsConstructor
public class JmsListenerService {

    private final ObjectMapper objectMapper;

//    @JmsListener(destination = JmsConfig.JMS_PRODUCT_QUEUE)
//    public void listenProductMessage(//@Payload
//                                               ProductDto productDto) {
//        System.out.println(productDto);
//    }

    @JmsListener(destination = JmsConfig.JMS_PRODUCT_QUEUE)
    public void listenProductMessage(String message) {
        ProductDto productDto;
        try {
            productDto = objectMapper.readValue(message, ProductDto.class);
            System.out.println(productDto);
        } catch (JsonProcessingException e) {
            e.getMessage();
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
