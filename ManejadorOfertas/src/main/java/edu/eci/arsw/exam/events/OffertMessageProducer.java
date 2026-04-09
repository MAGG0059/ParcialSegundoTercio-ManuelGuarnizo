package edu.eci.arsw.exam.events;

import edu.eci.arsw.exam.Product;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

public class OffertMessageProducer {

    private AmqpTemplate amqpTemplate;

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendMessages(Product product) {
        MessageConverter converter = new SimpleMessageConverter();
        MessageProperties props = new MessageProperties();
        Message message = converter.toMessage(product.getBody(), props);
        amqpTemplate.send("productos", message);
        System.out.println("Producto enviado: " + product.getCode());
    }
}