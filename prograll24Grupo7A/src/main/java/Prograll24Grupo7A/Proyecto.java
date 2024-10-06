/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prograll24Grupo7A;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;
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
import test28.exceptions.NonexistentEntityException;

public class Proyecto {

    static Scanner entrada = new Scanner(System.in);
    static boolean estado = true;

    public static void main(String[] args) {

        
    
       // login();
        // create_usuario();
        //create_rol();
        
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
                            + "6. Roles para los usuarios \n"
                            + "7. Crear un Usuario \n"
                            + "8. Salir \n"
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
                            
                            opcionInventario = entrada.nextInt();
                            
                            switch (opcionInventario) {
                                case 1:
                                    crearInventario(entrada, inventarioController);
                                    break;
                                case 2:
                                    leerInventario(inventarioController);
                                    break;
                                case 3:
                                    actualizarInventario(entrada, inventarioController);
                                    break;
                                case 4:
                                    eliminarInventario(entrada, inventarioController);
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
                          create_cliente();
                              
                        break;
                    case 5:
                        create_factura();
                        estado = true;
                        break;
                    case 6: 
                        create_rol();
                        break;
                    case 7:
                        create_usuario();
                        break;
                    case 8:
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
     static ClientesJpaController clientesController = new ClientesJpaController();
    public static void create_cliente() {
        String menuOptions = "1. Crear Cliente\n2. Editar Cliente\n3. Eliminar Cliente\n4. Buscar Cliente\n5. Mostrar Todos los Clientes\n6. Salir";
        int option;
        do {
            option = Integer.parseInt(JOptionPane.showInputDialog(menuOptions));
            switch (option) {
                case 1:
                    crearCliente();
                    break;
                case 2:
                    editarCliente();
                    break;
                case 3:
                    eliminarCliente();
                    break;
                case 4:
                    buscarCliente();
                    break;
                case 5:
                    mostrarClientes();
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }
        } while (option != 6);
    }

    // Crear nuevo cliente
    public static void crearCliente() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        try {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del cliente:");
            String correo = JOptionPane.showInputDialog("Ingrese el correo del cliente:");
            String telefono = JOptionPane.showInputDialog("Ingrese el teléfono del cliente:");

            Clientes cliente = new Clientes();
            cliente.setNombre(nombre);
            cliente.setCorreo(correo);
            cliente.setTelefono(telefono);

            clientesController.create(cliente);
            JOptionPane.showMessageDialog(null, "Cliente creado con éxito");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear cliente: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Editar un cliente existente
    public static void editarCliente() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        try {
            Long id = Long.parseLong(JOptionPane.showInputDialog("Ingrese el ID del cliente a editar:"));
            Clientes cliente = clientesController.findCliente(id);

            if (cliente != null) {
                String nombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del cliente:", cliente.getNombre());
                String correo = JOptionPane.showInputDialog("Ingrese el nuevo correo del cliente:", cliente.getCorreo());
                String telefono = JOptionPane.showInputDialog("Ingrese el nuevo teléfono del cliente:", cliente.getTelefono());

                cliente.setNombre(nombre);
                cliente.setCorreo(correo);
                cliente.setTelefono(telefono);

                clientesController.edit(cliente);
                JOptionPane.showMessageDialog(null, "Cliente editado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Cliente no encontrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar cliente: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Eliminar un cliente
    public static void eliminarCliente() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        try {
            Long id = Long.parseLong(JOptionPane.showInputDialog("Ingrese el ID del cliente a eliminar:"));
            clientesController.destroy(id);
            JOptionPane.showMessageDialog(null, "Cliente eliminado con éxito");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar cliente: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Buscar un cliente por ID
    public static void buscarCliente() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        try {
            Long id = Long.parseLong(JOptionPane.showInputDialog("Ingrese el ID del cliente a buscar:"));
            Clientes cliente = clientesController.findCliente(id);
            if (cliente != null) {
                JOptionPane.showMessageDialog(null, "Cliente encontrado:\n" + cliente);
            } else {
                JOptionPane.showMessageDialog(null, "Cliente no encontrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Mostrar todos los clientes
    public static void mostrarClientes() {
        try {
            List<Clientes> clientes = clientesController.findClientesEntities();
            StringBuilder clientesList = new StringBuilder();
            for (Clientes cliente : clientes) {
                clientesList.append(cliente).append("\n");
            }
            JOptionPane.showMessageDialog(null, clientesList.length() > 0 ? clientesList.toString() : "No hay clientes registrados");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar clientes: " + e.getMessage());
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
    
        private static void crearInventario(Scanner scanner, InventarioJpaController inventarioController) {
        Inventario inventario = new Inventario();
        System.out.println("Ingrese el ID del inventario");
        scanner.nextInt();
        inventario.setIdInventario(Long.MIN_VALUE);
        System.out.println("Ingrese el nombre del producto:");
        inventario.setNombreProducto(scanner.nextLine());
        scanner.nextLine();
        System.out.println("Ingrese la cantidad del producto:");
        inventario.setCantidad(scanner.nextInt());
        

        try {
            inventarioController.create(inventario);
            System.out.println("Inventario creado con éxito.");
        } catch (Exception e) {
            System.out.println("Error al crear inventario: " + e.getMessage());
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
        System.out.println("Ingrese el nuevo nombre del producto :");
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
        idInventario = scanner.nextLong();
        
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
        // Crear el EntityManagerFactory 
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
    
    static VentasJpaController ventasController = new VentasJpaController();
    public static void create_ventas() {
        // Crear el EntityManagerFactory 
        

         
        boolean running = true;

        while (running) {
            String opcion = JOptionPane.showInputDialog(null, 
                    "=== Menú de Ventas ===\n"
                    + "1. Crear Venta\n"
                    + "2. Leer Venta\n"
                    + "3. Actualizar Venta\n"
                    + "4. Eliminar Venta\n"
                    + "5. Listar todas las Ventas\n"
                    + "6. Salir\n\n"
                    + "Elige una opción:");

            if (opcion == null) {
                running = false;
                break;
            }

            switch (opcion) {
                case "1":
                    crearVenta();
                    break;
                case "2":
                    leerVenta();
                    break;
                case "3":
                    actualizarVenta();
                    break;
                case "4":
                    eliminarVenta();
                    break;
                case "5":
                    listarVentas();
                    break;
                case "6":
                    running = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida, intenta de nuevo.");
            }
        }
    }

    private static void crearVenta() {
        try {
            int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese cantidad:"));
            BigDecimal precio = new BigDecimal(JOptionPane.showInputDialog("Ingrese precio:"));

            Long facturaId = Long.parseLong(JOptionPane.showInputDialog("Ingrese ID de la Factura:"));
            Factura factura = ventasController.getEntityManager().find(Factura.class, facturaId);

            Long productoId = Long.parseLong(JOptionPane.showInputDialog("Ingrese ID del Producto (Inventario):"));
            Inventario inventario = ventasController.getEntityManager().find(Inventario.class, productoId);

            if (factura != null && inventario != null) {
                Ventas nuevaVenta = new Ventas();
                nuevaVenta.setCantidad(cantidad);
                nuevaVenta.setPrecio(precio);
                nuevaVenta.setFacturaId(factura);
                nuevaVenta.setProductoId(inventario);

                ventasController.create(nuevaVenta);
                JOptionPane.showMessageDialog(null, "Venta creada con éxito:\n" + nuevaVenta);
            } else {
                JOptionPane.showMessageDialog(null, "Factura o Producto no encontrados.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear la venta: " + e.getMessage());
        }
    }

    private static void leerVenta() {
        try {
            Long id = Long.parseLong(JOptionPane.showInputDialog("Ingrese ID de la Venta a leer:"));
            Ventas venta = ventasController.findVentas(id);

            if (venta != null) {
                JOptionPane.showMessageDialog(null, "Venta encontrada:\n" + venta);
            } else {
                JOptionPane.showMessageDialog(null, "Venta no encontrada.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer la venta: " + e.getMessage());
        }
    }

    private static void actualizarVenta() {
        try {
            Long id = Long.parseLong(JOptionPane.showInputDialog("Ingrese ID de la Venta a actualizar:"));
            Ventas venta = ventasController.findVentas(id);

            if (venta != null) {
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Nueva cantidad:"));
                BigDecimal precio = new BigDecimal(JOptionPane.showInputDialog("Nuevo precio:"));

                Long facturaId = Long.parseLong(JOptionPane.showInputDialog("Ingrese nuevo ID de la Factura:"));
                Factura factura = ventasController.getEntityManager().find(Factura.class, facturaId);

                Long productoId = Long.parseLong(JOptionPane.showInputDialog("Ingrese nuevo ID del Producto (Inventario):"));
                Inventario inventario = ventasController.getEntityManager().find(Inventario.class, productoId);

                if (factura != null && inventario != null) {
                    venta.setCantidad(cantidad);
                    venta.setPrecio(precio);
                    venta.setFacturaId(factura);
                    venta.setProductoId(inventario);

                    ventasController.edit(venta);
                    JOptionPane.showMessageDialog(null, "Venta actualizada con éxito:\n" + venta);
                } else {
                    JOptionPane.showMessageDialog(null, "Factura o Producto no encontrados.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Venta no encontrada.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la venta: " + e.getMessage());
        }
    }

    private static void eliminarVenta() {
        try {
            Long id = Long.parseLong(JOptionPane.showInputDialog("Ingrese ID de la Venta a eliminar:"));
            ventasController.destroy(id);
            JOptionPane.showMessageDialog(null, "Venta eliminada con éxito.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la venta: " + e.getMessage());
        }
    }

    private static void listarVentas() {
        List<Ventas> ventasList = ventasController.findVentasEntities();
        if (!ventasList.isEmpty()) {
            StringBuilder listado = new StringBuilder("=== Listado de Ventas ===\n");
            for (Ventas venta : ventasList) {
                listado.append(venta).append("\n");
            }
            JOptionPane.showMessageDialog(null, listado.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron ventas.");
        }
    }
    }
