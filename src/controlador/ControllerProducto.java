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
public class ControllerProducto {

    ViewProductos vistaPro;
    ManagerFactory manage;
    ProductoJpaController modeloProducto;
    ModeloTablaProducto modeloTPro;
    Producto pro;
    JDesktopPane panelEscritorio;
    ListSelectionModel listaproductomodel;
    Validaciones validacion;

    public ControllerProducto(ViewProductos vistaPro, ManagerFactory manage, ProductoJpaController modeloProducto, /*Persona per,*/ JDesktopPane panelEscritorio) {
        //this.vistaPro = vistaPro;
        this.manage = manage;
        this.modeloProducto = modeloProducto;
        this.panelEscritorio = panelEscritorio;
        this.modeloTPro = new ModeloTablaProducto();
        this.modeloTPro.setFilas(modeloProducto.findProductoEntities());

        if (ControllerAdministrador.vpro == null) {
            ControllerAdministrador.vpro = new ViewProductos();
            this.vistaPro = ControllerAdministrador.vpro;
            this.panelEscritorio.add(this.vistaPro);
            //Para centar la vista en la ventana
            Dimension desktopSize = this.panelEscritorio.getSize();
            Dimension FrameSize = this.vistaPro.getSize();
            this.vistaPro.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
            this.vistaPro.setTitle("PRODUCTOS");
            this.vistaPro.getjTableDatosProductos().setModel(modeloTPro);
            ControllerAdministrador.vpro.show();
            controlMetodosProducto();

        } else {
            ControllerAdministrador.vpro.show();
        }
    }

