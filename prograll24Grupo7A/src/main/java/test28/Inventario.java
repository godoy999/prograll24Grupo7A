/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test28;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;

/**
 *
 * @author Melvin
 */
@Entity
@Table(name = "Inventario", catalog = "prograll24Grupo7A", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "Inventario.findAll", query = "SELECT i FROM Inventario i")})
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_inventario", nullable = false)
    private Long idInventario;
    @Basic(optional = false)
    @Column(name = "nombre_producto", nullable = false, length = 255)
    private String nombreProducto;
    @Basic(optional = false)
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    @JoinColumn(name = "vendedor_id", referencedColumnName = "id_vendedores", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Vendedores vendedorId;

    public Inventario() {
    }

    public Inventario(Long idInventario) {
        this.idInventario = idInventario;
    }

    public Inventario(Long idInventario, String nombreProducto, int cantidad) {
        this.idInventario = idInventario;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
    }

    public Long getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Long idInventario) {
        this.idInventario = idInventario;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Vendedores getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Vendedores vendedorId) {
        this.vendedorId = vendedorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInventario != null ? idInventario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventario)) {
            return false;
        }
        Inventario other = (Inventario) object;
        if ((this.idInventario == null && other.idInventario != null) || (this.idInventario != null && !this.idInventario.equals(other.idInventario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test28.Inventario[ idInventario=" + idInventario + " ]";
    }
    
}
