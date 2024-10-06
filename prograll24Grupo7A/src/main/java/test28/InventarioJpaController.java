package test28;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
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
            if (em != null) {
                em.close();
            }
        }
    }

    // Método para encontrar un inventario por ID
    public Inventario findInventario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inventario.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Método para crear un nuevo inventario
    public void create(Inventario inventario) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin(); // Iniciar la transacción
            if (findInventario(inventario.getIdInventario()) != null) {
                throw new Exception("El inventario con id " + inventario.getIdInventario() + " ya existe.");
            }
            em.persist(inventario);
            em.getTransaction().commit(); // Commit de la transacción
        } catch (Exception ex) {
            em.getTransaction().rollback(); // En caso de error, revertimos la transacción
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Método para actualizar un inventario existente
    public void edit(Inventario inventario) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin(); // Iniciar la transacción
            if (findInventario(inventario.getIdInventario()) == null) {
                throw new Exception("El inventario con id " + inventario.getIdInventario() + " no existe.");
            }
            em.merge(inventario);
            em.getTransaction().commit(); // Commit de la transacción
        } catch (Exception ex) {
            em.getTransaction().rollback(); // Revertir transacción en caso de error
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Método para eliminar un inventario por ID
    public void destroy(Long id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin(); // Iniciar la transacción
            Inventario inventario;
            try {
                inventario = em.getReference(Inventario.class, id);
                inventario.getIdInventario(); // Verificar si el inventario existe
            } catch (EntityNotFoundException enfe) {
                throw new Exception("El inventario con id " + id + " no existe.", enfe);
            }
            em.remove(inventario);
            em.getTransaction().commit(); // Commit de la transacción
        } catch (Exception ex) {
            em.getTransaction().rollback(); // Revertir transacción en caso de error
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Método para cerrar el EntityManagerFactory
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}
