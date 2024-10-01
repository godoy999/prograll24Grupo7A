/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test28;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
//import javax.transaction.UserTransaction;

public class FacturaJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public FacturaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Método Create (para insertar una nueva factura en la base de datos)
    public void create(Factura factura) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            // Asociar cliente si es necesario
            if (factura.getClienteId() != null) {
                Clientes cliente = em.getReference(Clientes.class, factura.getClienteId().getIdCliente());
                factura.setClienteId(cliente);
            }
            em.persist(factura);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFactura(factura.getIdFactura()) != null) {
                throw new Exception("Factura con id " + factura.getIdFactura() + " ya existe.");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Método para buscar una factura por su ID
    public Factura findFactura(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factura.class, id);
        } finally {
            em.close();
        }
    }

    // Método para eliminar una factura
    public void delete(Long id) throws EntityNotFoundException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Factura factura = em.getReference(Factura.class, id);
            if (factura == null) {
                throw new EntityNotFoundException("La factura con ID " + id + " no existe.");
            }
            em.remove(factura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Método para actualizar una factura
    public void edit(Factura factura) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Factura actualFactura = em.find(Factura.class, factura.getIdFactura());
            if (actualFactura == null) {
                throw new EntityNotFoundException("Factura con id " + factura.getIdFactura() + " no existe.");
            }
            em.merge(factura);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFactura(factura.getIdFactura()) == null) {
                throw new EntityNotFoundException("Factura con id " + factura.getIdFactura() + " no existe.");
            }
            throw ex;
        } finally {
            em.close();
        }
    }
}

