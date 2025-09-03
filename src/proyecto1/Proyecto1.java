package proyecto1;
/**
 *
 * @author gabsu
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.time.LocalDateTime;

public class Proyecto1 {
    static final int MAX_PRODUCTOS = 200;
    static final int MAX_VENTAS = 1000;
    static final int MAX_BITACORA = 2000;
    static final String DATA_DIR = "data";
    static final String INVENTARIO_FILE = DATA_DIR + File.separator + "inventario.csv";
    static final String VENTAS_FILE = DATA_DIR + File.separator + "ventas.csv";
    static final String BITACORA_FILE = DATA_DIR + File.separator + "bitacora.log";
    
    static final String ESTUDIANTE_NOMBRE = "Gabriel Eduardo Urbina Sunún";
    static final String ESTUDIANTE_CARNET = "202300384";
    
    static Producto[] inventario = new Producto[MAX_PRODUCTOS];
    static int numProductos = 0;
    static Venta[] ventas = new Venta[MAX_VENTAS];
    static int numVentas = 0;
    static BitacoraEntrada[] bitacora = new BitacoraEntrada[MAX_BITACORA];
    static int numBitacora = 0;
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        asegurarCarpetaDatos();
        cargarInventario();
        cargarVentas();
        registrarBitacora("INICIO", true);

        while (true) {
            int opcion = menu();
            switch (opcion) {
                case 1: agregarProducto(); break;
                case 2: buscarProducto(); break;
                case 3: eliminarProducto(); break;
                case 4: registrarVenta(); break;
                case 5: generarReportes(); break;
                case 6: verDatosEstudiante(); break;
                case 7: verBitacora(); break;
                case 8: salir(); return;
                default: System.out.println("Opción inválida.\n");
            }
        }
    }

    static int menu() {
        System.out.println("\n----- BIENVENIDO A LA TIENDA DE ROPA -----");
        System.out.println("1. Agregar Producto");
        System.out.println("2. Buscar Producto");
        System.out.println("3. Eliminar Producto");
        System.out.println("4. Registrar Venta");
        System.out.println("5. Generar Reportes");
        System.out.println("6. Ver Datos del Estudiante");
        System.out.println("7. Bitácora");
        System.out.println("8. Salir");
        System.out.print("Seleccione la opción: ");
        int op = leerEntero();
        return op;
    }

    static void agregarProducto() {
        System.out.println("\n--- Agregar Producto ---");
        if (numProductos >= MAX_PRODUCTOS) {
            System.out.println("Inventario lleno.");
            registrarBitacora("AGREGAR PRODUCTO", false);
            return;
        }
        System.out.print("Código único: ");
        String codigo = sc.nextLine().trim();
        if (buscarIndiceProductoPorCodigo(codigo) != -1) {
            System.out.println("Error: Código ya existente.");
            registrarBitacora("AGREGAR PRODUCTO", false);
            return;
        }
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Categoría: ");
        String categoria = sc.nextLine().trim();
        System.out.print("Precio (Q): ");
        double precio = leerDoublePositivo();
        System.out.print("Cantidad en stock: ");
        int stock = leerEnteroPositivo();

        inventario[numProductos++] = new Producto(codigo, nombre, categoria, precio, stock);
        guardarInventario();
        System.out.println("Producto agregado correctamente.");
        registrarBitacora("AGREGAR PRODUCTO", true);
    }
    
    static void buscarProducto() {
        System.out.println("\n--- Buscar Producto ---");
        System.out.println("Buscar por: 1. Código  2. Nombre  3. Categoría");
        System.out.print("Seleccione: ");
        int tipo = leerEntero();
        boolean encontrado = false;
        switch (tipo) {
            case 1:
                System.out.print("Código: ");
                String codigo = sc.nextLine().trim();
                int idx = buscarIndiceProductoPorCodigo(codigo);
                if (idx != -1) {
                    System.out.println("Encontrado: " + inventario[idx]);
                    encontrado = true;
                }
                break;
            case 2:
                System.out.print("Nombre del producto: ");
                String nombre = sc.nextLine().trim().toLowerCase();
                for (int i = 0; i < numProductos; i++) {
                    if (inventario[i].nombre.toLowerCase().contains(nombre)) {
                        System.out.println(inventario[i]);
                        encontrado = true;
                    }
                }
                break;
            case 3:
                System.out.print("Categoría: ");
                String categoria = sc.nextLine().trim().toLowerCase();
                for (int i = 0; i < numProductos; i++) {
                    if (inventario[i].categoria.toLowerCase().equals(categoria)) {
                        System.out.println(inventario[i]);
                        encontrado = true;
                    }
                }
                break;
            default:
                System.out.println("Opción de búsqueda inválida.");
        }
        if (!encontrado) System.out.println("No se encontraron coincidencias.");
        registrarBitacora("BUSCAR", encontrado);
}


    static void verDatosEstudiante() {
        System.out.println("\n--- Datos del Estudiante ---");
        System.out.println("Nombre : " + ESTUDIANTE_NOMBRE);
        System.out.println("Carnet : " + ESTUDIANTE_CARNET);
        registrarBitacora("DATOS", true);
    }

    static void verBitacora() {
        System.out.println("\n--- Bitácora (temporal) ---");
        for (int i = 0; i < numBitacora; i++) {
            System.out.println(bitacora[i]);
        }
        guardarBitacoraArchivo();
        registrarBitacora("BITACORA", true);
    }

    static void salir() {
        guardarInventario();
        guardarBitacoraArchivo();
        registrarBitacora("SALIR", true);
        System.out.println("Saliendo... ¡Hasta luego!");
    }
    
    static int buscarIndiceProductoPorCodigo(String codigo) {
        for (int i = 0; i < numProductos; i++) {
            if (inventario[i].codigo.equalsIgnoreCase(codigo)) return i;
        }
        return -1;
    }

    static int leerEntero() {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.print("Ingrese un entero válido: ");
            }
        }
    }

    static int leerEnteroPositivo() {
        while (true) {
            int v = leerEntero();
            if (v > 0) return v;
            System.out.print("Debe ser > 0. Intente de nuevo: ");
        }
    }

    static double leerDoublePositivo() {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                double v = Double.parseDouble(s);
                if (v > 0) return v;
            } catch (Exception e) {}
            System.out.print("Ingrese un número válido > 0: ");
        }
    }

    static String fechaHora() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        return String.format("%02d/%02d/%04d %02d:%02d:%02d",
                now.getDayOfMonth(), now.getMonthValue(), now.getYear(),
                now.getHour(), now.getMinute(), now.getSecond());
    }

    static String fechaHoraArchivo() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        return String.format("%02d_%02d_%04d_%02d_%02d_%02d",
                now.getDayOfMonth(), now.getMonthValue(), now.getYear(),
                now.getHour(), now.getMinute(), now.getSecond());
    }

    static void asegurarCarpetaDatos() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();
    }

    static void cargarInventario() {
        File f = new File(INVENTARIO_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";", -1);
                if (p.length != 5) continue;
                if (numProductos >= MAX_PRODUCTOS) break;
                Producto prod = new Producto(p[0], p[1], p[2],
                        Double.parseDouble(p[3]), Integer.parseInt(p[4]));
                inventario[numProductos++] = prod;
            }
        } catch (Exception e) {
            System.out.println("Error al cargar inventario: " + e.getMessage());
        }
    }

    static void guardarInventario() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTARIO_FILE))) {
            for (int i = 0; i < numProductos; i++) {
                Producto p = inventario[i];
                bw.write(p.codigo + ";" + p.nombre + ";" + p.categoria + ";" + p.precio + ";" + p.stock);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar inventario: " + e.getMessage());
        }
    }

    static void cargarVentas() {
        File f = new File(VENTAS_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";", -1);
                if (p.length != 4) continue;
                if (numVentas >= MAX_VENTAS) break;
                Venta v = new Venta(p[0], Integer.parseInt(p[1]), p[2], Double.parseDouble(p[3]));
                ventas[numVentas++] = v;
            }
        } catch (Exception e) {
            System.out.println("Error al cargar ventas: " + e.getMessage());
        }
    }

    static void registrarBitacora(String tipo, boolean ok) {
        if (numBitacora >= MAX_BITACORA) return;
        String fecha = fechaHoraHumana();
        String usuario = System.getProperty("user.name");
        bitacora[numBitacora++] = new BitacoraEntrada(fecha, tipo, ok, usuario);
    }

    static void guardarBitacoraArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BITACORA_FILE))) {
            for (int i = 0; i < numBitacora; i++) {
                bw.write(bitacora[i].toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar bitácora: " + e.getMessage());
        }
    }
}
