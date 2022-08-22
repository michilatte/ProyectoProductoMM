package controlador;

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicListUI;
import viewInterna.*;
import proyecto_producto.*;
import modelo.*;
import modelo.exceptions.NonexistentEntityException;

/**
 *
 * @author miri
 */
public class ControllerPersona {

    ViewPersonas vistaP;
    ManagerFactory manage;
    PersonaJpaController modeloPersona;
    Persona per;
    JDesktopPane panelEscritorio;
    ModeloTablaPersona modeloTP;
    ListSelectionModel listapersonamodel;

    public ControllerPersona(ViewPersonas vistaP, ManagerFactory manage, PersonaJpaController modeloPersona, /*Persona per,*/ JDesktopPane panelEscritorio) {
        //this.vistaP = vistaP;
        this.manage = manage;
        this.modeloPersona = modeloPersona;
        this.panelEscritorio = panelEscritorio;
        this.modeloTP = new ModeloTablaPersona();
        this.modeloTP.setFilas(modeloPersona.findPersonaEntities());

        if (ControllerAdministrador.vp == null) {
            ControllerAdministrador.vp = new ViewPersonas();
            this.vistaP = ControllerAdministrador.vp;
            this.panelEscritorio.add(this.vistaP);
            //Para centrar la vista en la ventana
            Dimension desktopSize = this.panelEscritorio.getSize();
            Dimension FrameSize = this.vistaP.getSize();
            this.vistaP.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            this.vistaP.setTitle("PERSONAS");
            this.vistaP.getjTableDatosPersonas().setModel(modeloTP);
            ControllerAdministrador.vp.show();
            controlMetodosPersona();

        } else {
            ControllerAdministrador.vp.show();
        }
    }

