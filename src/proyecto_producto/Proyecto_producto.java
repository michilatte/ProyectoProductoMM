/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_producto;

import controlador.*;
import modelo.*;
import vista.*;
import viewInterna.*;

/**
 *
 * @author miri
 */
public class Proyecto_producto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ManagerFactory manager = new ManagerFactory();
        ViewLogin vista = new ViewLogin();
        ViewAdmin vistaA = new ViewAdmin();
        
        UsuarioJpaController modelo = new UsuarioJpaController(manager.getEmf());
        ControllerLogin controlador = new ControllerLogin(manager, vista, modelo,vistaA);;
    }
    
}
