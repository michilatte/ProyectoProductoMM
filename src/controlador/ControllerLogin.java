
package controlador;

import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;
import modelo.*;
import proyecto_producto.ManagerFactory;
import vista.*;
import viewInterna.*;
import controlador.*;

/**
 *
 * @author miri
 */
public class ControllerLogin {
    private ManagerFactory manager;
    private ViewLogin vistaL;
    private UsuarioJpaController modelo;
    private ViewAdmin vistaA;
   

    public ControllerLogin(ManagerFactory manager, ViewLogin vistaL, UsuarioJpaController modelo, ViewAdmin vistaA) {
        this.manager = manager;
        this.vistaL = vistaL;
        this.modelo = modelo;
        this.vistaA = vistaA;
        this.vistaA.setLocationRelativeTo(null);
        this.vistaL.setLocationRelativeTo(null);
        this.vistaL.setVisible(true);
        iniciaControl();
    }    
    public void iniciaControl(){
        vistaL.getjButtonEntrar().addActionListener(le->controlLogin());
        vistaL.getjButtonSalir().addActionListener(ls ->salirLogin() );
        vistaA.getjButtonAtras().addActionListener(lb -> regresar());
    }
    public void controlLogin(){
        String usuario=vistaL.getjTextFieldUsuario().getText();
        String clave=new String(vistaL.getjPasswordFieldClave().getPassword());
        
        try{
            Usuario user = modelo.buscarUsuario(usuario, clave);
        if(user!=null){
            Resouces.success("Usuario Correcto","~ BIENVENID@ ~");
            new ControllerAdministrador(vistaA,manager);
            limpiarLogin();
            vistaA.setVisible(true);
            vistaL.dispose();
        }else{
            Resouces.error("Atención!!", "Usuario Incorrecto");
        } 
        }catch(PersistenceException e){
            Resouces.error("ERROR!!","Conectese a la Base de Datos");
        }
    }
    public void  salirLogin(){
        //JOptionPane.showMessageDialog(vistaL, "~Saliendo del programa~");
        System.exit(0);
    }
    public void regresar(){
        vistaA.dispose();
        vistaL.setVisible(true);
    }
    public void limpiarLogin(){
        vistaL.getjTextFieldUsuario().setText("");
        vistaL.getjPasswordFieldClave().setText("");
    }
}
