
import clasesbean.*;
import java.util.Scanner;
import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

public class MenuOperaciones {

    public static void main(String[] args) {
        Integer opcion = 1;
        while (opcion != 0) {
            menu();
            Scanner teclado = new Scanner(System.in);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
                switch (opcion) {
                    case 1:
                        llenarproductos();
                        break;
                    case 2:
                        System.out.println("Teclea id de producto:");
                        int id = teclado.nextInt();
                        teclado.nextLine();
                        System.out.println("Teclea cantidad:");
                        int cant = teclado.nextInt();
                        teclado.nextLine();
                        llenarventas(id, cant);
                        break;
                    case 3:
                        verproductos();
                        break;
                    case 4:
                        verventas();
                        break;
                    case 5:
                        verpedidos();
                        break;
                    case 0:
                        break;
                }
            } catch (Exception e) {
                // Excepción por si no introduce un número
                System.out.println("Debe introducir un número del 0 al 5");
                System.out.println(e);
            }
        }
    }

    public static void menu() {
        System.out.println("1 - Llenar productos");
        System.out.println("2 - Insertar venta");
        System.out.println("3 - Ver productos");
        System.out.println("4 - Ver Ventas");
        System.out.println("5 - Ver pedidos");
        System.out.println("0 - Salir");
    }

    public static void llenarproductos() {
        BaseDatos bd = new BaseDatos();
        Producto p1 = new Producto(1, "Duruss Cobalt", 10, 3, 220);
        Producto p2 = new Producto(2, "Varlion Avant Carbon", 5, 2, 176);
        Producto p3 = new Producto(3, "Star Vie Pyramid R50", 20, 5, 193);
        Producto p4 = new Producto(4, "Dunlop Titan", 8, 3, 85);

        Producto p5 = new Producto(5, "Vision King jm", 7, 1, 159);
        Producto p6 = new Producto(6, "Slazenger Reflex Pro", 5, 2, 80);
        //Almacenamos productos
        bd.insertaProducto(p1);
        bd.insertaProducto(p2);
        bd.insertaProducto(p3);
        bd.insertaProducto(p4);
        bd.insertaProducto(p5);
        bd.insertaProducto(p6);
        System.out.printf("Productos insertados.");
        bd.closeBD(); // Cerrar BD
    }

    public static void llenarventas(int idproducto, int cantidad) {
        // Datos de entrada para la venta
        //int idproducto = 2; //idproducto
        // int cantidad = 4; //cantidad
        BaseDatos bd = new BaseDatos();
        ODB odb = bd.getOdb();
        IQuery query = new CriteriaQuery(Producto.class,
                Where.equal("idproducto", idproducto));
        Objects<Producto> objetos = odb.getObjects(query);
        try {
            // Obtiene solo el primer objeto encontrado
            Producto pro = (Producto) objetos.getFirst();
            System.out.printf("ID=> %d: %s, STOCK-ACT: %d, STOCK-MIN: %d, PVP:%.2f %n", idproducto,
                    pro.getDescripcion(),
                    pro.getStockactual(), pro.getStockminimo(), pro.getPvp());
            System.out.printf("Cantidad: %d%n", cantidad);
            //se inserta la venta
            bd.insertaVenta(pro, cantidad);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("NO EXISTE EL PRODUCTO");
        } finally {
            bd.closeBD(); // Cerrar BD
        }
    }

    public static void verproductos() {
        BaseDatos bd = new BaseDatos();
        ODB odb = bd.getOdb();
        //recuperamos todos los objetos
        Objects<Producto> objects = odb.getObjects(Producto.class);
        System.out.printf("Número de Productos: %d%n", objects.size());
        int i = 1;
        // visualizamos los productos
        while (objects.hasNext()) {
            Producto pro = objects.next();
            System.out.printf("%d: %s, STOCK ACTUAL: %d, MINIMO: %d, Pvp: %.2f%n", i++,
                    pro.getDescripcion(), pro.getStockactual(),
                    pro.getStockminimo(), pro.getPvp());
        }
        bd.closeBD(); // Cerrar BD
    }

    public static void verventas() {
        BaseDatos bd = new BaseDatos();
        ODB odb = bd.getOdb();
        //recuperamos todos los objetos
        Objects<Venta> objects = odb.getObjects(Venta.class);
        System.out.printf("Número de ventas: %d%n", objects.size());
        int i = 1;
        // visualizamos los productos
        while (objects.hasNext()) {
            Venta pro = objects.next();
            System.out.println("Num venta: " + pro.getNumeroventa()
                    + ", idproducto: " + pro.getIdproducto() + ", fecha: " + pro.getFechaventa() + ", cantidad: " + pro.getCantidad()
                    + ", observaciones: " + pro.getObservaciones());
        }
        bd.closeBD(); // Cerrar BD
    }

    public static void verpedidos() {
        BaseDatos bd = new BaseDatos();
        ODB odb = bd.getOdb();
        //recuperamos todos los objetos
        Objects<Pedido> objects = odb.getObjects(Pedido.class);
        System.out.printf("Número de pedidos: %d%n", objects.size());
        int i = 1;
        // visualizamos los productos
        while (objects.hasNext()) {
            Pedido pro = objects.next();
            System.out.println("Num pedido: " + pro.getNumeropedido()
                    + ", nombre producto: " + pro.getProducto().getDescripcion()
                    + ", fecha: " + pro.getFecha() + ", cantidad: " + pro.getCantidad());
        }
        bd.closeBD(); // Cerrar BD
    }
}
