/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prograll24Grupo7A;


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;
import javax.persistence.EntityTransaction;
import test28.Clientes;
import test28.Factura;
import test28.FacturaJpaController;

/**
 *
 * @author Melvin
 */
public class Proyecto {
    public static void main(String[] args) {
      CreateCliente();
        
        
    }
        
        public static void CreateCliente(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        if (emf != null) {
    System.out.println("EntityManagerFactory creada correctamente.");
} else {
    System.out.println("EntityManagerFactory es nula.");
}
        Scanner entrada = new Scanner(System.in);
        Clientes a = new Clientes();

        System.out.println("Ingrese su nombre");
        String nombre = entrada.nextLine();
        a.setNombre(nombre);

        System.out.println("Ingrese su correo");
        String correo = entrada.nextLine();
        a.setCorreo(correo);

        System.out.println("Ingrese numero de telefono");
        String telefono = entrada.nextLine();
        a.setTelefono(telefono);

        
        try {
            em.getTransaction().begin(); // Iniciamos la transacción
            em.persist(a); // El objeto a es la instancia de la clase Alumno
            em.getTransaction().commit(); // Si todo salió bien hará el commit
        } catch (Exception e) {
            em.getTransaction().rollback(); // Si ocurrió una excepción, hará rollback
            e.printStackTrace(); // Queden registros a medias
        } finally {
            em.close(); // Cerramos el EntityManager
            emf.close(); // Cerramos el EntityManagerFactory
        }
        }  
        
        
      /*  public static void create_factura(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese la fecha de la factura (YYYY-MM-DD): ");
        String fechaInput = scanner.nextLine();
        Date fecha = java.sql.Date.valueOf(fechaInput);

        System.out.println("Ingrese el monto total: ");
        BigDecimal montoTotal = scanner.nextBigDecimal();

        System.out.println("Ingrese el ID del cliente: ");
        Long clienteId = scanner.nextLong();

   //     EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            // Encontrar al cliente por ID
            Clientes cliente = em.find(Clientes.class, clienteId);
            if (cliente == null) {
                System.out.println("Cliente con ID " + clienteId + " no encontrado.");
                return;
            }

            // Crear la factura
            Factura factura = new Factura();
            factura.setFecha(fecha);
            factura.setMontoTotal(montoTotal);
            factura.setClienteId(cliente);

            // Persistir la factura
            em.persist(factura);
            tx.commit();

            System.out.println("Factura creada exitosamente con ID: " + factura.getIdFactura());

        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        }*/

}
