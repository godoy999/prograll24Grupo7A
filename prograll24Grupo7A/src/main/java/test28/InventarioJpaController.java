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

    // Método para cerrar el EntityManagerFactory
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}
