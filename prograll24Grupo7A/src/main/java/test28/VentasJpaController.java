
package test28;




import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.List;


public class VentasJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public VentasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public VentasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ventas ventas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura factura = ventas.getFacturaId();
            Inventario inventario = ventas.getProductoId();
            if (factura != null) {
                factura = em.getReference(factura.getClass(), factura.getIdFactura());
                ventas.setFacturaId(factura);
            }
            if (inventario != null) {
                inventario = em.getReference(inventario.getClass(), inventario.getIdInventario());
                ventas.setProductoId(inventario);
            }
            em.persist(ventas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ventas ventas) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas persistentVentas = em.find(Ventas.class, ventas.getIdVentas());
            Factura facturaOld = persistentVentas.getFacturaId();
            Factura facturaNew = ventas.getFacturaId();
            Inventario inventarioOld = persistentVentas.getProductoId();
            Inventario inventarioNew = ventas.getProductoId();
            if (facturaNew != null) {
                facturaNew = em.getReference(facturaNew.getClass(), facturaNew.getIdFactura());
                ventas.setFacturaId(facturaNew);
            }
            if (inventarioNew != null) {
                inventarioNew = em.getReference(inventarioNew.getClass(), inventarioNew.getIdInventario());
                ventas.setProductoId(inventarioNew);
            }
            ventas = em.merge(ventas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (ventas.getIdVentas() == null || findVentas(ventas.getIdVentas()) == null) {
                throw new EntityNotFoundException("The ventas with id " + ventas.getIdVentas() + " no longer exists.");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas ventas;
            try {
                ventas = em.getReference(Ventas.class, id);
                ventas.getIdVentas();
            } catch (EntityNotFoundException enfe) {
                throw new EntityNotFoundException("The ventas with id " + id + " no longer exists.");
            }
            em.remove(ventas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ventas> findVentasEntities() {
        return findVentasEntities(true, -1, -1);
    }

    public List<Ventas> findVentasEntities(int maxResults, int firstResult) {
        return findVentasEntities(false, maxResults, firstResult);
    }

    private List<Ventas> findVentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT v FROM Ventas v");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Ventas findVentas(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ventas.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT COUNT(v) FROM Ventas v");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}

