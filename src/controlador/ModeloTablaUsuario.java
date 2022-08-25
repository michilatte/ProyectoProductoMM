/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author 59399
 */
public class ModeloTablaUsuario extends AbstractTableModel {

    private String[] columnas = {"USUARIO", "CONTRASEÑA", "PERSONA"};
    public static List<Usuario> filas;
    private Usuario usuarioSelecionado;
    private int indice;

    public ModeloTablaUsuario() {
        filas = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return filas.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override 
    public Object getValueAt(int rowIndex, int columnIndex) {
        usuarioSelecionado = filas.get(rowIndex);
        this.indice = rowIndex;
        switch (columnIndex) {
            case 0:
                return usuarioSelecionado.getUsuarioUsu();
            case 1:
                return usuarioSelecionado.getClaveUsu();
            case 2:
                return usuarioSelecionado.getIdpersonaUsu();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            default:
                return Object.class;
        }
    }

    public String[] getColumnas() {
        return columnas;
    }

    public void setColumnas(String[] columnas) {
        this.columnas = columnas;
    }

    public List<Usuario> getFilas() {
        return filas;
    }

    public void setFilas(List<Usuario> filas) {
        this.filas = filas;
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void actualizar(Usuario u) {
        setUsuarioSelecionado(null);
        if (u != null) {
            filas.add(indice, u);
            fireTableDataChanged();
        }
    }

    public void agregar(Usuario u) {
        if (u != null) {
            filas.add(u);
            fireTableDataChanged();
        }
    }

    public void eliminar(Usuario u) {
        if (u != null) {
            filas.remove(u);
            fireTableDataChanged();
        }

    }
}
