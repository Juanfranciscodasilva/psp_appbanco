package Ventanas;

import Clases.Response;
import Clases.Usuario;
import Utils.EncriptacionUtil;
import cliente.Main;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class VRegistrarUsuario extends javax.swing.JFrame {
    
    private final String dniChars="TRWAGMYFPDXBNJZSQVHLCKE";   

    public VRegistrarUsuario() {
        initComponents();
        setLocationRelativeTo(null);
        errorDni.setVisible(false);
        errorPass.setVisible(false);
        errorNombre.setVisible(false);
        errorApellidos.setVisible(false);
        errorEmail.setVisible(false);
        errorEdad.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tDni = new javax.swing.JTextField();
        errorDni = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tPass1 = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        tPass2 = new javax.swing.JPasswordField();
        errorPass = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tNombre = new javax.swing.JTextField();
        errorNombre = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tApellidos = new javax.swing.JTextField();
        errorApellidos = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tEmail = new javax.swing.JTextField();
        errorEmail = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tEdad = new javax.swing.JTextField();
        errorEdad = new javax.swing.JLabel();
        Registrar = new javax.swing.JButton();
        Cancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Registrar Nuevo Usuario");

        jLabel2.setText("DNI:");

        errorDni.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        errorDni.setForeground(new java.awt.Color(255, 0, 0));
        errorDni.setText("*El DNI no es válido");

        jLabel4.setText("Contraseña:");

        jLabel5.setText("Confirmar Contraseña:");

        errorPass.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        errorPass.setForeground(new java.awt.Color(255, 0, 0));
        errorPass.setText("*Obligatorio: 1 mayus, 1 minus, 1 num, 1 especial, tamaño +8");

        jLabel3.setText("Nombre:");

        errorNombre.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        errorNombre.setForeground(new java.awt.Color(255, 0, 0));
        errorNombre.setText("*El nombre no es válido");

        jLabel6.setText("Apellidos:");

        errorApellidos.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        errorApellidos.setForeground(new java.awt.Color(255, 0, 0));
        errorApellidos.setText("*Los apellidos no son válidos");

        jLabel7.setText("Email:");

        errorEmail.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        errorEmail.setForeground(new java.awt.Color(255, 0, 0));
        errorEmail.setText("*El email no es válido");

        jLabel8.setText("Edad:");

        errorEdad.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        errorEdad.setForeground(new java.awt.Color(255, 0, 0));
        errorEdad.setText("*La edad no es válida");

        Registrar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        Registrar.setText("Registrar");
        Registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegistrarActionPerformed(evt);
            }
        });

        Cancelar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        Cancelar.setText("Cancelar");
        Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(Registrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Cancelar)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(errorNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(errorApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(errorEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(errorEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(errorDni, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tDni, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                                    .addComponent(errorPass)
                                    .addComponent(tPass2)
                                    .addComponent(tPass1))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errorDni)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tPass1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tPass2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errorPass)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errorNombre)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errorApellidos)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errorEmail)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errorEdad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Registrar)
                    .addComponent(Cancelar))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarActionPerformed
        Main.abrirLogin();
    }//GEN-LAST:event_CancelarActionPerformed

    private void RegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistrarActionPerformed
        errorDni.setVisible(false);
        errorPass.setVisible(false);
        errorNombre.setVisible(false);
        errorApellidos.setVisible(false);
        errorEmail.setVisible(false);
        errorEdad.setVisible(false);
        
        if(ValidarDatos()){
            try{
                Usuario u = construirUsuario();
//Usuario u =new Usuario();
//u.setNuevo(true);
                Response respuesta = Main.registrarUsuario(u);
                if(respuesta.isCorrecto()){
                    String cuentaBancaria = respuesta.getMensajeCorrecto();
                    JOptionPane.showMessageDialog(this, "Usted se ha registrado\n\nSu número de cuenta bancaria es: "+cuentaBancaria+"\n\n"
                            + "Es importante que guarde y mantenga su número de cuenta para poder realizar transacciones, por motivos de seguridad "
                            + "se le pedirá el número de cuenta antes de cualquier transacción en su cuenta");
                    Main.abrirLogin();
                }else{
                    JOptionPane.showMessageDialog(this, respuesta.getMensajeError(), "", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(this, "Ha ocurrido un error al registrar el usuario", "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_RegistrarActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VRegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VRegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VRegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VRegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VRegistrarUsuario().setVisible(true);
            }
        });
    }
    
    public Usuario construirUsuario() throws Exception{
        Usuario usu = new Usuario();
        String dni = tDni.getText().trim();
        String pass1 = EncriptacionUtil.encriptarString(tPass1.getText().trim());
        String nombre = tNombre.getText().trim();
        String apellidos = tApellidos.getText().trim();
        String email = tEmail.getText().trim();
        int edad = Integer.parseInt(tEdad.getText().trim());
        usu.setDni(dni);
        usu.setPassword(pass1);
        usu.setNombre(nombre);
        usu.setApellidos(apellidos);
        usu.setEmail(email);
        usu.setEdad(edad);
        usu.setNuevo(true);
        return usu;
    }
    
    public boolean ValidarDatos(){
        String dni = tDni.getText().trim();
        String pass1 = tPass1.getText().trim();
        String pass2 = tPass2.getText().trim();
        String nombre = tNombre.getText().trim();
        String apellidos = tApellidos.getText().trim();
        String email = tEmail.getText().trim();
        String edadString = tEdad.getText().trim();
        
        boolean valido = true;
        if(!validarDni(dni)){
            valido = false;
        }
        if(!validarPassword(pass1,pass2)){
            valido = false;
        }
        if(!validarNombre(nombre)){
            valido = false;
        }
        if(!validarApellidos(apellidos)){
            valido = false;
        }
        if(!validarEmail(email)){
            valido = false;
        }
        if(!validarEdadString(edadString)){
            valido = false;
        }
        
        return valido;
    }
    
    private boolean validarDni(String dni){
        Pattern patron = Pattern.compile("^[0-9]{8}[A-z]{1}$");
        Matcher matcher= patron.matcher(dni);
        if(matcher.matches()){
            int intPartDNI = Integer.parseInt(dni.substring(0, 8));
            int valNumDni = intPartDNI % 23;
            if(dniChars.charAt(valNumDni) == dni.charAt(8)){
                errorDni.setVisible(false);
                return true;
            }else{
                errorDni.setText("La letra no corresponde a un DNI real");
                errorDni.setVisible(true);
                return false;
            }
        }
        errorDni.setText("El DNI no es válido");
        errorDni.setVisible(true);
        return false;
    }
    
    private boolean validarNombre(String nombre) {
        Pattern patron = Pattern.compile("^([a-zA-ZñÑáéíóúÁÉÍÓÚüÜ]{2,25})(([ ]{1}[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ]{2,25}){0,5})$");
        Matcher matcher = patron.matcher(nombre);
        if(matcher.matches()){
            errorNombre.setVisible(false);
            return true;
        }
        errorNombre.setText("*El nombre no es válido");
        errorNombre.setVisible(true);
        return false;
    }
    
    private boolean validarPassword(String pass1, String pass2) {
        Pattern patron = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$");
        Matcher matcher = patron.matcher(pass1);
        if(matcher.matches()){
            if(pass1.equals(pass2)){
                errorPass.setVisible(false);
                return true;
            }else{
                errorPass.setText("*Las contraseñas no coinciden");
                errorPass.setVisible(true);
                return false;
            }
        }
        errorPass.setText("*Obligatorio: 1 mayus, 1 minus, 1 num, 1 especial, tamaño +8");
        errorPass.setVisible(true);
        return false;
    }
    
    private boolean validarApellidos(String apellidos) {
        Pattern patron = Pattern.compile("^([a-zA-ZñÑáéíóúÁÉÍÓÚüÜ]{2,25})(([ ]{1}[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ]{2,25}){0,5})$");
        Matcher matcher = patron.matcher(apellidos);
        if(matcher.matches()){
            errorApellidos.setVisible(false);
            return true;
        }
        errorApellidos.setText("*Los apellidos no son válidos");
        errorApellidos.setVisible(true);
        return false;
    }
    
    private boolean validarEmail(String email) {
        Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = patron.matcher(email);
        if(matcher.matches()){
            errorEmail.setVisible(false);
            return true;
        }
        errorEmail.setText("*El email no es válido");
        errorEmail.setVisible(true);
        return false;
    }
    
    private boolean validarEdadString(String edadString) {     
        Pattern patron = Pattern.compile("^[0-9]{1,3}$");
        Matcher matcher = patron.matcher(edadString);
        if(matcher.matches()){
            int edad = Integer.parseInt(edadString);
            if(edad >= 18){
                errorEdad.setVisible(false);
                return true;
            }else{
                errorEdad.setText("*La edad es menor a 18");
                errorEdad.setVisible(true);
            }
        }
        errorEdad.setText("*La edad no es válida");
        errorEdad.setVisible(true);
        return false;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cancelar;
    private javax.swing.JButton Registrar;
    private javax.swing.JLabel errorApellidos;
    private javax.swing.JLabel errorDni;
    private javax.swing.JLabel errorEdad;
    private javax.swing.JLabel errorEmail;
    private javax.swing.JLabel errorNombre;
    private javax.swing.JLabel errorPass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField tApellidos;
    private javax.swing.JTextField tDni;
    private javax.swing.JTextField tEdad;
    private javax.swing.JTextField tEmail;
    private javax.swing.JTextField tNombre;
    private javax.swing.JPasswordField tPass1;
    private javax.swing.JPasswordField tPass2;
    // End of variables declaration//GEN-END:variables
}
