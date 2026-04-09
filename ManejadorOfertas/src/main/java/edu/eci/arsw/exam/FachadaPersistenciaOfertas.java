package edu.eci.arsw.exam;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class FachadaPersistenciaOfertas {

    final private Map<String, Product> mapaProductosSolicitados = new LinkedHashMap<>();
    final private Map<String, String> mapaOferentesAsignados = new LinkedHashMap<>();
    final private Map<String, Integer> mapaMontosAsignados = new LinkedHashMap<>();
    final private Map<String, Integer> mapaOfertasRecibidas = new LinkedHashMap<>();
    final private Map<String, List<Oferta>> ofertasPorProducto = new LinkedHashMap<>();

    private RabbitTemplate rabbitTemplate;

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public Map<String, Product> getMapaProductosSolicitados() {
        return mapaProductosSolicitados;
    }

    public Map<String, Integer> getMapaOfertasRecibidas() {
        return mapaOfertasRecibidas;
    }

    public Map<String, String> getMapaOferentesAsignados() {
        return mapaOferentesAsignados;
    }

    public Map<String, Integer> getMapaMontosAsignados() {
        return mapaMontosAsignados;
    }

    public synchronized boolean agregarOferta(String productCode, Oferta oferta) {
        ofertasPorProducto.putIfAbsent(productCode, new ArrayList<>());

        List<Oferta> ofertas = ofertasPorProducto.get(productCode);

        synchronized (ofertas) {
            if (ofertas.size() >= 3) {
                return false;
            }

            ofertas.add(oferta);
            mapaOfertasRecibidas.put(productCode, ofertas.size());

            Oferta mejorOferta = ofertas.stream()
                    .max(Comparator.comparing(Oferta::getMonto))
                    .orElse(null);

            if (mejorOferta != null) {
                mapaOferentesAsignados.put(productCode, mejorOferta.getCompradorId());
                mapaMontosAsignados.put(productCode, mejorOferta.getMonto());
            }

            if (ofertas.size() == 3) {
                Oferta ganador = mejorOferta;
                System.out.println("SUBASTA FINALIZADA para " + productCode);
                System.out.println("GANADOR: " + ganador.getCompradorId() + " con $" + ganador.getMonto());

                if (rabbitTemplate != null) {
                    rabbitTemplate.convertAndSend("WINNER-EXCHANGE", "winner", ganador);
                    System.out.println("Notificacion enviada al ganador: " + ganador.getCompradorId());
                }
                return true;
            }
        }
        return true;
    }

    public boolean isSubastaCerrada(String productCode) {
        List<Oferta> ofertas = ofertasPorProducto.get(productCode);
        if (ofertas == null) return false;
        synchronized (ofertas) {
            return ofertas.size() >= 3;
        }
    }

    public Oferta getGanador(String productCode) {
        List<Oferta> ofertas = ofertasPorProducto.get(productCode);
        if (ofertas == null || ofertas.size() < 3) return null;
        synchronized (ofertas) {
            return ofertas.stream()
                    .max(Comparator.comparing(Oferta::getMonto))
                    .orElse(null);
        }
    }
}