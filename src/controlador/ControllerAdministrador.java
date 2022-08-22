
package controlador;

import modelo.*;
import vista.ViewAdmin;
import proyecto_producto.*;
import viewInterna.*;

/**
 *
 * @author miri
 */
public class ControllerAdministrador extends javax.swing.JFrame{
    ViewAdmin vistaA;
    ManagerFactory manager;
    public static ViewPersonas vp;
    public static ViewUsuarios vu;
    public static ViewProductos vpro;

    
    public ControllerAdministrador(ViewAdmin vistaA, ManagerFactory manager) {
        this.vistaA = vistaA;
        this.manager = manager;
        this.vistaA.setExtendedState(MAXIMIZED_BOTH);

        controlEvento();
    }
    
    public void controlEvento(){
        vistaA.getjMenuItemPersonas().addActionListener(p->cargarVistaPersona());
        vistaA.getjMenuItemUsuario().addActionListener(u->cargarVistaUsuario());
        vistaA.getjMenuItemProductos().addActionListener(pro->cargarVistaProducto());
    }
    public void cargarVistaPersona(){
        new ControllerPersona(vp,manager,new PersonaJpaController(manager.getEmf()),vistaA.getjDesktopPaneAdmin());
        System.out.println("");
    }
    public void cargarVistaUsuario(){
        new ControllerUsuario(vu,manager,new UsuarioJpaController(manager.getEmf()),vistaA.getjDesktopPaneAdmin());
        System.out.println("");
    }
    public void cargarVistaProducto(){
        new ControllerProducto(vpro,manager,new ProductoJpaController(manager.getEmf()),vistaA.getjDesktopPaneAdmin());
        System.out.println("");
    }
}
