package controlador;

import java.awt.Dimension;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import viewInterna.*;
import modelo.*;
import modelo.exceptions.*;
import proyecto_producto.ManagerFactory;

/**
 *
 * @author miri
 */
public class ControllerUsuario {

    ViewUsuarios vistaU;
    ManagerFactory manage;
    UsuarioJpaController modeloUsuario;
    Usuario usu;
    ModeloTablaUsuario modeloTU;
    JDesktopPane panelEscritorio;
    ListSelectionModel listausuariomodel;
    Validaciones validacion;

    public ControllerUsuario(ViewUsuarios vistaU, ManagerFactory manage, UsuarioJpaController modeloUsuario, /*Persona per,*/ JDesktopPane panelEscritorio) {
        this.vistaU = vistaU;
        this.manage = manage;
        this.modeloUsuario = modeloUsuario;
        this.panelEscritorio = panelEscritorio;
        this.modeloTU = new ModeloTablaUsuario();
        this.modeloTU.setFilas(modeloUsuario.findUsuarioEntities());

        if (ControllerAdministrador.vu == null) {
            ControllerAdministrador.vu = new ViewUsuarios();
            this.vistaU = ControllerAdministrador.vu;
            this.panelEscritorio.add(this.vistaU);
            //Para centar la vista en la ventana
            Dimension desktopSize = this.panelEscritorio.getSize();
            Dimension FrameSize = this.vistaU.getSize();
            this.vistaU.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            this.vistaU.setTitle("USUARIOS");
            this.vistaU.getjTableDatosUsuarios().setModel(modeloTU);
            ControllerAdministrador.vu.show();
            controlMetodosUsuario();
            cargarCombobox();
        } else {
            ControllerAdministrador.vu.show();
        }
    }

