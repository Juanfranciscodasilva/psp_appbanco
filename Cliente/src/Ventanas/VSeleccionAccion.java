package Ventanas;

import Clases.Response;
import cliente.Main;
import javax.swing.JOptionPane;

public class VSeleccionAccion extends javax.swing.JFrame {

    public VSeleccionAccion() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btSaldo = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btTransferencia = new javax.swing.JButton();
        btRetirar = new javax.swing.JButton();
        btIngresar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btSaldo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btSaldo.setText("Consultar Saldo");
        btSaldo.setMaximumSize(new java.awt.Dimension(103, 45));
        btSaldo.setMinimumSize(new java.awt.Dimension(103, 45));
        btSaldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaldoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Realizar acción");
        jLabel3.setToolTipText("");

        btTransferencia.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btTransferencia.setText("Realizar Transferencia");
        btTransferencia.setMaximumSize(new java.awt.Dimension(103, 45));
        btTransferencia.setMinimumSize(new java.awt.Dimension(103, 45));
        btTransferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTransferenciaActionPerformed(evt);
            }
        });

        btRetirar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btRetirar.setText("Retirar Dinero");
        btRetirar.setMaximumSize(new java.awt.Dimension(103, 45));
        btRetirar.setMinimumSize(new java.awt.Dimension(103, 45));
        btRetirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRetirarActionPerformed(evt);
            }
        });

        btIngresar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btIngresar.setText("Ingresar Dinero");
        btIngresar.setMaximumSize(new java.awt.Dimension(103, 45));
        btIngresar.setMinimumSize(new java.awt.Dimension(103, 45));
        btIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIngresarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Bienvenido/a");
        jLabel4.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btTransferencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btSaldo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btRetirar, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                    .addComponent(btIngresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btRetirar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSaldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaldoActionPerformed
        try{
            String cuenta = solicitarNumeroCuenta();
            if(cuenta != null){
                Response respuesta = Main.solicitarSaldo(cuenta);
                if(respuesta.isCorrecto()){
                    JOptionPane.showMessageDialog(null, "El saldo de su cuenta es: "+respuesta.getMensajeCorrecto()+"€.");
                }else{
                    JOptionPane.showMessageDialog(this, respuesta.getMensajeError(), "", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al solicitar el saldo. Por favor intentelo de nuevo.", "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btSaldoActionPerformed

    private void btTransferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTransferenciaActionPerformed
        
    }//GEN-LAST:event_btTransferenciaActionPerformed

    private void btRetirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRetirarActionPerformed
        
    }//GEN-LAST:event_btRetirarActionPerformed

    private void btIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIngresarActionPerformed
        
    }//GEN-LAST:event_btIngresarActionPerformed

    public String solicitarNumeroCuenta(){
        String numeroCuenta = JOptionPane.showInputDialog(null, "Indique su número de cuenta por favor");
        while(numeroCuenta != null && (numeroCuenta.length() != 12 || !isNumeric(numeroCuenta))){
            numeroCuenta = JOptionPane.showInputDialog(null, "El número de cuenta indicado no corresponde con el formato del banco. Por favor indique un número de cuenta correcto");
        }
        return numeroCuenta;
    }
    
    public boolean isNumeric(String cadena){
        try{
            Long.parseLong(cadena);
            return true;
        }catch(Exception ex){
            return false;
        }
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VSeleccionAccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VSeleccionAccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VSeleccionAccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VSeleccionAccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VSeleccionAccion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btIngresar;
    private javax.swing.JButton btRetirar;
    private javax.swing.JButton btSaldo;
    private javax.swing.JButton btTransferencia;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
