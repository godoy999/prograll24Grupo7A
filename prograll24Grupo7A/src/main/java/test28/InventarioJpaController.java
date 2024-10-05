/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test28;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class InventarioJpaController {
    
    private EntityManagerFactory emf;

    public InventarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU"); 
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Método para obtener todos los inventarios
    public List<Inventario> findInventarioEntities() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Inventario> query = em.createNamedQuery("Inventario.findAll", Inventario.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Método para encontrar un inventario por ID
    public Inventario findInventario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inventario.class, id);
        } finally {
            em.close();
        }
    }

    // Método para crear un nuevo inventario
    public void create(Inventario inventario) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(inventario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInventario(inventario.getIdInventario()) != null) {
                throw new Exception("El inventario con id " + inventario.getIdInventario() + " ya existe.");
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Método para actualizar un inventario
    public void edit(Inventario inventario) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(inventario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInventario(inventario.getIdInventario()) == null) {
                throw new Exception("El inventario con id " + inventario.getIdInventario() + " no existe.");
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // Método para eliminar un inventario
    public void destroy(Long id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Inventario inventario = em.getReference(Inventario.class, id);
            if (inventario == null) {
                throw new Exception("El inventario con id " + id + " no existe.");
            }
            em.remove(inventario);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Método para cerrar el EntityManagerFactory
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}