    public void controlMetodosUsuario() {
        this.vistaU.getjButtonCrearUsu().addActionListener(c -> crearUsuario());
        this.vistaU.getjButtonEditarUsu().addActionListener(e -> editarUsuario());
        this.vistaU.getjButtonEliminarUsu().addActionListener(el -> eliminarUsuario());
        this.vistaU.getjButtonLimpiarUsu().addActionListener(lim -> limpiarUsuarios());
        this.vistaU.getjTableDatosUsuarios().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listausuariomodel = this.vistaU.getjTableDatosUsuarios().getSelectionModel();
        listausuariomodel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    usuarioSeleccionado();
                }
            }

        });
        this.vistaU.getjButtonBuscarUsu().addActionListener(b -> buscarUsuario());
        this.vistaU.getjCheckBoxMostrarTU().addActionListener(bc -> buscarUsuario());
        this.vistaU.getjButtonLimpiarUsuB().addActionListener(lp ->limpiarBusquedaUsu());
    }

    //M É T O D O S  C R U D 
    //CREAR PERSONA
    public void crearUsuario() {
        if (validarCampos() == false) {
            Resouces.warning("Atención!!", "Por favor, llene todos los campos");
        } else {
            usu = new Usuario();
            usu.setUsuarioUsu(this.vistaU.getjTextFieldUsuario().getText());
            usu.setClaveUsu(this.vistaU.getjPasswordFieldClave().getText());
            usu.setIdpersonaUsu((Persona) this.vistaU.getjComboBoxPersonasUsu().getSelectedItem());
            modeloUsuario.create(usu);
            modeloTU.agregar(usu);
            limpiarUsuarios();
            Resouces.success("Atención!!", "Usuario Creado Exitosamente");
        }
    }

    //EDITAR PERSONA
    public void editarUsuario() {
        if (validarCampos() == false) {
            Resouces.warning("Atención!!", "Por favor, llene todos los campos");
        } else {
            if (usu != null) {
                usu.setUsuarioUsu(this.vistaU.getjTextFieldUsuario().getText());
                usu.setClaveUsu(this.vistaU.getjPasswordFieldClave().getText());
                usu.setIdpersonaUsu((Persona) this.vistaU.getjComboBoxPersonasUsu().getSelectedItem());
                try {
                    int select = JOptionPane.showConfirmDialog(vistaU, "¿ESTÁS SEGUR@ DE EDITAR LOS DATOS DE ESTE USUARIO?");
                    if (select == JOptionPane.YES_OPTION) {
                        modeloUsuario.edit(usu);
                        modeloTU.eliminar(usu);
                        modeloTU.actualizar(usu);
                        Resouces.success("Atención!!", "Usuario Editado Exitosamente");
                    }
                } catch (Exception e) {
                    Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, e);
                }
                limpiarUsuarios();
            }
        }
    }

    //ELIMINAR PERSONA
    public void eliminarUsuario() {
        if (usu != null) {
            try {
                int select = JOptionPane.showConfirmDialog(vistaU, "¿ESTÁS SEGUR@ DE EDITAR LOS DATOS DE ESTE USUARIO?");
                if (select == JOptionPane.YES_OPTION) {
                    modeloUsuario.destroy(usu.getIdusuario());
                    modeloTU.eliminar(usu);
                    //modeloTU.actualizar(usu);
                    Resouces.success("Atención!!", "Usuario Eliminado Exitosamente");
                }
            } catch (NonexistentEntityException e) {
                Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, e);
            }
            limpiarUsuarios();
        }
    }

    public void cargarCombobox() {
        try {
            Vector v = new Vector();
            v.addAll(new PersonaJpaController(manage.getEmf()).findPersonaEntities());
            this.vistaU.getjComboBoxPersonasUsu().setModel(new DefaultComboBoxModel(v));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Capturando errores cargarCombobox()");
        }
    }

    public void limpiarUsuarios() {
        vistaU.getjTextFieldUsuario().setText("");
        vistaU.getjPasswordFieldClave().setText("");
        vistaU.getjComboBoxPersonasUsu().setSelectedIndex(0);
        usu = null;
        //CONTROL DE BOTONES
        this.vistaU.getjButtonEditarUsu().setEnabled(false);
        this.vistaU.getjButtonEliminarUsu().setEnabled(false);
        this.vistaU.getjButtonCrearUsu().setEnabled(true);
        this.vistaU.getjTableDatosUsuarios().getSelectionModel().clearSelection();
    }

    public void limpiarBusquedaUsu() {
        vistaU.getjTextFieldBusquedaUsus().setText("");
        modeloTU.setFilas(modeloUsuario.findUsuarioEntities());
        modeloTU.fireTableDataChanged();
    }

    public void usuarioSeleccionado() {
        if (this.vistaU.getjTableDatosUsuarios().getSelectedRow() != -1) {
            usu = modeloTU.getFilas().get(this.vistaU.getjTableDatosUsuarios().getSelectedRow());
            this.vistaU.getjTextFieldUsuario().setText(usu.getUsuarioUsu());
            this.vistaU.getjPasswordFieldClave().setText(usu.getClaveUsu());
            this.vistaU.getjComboBoxPersonasUsu().setSelectedItem(usu.getIdpersonaUsu());
            //CONTROLES DE BOTONES
            this.vistaU.getjButtonEditarUsu().setEnabled(true);
            this.vistaU.getjButtonEliminarUsu().setEnabled(true);
            this.vistaU.getjButtonCrearUsu().setEnabled(false);
        }
    }

    public void buscarUsuario() {
        if (this.vistaU.getjCheckBoxMostrarTU().isSelected()) {
            modeloTU.setFilas(modeloUsuario.findUsuarioEntities());
            modeloTU.fireTableDataChanged();
        } else {
            if (!this.vistaU.getjTextFieldBusquedaUsus().getText().equals("")) {
                modeloTU.setFilas(modeloUsuario.buscarUSU(this.vistaU.getjTextFieldBusquedaUsus().getText()));
                modeloTU.fireTableDataChanged();
            } else {
                limpiarBusquedaUsu();
            }
        }
    }

    public boolean validarCampos() {
        boolean validar = true;
        if (this.vistaU.getjTextFieldUsuario().getText().isEmpty()) {
            validar = false;
        } else {
            if (!validacion.validarUsuario(this.vistaU.getjTextFieldUsuario().getText())) {
                Resouces.warning("Atención!!", "Nombre de Usuario Incorrecto");
                validar = false;
            }
        }
        if (this.vistaU.getjPasswordFieldClave().getText().isEmpty()) {
            validar = false;
        }
        else {
            if (!validacion.validarContrasena(this.vistaU.getjPasswordFieldClave().getText())) {
                Resouces.warning("Atención!!", "Contraseña Incorrecta");
                validar = false;
            }
        }
        return validar;
    }

   
}
