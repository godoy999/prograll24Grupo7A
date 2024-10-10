package test28;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import test28.exceptions.NonexistentEntityException;

/**
 * JpaController for managing Usuarios entities.
 */
public class UsuariosJpaController implements Serializable {

    public List<Object[]> findUsuariosWithRoles() {
        EntityManager em = getEntityManager();
        try {
            // Consulta JPQL que hace un JOIN entre Usuarios y Roles
            Query query = em.createQuery(
                "SELECT u, r FROM Usuarios u JOIN u.rol r");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    
    public Usuarios findByUsuarioAndContrasena(String correo, String contrasena) {
    EntityManager em = getEntityManager();
    try {
        // Consulta JPQL para buscar el usuario con correo y contraseña
        Query query = em.createQuery("SELECT u FROM Usuarios u WHERE u.correo = :correo AND u.password = :contrasena");
        query.setParameter("correo", correo);
        query.setParameter("contrasena", contrasena);
        
        // Intenta obtener un único resultado
        try {
            return (Usuarios) query.getSingleResult();
        } catch (Exception e) {
            // Si no se encuentra ningún usuario, devolver null
            return null;
        }
    } finally {
        em.close();
    }
}
    
    private EntityManagerFactory emf = null;

    // Constructor predeterminado que inicializa el EntityManagerFactory
    public UsuariosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
    }

    // Constructor que permite inyectar el EntityManagerFactory
    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Create new Usuario
    public void create(Usuarios usuario) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            // Persist the usuario entity
            em.persist(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Edit or update existing Usuario
    public void edit(Usuarios usuario) throws NonexistentEntityException, Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            // Merge the changes to the usuario entity
            usuario = em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            Long id = usuario.getIdUsuario();
            if (id == null || findUsuario(id) == null) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Delete existing Usuario
    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Usuarios usuario;
            try {
                usuario = em.getReference(Usuarios.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Find Usuario by id
    public Usuarios findUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Get all Usuarios
    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    // Get a specific number of Usuarios with limit and offset
    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Get the number of Usuarios in the database
    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
}
