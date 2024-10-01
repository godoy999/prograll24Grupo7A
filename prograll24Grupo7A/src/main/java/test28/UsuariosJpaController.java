/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test28;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
//import javax.transaction.UserTransaction;
//import test28.Usuarios;
import test28.exceptions.NonexistentEntityException;

public class UsuariosJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public UsuariosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuario) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getIdUsuario()) != null) {
                throw new Exception("El usuario ya existe.");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            usuario = em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            Long id = usuario.getIdUsuario();
            if (findUsuario(id) == null) {
                throw new NonexistentEntityException("El usuario con id " + id + " no existe.");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuario;
            try {
                usuario = em.getReference(Usuarios.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El usuario con id " + id + " no existe.", enfe);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

   private List<Usuarios> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
        // Definir el tipo de la consulta con CriteriaQuery
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Usuarios.class);  // Especificar que se trata de Usuarios
        var root = cq.from(Usuarios.class);      // Raíz de la consulta
        cq.select(root);                         // Seleccionar todos los campos

        var q = em.createQuery(cq);              // Crear la consulta
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }

        return q.getResultList();                // Obtener la lista de resultados
    } finally {
        em.close();
    }
}


    public Usuarios findUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            var cq = em.getCriteriaBuilder().createQuery();
            var rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            var q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}

