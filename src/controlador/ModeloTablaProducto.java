/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.Producto;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author 59399
 */
public class ModeloTablaProducto extends AbstractTableModel {

    private String[] columnas = {"NOMBRE", "PRECIO", "CANTIDAD"};
    public static List<Producto> filas;
    private Producto productoSelecionado;
    private int indice;

    public ModeloTablaProducto() {
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
        productoSelecionado = filas.get(rowIndex);
        this.indice = rowIndex;
        switch (columnIndex) {
            case 0:
                return productoSelecionado.getNombrePro();
            case 1:
                return productoSelecionado.getPrecioPro();
            case 2:
                return productoSelecionado.getCantidadPro();
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
                return Long.class;
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

    public List<Producto> getFilas() {
        return filas;
    }

    public void setFilas(List<Producto> filas) {
        this.filas = filas;
    }

    public Producto getProductoSelecionado() {
        return productoSelecionado;
    }

    public void setProductoSelecionado(Producto productoSelecionado) {
        this.productoSelecionado = productoSelecionado;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void actualizar(Producto pro) {
        setProductoSelecionado(null);
        if (pro != null) {
            filas.add(indice, pro);
            fireTableDataChanged();
        }
    }

    public void agregar(Producto pro) {
        if (pro != null) {
            filas.add(pro);
            fireTableDataChanged();
        }
    }

    public void eliminar(Producto pro) {
        if (pro != null) {
            filas.remove(pro);
            fireTableDataChanged();
        }

    }
}
