package Ventanas;

import Clases.Response;
import Clases.Usuario;
import Utils.EncriptacionUtil;
import cliente.Main;
import javax.swing.JOptionPane;

public class VIniciarSesion extends javax.swing.JFrame {

    public VIniciarSesion() {
        initComponents();
        setLocationRelativeTo(null);
        incorrectUser.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        eUsuario = new javax.swing.JLabel();
        tDni = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        incorrectUser = new javax.swing.JLabel();
        Acceder = new javax.swing.JButton();
        tPass = new javax.swing.JPasswordField();
        Registrar = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        eUsuario.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        eUsuario.setText("DNI:");

        tDni.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setText("Contraseña:");

        incorrectUser.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        incorrectUser.setForeground(new java.awt.Color(255, 0, 0));
        incorrectUser.setText("*Las credenciales no corresponden a ningún usuario");

        Acceder.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        Acceder.setText("Acceder");
        Acceder.setMaximumSize(new java.awt.Dimension(103, 45));
        Acceder.setMinimumSize(new java.awt.Dimension(103, 45));
        Acceder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AccederActionPerformed(evt);
            }
        });

        Registrar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        Registrar.setText("Registrar usuario");
        Registrar.setMaximumSize(new java.awt.Dimension(180, 45));
        Registrar.setMinimumSize(new java.awt.Dimension(180, 45));
        Registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegistrarActionPerformed(evt);
            }
        });

        Salir.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        Salir.setText("Salir");
        Salir.setMaximumSize(new java.awt.Dimension(103, 45));
        Salir.setMinimumSize(new java.awt.Dimension(103, 45));
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("LOGIN");
        jLabel3.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Acceder, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(Registrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(eUsuario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tDni)
                            .addComponent(incorrectUser)
                            .addComponent(tPass, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(31, Short.MAX_VALUE))
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eUsuario)
                    .addComponent(tDni))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tPass, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(incorrectUser)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Registrar, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(Acceder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Salir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AccederActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AccederActionPerformed
        incorrectUser.setVisible(false);
        try{
            String dni = tDni.getText().trim();
            String pass = EncriptacionUtil.encriptarString(tPass.getText().trim());
            Usuario usu = new Usuario(dni,pass);
            usu.setNuevo(false);
            Response respuesta = Main.iniciarSesion(usu);
            if(respuesta.isCorrecto()){
                JOptionPane.showMessageDialog(this, "Usted ha iniciado sesión");
                Main.abrirBanco();
            }else{
                incorrectUser.setText(respuesta.getMensajeError());
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error al iniciar sesión", "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_AccederActionPerformed

    private void RegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistrarActionPerformed
        Main.abrirRegistro();
    }//GEN-LAST:event_RegistrarActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        Main.cerrarPrograma();
    }//GEN-LAST:event_SalirActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VIniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VIniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VIniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VIniciarSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VIniciarSesion().setVisible(true);
            }
        });
    }
    
    public void setUsuario(String usuario){
        tDni.setText(usuario);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Acceder;
    private javax.swing.JButton Registrar;
    private javax.swing.JButton Salir;
    private javax.swing.JLabel eUsuario;
    private javax.swing.JLabel incorrectUser;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField tDni;
    private javax.swing.JPasswordField tPass;
    // End of variables declaration//GEN-END:variables
}