    public void controlMetodosProducto() {
        this.vistaPro.getjButtonCrearPro().addActionListener(c -> crearProducto());
        this.vistaPro.getjButtonEditarPro().addActionListener(e -> editarProducto());
        this.vistaPro.getjButtonEliminarPro().addActionListener(el -> eliminarProducto());
        this.vistaPro.getjButtonLimpiarPro().addActionListener(lim -> limpiarProductos());
        this.vistaPro.getjTableDatosProductos().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaproductomodel = this.vistaPro.getjTableDatosProductos().getSelectionModel();
        listaproductomodel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    productoSeleccionado();
                }
            }

        });
        this.vistaPro.getjButtonBuscarPro().addActionListener(b -> buscarProducto());
        this.vistaPro.getjCheckBoxMostrarT().addActionListener(bc -> buscarProducto());
        this.vistaPro.getjButtonLimpiarProB().addActionListener(lp ->limpiarBusquedaPro());
        this.vistaPro.getjButtonReportarProGeneral().addActionListener(lr-> reporteGeneral());
        this.vistaPro.getjButtonReporteIndivPro().addActionListener(lr-> reporteIndividual());
    }

    //M É T O D O S  C R U D 
    //CREAR PERSONA
    public void crearProducto() {
        if (validarCampos() == false) {
            Resouces.warning("Atención!!", "Por favor, llene todos los campos");
        } else {
            pro = new Producto();
            pro.setNombrePro(this.vistaPro.getjTextFieldNombrePro().getText());
            pro.setPrecioPro(Double.parseDouble(this.vistaPro.getjTextFieldPrecioPro().getText()));
            pro.setCantidadPro(Integer.parseInt(this.vistaPro.getjSpinnerCantidad().getValue().toString()));

            modeloProducto.create(pro);
            modeloTPro.agregar(pro);
            limpiarProductos();
            Resouces.success("Atención!!", "Producto Creado Exitosamente");
        }
    }

    //EDITAR PERSONA
    public void editarProducto() {
        if (validarCampos() == false) {
            Resouces.warning("Atención!!", "Por favor, llene todos los campos");
        } else {
            if (pro != null) {
                pro.setNombrePro(this.vistaPro.getjTextFieldNombrePro().getText());
                pro.setPrecioPro(Double.parseDouble(this.vistaPro.getjTextFieldPrecioPro().getText()));
                pro.setCantidadPro(Integer.parseInt(this.vistaPro.getjSpinnerCantidad().getValue().toString()));
                try {
                    int select = JOptionPane.showConfirmDialog(vistaPro, "¿ESTÁS SEGUR@ DE EDITAR LOS DATOS DE ESTE PRODUCTO?");
                    if (select == JOptionPane.YES_OPTION) {
                        modeloProducto.edit(pro);
                        modeloTPro.eliminar(pro);
                        modeloTPro.actualizar(pro);
                        Resouces.success("Atención!!", "Producto Editado Exitosamente");
                    }
                } catch (Exception e) {
                    Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, e);
                }
                limpiarProductos();
            }
        }
    }

    //ELIMINAR PERSONA
    public void eliminarProducto() {
        if (pro != null) {
            try {
                int select = JOptionPane.showConfirmDialog(vistaPro, "¿ESTÁS SEGUR@ DE EDITAR LOS DATOS DE ESTA PERSONA?");
                if (select == JOptionPane.YES_OPTION) {
                    modeloProducto.destroy(pro.getIdproducto());
                    modeloTPro.eliminar(pro);
                    //modeloTPro.actualizar(pro);
                    limpiarProductos();
                    Resouces.success("Atención!!", "Producto Eliminado Exitosamente");
                }
            } catch (NonexistentEntityException e) {
                Logger.getLogger(ControllerPersona.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void reporteGeneral() {
        Resouces.imprimirReporte(ManagerFactory.getConnection(manage.getEmf().createEntityManager()), "/reportes/Productos.jasper",new HashMap());
    }
    public void reporteIndividual(){
        if(pro != null){
            Map parameters = new HashMap();
            parameters.put("cod",pro.getIdproducto());
            Resouces.imprimirReporte(ManagerFactory.getConnection(manage.getEmf().createEntityManager()), "/reportes/proIndividual.jasper",parameters);
   
        }else{
            Resouces.warning("Atención!!", "Debe seleccionar un producto");
        }
    }
    
    public void limpiarProductos() {
        vistaPro.getjTextFieldNombrePro().setText("");
        vistaPro.getjTextFieldPrecioPro().setText("");
        vistaPro.getjSpinnerCantidad().setValue(0);
        pro = null;
        //CONTROL DE BOTONES
        this.vistaPro.getjButtonEditarPro().setEnabled(false);
        this.vistaPro.getjButtonEliminarPro().setEnabled(false);
        this.vistaPro.getjButtonCrearPro().setEnabled(true);
        this.vistaPro.getjTableDatosProductos().getSelectionModel().clearSelection();
    }

    public void limpiarBusquedaPro() {
        vistaPro.getjTextFieldBusquedaPro().setText("");
        modeloTPro.setFilas(modeloProducto.findProductoEntities());
        modeloTPro.fireTableDataChanged();
    }

    public void productoSeleccionado() {
        if (this.vistaPro.getjTableDatosProductos().getSelectedRow() != -1) {
            pro = modeloTPro.getFilas().get(this.vistaPro.getjTableDatosProductos().getSelectedRow());
            this.vistaPro.getjTextFieldNombrePro().setText(pro.getNombrePro());
            String precio = String.valueOf(pro.getPrecioPro());
            this.vistaPro.getjTextFieldPrecioPro().setText(precio);
            this.vistaPro.getjSpinnerCantidad().setValue(pro.getCantidadPro());
            //CONTROLES DE BOTONES
            this.vistaPro.getjButtonEditarPro().setEnabled(true);
            this.vistaPro.getjButtonEliminarPro().setEnabled(true);
            this.vistaPro.getjButtonCrearPro().setEnabled(false);
        }
    }

    public void buscarProducto() {
        if (this.vistaPro.getjCheckBoxMostrarT().isSelected()) {
            modeloTPro.setFilas(modeloProducto.findProductoEntities());
            modeloTPro.fireTableDataChanged();
        } else {
            if (!this.vistaPro.getjTextFieldBusquedaPro().getText().equals("")) {
                modeloTPro.setFilas(modeloProducto.buscarProducto(this.vistaPro.getjTextFieldBusquedaPro().getText()));
                modeloTPro.fireTableDataChanged();
            } else {
                limpiarBusquedaPro();
            }
        }
    }

    public boolean validarCampos() {
        boolean validar = true;
        if (this.vistaPro.getjTextFieldNombrePro().getText().isEmpty()) {
            validar = false;
        }
        if (this.vistaPro.getjTextFieldPrecioPro().getText().isEmpty()) {
            validar = false;
        } else {
            if (!validacion.validarNumeros(this.vistaPro.getjTextFieldPrecioPro().getText())) {
                JOptionPane.showMessageDialog(vistaPro, "Debe ser un precio numérico");
                validar = false;
            }
        }
        return validar;
    }

    
}
