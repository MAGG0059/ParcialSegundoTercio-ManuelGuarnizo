package edu.eci.arsw.exam.events;

import edu.eci.arsw.exam.IdentityGenerator;
import edu.eci.arsw.exam.Oferta;
import edu.eci.arsw.exam.Product;
import java.util.Random;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class OffertMessageListener implements MessageListener {

    Random rand = new Random(System.currentTimeMillis());
    private RabbitTemplate rabbitTemplate;

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public OffertMessageListener() {
        super();
        System.out.println("Comprador #" + IdentityGenerator.actualIdentity + " esperando eventos...");
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("MENSAJE RECIBIDO");
        try {
            Product receivedProduct = new Product(message.getBody());
            System.out.println("Comprador #" + IdentityGenerator.actualIdentity + " recibio: " + receivedProduct.getCode());

            int startPrice = receivedProduct.getStartPrice();
            int montoOferta = startPrice + Math.abs(rand.nextInt(99999999));

            System.out.println("Ofertando $" + montoOferta + " por " + receivedProduct.getCode());

            Oferta oferta = new Oferta(IdentityGenerator.actualIdentity, receivedProduct.getCode(), montoOferta);

            if (rabbitTemplate != null) {
                rabbitTemplate.convertAndSend("ofertas", oferta);
                System.out.println("Oferta enviada correctamente");
            } else {
                System.err.println("rabbitTemplate es null en onMessage");
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}