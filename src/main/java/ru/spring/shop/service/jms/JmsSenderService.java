package ru.spring.shop.service.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ru.api.product.dto.ProductDto;
import ru.spring.shop.config.jms.JmsConfig;


@Component
@RequiredArgsConstructor
public class JmsSenderService {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

//    public void sendProductMessage(ProductDto productDto) {
//        jmsTemplate.convertAndSend(JmsConfig.JMS_PRODUCT_QUEUE, new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                try {
//                    TextMessage textMessage = session.createTextMessage(objectMapper.writeValueAsString(productDto));
//                    textMessage.setStringProperty("_type", ProductDto.class.getCanonicalName());
//                    return textMessage;
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                    throw new JMSException(e.getMessage());
//                }
//            }
//        });
//    }

    public void sendProductMessage(ProductDto productDto) {
        try {
            jmsTemplate.convertAndSend(JmsConfig.JMS_PRODUCT_QUEUE, objectMapper.writeValueAsString(productDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
