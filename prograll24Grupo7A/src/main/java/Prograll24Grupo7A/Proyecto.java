/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prograll24Grupo7A;

//import static Prograll24Grupo7A.Proyecto1.iniciarSesion;
//import static Prograll24Grupo7A.Proyecto1.registrarUsuario;
import java.math.BigDecimal;
import static java.time.Clock.system;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;
import javax.persistence.EntityNotFoundException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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
import static test28.Ventas_.precio;
import test28.exceptions.NonexistentEntityException;

public class Proyecto {

    static Scanner entrada = new Scanner(System.in);
    static boolean estado = true;
    static InventarioJpaController inventarioController;

    public static void main(String[] args) {
        
        login();
        //menu();
    }
    //------------------------------------------------LOGIN------------------------------------------------------------   

    public static void login() {
        String[] opciones = {"Iniciar sesión", "Registrar usuario"};

        // Mostrar el JOptionPane con las opciones
        int seleccion = JOptionPane.showOptionDialog(
                null,
                "Seleccione una opción:",
                "Opciones",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]);
        if (seleccion == 0) {
            iniciar_sesion();
            menu();
        } else if (seleccion == 1) {
            registrar_usuario();
            login();
        } else {
            JOptionPane.showMessageDialog(null, "No seleccionaste ninguna opción.");
            System.exit(0);
        }

    }

    public static void menu() {

        int opcion = 0;
        do {
            try {
                String menu = "-------------------------------------------\n"
                        + "Bienvenido a nuestra Empresa de tecnologia \n"
                        + "1. Desea Hacer un CRUD al inventario \n"
                        + "2. Desea Hacer un CRUD al vendedor \n"
                        + "3. Desea Hacer un CRUD a la venta \n"
                        + "4. Desea Hacer un CRUD al cliente \n"
                        + "5. Desea Generar la factura \n"
                        + "6. Roles para los usuarios \n"
                        + "7. Hacer un CRUD para Usuario \n"
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

                        do {
                            String opcionStr = JOptionPane.showInputDialog(null,
                                    "Seleccione una opción:\n"
                                    + "1. Crear Inventario\n"
                                    + "2. Leer Inventario\n"
                                    + "3. Actualizar Inventario\n"
                                    + "4. Eliminar Inventario\n"
                                    + "5. Mostrar la lista del inventario\n"
                                    + "6. Regresar al menú principal",
                                    "Menú Inventario", JOptionPane.QUESTION_MESSAGE);

                            if (opcionStr != null && !opcionStr.isEmpty()) {
                                try {
                                    opcionInventario = Integer.parseInt(opcionStr);
                                } catch (NumberFormatException e) {
                                    JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
                                    continue;
                                }
                                switch (opcionInventario) {
                                    case 1:
                                        create_inventario();
                                        break;
                                    case 2:
                                        find_inventario();

                                        break;
                                    case 3:
                                        update_inventario();
                                        break;
                                    case 4:
                                        delete_inventario();
                                        break;
                                    case 5:
                                        list_inventarios();
                                        break;
                                    case 6:
                                        JOptionPane.showMessageDialog(null, "Regresando al menú principal.");
                                        menu();
                                        break;
                                    default:
                                        JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");

                                }
                            }
                        } while (opcion != 5);

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
                        create_menu_cliente();

                        break;
                    case 5:
                        create_factura();
                        estado = true;
                        break;
                    case 6:
                        create_menu_rol();
                        break;
                    case 7:
                        create_menu_u();
                        break;
                    case 8:
                        JOptionPane.showMessageDialog(null, "Que tengas un Feliz Día!!", "Salir", JOptionPane.INFORMATION_MESSAGE);
                        estado = false;
                        System.exit(0);
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

    public static void create_menu_cliente() {
        String menuOptions = "1. Crear Cliente\n2. Editar Cliente\n3. Eliminar Cliente\n4. Buscar Cliente\n5. Mostrar Todos los Clientes\n6. Regresar al Menu Principal";
        int option;
        do {
            option = Integer.parseInt(JOptionPane.showInputDialog(menuOptions));
            switch (option) {
                case 1:
                    crear_cliente();
                    break;
                case 2:
                    editar_cliente();
                    break;
                case 3:
                    eliminar_cliente();
                    break;
                case 4:
                    buscar_cliente();
                    break;
                case 5:
                    mostrar_clientes();
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "Regresando al menu");
                    menu();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }
        } while (option != 6);
    }

    // Crear nuevo cliente
    public static void crear_cliente() {
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
    public static void editar_cliente() {
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
    public static void eliminar_cliente() {
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
    public static void buscar_cliente() {
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
    // Mostrar todos los clientes
    public static void mostrar_clientes() {
        try {
            List<Clientes> clientes = clientesController.findClientesEntities();
            StringBuilder clientesList = new StringBuilder();

            if (clientes.isEmpty()) {
                clientesList.append("No hay clientes registrados");
            } else {
                for (Clientes cliente : clientes) {
                    clientesList.append("ID: ").append(cliente.getIdCliente()).append("\n")
                            .append("Nombre: ").append(cliente.getNombre()).append("\n")
                            .append("Correo: ").append(cliente.getCorreo()).append("\n")
                            .append("Teléfono: ").append(cliente.getTelefono()).append("\n")
                            .append("---------------------\n");
                }
            }

            JOptionPane.showMessageDialog(null, clientesList.toString());
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
            String[] opciones = {"Crear nueva factura", "Buscar factura por ID", "Editar factura", "Eliminar factura", "Salir"};
            int opcion = JOptionPane.showOptionDialog(null, "Selecciona una opción:", "Menú de Facturas",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            switch (opcion) {
                case 0:
                    // Crear nueva factura
                    try {
                        String clienteIdStr = JOptionPane.showInputDialog("Ingrese el ID del cliente:");
                        Long clienteId = Long.parseLong(clienteIdStr);

                        String fechaStr = JOptionPane.showInputDialog("Ingrese la fecha de la factura (YYYY-MM-DD):");
                        Date fecha = java.sql.Date.valueOf(fechaStr);

                        String montoTotalStr = JOptionPane.showInputDialog("Ingrese el monto total:");
                        BigDecimal montoTotal = new BigDecimal(montoTotalStr);

                        Factura nuevaFactura = new Factura();
                        nuevaFactura.setFecha(fecha);
                        nuevaFactura.setMontoTotal(montoTotal);

                        Clientes cliente = new Clientes();
                        cliente.setIdCliente(clienteId);
                        nuevaFactura.setClienteId(cliente);

                        facturaController.create(nuevaFactura);
                        JOptionPane.showMessageDialog(null, "Factura creada exitosamente.");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al crear la factura: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 1:
                    // Buscar factura por ID
                    try {
                        String idFacturaStr = JOptionPane.showInputDialog("Ingrese el ID de la factura:");
                        Long idFactura = Long.parseLong(idFacturaStr);

                        Factura factura = facturaController.findFactura(idFactura);
                        if (factura != null) {
                            String detallesFactura = "Factura encontrada:\n"
                                    + "ID: " + factura.getIdFactura() + "\n"
                                    + "Fecha: " + factura.getFecha() + "\n"
                                    + "Monto Total: " + factura.getMontoTotal() + "\n"
                                    + "Cliente ID: " + factura.getClienteId().getIdCliente();
                            JOptionPane.showMessageDialog(null, detallesFactura);
                        } else {
                            JOptionPane.showMessageDialog(null, "Factura no encontrada.");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al buscar la factura: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 2:
                    // Editar factura
                    try {
                        String idFacturaEditarStr = JOptionPane.showInputDialog("Ingrese el ID de la factura a editar:");
                        Long idFacturaEditar = Long.parseLong(idFacturaEditarStr);

                        Factura facturaAEditar = facturaController.findFactura(idFacturaEditar);
                        if (facturaAEditar != null) {
                            String nuevoMontoTotalStr = JOptionPane.showInputDialog("Ingrese el nuevo monto total:");
                            BigDecimal nuevoMontoTotal = new BigDecimal(nuevoMontoTotalStr);
                            facturaAEditar.setMontoTotal(nuevoMontoTotal);

                            facturaController.edit(facturaAEditar);
                            JOptionPane.showMessageDialog(null, "Factura actualizada exitosamente.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Factura no encontrada.");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al editar la factura: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 3:
                    // Eliminar factura
                    try {
                        String idFacturaEliminarStr = JOptionPane.showInputDialog("Ingrese el ID de la factura a eliminar:");
                        Long idFacturaEliminar = Long.parseLong(idFacturaEliminarStr);

                        facturaController.delete(idFacturaEliminar);
                        JOptionPane.showMessageDialog(null, "Factura eliminada exitosamente.");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al eliminar la factura: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 4:
                    // Salir
                    estado = false;
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
                    break;
            }
        }
    }

    //-----------------------------------------CRUD INVENTARIO------------------------------------------------------------------
    private static void create_inventario() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        inventarioController = new InventarioJpaController();  // Corregido

        try {
            String nombreProducto = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto:");
            String cantidadStr = JOptionPane.showInputDialog(null, "Ingrese la cantidad:");
            int cantidad = Integer.parseInt(cantidadStr);
            String idVendedoresStr = JOptionPane.showInputDialog(null, "Ingrese el ID del vendedor:");
            Long idVendedores = Long.parseLong(idVendedoresStr);

            // Crear instancia de Inventario
            Inventario inventario = new Inventario();
            inventario.setNombreProducto(nombreProducto);
            inventario.setCantidad(cantidad);

            // Crear instancia de Vendedores
            Vendedores vendedor = new Vendedores();
            vendedor.setIdVendedores(idVendedores);

            // Asignar vendedor al inventario
            inventario.setVendedorId(vendedor);

            // Crear inventario en la base de datos
            inventarioController.create(inventario);
            JOptionPane.showMessageDialog(null, "Inventario creado con éxito.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear el inventario: " + e.getMessage());
        }
    }

    private static void find_inventario() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        inventarioController = new InventarioJpaController();

        try {
            String idStr = JOptionPane.showInputDialog(null, "Ingrese el ID del inventario:");
            Long id = Long.parseLong(idStr);

            Inventario inventario = inventarioController.findInventario(id);
            if (inventario != null) {
                String mensaje = String.format("ID: %d\nNombre del Producto: %s\nCantidad: %d\nID del Vendedor: %d",
                        inventario.getIdInventario(), inventario.getNombreProducto(),
                        inventario.getCantidad(), inventario.getVendedorId().getIdVendedores());
                JOptionPane.showMessageDialog(null, mensaje);
            } else {
                JOptionPane.showMessageDialog(null, "Inventario no encontrado.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el inventario: " + e.getMessage());
        }
    }

    private static void update_inventario() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        inventarioController = new InventarioJpaController();

        try {
            String idStr = JOptionPane.showInputDialog(null, "Ingrese el ID del inventario a actualizar:");
            Long id = Long.parseLong(idStr);

            Inventario inventario = inventarioController.findInventario(id);
            if (inventario == null) {
                JOptionPane.showMessageDialog(null, "Inventario no encontrado.");
                return;
            }

            String nombreProducto = JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre del producto:");
            String cantidadStr = JOptionPane.showInputDialog(null, "Ingrese la nueva cantidad:");
            int cantidad = Integer.parseInt(cantidadStr);

            inventario.setNombreProducto(nombreProducto);
            inventario.setCantidad(cantidad);

            inventarioController.edit(inventario);
            JOptionPane.showMessageDialog(null, "Inventario actualizado con éxito.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el inventario: " + e.getMessage());
        }
    }

    private static void delete_inventario() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        inventarioController = new InventarioJpaController();

        try {
            String idStr = JOptionPane.showInputDialog(null, "Ingrese el ID del inventario a eliminar:");
            Long id = Long.parseLong(idStr);

            inventarioController.destroy(id);
            JOptionPane.showMessageDialog(null, "Inventario eliminado con éxito.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el inventario: " + e.getMessage());
        }
    }

    private static void list_inventarios() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        inventarioController = new InventarioJpaController();

        List<Inventario> inventarios = inventarioController.findInventarioEntities();
        if (inventarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay inventarios disponibles.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("-- Lista de Inventarios --\n");
            for (Inventario inventario : inventarios) {
                sb.append(String.format("ID: %d\nNombre del Producto: %s\nCantidad: %d\nID del Vendedor: %d\n-------------------------\n",
                        inventario.getIdInventario(), inventario.getNombreProducto(),
                        inventario.getCantidad(), inventario.getVendedorId().getIdVendedores()));
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }
//----------------------------------------------------CRUD ROL------------------------------------------------------------------------    
    private static RolesJpaController rolesController = new RolesJpaController();

    public static void create_menu_rol() {

        boolean continuar = true;

        while (continuar) {
            String[] opciones = {"Crear Rol", "Leer Todos", "Actualizar Rol", "Eliminar Rol", "Salir"};
            String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione una opción", "CRUD Roles",
                    JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            if (seleccion == null || seleccion.equals("Salir")) {
                continuar = false;
                continue;
            }

            switch (seleccion) {
                case "Crear Rol":
                    create_rol();
                    break;
                case "Leer Todos":
                    list_roles();
                    break;
                case "Actualizar Rol":
                    update_rol();
                    break;
                case "Eliminar Rol":
                    delete_rol();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
                    break;
            }
        }
    }

    // Crear nuevo rol
    public static void create_rol() {
        String nombre = JOptionPane.showInputDialog("Introduce el nombre del nuevo rol:");
        if (nombre != null && !nombre.isEmpty()) {
            Roles nuevoRol = new Roles();
            nuevoRol.setNombre(nombre);

            try {
                rolesController.create(nuevoRol);
                JOptionPane.showMessageDialog(null, "Rol creado con éxito.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al crear el rol: " + e.getMessage());
            }
        }
    }

    // Leer todos los roles
    public static void list_roles() {
        List<Roles> rolesList = rolesController.findRolesEntities();
        if (rolesList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay roles disponibles.");
        } else {
            StringBuilder rolesInfo = new StringBuilder("Lista de Roles:\n");
            for (Roles rol : rolesList) {
                rolesInfo.append("ID: ").append(rol.getIdRol())
                        .append(" - Nombre: ").append(rol.getNombre()).append("\n");
            }
            JOptionPane.showMessageDialog(null, rolesInfo.toString());
        }
    }

    // Actualizar rol
    public static void update_rol() {
        String idStr = JOptionPane.showInputDialog("Introduce el ID del rol a actualizar:");
        if (idStr != null && !idStr.isEmpty()) {
            Long id = Long.parseLong(idStr);
            Roles rol = rolesController.findRol(id);

            if (rol != null) {
                String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre para el rol:", rol.getNombre());
                if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
                    rol.setNombre(nuevoNombre);
                    try {
                        rolesController.edit(rol);
                        JOptionPane.showMessageDialog(null, "Rol actualizado con éxito.");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al actualizar el rol: " + e.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Rol no encontrado.");
            }
        }
    }

    // Eliminar rol
    public static void delete_rol() {
        String idStr = JOptionPane.showInputDialog("Introduce el ID del rol a eliminar:");
        if (idStr != null && !idStr.isEmpty()) {
            Long id = Long.parseLong(idStr);
            try {
                rolesController.destroy(id);
                JOptionPane.showMessageDialog(null, "Rol eliminado con éxito.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar el rol: " + e.getMessage());
            }
        }
    }

//-----------------------------------------------CRUD USUARIO---------------------------------------------------------------------    
    private static EntityManagerFactory emf;
    private static UsuariosJpaController usuariosController;

    public static void create_menu_u() {

        // Crear el EntityManagerFactory 
        emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        usuariosController = new UsuariosJpaController(emf);

        boolean continuar = true;
        while (continuar) {
            String[] opciones = {"Crear", "Leer", "Actualizar", "Eliminar", "Salir"};
            int seleccion = JOptionPane.showOptionDialog(null, "Seleccione una operación", "CRUD Usuarios",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            switch (seleccion) {
                case 1:
                    create_usuario();
                    break;
                case 2:
                    list_usuarios();
                    break;
                case 3:
                    update_usuario();
                    break;
                case 4:
                    delete_usuario();
                    break;
                case 5:
                    continuar = false;
                    JOptionPane.showInputDialog(null, "Regresando al menu Principal!!");
                    menu();
                    break;
                default:
                    break;
            }
        }
    }

    // Método para crear un usuario
    private static void create_usuario() {
        try {
            String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del usuario:");
            String correo = JOptionPane.showInputDialog(null, "Ingrese el correo del usuario:");
            String password = JOptionPane.showInputDialog(null, "Ingrese la contraseña del usuario:");

            // Crear una instancia de usuario
            Usuarios usuario = new Usuarios();
            usuario.setNombre(nombre);
            usuario.setCorreo(correo);
            usuario.setPassword(password);

            // Asignar un rol (puedes modificar este paso para asignar roles válidos)
            Roles rol = new Roles();
            rol.setIdRol(1L); // Asignar un ID de rol válido
            usuario.setRol(rol);

            usuariosController.create(usuario);
            JOptionPane.showMessageDialog(null, "Usuario creado con éxito");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear usuario: " + e.getMessage());
        }
    }

    // Método para leer todos los usuarios
    private static void list_usuarios() {
        List<Usuarios> usuarios = usuariosController.findUsuariosEntities();
        StringBuilder usuariosList = new StringBuilder("Lista de Usuarios:\n");

        for (Usuarios usuario : usuarios) {
            usuariosList.append("ID: ").append(usuario.getIdUsuario())
                    .append(", Nombre: ").append(usuario.getNombre())
                    .append(", Correo: ").append(usuario.getCorreo())
                    .append("\n");
        }

        JOptionPane.showMessageDialog(null, usuariosList.toString());
    }

    // Método para actualizar un usuario
    private static void update_usuario() {
        try {
            Long id = Long.parseLong(JOptionPane.showInputDialog(null, "Ingrese el ID del usuario a actualizar:"));
            Usuarios usuario = usuariosController.findUsuario(id);

            if (usuario != null) {
                String nuevoNombre = JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre del usuario:", usuario.getNombre());
                String nuevoCorreo = JOptionPane.showInputDialog(null, "Ingrese el nuevo correo del usuario:", usuario.getCorreo());
                String nuevaPassword = JOptionPane.showInputDialog(null, "Ingrese la nueva contraseña del usuario:");

                usuario.setNombre(nuevoNombre);
                usuario.setCorreo(nuevoCorreo);
                usuario.setPassword(nuevaPassword);

                usuariosController.edit(usuario);
                JOptionPane.showMessageDialog(null, "Usuario actualizado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar usuario: " + e.getMessage());
        }
    }

    // Método para eliminar un usuario
    private static void delete_usuario() {
        try {
            Long id = Long.parseLong(JOptionPane.showInputDialog(null, "Ingrese el ID del usuario a eliminar:"));
            usuariosController.destroy(id);
            JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage());
        }

    }
//-----------------------------------------------------------------CRUD VENDEDORES---------------------------------------------------    

    public static void create_vendedores() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
        VendedoresJpaController vendedoresController = new VendedoresJpaController();

        // Menú de opciones
        boolean salir = false;
        while (!salir) {
            String[] options = {"Crear nuevo Vendedor", "Ver todos los Vendedores", "Actualizar Vendedor", "Eliminar Vendedor", "Salir"};
            int opcion = JOptionPane.showOptionDialog(null, "Selecciona una opción", "Menú CRUD Vendedores",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (opcion) {
                case 1: // Crear nuevo Vendedor
                    String nombre = JOptionPane.showInputDialog("Introduce el nombre del vendedor:");
                    String correo = JOptionPane.showInputDialog("Introduce el correo del vendedor:");

                    Vendedores nuevoVendedor = new Vendedores();
                    nuevoVendedor.setNombre(nombre);
                    nuevoVendedor.setCorreo(correo);

                    // Guardar el vendedor en la base de datos
                    try {
                        vendedoresController.create(nuevoVendedor);
                        JOptionPane.showMessageDialog(null, "Vendedor creado con éxito.");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al crear vendedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 2: // Ver todos los Vendedores
                    StringBuilder vendedoresList = new StringBuilder("---- Lista de Vendedores ----\n");
                    for (Vendedores v : vendedoresController.findVendedoresEntities()) {
                        vendedoresList.append("ID: ").append(v.getIdVendedores()).append(" - Nombre: ")
                                .append(v.getNombre()).append(" - Correo: ").append(v.getCorreo()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, vendedoresList.toString());
                    break;

                case 3: // Actualizar Vendedor
                    String idUpdateStr = JOptionPane.showInputDialog("Introduce el ID del vendedor que deseas actualizar:");
                    Long idUpdate = Long.parseLong(idUpdateStr);

                    Vendedores vendedorActualizar = vendedoresController.findVendedor(idUpdate);
                    if (vendedorActualizar != null) {
                        String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre:", vendedorActualizar.getNombre());
                        String nuevoCorreo = JOptionPane.showInputDialog("Nuevo correo:", vendedorActualizar.getCorreo());

                        vendedorActualizar.setNombre(nuevoNombre);
                        vendedorActualizar.setCorreo(nuevoCorreo);

                        try {
                            vendedoresController.edit(vendedorActualizar);
                            JOptionPane.showMessageDialog(null, "Vendedor actualizado con éxito.");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Error al actualizar vendedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Vendedor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 4: // Eliminar Vendedor
                    String idDeleteStr = JOptionPane.showInputDialog("Introduce el ID del vendedor que deseas eliminar:");
                    Long idDelete = Long.parseLong(idDeleteStr);

                    try {
                        vendedoresController.destroy(idDelete);
                        JOptionPane.showMessageDialog(null, "Vendedor eliminado con éxito.");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al eliminar vendedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 5: // Salir
                    salir = true;
                    JOptionPane.showInputDialog(null, "Regresando al menu Principal!!");
                    menu();
                    menu();
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
                    break;
            }
        }
    }

//-----------------------------------------------------CRUD VENTAS-----------------------------------------------------------------    
    public static void create_ventas() {
        String menuOptions = "1. Crear Venta\n2. Mostrar Ventas\n3. Actualizar Ventas\n4. Eliminar Ventas\n5.Salir";
        int option;
        do {
            option = Integer.parseInt(JOptionPane.showInputDialog(menuOptions));
            switch (option) {
                case 1:
                    crear();
                    break;
                case 2:
                    read_ventas();
                    break;
                case 3:
                    update_ventas();
                    break;
                case 4:
                    delete_ventas();
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Regresando al menu Principal!!");
                    menu();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }
        } while (option != 6);
    }

    public static void crear() {
        VentasJpaController ventasController = new VentasJpaController();

        try {
            // Solicitar datos para la venta
            String cantidadStr = JOptionPane.showInputDialog(null, "Introduce la cantidad:", "Crear Venta", JOptionPane.QUESTION_MESSAGE);
            int cantidad = Integer.parseInt(cantidadStr);

            String precioStr = JOptionPane.showInputDialog(null, "Introduce el precio:", "Crear Venta", JOptionPane.QUESTION_MESSAGE);
            BigDecimal precio = new BigDecimal(precioStr);

            String idFacturaStr = JOptionPane.showInputDialog(null, "Introduce el ID de la factura:", "Crear Venta", JOptionPane.QUESTION_MESSAGE);
            Long idFactura = Long.parseLong(idFacturaStr);

            String idInventarioStr = JOptionPane.showInputDialog(null, "Introduce el ID del producto (Inventario):", "Crear Venta", JOptionPane.QUESTION_MESSAGE);
            Long idInventario = Long.parseLong(idInventarioStr);

            // Crear instancia de Ventas
            Ventas venta = new Ventas();
            venta.setCantidad(cantidad);
            venta.setPrecio(precio);

            // Asignar Factura e Inventario
            Factura factura = new Factura();
            factura.setIdFactura(idFactura);
            venta.setFacturaId(factura);

            Inventario inventario = new Inventario();
            inventario.setIdInventario(idInventario);
            venta.setProductoId(inventario);

            // Guardar la venta
            ventasController.create(venta);
            JOptionPane.showMessageDialog(null, "Venta creada con éxito.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void read_ventas() {
        VentasJpaController ventasController = new VentasJpaController();

        List<Ventas> listaVentas = ventasController.findVentasEntities();

        if (listaVentas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay ventas registradas.", "Leer Ventas", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder sb = new StringBuilder();
            for (Ventas v : listaVentas) {
                sb.append("ID: ").append(v.getIdVentas())
                        .append(", Cantidad: ").append(v.getCantidad())
                        .append(", Precio: ").append(v.getPrecio())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString(), "Lista de Ventas", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void update_ventas() {
        VentasJpaController ventasController = new VentasJpaController();

        try {
            // Solicitar el ID de la venta a actualizar
            String idVentasStr = JOptionPane.showInputDialog(null, "Introduce el ID de la venta a actualizar:", "Actualizar Venta", JOptionPane.QUESTION_MESSAGE);
            Long idVentas = Long.parseLong(idVentasStr);

            Ventas venta = ventasController.findVentas(idVentas);

            if (venta != null) {
                // Actualizar datos
                String nuevaCantidadStr = JOptionPane.showInputDialog(null, "Introduce la nueva cantidad:", "Actualizar Venta", JOptionPane.QUESTION_MESSAGE);
                int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);

                String nuevoPrecioStr = JOptionPane.showInputDialog(null, "Introduce el nuevo precio:", "Actualizar Venta", JOptionPane.QUESTION_MESSAGE);
                BigDecimal nuevoPrecio = new BigDecimal(nuevoPrecioStr);

                venta.setCantidad(nuevaCantidad);
                venta.setPrecio(nuevoPrecio);

                ventasController.edit(venta);
                JOptionPane.showMessageDialog(null, "Venta actualizada con éxito.");

            } else {
                JOptionPane.showMessageDialog(null, "Venta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void delete_ventas() {
        VentasJpaController ventasController = new VentasJpaController();

        try {
            // Solicitar el ID de la venta a eliminar
            String idVentasStr = JOptionPane.showInputDialog(null, "Introduce el ID de la venta a eliminar:", "Eliminar Venta", JOptionPane.QUESTION_MESSAGE);
            Long idVentas = Long.parseLong(idVentasStr);

            ventasController.destroy(idVentas);
            JOptionPane.showMessageDialog(null, "Venta eliminada con éxito.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /* -------------- INICIAR SESION -------------- */
    public static void iniciar_sesion() {
        JTextField usuarioField = new JTextField();
        JTextField contrasenaField = new JPasswordField();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Correo:"));
        panel.add(usuarioField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Contraseña:"));
        panel.add(contrasenaField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Iniciar sesión", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
            UsuariosJpaController usuarioController = new UsuariosJpaController(emf);
            String correo = usuarioField.getText();
            String contrasena = contrasenaField.getText();
            Usuarios usuario = usuarioController.findByUsuarioAndContrasena(correo, contrasena);
            if (usuario == null) {
                JOptionPane.showMessageDialog(null, "Usuario invalido");
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombre());

            }

        } else {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
            System.exit(0);
        }

    }

    /* ----------------- REGISTRAR USUARIO -----------------*/
    public static void registrar_usuario() {
        JPanel panel = new JPanel();
        JTextField nombreField = new JTextField(20);
        JTextField correoField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Correo:"));
        panel.add(correoField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(passwordField);

        int option = JOptionPane.showConfirmDialog(null, panel, "Registrar Usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            String correo = correoField.getText();
            String password = new String(passwordField.getPassword());

            if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }

            Usuarios nuevoUsuario = new Usuarios();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setCorreo(correo);
            nuevoUsuario.setPassword(password);

            // Crear el EntityManagerFactory
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_prograll24Grupo7A_jar_1.0-SNAPSHOTPU");
            RolesJpaController rolesJpaController = new RolesJpaController(emf);

            // Busca el rol en la base de datos
            Roles rol = rolesJpaController.findRol(1L);

            if (rol != null) {
                nuevoUsuario.setRol(rol);

                try {
                    UsuariosJpaController usuariosJpaController = new UsuariosJpaController(emf);
                    usuariosJpaController.create(nuevoUsuario);
                    JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al registrar el usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "El rol especificado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            emf.close();
        }
    }

}
