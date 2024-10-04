/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prograll24Grupo7A;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;
import javax.persistence.EntityTransaction;
import test28.Clientes;
import test28.Factura;
import test28.FacturaJpaController;
import test28.Inventario;
import test28.InventarioJpaController;
import test28.Roles;
import test28.RolesJpaController;
import test28.Usuarios;
import test28.UsuariosJpaController;
import test28.Vendedores;
import test28.VendedoresJpaController;
import test28.Ventas;
import test28.VentasJpaController;

/**
 *
 * @author Melvin
 */
public class Proyecto {
static Scanner entrada = new Scanner(System.in);
    public static void main(String[] args) {
        
        //create_ventas();
       // create_vendedores();
       // create_usuario();
        //create_rol();
        //create_inventario();
        create_factura();
        //create_cliente();
        
        
        
        

    }

    public static void create_cliente() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        if (emf != null) {
            System.out.println("EntityManagerFactory creada correctamente.");
        } else {
            System.out.println("EntityManagerFactory es nula.");
        }
        
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

    public static void create_factura() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        

        FacturaJpaController facturaController = new FacturaJpaController();
        boolean estado = true;
        while (estado) {
            System.out.println("------ Menú ------");
            System.out.println("1. Crear nueva factura");
            System.out.println("2. Buscar factura por ID");
            System.out.println("3. Editar factura");
            System.out.println("4. Eliminar factura");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            int opcion = entrada.nextInt();

            switch (opcion) {
                case 1:
                    // Crear nueva factura
                    System.out.print("Ingrese el ID del cliente: ");
                    Long clienteId = entrada.nextLong();

                    System.out.print("Ingrese la fecha de la factura (YYYY-MM-DD): ");
                    String fechaStr = entrada.next();
                    Date fecha = java.sql.Date.valueOf(fechaStr);

                    System.out.print("Ingrese el monto total: ");
                    BigDecimal montoTotal = entrada.nextBigDecimal();

                    Factura nuevaFactura = new Factura();
                    nuevaFactura.setFecha(fecha);
                    nuevaFactura.setMontoTotal(montoTotal);

                    // Aquí podrías obtener el cliente desde la base de datos
                    Clientes cliente = new Clientes();
                    cliente.setIdCliente(clienteId);  // Supongamos que ya tienes el cliente con el ID dado
                    nuevaFactura.setClienteId(cliente);

                    try {
                        facturaController.create(nuevaFactura);
                        System.out.println("Factura creada exitosamente.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    // Buscar factura por ID
                    System.out.print("Ingrese el ID de la factura: ");
                    Long idFactura = entrada.nextLong();

                    Factura factura = facturaController.findFactura(idFactura);
                    if (factura != null) {
                        System.out.println("Factura encontrada: " + factura);
                    } else {
                        System.out.println("Factura no encontrada.");
                    }
                    break;

                case 3:
                    // Editar factura
                    System.out.print("Ingrese el ID de la factura a editar: ");
                    Long idFacturaEditar = entrada.nextLong();

                    Factura facturaAEditar = facturaController.findFactura(idFacturaEditar);
                    if (facturaAEditar != null) {
                        System.out.print("Ingrese el nuevo monto total: ");
                        BigDecimal nuevoMontoTotal = entrada.nextBigDecimal();
                        facturaAEditar.setMontoTotal(nuevoMontoTotal);

                        try {
                            facturaController.edit(facturaAEditar);
                            System.out.println("Factura actualizada exitosamente.");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Factura no encontrada.");
                    }
                    break;

                case 4:
                    // Eliminar factura
                    System.out.print("Ingrese el ID de la factura a eliminar: ");
                    Long idFacturaEliminar = entrada.nextLong();

                    try {
                        facturaController.delete(idFacturaEliminar);
                        System.out.println("Factura eliminada exitosamente.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 5:
                    System.out.println("Saliendo...");
                    estado = false;
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    public static void create_inventario() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        InventarioJpaController inventarioController = new InventarioJpaController();

        try {
            // Ingresar los datos para el nuevo producto
            System.out.println("Ingrese el nombre del producto:");
            String nombreProducto = entrada.nextLine();

            System.out.println("Ingrese la cantidad:");
            int cantidad = entrada.nextInt();

            // Nota: Aquí deberías tener un método para validar y obtener un vendedorId existente
            System.out.println("Ingrese el ID del vendedor:");
            Long vendedorId = entrada.nextLong();

            // Iniciar transacción para guardar el nuevo inventario
            em.getTransaction().begin();

            // Crear el objeto Inventario con los datos ingresados
            Vendedores vendedor = em.find(Vendedores.class, vendedorId);  // Verifica que existe el vendedor con ese ID
            if (vendedor == null) {
                System.out.println("Vendedor con ID " + vendedorId + " no existe.");
                return;
            }

            Inventario nuevoInventario = new Inventario();
            nuevoInventario.setNombreProducto(nombreProducto);
            nuevoInventario.setCantidad(cantidad);
            nuevoInventario.setVendedorId(vendedor);

            // Persistir (guardar) el nuevo inventario en la base de datos
            em.persist(nuevoInventario);
            em.getTransaction().commit();  // Confirmar la transacción

            System.out.println("¡Producto guardado exitosamente!");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();  // Revertir si hay un error
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
            entrada.close();
        }
    }

    public static void create_rol() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");

        // Instanciar el controlador de Roles
        RolesJpaController rolesController = new RolesJpaController(emf);


        // Menú del CRUD
        boolean salir = false;
        while (!salir) {
            System.out.println("==== Menú CRUD Roles ====");
            System.out.println("1. Crear Rol");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = entrada.nextInt();
            entrada.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    // Crear Rol (Create)
                    System.out.println("Ingrese el nombre del nuevo rol:");
                    String nombre = entrada.nextLine();

                    // Crear la instancia del objeto Roles
                    Roles nuevoRol = new Roles();
                    nuevoRol.setNombre(nombre);

                    // Guardar en la base de datos
                    rolesController.create(nuevoRol);
                    System.out.println("Rol creado exitosamente.");
                    break;

                case 2:
                    // Salir
                    System.out.println("Saliendo del programa.");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }

        // Cerrar el EntityManagerFactory
        emf.close();
        entrada.close();
    }

    public static void create_usuario() {
        // Crear el EntityManagerFactory (nombre del persistence unit en persistence.xml)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        UsuariosJpaController usuarioController = new UsuariosJpaController(emf);

        // Pedir al usuario que ingrese los datos
        System.out.println("Ingrese el nombre del usuario:");
        String nombre = entrada.nextLine();

        System.out.println("Ingrese el correo del usuario:");
        String correo = entrada.nextLine();

        System.out.println("Ingrese la contraseña del usuario:");
        String password = entrada.nextLine();

        System.out.println("Ingrese el ID del rol:");
        Long rolId = entrada.nextLong();

        // Crear el nuevo usuario
        Usuarios nuevoUsuario = new Usuarios();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setPassword(password);

        // Asignar el rol (deberías tener una instancia de la entidad Roles)
        Roles rol = new Roles();
        rol.setIdRol(rolId); // Asegúrate de que el ID del rol existe en la base de datos
        nuevoUsuario.setRol(rol);

        try {
            // Intentar crear el nuevo usuario en la base de datos
            usuarioController.create(nuevoUsuario);
            System.out.println("Usuario creado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al crear el usuario: " + e.getMessage());
        } finally {
            // Cerrar EntityManagerFactory
            emf.close();
            entrada.close();
        }

    }

    public static void create_vendedores() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        VendedoresJpaController vendedoresController = new VendedoresJpaController();
       

        // Menú de opciones
        boolean salir = false;
        while (!salir) {
            System.out.println("---- Menú CRUD Vendedores ----");
            System.out.println("1. Crear nuevo Vendedor");
            System.out.println("2. Ver todos los Vendedores");
            System.out.println("3. Actualizar Vendedor");
            System.out.println("4. Eliminar Vendedor");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            int opcion = entrada.nextInt();
            entrada.nextLine();  // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    // Crear nuevo Vendedor
                    System.out.print("Introduce el nombre del vendedor: ");
                    String nombre = entrada.nextLine();
                    System.out.print("Introduce el correo del vendedor: ");
                    String correo = entrada.nextLine();

                    Vendedores nuevoVendedor = new Vendedores();
                    nuevoVendedor.setNombre(nombre);
                    nuevoVendedor.setCorreo(correo);

                    // Guardar el vendedor en la base de datos
                    vendedoresController.create(nuevoVendedor);
                    System.out.println("Vendedor creado con éxito.\n");
                    break;

                case 2:
                    // Leer todos los vendedores
                    System.out.println("---- Lista de Vendedores ----");
                    for (Vendedores v : vendedoresController.findVendedoresEntities()) {
                        System.out.println("ID: " + v.getIdVendedores() + " - Nombre: " + v.getNombre() + " - Correo: " + v.getCorreo());
                    }
                    System.out.println("-------------------------------\n");
                    break;

                case 3:
                    // Actualizar un vendedor existente
                    System.out.print("Introduce el ID del vendedor que deseas actualizar: ");
                    Long idUpdate = entrada.nextLong();
                    entrada.nextLine();  // Consumir el salto de línea
                    Vendedores vendedorActualizar = vendedoresController.findVendedor(idUpdate);

                    if (vendedorActualizar != null) {
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = entrada.nextLine();
                        System.out.print("Nuevo correo: ");
                        String nuevoCorreo = entrada.nextLine();

                        vendedorActualizar.setNombre(nuevoNombre);
                        vendedorActualizar.setCorreo(nuevoCorreo);

                        try {
                            vendedoresController.edit(vendedorActualizar);
                            System.out.println("Vendedor actualizado con éxito.\n");
                        } catch (Exception e) {
                            System.out.println("Error al actualizar vendedor: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Vendedor no encontrado.\n");
                    }
                    break;

                case 4:
                    // Eliminar un vendedor
                    System.out.print("Introduce el ID del vendedor que deseas eliminar: ");
                    Long idDelete = entrada.nextLong();
                    entrada.nextLine();  // Consumir el salto de línea

                    try {
                        vendedoresController.destroy(idDelete);
                        System.out.println("Vendedor eliminado con éxito.\n");
                    } catch (Exception e) {
                        System.out.println("Error al eliminar vendedor: " + e.getMessage());
                    }
                    break;

                case 5:
                    // Salir del programa
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.\n");
                    break;
            }
        }

        entrada.close();
    }

    public static void create_ventas() {
        // Crear el EntityManagerFactory (utiliza el nombre correcto de tu persistence unit)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        VentasJpaController ventasController = new VentasJpaController(emf);

        // Solicitar los datos al usuario
        System.out.println("Creación de una nueva venta");

        System.out.print("Ingrese la cantidad: ");
        int cantidad = entrada.nextInt();

        System.out.print("Ingrese el precio: ");
        BigDecimal precio = entrada.nextBigDecimal();

        System.out.print("Ingrese el ID de la factura: ");
        Long facturaId = entrada.nextLong();

        System.out.print("Ingrese el ID del producto (Inventario): ");
        Long productoId = entrada.nextLong();

        // Crear una instancia de Ventas con los datos ingresados
        Ventas nuevaVenta = new Ventas();
        nuevaVenta.setCantidad(cantidad);
        nuevaVenta.setPrecio(precio);

        // Configurar las relaciones con Factura e Inventario
        Factura factura = new Factura();
        factura.setIdFactura(facturaId);  // Establecer el ID de la factura
        nuevaVenta.setFacturaId(factura);

        Inventario inventario = new Inventario();
        inventario.setIdInventario(productoId);  // Establecer el ID del producto
        nuevaVenta.setProductoId(inventario);

        // Llamar al método create para guardar la nueva venta en la base de datos
        try {
            ventasController.create(nuevaVenta);
            System.out.println("Venta creada exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al crear la venta.");
        } finally {
            // Cerrar el EntityManagerFactory
            emf.close();
        }
    }
}
