package ej1_test_consola;

import java.io.IOException;

import ej1_almacen.*;
import utiles.Menu;
import utiles.Teclado;

/**
 * Crea el programa GESTISIMAL (GESTIón SIMplificada de Almacén) para llevar el
 * control de los artículos de un almacén. De cada artículo se debe saber el
 * código, la descripción, el precio de compra, el precio de venta y el stock
 * (número de unidades). La entrada y salida de mercancía supone respectivamente
 * el incremento y decremento de stock de un determinado artículo. Hay que
 * controlar que no se pueda sacar más mercancía de la que hay en el almacén.
 * 
 * @author Rafael Infante Carrillo
 * @version 1.0
 */
public class TestAlmacen {

  // instanciamos almacen
  static Almacen almacen = new Almacen();

  /**
   * Menú principal de la aplicación
   */
  static Menu menuPrincipal = new Menu(" -- ALMACÉN -- ", new String[] { "Mostrar el listado.", "Dar de alta.",
      "Dar de baja.", "Modificación.", "Entrada de mercancía.", "Salida de mercancía.", "Salir." });

  /**
   * Menú para elegir el IVA del artículo
   */
  static Menu menuIVA = new Menu(" -- IVA -- ", new String[] { "General", "Reducido", "Súper reducido" });

  /* MAIN */
  public static void main(String[] args) {

    // Algunas instancias predefinidas.

    try {
      almacen.darAlta("Nestea", 6, 35, 40, TipoIva.REDUCIDO);
      almacen.darAlta("Coca-Cola", 30, 45, 68, TipoIva.GENERAL);
      almacen.darAlta("Fanta", 33, 42, 53, TipoIva.SUPER_REDUCIDO);

    } catch (ValorNegativoStockException | IvaInvalidoException | PrecioNegativoCompraException
        | PrecioNegativoVentaException e) {
      // aqui nunca entra
    }
    
    //////////////////////////////////////revisar esta parte/////////////////////////////////////////////
    try {
      ejecutaMenu();
    } catch (NumberFormatException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ParametroNoNumericoException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (AlmacenVacioException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  /**
   * método vacio que finaliza el programa
   */
  private static void finalizarPrograma() {

    System.out.println("Fin de programa.");
    System.exit(0);

  }

  /**
   * método vacio que ejecuta el menú junto a mostrarMenu() y elegirOpcion()
   * 
   * @throws ParametroNoNumericoException
   * @throws ValorNegativoStockException
   * @throws IOException
   * @throws NumberFormatException
   * @throws AlmacenVacioException
   */
  private static void ejecutaMenu()
      throws ParametroNoNumericoException, NumberFormatException, IOException, AlmacenVacioException {

    do {

      switch (menuPrincipal.gestionar()) {

      case 1:
        mostrar();
        break;

      case 2:
        darAlta();
        break;

      case 3:
        darBaja();
        break;

      case 4:
        modificarArticulo();
        break;

      case 5:
        incrementarStock();
        break;

      case 6:
        decrementarStock();
        break;

      case 7:
        finalizarPrograma();
        break;

      // si el switch no entra en ningún case, entra en default
      default:
        System.err.println("No introdujiste una opción correcta. Inténtalo de nuevo.\n");
        break;
      }

    } while (true);
  }

  /**
   * metodo vacio que muestra el almacen
   */
  private static void mostrar() {
    System.out.println(almacen.toString());
  }

  /**
   * método vacio que añade un nuevo artículo al almacén
   * 
   * @throws IOException
   * @throws NumberFormatException
   * @throws PrecioNegativoVentaException
   * @throws PrecioNegativoCompraException
   * @throws IvaInvalidoException
   * @throws ValorNegativoStockException
   */
  private static void darAlta() {
    try {

      String descripcion = Teclado.leerCadena("Introduce una breve descripción del artículo:");

      double precioCompra = Teclado.leerDecimal("Precio de compra del artículo: ");

      double precioVenta = Teclado.leerDecimal("Precio de venta del artículo: ");

      int stock = Teclado.leerEntero("Cantidad del artículo en stock: ");

      almacen.darAlta(descripcion, precioCompra, precioVenta, stock, elegirIVA());

    } catch (ValorNegativoStockException | IvaInvalidoException | PrecioNegativoCompraException
        | PrecioNegativoVentaException e) {
      System.err.println("Error al añadir el artículo.\n" + e.getMessage());
    }

  }

  /**
   * método vacio que elimina un artículo del almacén
   */
  private static void darBaja() {

    if (!almacen.darBaja(Teclado.leerEntero("Introduce el código identificador del artículo a eliminar: ")))
      System.err.println("El artículo no se encuentra en el almacén.\n");

    System.out.println("Articulo eliminado.");
  }

  /**
   * método vacio que permite modificar los valores de un artículo
   */
  private static void modificarArticulo() {

    try {

      int codigo = Teclado.leerEntero("Introduce el código identificador del artículo a modificar: ");
      Articulo articulo = almacen.getCodigo(codigo);

      String descripcion = Teclado.leerCadena("Introduce una breve descripción del artículo:");

      double precioCompra = Teclado.leerDecimal("Precio de compra del artículo: ");

      double precioVenta = Teclado.leerDecimal("Precio de venta del artículo: ");

      int stock = Teclado.leerEntero("Cantidad del artículo en stock: ");

      // menuIva();
      // TipoIva iva = elegirIVA();

      almacen.modificarArticulo(codigo, descripcion, precioCompra, precioVenta, stock, elegirIVA());

    } catch (ValorNegativoStockException | IvaInvalidoException | PrecioNegativoCompraException
        | PrecioNegativoVentaException e) {

      System.err.println("Hubo algún problema al modificar el artículo.\n" + e.getMessage());
    }
  }

  /**
   * método vacio que incrementa el stock según el parámetro codigo que le envie
   * el usuario
   */
  private static void incrementarStock() {

    try {

      int codigo = Teclado.leerEntero("Introduce el código identificador del artículo a aumentar el stock: ");

      Articulo articulo = almacen.getCodigo(codigo);

      int cantidad = Teclado
          .leerEntero("Introduce cuánto stock nuevo hay en el almacén (" + articulo.getStock() + " actuales): ");

      System.out.println("Stock añadido correctamente.");

      almacen.incrementarStock(codigo, cantidad);
    } catch (ValorNegativoStockException e) {
      System.err.println("Hubo algún problema al incrementar el stock.\n" + e.getMessage());
    }

  }

  /**
   * método vacio que decrementa el stock según el parámetro codigo que le envie
   * el usuario
   */
  private static void decrementarStock() {
    try {
      int codigo = Teclado.leerEntero("Introduce el código identificador del artículo a disminuir el stock: ");

      Articulo articulo = almacen.getCodigo(codigo);

      int cantidad = Teclado
          .leerEntero("Introduce cuánto stock se ha eliminado del almacén (" + articulo.getStock() + " actuales): ");
      
      System.out.println("Stock eliminado correctamente.");
      
      almacen.decrementarStock(codigo, cantidad);
      
    } catch (ValorNegativoStockException e) {
      System.err.println("Hubo algún problema al decrementar el stock.\n" + e.getMessage());
    }
    

  }

  /**
   * Método con el cuál se comprueba que el valor del menú del IVA sea correcto.
   * 
   * @return numeroIVA
   * @throws NumberFormatException
   * @throws IOException
   */
  private static TipoIva elegirIVA() {

    try {
      switch (menuIVA.gestionar()) {
      case 1:
        return TipoIva.GENERAL;

      case 2:
        return TipoIva.REDUCIDO;

      default:
        return TipoIva.SUPER_REDUCIDO;
      }
    } catch (NumberFormatException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;

  }

}