package controlador;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    personaSeleccionada();
                }
            }
        });
        this.vistaP.getjButtonBuscarPer().addActionListener(b -> buscarPersona());
        this.vistaP.getjCheckBoxMostrarTP().addActionListener(bc -> buscarPersona());
        this.vistaP.getjButtonLimpiarPer1().addActionListener(lp -> limpiarBusquedaPer());
        this.vistaP.getjButtonReporteGeneral().addActionListener(lr-> reporteGeneral());
        this.vistaP.getjButtonReporteIndividual().addActionListener(li -> reporteIndividual());
        
    }

    //M É T O D O S  C R U D
    //CREAR PERSONA
    public void crearPersona() {
        if (camposVacios() == false) {
            Resouces.warning("ATENCIÓN!!", "Por favor, llene todos los campos");
        } else {
            if (validarCampos() == true) {
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
    }

    //EDITAR PERSONA
    public void editarPersona() {
        if (camposVacios() == false) {
            Resouces.warning("ATENCIÓN!!", "Por favor, llene todos los campos");
        } else {
            if (validarCampos() == true) {
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
                modeloTP.setFilas(modeloPersona.buscarPersonaCed(this.vistaP.getjTextFieldBusquedaPer().getText()));
                modeloTP.fireTableDataChanged();
            } else {
                limpiarBusquedaPer();
            }
        }
    }

    // REPORTESSSS
    //llamar
    public void reporteGeneral() {
        Resouces.imprimirReporte(ManagerFactory.getConnection(manage.getEmf().createEntityManager()), "/reportes/Personas.jasper",new HashMap());
    }
    
    public void reporteIndividual(){
        if(per != null){
            Map parameters = new HashMap();
            parameters.put("id",per.getIdpersona());
            Resouces.imprimirReporte(ManagerFactory.getConnection(manage.getEmf().createEntityManager()), "/reportes/pIndividual.jasper",parameters);
   
        }else{
            Resouces.warning("Atención!!", "Debe seleccionar una persona");
        }
    }

    public boolean validarCampos() {
        Validaciones validar = new Validaciones();
        boolean validado = false;

        if (validar.ValidarTextoConEspacio(this.vistaP.getjTextFieldNombres().getText())) {

            if (validar.ValidarTextoConEspacio(this.vistaP.getjTextFieldApellidos().getText())) {

                //segunda  valid
                if (validar.validarCedula(this.vistaP.getjTextFieldCedula().getText())) {

                    //Segunda valid
                    if (validar.validarCelu(this.vistaP.getjTextFieldCelular().getText())) {

                        //Segunda valid
                        if (validar.validarEmail(this.vistaP.getjTextFieldCorreo().getText())) {

                            //Segunda valid
                            if (validar.validarDirec(this.vistaP.getjTextFieldDireccion().getText())) {
                                //Segunda valid
                                validado = true;
                            } else {
                                Resouces.warning("Atención", "Dirección Incorrecta");
                            }

                        } else {
                            Resouces.warning("Atención", "Correo Incorrecto");
                        }

                    } else {
                        Resouces.warning("Atención", "Nro Celular Incorrecto");
                    }

                } else {
                    Resouces.warning("Atención", "Cédula Incorrecta");
                }

            } else {
                Resouces.warning("Atención", "Apellido Incorrecto");
            }

        } else {
            Resouces.warning("Atención", "Nombre Incorrecto");
        }

        return validado;
    }

    public boolean camposVacios() {
        boolean validar = true;
        if (this.vistaP.getjTextFieldNombres().getText().isEmpty()) {
            validar = false;
        }
        if (this.vistaP.getjTextFieldApellidos().getText().isEmpty()) {
            validar = false;
        }
        if (this.vistaP.getjTextFieldCedula().getText().isEmpty()) {
            validar = false;
        }
        if (this.vistaP.getjTextFieldCorreo().getText().isEmpty()) {
            validar = false;
        }
        if (this.vistaP.getjTextFieldCelular().getText().isEmpty()) {
            validar = false;
        }
        if (this.vistaP.getjTextFieldDireccion().getText().isEmpty()) {
            validar = false;
        }
        return validar;
    }

}