    public void controlMetodosPersona() {
        this.vistaP.getjButtonCrearPer().addActionListener(c -> crearPersona());
        this.vistaP.getjButtonEditarPer().addActionListener(e -> editarPersona());
        this.vistaP.getjButtonEliminarPer().addActionListener(el -> eliminarPersona());
        this.vistaP.getjButtonLimpiarPer().addActionListener(lim -> limpiarPersonas());
        this.vistaP.getjTableDatosPersonas().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listapersonamodel = this.vistaP.getjTableDatosPersonas().getSelectionModel();
        listapersonamodel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    personaSeleccionada();
                }
            }

        });
        this.vistaP.getjButtonBuscarPer().addActionListener(b -> buscarPersona());
        this.vistaP.getjCheckBoxMostrarTP().addActionListener(bc -> buscarPersona());
        this.vistaP.getjButtonLimpiarPer1().addActionListener(lp ->limpiarBusquedaPer());

    }

    //M É T O D O S  C R U D 
    //CREAR PERSONA
    public void crearPersona() {
        if (validarCampos() == false) {
            Resouces.warning("Atención!!", "Por favor, llene todos los campos");
        } else {
            per = new Persona();
            per.setNombrePer(this.vistaP.getjTextFieldNombres().getText());
            per.setApellidoPer(this.vistaP.getjTextFieldApellidos().getText());
            per.setCedulaPer(this.vistaP.getjTextFieldCedula().getText());
            per.setCelularPer(this.vistaP.getjTextFieldCelular().getText());
            per.setCorreoPer(this.vistaP.getjTextFieldCorreo().getText());
            per.setDireccionPer(this.vistaP.getjTextFieldDireccion().getText());

            modeloPersona.create(per);
            modeloTP.agregar(per);
            limpiarPersonas();
            Resouces.success("Atención!!", "Persona Creada Exitosamente");
        }
    }

    //EDITAR PERSONA
    public void editarPersona() {
        if (validarCampos() == false) {
            Resouces.warning("Atención!!", "Por favor, llene todos los campos");
        } else {
            if (per != null) {
                per.setNombrePer(this.vistaP.getjTextFieldNombres().getText());
                per.setApellidoPer(this.vistaP.getjTextFieldApellidos().getText());
                per.setCedulaPer(this.vistaP.getjTextFieldCedula().getText());
                per.setCelularPer(this.vistaP.getjTextFieldCelular().getText());
                per.setCorreoPer(this.vistaP.getjTextFieldCorreo().getText());
                per.setDireccionPer(this.vistaP.getjTextFieldDireccion().getText());

                try {
                    int select = JOptionPane.showConfirmDialog(vistaP, "¿ESTÁS SEGUR@ DE EDITAR LOS DATOS DE ESTA PERSONA?");
                    if (select == JOptionPane.YES_OPTION) {
                        modeloPersona.edit(per);
                        modeloTP.eliminar(per);
                        modeloTP.actualizar(per);
                        limpiarPersonas();
                        Resouces.success("Atención!!", "Persona Editada Exitosamente");
                    }
                } catch (Exception e) {
                    Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

    //ELIMINAR PERSONA
    public void eliminarPersona() {
        if (per != null) {
            try {
                int select = JOptionPane.showConfirmDialog(vistaP, "¿ESTÁS SEGUR@ DE ELIMINAR ESTA PERSONA?");
                if (select == JOptionPane.YES_OPTION) {
                    modeloPersona.destroy(per.getIdpersona());
                    modeloTP.eliminar(per);
                    //modeloTP.actualizar(per);
                    limpiarPersonas();
                    Resouces.success("Atención!!", "Persona Eliminada Exitosamente");
                }
            } catch (NonexistentEntityException e) {
                Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    
    public void limpiarPersonas() {
        vistaP.getjTextFieldNombres().setText("");
        vistaP.getjTextFieldApellidos().setText("");
        vistaP.getjTextFieldCedula().setText("");
        vistaP.getjTextFieldCelular().setText("");
        vistaP.getjTextFieldCorreo().setText("");
        vistaP.getjTextFieldDireccion().setText("");
        per = null;
        //CONTROL DE BOTONES
        this.vistaP.getjButtonEditarPer().setEnabled(false);
        this.vistaP.getjButtonEliminarPer().setEnabled(false);
        this.vistaP.getjButtonCrearPer().setEnabled(true);
        this.vistaP.getjTableDatosPersonas().getSelectionModel().clearSelection();
    }

    public void limpiarBusquedaPer() {
        this.vistaP.getjTextFieldBusquedaPer().setText("");
        modeloTP.setFilas(modeloPersona.findPersonaEntities());
        modeloTP.fireTableDataChanged();
    }

    public void personaSeleccionada() {
        if (this.vistaP.getjTableDatosPersonas().getSelectedRow() != -1) {
            per = modeloTP.getFilas().get(this.vistaP.getjTableDatosPersonas().getSelectedRow());
            this.vistaP.getjTextFieldNombres().setText(per.getNombrePer());
            this.vistaP.getjTextFieldApellidos().setText(per.getApellidoPer());
            this.vistaP.getjTextFieldCedula().setText(per.getCedulaPer());
            this.vistaP.getjTextFieldCelular().setText(per.getCelularPer());
            this.vistaP.getjTextFieldCorreo().setText(per.getCorreoPer());
            this.vistaP.getjTextFieldDireccion().setText(per.getDireccionPer());
            //CONTROLES DE BOTONES
            this.vistaP.getjButtonEditarPer().setEnabled(true);
            this.vistaP.getjButtonEliminarPer().setEnabled(true);
            this.vistaP.getjButtonCrearPer().setEnabled(false);
        }
    }

    public void buscarPersona() {
        if (this.vistaP.getjCheckBoxMostrarTP().isSelected()) {
            modeloTP.setFilas(modeloPersona.findPersonaEntities());
            modeloTP.fireTableDataChanged();
        } else {
            if (!this.vistaP.getjTextFieldBusquedaPer().getText().equals("")) {
                modeloTP.setFilas(modeloPersona.buscarPersona(this.vistaP.getjTextFieldBusquedaPer().getText()));
                modeloTP.fireTableDataChanged();
            } else {
                limpiarBusquedaPer();
            }
        }
    }
    
    public boolean validarCampos() {
        boolean validar = true;
        if (this.vistaP.getjTextFieldNombres().getText().isEmpty()) {
            validar = false;
        } else {
            if (!this.vistaP.getjTextFieldNombres().getText().matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
                JOptionPane.showMessageDialog(vistaP, "Nombre incorrecto");
                validar = false;
            }
        }
        if (this.vistaP.getjTextFieldApellidos().getText().isEmpty()) {
            validar = false;
        } else {
            if (!this.vistaP.getjTextFieldApellidos().getText().matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
                JOptionPane.showMessageDialog(vistaP, "Apellido incorrecto");
                validar = false;
            }
        }
        if (this.vistaP.getjTextFieldCedula().getText().isEmpty()) {
            validar = false;
        } else {
            if (!this.vistaP.getjTextFieldCedula().getText().matches("[^a-zA-Z]|\\d{10}$")) {
                JOptionPane.showMessageDialog(vistaP, "Cédula incorrecta");
                validar = false;
            }
        }
        if (this.vistaP.getjTextFieldCorreo().getText().isEmpty()) {
            validar = false;
        } else {
            if (!this.vistaP.getjTextFieldCorreo().getText().matches("[^@]+@[^@]+\\.[a-zA-Z]{2,}")) {
                JOptionPane.showMessageDialog(vistaP, "Correo incorrecto");
                validar = false;
            }
        }
        if (this.vistaP.getjTextFieldCelular().getText().isEmpty()) {
            validar = false;
        } else {
            if (!this.vistaP.getjTextFieldCelular().getText().matches("[^a-zA-Z]|\\d{10}$")) {
                JOptionPane.showMessageDialog(vistaP, "Nro Celular  incorrecto");
                validar = false;
            }
        }
        return validar;
    }

    
}
