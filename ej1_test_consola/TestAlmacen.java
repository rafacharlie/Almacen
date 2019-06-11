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

    darAltaPrueba();

    ejecutaMenu();

  }

  /**
   * Metodo que da de alta a algunos productos.
   */
  private static void darAltaPrueba() {
    // Algunas instancias predefinidas.

    try {
      almacen.darAlta("Nestea", 6, 35, 40, TipoIva.REDUCIDO);
      almacen.darAlta("Coca-Cola", 30, 45, 68, TipoIva.GENERAL);
      almacen.darAlta("Fanta", 33, 42, 53, TipoIva.SUPER_REDUCIDO);
    } catch (ValorNegativoStockException | IvaInvalidoException | PrecioNegativoCompraException
        | PrecioNegativoVentaException e) {
      // Aqui nunca entra
    }
  }

  /**
   * método que finaliza el programa
   */
  private static void finalizarPrograma() {

    System.out.println("Fin de programa.");
    System.exit(0);

  }

  /**
   * método que ejecuta el menú principal
   *
   * @throws AlmacenVacioException
   */
  private static void ejecutaMenu() {

    do {

      try {

        elegirOpcionMenu();

      } catch (AlmacenVacioException e) {
        System.err.println(e.getMessage());
      }

    } while (true);
  }
  
  /**
   * Metodo que elige opcion en el menu principal. 
   *
   * @throws AlmacenVacioException
   */

  private static void elegirOpcionMenu() throws AlmacenVacioException {
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
    }

  }

  /**
   * metodo que muestra el almacen
   * 
   * @throws AlmacenVacioException
   */
  private static void mostrar() throws AlmacenVacioException {

    comprobarSiVacio();

    System.out.println(almacen.toString());
  }

  /**
   * Método añadir en el que se le pedirá al usuario los datos del artículo.
   * 
   */
  private static void darAlta() {

    try {

      almacen.darAlta(Teclado.leerCadena("Introduce una breve descripción del artículo:"),
          Teclado.leerDecimal("Precio de compra del artículo: "), Teclado.leerDecimal("Precio de venta del artículo: "),
          Teclado.leerEntero("Cantidad del artículo en stock: "), elegirIVA());

    } catch (ValorNegativoStockException | IvaInvalidoException | PrecioNegativoCompraException
        | PrecioNegativoVentaException e) {
      System.err.println("Error al añadir el artículo.\n" + e.getMessage());
    }

  }

  /**
   * Método para dar de baja un artículo de la lista.
   * 
   * @throws AlmacenVacioException
   */
  private static void darBaja() throws AlmacenVacioException {

    comprobarSiVacio();

    if (!almacen.darBaja(Teclado.leerEntero("Introduce el código identificador del artículo a eliminar: ")))
      System.err.println("El artículo no se encuentra en el almacén.\n");
    else {
      System.out.println("Articulo eliminado.");
    }
  }

  /**
   * Metodo que comprueba si el almacen esta vacio.
   * 
   * @throws AlmacenVacioException
   */
  private static void comprobarSiVacio() throws AlmacenVacioException {

    if (almacen.isEmpty())
      throw new AlmacenVacioException("No se ha podido realizar la accion, el almacen esta vacio.");
  }

  /**
   * método que permite modificar los valores de un artículo
   * 
   * @throws AlmacenVacioException
   */
  private static void modificarArticulo() throws AlmacenVacioException {

    comprobarSiVacio();

    try {

      almacen.modificarArticulo(Teclado.leerEntero("Introduce el código identificador del artículo a modificar: "),
          Teclado.leerCadena("Introduce una breve descripción del artículo:"),
          Teclado.leerDecimal("Precio de compra del artículo: "), Teclado.leerDecimal("Precio de venta del artículo: "),
          Teclado.leerEntero("Cantidad del artículo en stock: "), elegirIVA());

    } catch (ValorNegativoStockException | IvaInvalidoException | PrecioNegativoCompraException
        | PrecioNegativoVentaException | codigoNoExisteException e) {

      System.err.println("Hubo algún problema al modificar el artículo.\n" + e.getMessage());
    }
  }

  /**
   * método que incrementa el stock.
   * 
   * @throws AlmacenVacioException
   */
  private static void incrementarStock() throws AlmacenVacioException {

    comprobarSiVacio();

    try {

      int codigo = Teclado.leerEntero("Introduce el código identificador del artículo a aumentar el stock: ");

      almacen.incrementarStock(codigo, Teclado.leerEntero(
          "Introduce cuánto stock nuevo hay en el almacén (" + almacen.getCodigo(codigo).getStock() + " actuales): "));

      System.out.println("Stock añadido correctamente.");

    } catch (ValorNegativoStockException | codigoNoExisteException e) {
      System.err.println("Hubo algún problema al incrementar el stock.\n" + e.getMessage());
    }

  }

  /**
   * método que decrementa el stock.
   * 
   * @throws AlmacenVacioException
   */
  private static void decrementarStock() throws AlmacenVacioException {

    comprobarSiVacio();

    try {

      int codigo = Teclado.leerEntero("Introduce el código identificador del artículo a disminuir el stock: ");

      almacen.decrementarStock(codigo, Teclado.leerEntero("Introduce cuánto stock se ha eliminado del almacén ("
          + almacen.getCodigo(codigo).getStock() + " actuales): "));

      System.out.println("Stock eliminado correctamente.");

    } catch (ValorNegativoStockException | codigoNoExisteException e) {
      System.err.println("Hubo algún problema al decrementar el stock.\n" + e.getMessage());
    }

  }

  /**
   * Método con el cuál se comprueba que el valor del menú del IVA sea correcto.
   * 
   * @return TipoIva enum
   */
  private static TipoIva elegirIVA() {

    switch (menuIVA.gestionar()) {
    case 1:
      return TipoIva.GENERAL;

    case 2:
      return TipoIva.REDUCIDO;

    default:
      return TipoIva.SUPER_REDUCIDO;
    }

  }

}
