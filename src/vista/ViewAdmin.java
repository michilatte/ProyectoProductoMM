package vista;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author miri
 */
public class ViewAdmin extends javax.swing.JFrame {

    /**
     * Creates new form ViewAdmin
     */
    public ViewAdmin() {
        initComponents();
    }

    public JDesktopPane getjDesktopPaneAdmin() {
        return jDesktopPaneAdmin;
    }

    public void setjDesktopPaneAdmin(JDesktopPane jDesktopPaneAdmin) {
        this.jDesktopPaneAdmin = jDesktopPaneAdmin;
    }

    public JMenuBar getjMenuBar1() {
        return jMenuBar1;
    }

    public void setjMenuBar1(JMenuBar jMenuBar1) {
        this.jMenuBar1 = jMenuBar1;
    }

    public JMenu getjMenuConfiguracion() {
        return jMenuConfiguracion;
    }

    public void setjMenuConfiguracion(JMenu jMenuConfiguracion) {
        this.jMenuConfiguracion = jMenuConfiguracion;
    }

    public JMenuItem getjMenuItem3() {
        return jMenuItem3;
    }

    public void setjMenuItem3(JMenuItem jMenuItem3) {
        this.jMenuItem3 = jMenuItem3;
    }

    public JMenuItem getjMenuItemPersonas() {
        return jMenuItemPersonas;
    }

    public void setjMenuItemPersonas(JMenuItem jMenuItemPersonas) {
        this.jMenuItemPersonas = jMenuItemPersonas;
    }

    public JMenuItem getjMenuItemProductos() {
        return jMenuItemProductos;
    }

    public void setjMenuItemProductos(JMenuItem jMenuItemProductos) {
        this.jMenuItemProductos = jMenuItemProductos;
    }

    public JMenuItem getjMenuItemUsuario() {
        return jMenuItemUsuario;
    }

    public void setjMenuItemUsuario(JMenuItem jMenuItemUsuario) {
        this.jMenuItemUsuario = jMenuItemUsuario;
    }

    public JMenu getjMenuReportes() {
        return jMenuReportes;
    }

    public void setjMenuReportes(JMenu jMenuReportes) {
        this.jMenuReportes = jMenuReportes;
    }

    public JButton getjButtonAtras() {
        return jButtonAtras;
    }

    public void setjButtonAtras(JButton jButtonAtras) {
        this.jButtonAtras = jButtonAtras;
    }
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPaneAdmin = new javax.swing.JDesktopPane();
        jButtonAtras = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuConfiguracion = new javax.swing.JMenu();
        jMenuItemPersonas = new javax.swing.JMenuItem();
        jMenuItemUsuario = new javax.swing.JMenuItem();
        jMenuItemProductos = new javax.swing.JMenuItem();
        jMenuReportes = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jDesktopPaneAdmin.setBackground(new java.awt.Color(0, 29, 61));

        jButtonAtras.setBackground(new java.awt.Color(0, 30, 67));
        jButtonAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewImages/atras.png"))); // NOI18N
        jButtonAtras.setBorder(null);

        jDesktopPaneAdmin.setLayer(jButtonAtras, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPaneAdminLayout = new javax.swing.GroupLayout(jDesktopPaneAdmin);
        jDesktopPaneAdmin.setLayout(jDesktopPaneAdminLayout);
        jDesktopPaneAdminLayout.setHorizontalGroup(
            jDesktopPaneAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPaneAdminLayout.createSequentialGroup()
                .addContainerGap(861, Short.MAX_VALUE)
                .addComponent(jButtonAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jDesktopPaneAdminLayout.setVerticalGroup(
            jDesktopPaneAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPaneAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAtras)
                .addContainerGap(537, Short.MAX_VALUE))
        );

        jMenuConfiguracion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jMenuConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
        jMenuConfiguracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewImages/confi.png"))); // NOI18N
        jMenuConfiguracion.setText("CONFIGURACIÓN");

        jMenuItemPersonas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewImages/grupo.png"))); // NOI18N
        jMenuItemPersonas.setText("PERSONAS");
        jMenuConfiguracion.add(jMenuItemPersonas);

        jMenuItemUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewImages/usuario.png"))); // NOI18N
        jMenuItemUsuario.setText("USUARIOS");
        jMenuConfiguracion.add(jMenuItemUsuario);

        jMenuItemProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewImages/prod.png"))); // NOI18N
        jMenuItemProductos.setText("PRODUCTOS");
        jMenuConfiguracion.add(jMenuItemProductos);

        jMenuBar1.add(jMenuConfiguracion);

        jMenuReportes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jMenuReportes.setForeground(new java.awt.Color(0, 0, 0));
        jMenuReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewImages/reporte.png"))); // NOI18N
        jMenuReportes.setText("REPORTES");

        jMenuItem3.setText("jMenuItem1");
        jMenuReportes.add(jMenuItem3);

        jMenuBar1.add(jMenuReportes);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPaneAdmin)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPaneAdmin)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ViewAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ViewAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ViewAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ViewAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ViewAdmin().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAtras;
    private javax.swing.JDesktopPane jDesktopPaneAdmin;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuConfiguracion;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItemPersonas;
    private javax.swing.JMenuItem jMenuItemProductos;
    private javax.swing.JMenuItem jMenuItemUsuario;
    private javax.swing.JMenu jMenuReportes;
    // End of variables declaration//GEN-END:variables
}
