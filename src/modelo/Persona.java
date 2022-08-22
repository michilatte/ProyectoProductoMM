/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "persona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findByIdpersona", query = "SELECT p FROM Persona p WHERE p.idpersona = :idpersona"),
    @NamedQuery(name = "Persona.findByNombrePer", query = "SELECT p FROM Persona p WHERE p.nombrePer = :nombrePer"),
    @NamedQuery(name = "Persona.findByApellidoPer", query = "SELECT p FROM Persona p WHERE p.apellidoPer = :apellidoPer"),
    @NamedQuery(name = "Persona.findByCedulaPer", query = "SELECT p FROM Persona p WHERE p.cedulaPer = :cedulaPer"),
    @NamedQuery(name = "Persona.findByCelularPer", query = "SELECT p FROM Persona p WHERE p.celularPer = :celularPer"),
    @NamedQuery(name = "Persona.findByCorreoPer", query = "SELECT p FROM Persona p WHERE p.correoPer = :correoPer"),
    @NamedQuery(name = "Persona.findByDireccionPer", query = "SELECT p FROM Persona p WHERE p.direccionPer = :direccionPer")})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpersona")
    private Integer idpersona;
    @Column(name = "nombre_per")
    private String nombrePer;
    @Column(name = "apellido_per")
    private String apellidoPer;
    @Column(name = "cedula_per")
    private String cedulaPer;
    @Column(name = "celular_per")
    private String celularPer;
    @Column(name = "correo_per")
    private String correoPer;
    @Column(name = "direccion_per")
    private String direccionPer;
    @OneToMany(mappedBy = "idpersonaUsu")
    private List<Usuario> usuarioList;

    public Persona() {
    }

    public Persona(Integer idpersona) {
        this.idpersona = idpersona;
    }

    public Integer getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Integer idpersona) {
        this.idpersona = idpersona;
    }

    public String getNombrePer() {
        return nombrePer;
    }

    public void setNombrePer(String nombrePer) {
        this.nombrePer = nombrePer;
    }

    public String getApellidoPer() {
        return apellidoPer;
    }

    public void setApellidoPer(String apellidoPer) {
        this.apellidoPer = apellidoPer;
    }

    public String getCedulaPer() {
        return cedulaPer;
    }

    public void setCedulaPer(String cedulaPer) {
        this.cedulaPer = cedulaPer;
    }

    public String getCelularPer() {
        return celularPer;
    }

    public void setCelularPer(String celularPer) {
        this.celularPer = celularPer;
    }

    public String getCorreoPer() {
        return correoPer;
    }

    public void setCorreoPer(String correoPer) {
        this.correoPer = correoPer;
    }

    public String getDireccionPer() {
        return direccionPer;
    }

    public void setDireccionPer(String direccionPer) {
        this.direccionPer = direccionPer;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpersona != null ? idpersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.idpersona == null && other.idpersona != null) || (this.idpersona != null && !this.idpersona.equals(other.idpersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombrePer+" "+this.apellidoPer;
    }
    
}
