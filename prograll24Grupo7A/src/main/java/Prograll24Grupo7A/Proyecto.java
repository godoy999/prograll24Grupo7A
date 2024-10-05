/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prograll24Grupo7A;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.swing.JOptionPane;
import test28.Clientes;
import test28.ClientesJpaController;
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

public class Proyecto {

    static Scanner entrada = new Scanner(System.in);
    static boolean estado = true;

    public static void main(String[] args) {

        // create_usuario();
        //create_rol();
         Scanner scanner = new Scanner(System.in);
        ClientesJpaController clientesController = new ClientesJpaController();
        InventarioJpaController inventarioController = new InventarioJpaController();
        int opcion = 0;
        do {
            try {
                String menu = "-------------------------------------------\n"
                            + "Bienvenido a nuestra Empresa de tecnologia \n"
                            + "1. Desea agregar un producto al inventario \n"
                            + "2. Desea agregar un vendedor o actualizarlo \n"
                            + "3. Desea ingresar una venta o consultarla \n"
                            + "4. Desea Hacer un CRUD a cliente \n"
                            + "5. Desea Generar la factura \n"
                            + "6. Salir \n"
                            + "-------------------------------------------\n"
                            + "Que opcion desea: ";
                
                String input = JOptionPane.showInputDialog(null, menu, "Menú Principal", JOptionPane.QUESTION_MESSAGE);
                
                // Si el usuario cierra la ventana o no ingresa nada, salir
                if (input == null || input.trim().isEmpty()) {
                    estado = false;
                    JOptionPane.showMessageDialog(null, "Que tengas un Feliz Día!!", "Salir", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
                int r = Integer.parseInt(input);

                switch (r) {
                    case 1:
                        int opcionInventario = 0;
                    {
                       
                        do {
                            System.out.println("Seleccione una opción:");
                            System.out.println("1. Crear Inventario");
                            System.out.println("2. Leer Inventario");
                            System.out.println("3. Actualizar Inventario");
                            System.out.println("4. Eliminar Inventario");
                            System.out.println("5. Salir");
                            
                            opcionInventario = scanner.nextInt();
                            
                            switch (opcionInventario) {
                                case 1:
                                    create_inventario();
                                    break;
                                case 2:
                                    leerInventario(inventarioController);
                                    break;
                                case 3:
                                    actualizarInventario(scanner, inventarioController);
                                    break;
                                case 4:
                                    eliminarInventario(scanner, inventarioController);
                                    break;
                                case 5:
                                    inventarioController.close();
                                    System.out.println("Saliendo...");
                                    break;
                                default:
                                    System.out.println("Opción no válida. Intente de nuevo.");
                            }
                        } while (opcion != 5);
                    }
                    
                        estado = true;
                        break;

                    case 2:
                        create_vendedores();
                        estado = true;
                        break;

                    case 3:
                        create_ventas();
                        estado = true;
                        break;
                    case 4:
                          int opcionClientes = 0;
                    do {
                        System.out.println("Seleccione una opción para Clientes:");
                        System.out.println("1. Crear Cliente");
                        System.out.println("2. Leer Clientes");
                        System.out.println("3. Actualizar Cliente");
                        System.out.println("4. Eliminar Cliente");
                        System.out.println("5. Volver al menú principal");

                        opcionClientes = entrada.nextInt();

                        switch (opcionClientes) {
                            case 1:
                                insertar_clientes();
                                break;
                            case 2:
                                Consultar_clientes();
                                break;
                            case 3:
                                actualizar_cliente();
                                break;
                            case 4:
                                eliminar_cliente();
                                break;
                            case 5:
                                break; // Volver al menú principal
                            default:
                                System.out.println("Opción no válida, por favor ingrese una opción del 1 al 5.");
                                break;
                        }
                    } while (opcionClientes != 5);
                        break;
                    case 5:
                        create_factura();
                        estado = true;
                        break;
                    case 6:
                         JOptionPane.showMessageDialog(null, "Que tengas un Feliz Día!!", "Salir", JOptionPane.INFORMATION_MESSAGE);
                        estado = false;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "La opción escogida no es válida!!\nInténtelo de nuevo", "Error", JOptionPane.WARNING_MESSAGE);
                        break;
                }
            } catch (NumberFormatException e) {
                // Si el usuario ingresa algo que no es un número
                JOptionPane.showMessageDialog(null, "Ha ocurrido una excepción. Ingrese un valor numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
            
            } finally {

            }

        } while (estado);

    }

//------------------------------------------------CRUD CLIENTES------------------------------------------------------------    
    
    static void insertar_clientes(){

        String persistenceUnitname =null;
       
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");

        
        EntityManager em = emf.createEntityManager();
        
        Scanner scanner = new Scanner(System.in);
        Clientes c = new Clientes();
        System.out.println("Ingrese el ID del cliente");
        String idcliente = scanner.nextLine();
        c.setIdCliente(Long.MIN_VALUE);
        System.out.println("Ingrese el nombre del cliente:");
        String Nombre = scanner.nextLine();
        c.setNombre(Nombre);
        System.out.println("Ingrese el correo del cliente:");
        String Correo = scanner.nextLine();
        c.setCorreo(Correo);
        System.out.println("Ingrese el numero de telefono del cliente");
        String telefono = scanner.nextLine();
        c.setTelefono(telefono);
        
        try {
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
        em.close();
        }
    }
    
    static void Consultar_clientes(){
             
        List<Clientes> lstClientes = new ArrayList<>();
        
        ClientesJpaController ac = new ClientesJpaController();
                 
        try {
            lstClientes = ac.findClientesEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        for (Clientes al : lstClientes){
            System.out.println("ID: " + al.getIdCliente());
            System.out.println("Nombre: " + al.getNombre());
            System.out.println("Correo: " + al.getCorreo());
            System.out.println("Numero de Telefono: " + al.getTelefono());
            System.out.println("---------------------------------------------------");
            
        }
    }
    
    static void actualizar_cliente() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();

        ClientesJpaController ac = new ClientesJpaController(emf);

        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Ingrese el ID del cliente a actualizar:");
        Long idCliente = scanner.nextLong();
        scanner.nextLine();  // Consumir la nueva línea

        // Buscar el cliente
        Clientes cliente = ac.findClientes(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.println("Cliente encontrado: " + cliente.getNombre());

        System.out.println("Ingrese el nuevo nombre del cliente:");
        String nuevoNombre = scanner.nextLine();
        if (!nuevoNombre.isEmpty()) {
            cliente.setNombre(nuevoNombre);
        }

        System.out.println("Ingrese el nuevo correo del cliente:");
        String nuevoCorreo = scanner.nextLine();
        if (!nuevoCorreo.isEmpty()) {
            cliente.setCorreo(nuevoCorreo);
        }

        System.out.println("Ingrese el nuevo número de teléfono del cliente:");
        String nuevoTelefono = scanner.nextLine();
        if (!nuevoTelefono.isEmpty()) {
            cliente.setTelefono(nuevoTelefono);
        }

        // Actualizar el cliente en la base de datos
        try {
            ac.edit(cliente);
            System.out.println("Cliente actualizado con éxito.");
        } catch (Exception e) {
            System.out.println("Error al actualizar el cliente.");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
  static void eliminar_cliente() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        ClientesJpaController ac = new ClientesJpaController(emf);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el ID del cliente que desea eliminar:");
        Long idClientes = scanner.nextLong();

        // Buscar el cliente para eliminar
        Clientes clientes = ac.findClientes(idClientes);
        if (clientes == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        // Eliminar el cliente
        try {
            ac.destroy(idClientes);
            System.out.println("Cliente eliminado con éxito.");
        } catch (Exception e) {
            System.out.println("Error al eliminar el cliente.");
            e.printStackTrace();
        }
    }            
   
//---------------------------------------------------CRUD FACTURA------------------------------------------------------------------  
  
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
                    entrada.nextLine();

                    System.out.print("Ingrese la fecha de la factura (YYYY-MM-DD): ");
                    String fechaStr = entrada.next();
                    Date fecha = java.sql.Date.valueOf(fechaStr);
                   

                    System.out.print("Ingrese el monto total: ");
                    BigDecimal montoTotal = entrada.nextBigDecimal();

                    Factura nuevaFactura = new Factura();
                    nuevaFactura.setFecha(fecha);
                    nuevaFactura.setMontoTotal(montoTotal);

                    //obtener el cliente desde la base de datos
                    Clientes cliente = new Clientes();
                    cliente.setIdCliente(clienteId); 
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

                   
                {
                    Object factura = null;
                    if (factura != null) {
                        System.out.println("Factura encontrada: " + factura);
                    } else {
                        System.out.println("Factura no encontrada.");
                    }
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

    //-----------------------------------------CRUD INVENTARIO------------------------------------------------------------------
    
    public static void create_inventario() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        InventarioJpaController inventarioController = new InventarioJpaController();

        try {
            // Ingresar los datos para el nuevo producto
            System.out.println("Ingrese el nombre del producto:");
            String nombreProducto = entrada.nextLine();
            entrada.nextLine();

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
    
        private static void leerInventario(InventarioJpaController inventarioController) {
        List<Inventario> inventarios = inventarioController.findInventarioEntities();
        if (inventarios.isEmpty()) {
            System.out.println("No hay inventarios disponibles.");
        } else {
            System.out.println("Inventarios:");
            for (Inventario inventario : inventarios) {
                System.out.println("ID: " + inventario.getIdInventario() + ", Nombre: " + inventario.getNombreProducto() + 
                                   ", Cantidad: " + inventario.getCantidad());
            }
        }
    }

    private static void actualizarInventario(Scanner scanner, InventarioJpaController inventarioController) {
        System.out.println("Ingrese el ID del inventario a actualizar:");
        Long id = scanner.nextLong();
        Inventario inventario = inventarioController.findInventario(id);

        if (inventario == null) {
            System.out.println("Inventario no encontrado.");
            return;
        }

        System.out.println("Nombre actual: " + inventario.getNombreProducto());
        System.out.println("Ingrese el nuevo nombre del producto (deje vacío para no cambiar):");
        scanner.nextLine(); // Consumir la nueva línea
        String nuevoNombre = scanner.nextLine();
        if (!nuevoNombre.isEmpty()) {
            inventario.setNombreProducto(nuevoNombre);
        }

        System.out.println("Cantidad actual: " + inventario.getCantidad());
        System.out.println("Ingrese la nueva cantidad (deje vacío para no cambiar):");
        String nuevaCantidadStr = scanner.nextLine();
        if (!nuevaCantidadStr.isEmpty()) {
            inventario.setCantidad(Integer.parseInt(nuevaCantidadStr));
        }

        try {
            inventarioController.edit(inventario);
            System.out.println("Inventario actualizado con éxito.");
        } catch (Exception e) {
            System.out.println("Error al actualizar inventario: " + e.getMessage());
        }
    }

private static void eliminarInventario(Scanner scanner, InventarioJpaController inventarioController) {
    System.out.println("Ingrese el ID del inventario a eliminar:");

    // Asegurarse de que el ID se lea correctamente
    Long idInventario = null;
    try {
        // Leer la siguiente línea y convertirla en un número Long
        String input = scanner.nextLine().trim(); // Eliminar espacios innecesarios
        idInventario = Long.parseLong(input);
    } catch (NumberFormatException e) {
        System.out.println("Error: ID inválido. Debe ser un número.");
        return;
    }

    // Intentar eliminar el inventario con el ID ingresado
    try {
        inventarioController.destroy(idInventario);
        System.out.println("Inventario eliminado con éxito.");
    } catch (Exception e) {
        System.out.println("Error al eliminar inventario: " + e.getMessage());
    }
}



//----------------------------------------------------CRUD ROL------------------------------------------------------------------------    
    
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
    
//-----------------------------------------------CRUD USUARIO---------------------------------------------------------------------    

    public static void create_usuario() {
        // Crear el EntityManagerFactory (nombre del persistence unit en persistence.xml)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        UsuariosJpaController usuarioController = new UsuariosJpaController(emf);

        // Pedir al usuario que ingrese los datos
        System.out.println("Ingrese el nombre del usuario:");
        String nombre = entrada.nextLine();
        entrada.nextLine();

        System.out.println("Ingrese el correo del usuario:");
        String correo = entrada.nextLine();

        System.out.println("Ingrese la contraseña del usuario:");
        String password = entrada.nextLine();
        entrada.nextLine();

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

//-----------------------------------------------------------------CRUD VENDEDORES---------------------------------------------------    
    
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
                    entrada.nextLine();
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

//-----------------------------------------------------CRUD VENTAS-----------------------------------------------------------------    
    
    public static void create_ventas() {
        // Crear el EntityManagerFactory 
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        VentasJpaController ventasController = new VentasJpaController(emf);

        // Solicitar los datos al usuario
        System.out.println("Creación de una nueva venta");

        System.out.print("Ingrese la cantidad: ");
        int cantidad = entrada.nextInt();
        entrada.nextLine();

        System.out.print("Ingrese el precio: ");
        BigDecimal precio = entrada.nextBigDecimal();

        System.out.print("Ingrese el ID de la factura: ");
        Long facturaId = entrada.nextLong();
        entrada.nextLine();

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