package edu.eci.arsw.exam;

import edu.eci.arsw.exam.events.OffertMessageProducer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.amqp.AmqpException;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;

public class MainFrame extends javax.swing.JFrame {

    private OffertMessageProducer mproducer = null;
    private FachadaPersistenciaOfertas fpers = null;
    private static DefaultListModel<String> ofertasModel;

    public void setMessageProducer(OffertMessageProducer mproducer) {
        this.mproducer = mproducer;
    }

    public void setFachadaPersistenciaOfertas(FachadaPersistenciaOfertas fpers) {
        this.fpers = fpers;
    }

    public MainFrame() {
        initComponents();
        ofertasModel = new DefaultListModel<>();
        jListOfertas.setModel(ofertasModel);
    }

    public static void mostrarGanadorEnUI(String productCode, Oferta ganador) {
        SwingUtilities.invokeLater(() -> {
            ofertasModel.addElement("GANADOR - Producto: " + productCode + " | Comprador: " +
                    ganador.getCompradorId() + " | Monto: $" + ganador.getMonto());
        });
    }

    private void botonEnvioActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Product ts = new Product(this.codigoTareaTF.getText(),
                    this.descripcionTareaTF.getText(),
                    Integer.parseInt(this.startPriceField.getText()));

            mproducer.sendMessages(ts);
            fpers.getMapaProductosSolicitados().put(this.codigoTareaTF.getText(), ts);

        } catch (AmqpException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showInputDialog("Error:" + ex.getLocalizedMessage());
        }
    }

    private void codigoTareaTFActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descripcionTareaTF = new javax.swing.JTextArea();
        botonEnvio = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel2 = new javax.swing.JLabel();
        codigoTareaTF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        startPriceField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jListOfertas = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Descripcion");

        descripcionTareaTF.setColumns(20);
        descripcionTareaTF.setRows(5);
        jScrollPane1.setViewportView(descripcionTareaTF);

        botonEnvio.setText("Enviar");
        botonEnvio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEnvioActionPerformed(evt);
            }
        });

        jLabel2.setText("Ofertas aceptadas");

        jLabel3.setText("Codigo del producto");

        jLabel4.setText("Precio inicial");

        jScrollPane2.setViewportView(jListOfertas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane2)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel2)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
                                                                .addComponent(botonEnvio))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(startPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE)))
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel3))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(codigoTareaTF, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(codigoTareaTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(startPriceField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(botonEnvio, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15))
        );

        pack();
    }

    private javax.swing.JButton botonEnvio;
    private javax.swing.JTextField codigoTareaTF;
    private javax.swing.JTextArea descripcionTareaTF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField startPriceField;
    private javax.swing.JList<String> jListOfertas;
}