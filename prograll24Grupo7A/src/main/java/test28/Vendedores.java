/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test28;



import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Melvin
 */
@Entity
@Table(name = "Vendedores", catalog = "prograll24Grupo7A", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"correo"})})
@NamedQueries({
    @NamedQuery(name = "Vendedores.findAll", query = "SELECT v FROM Vendedores v")})
public class Vendedores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vendedores", nullable = false)
    private Long idVendedores;
    @Basic(optional = false)
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "correo", nullable = false, length = 255)
    private String correo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vendedorId", fetch = FetchType.LAZY)
    private List<Inventario> inventarioList;

    public Vendedores() {
    }

    public Vendedores(Long idVendedores) {
        this.idVendedores = idVendedores;
    }

    public Vendedores(Long idVendedores, String nombre, String correo) {
        this.idVendedores = idVendedores;
        this.nombre = nombre;
        this.correo = correo;
    }

    public Long getIdVendedores() {
        return idVendedores;
    }

    public void setIdVendedores(Long idVendedores) {
        this.idVendedores = idVendedores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Inventario> getInventarioList() {
        return inventarioList;
    }

    public void setInventarioList(List<Inventario> inventarioList) {
        this.inventarioList = inventarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVendedores != null ? idVendedores.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vendedores)) {
            return false;
        }
        Vendedores other = (Vendedores) object;
        if ((this.idVendedores == null && other.idVendedores != null) || (this.idVendedores != null && !this.idVendedores.equals(other.idVendedores))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test28.Vendedores[ idVendedores=" + idVendedores + " ]";
    }
    
}
