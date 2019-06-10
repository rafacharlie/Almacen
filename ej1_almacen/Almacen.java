package ej1_almacen;

import java.util.ArrayList;

/**
 * Crea el programa GESTISIMAL (GESTIón SIMplificada de Almacén) para llevar el
 * control de los artículos de un almacén. De cada artículo se debe saber el
 * código, la descripción, el precio de compra, el precio de venta y el stock
 * (número de unidades). La entrada y salida de mercancía supone respectivamente
 * el incremento y decremento de stock de un determinado artículo. Hay que
 * controlar que no se pueda sacar más mercancía de la que hay en el almacén.
 * 
 * Partiendo del enunciado del libro, vamos a planificar el diseño de la
 * aplicación antes de implementarla:
 * 
 * Necesito una clase Articulo que representa a los artículos del almacén. Su
 * estado será: código, descripción, precio de compra, precio de venta y número
 * de unidades (nunca negativas). Como comportamiento: Considero que el código
 * va a generarse de forma automática en el constructor, así no puede haber dos
 * artículos con el mismo código. Esto implica que no puede modificarse el
 * código de un artículo. Sí el resto de las propiedades. Podremos mostrar el
 * artículo, por lo que necesito una representación del artículo en forma de
 * cadena (toString) Clase Almacen que realice el alta, baja, modificación,
 * entrada de mercancía (incrementa unidades), salida de mercancía (decrementa
 * unidades) El estado será un ArrayList de artículos. Esta clase es un
 * envoltorio de un ArrrayList. Su comportamiento será: añadir artículos (no
 * puede haber dos artículos iguales), eliminar artículos, incrementar las
 * existencias de un articulo (se delega en la clase artículo), decrementar las
 * existencias de un artículo (nunca por debajo de cero, se delega en la clase
 * artículo), devolver un artículo (para mostrarlo). Para listar el almacén
 * podría devolverse una cadena con todos los artículos del almacén (toString)
 * Clase TestAlmacen, donde se realiza la comunicación con el usuario (mostrar
 * menú y recuperar opción del menú, mostrar errores, listar) y se manipula el
 * almacén. Debes organizarla en métodos que deleguen en la clase almacén
 * (listar, annadir, eliminar... al menos uno por cada una de las opciones del
 * menú).
 * 
 * @author Rafael Infante Carrillo
 * @version 1.0
 */

public class Almacen {

  // Definición de la colección articulo
  private ArrayList<Articulo> almacen = new ArrayList<Articulo>();

  /**
   * 
   * método que da de alta al artículo según los parámetros pasados en el
   * constructor
   * 
   * @param descripcion
   * @param precioCompra
   * @param precioVenta
   * @param stock
   * @param iva
   * @throws ValorNegativoStockException
   * @throws IvaInvalidoException
   * @throws PrecioNegativoCompraException
   * @throws PrecioNegativoVentaException
   */
  public void darAlta(String descripcion, double precioCompra, double precioVenta, int stock, TipoIva iva)
      throws ValorNegativoStockException, IvaInvalidoException, PrecioNegativoCompraException,
      PrecioNegativoVentaException {

    almacen.add(new Articulo(descripcion, precioCompra, precioVenta, stock, iva));

  }

  /**
   * método que da de baja al artículo según el codigo que se le
   * pase(identificador)
   * 
   * @param codigo
   */
  public boolean darBaja(int codigo) {

    return almacen.remove(new Articulo(codigo));

  }

  /**
   * método que modifica el artículo (reescribir los parámetros
   * descripcion,precios y stock) según el codigo que le pase el usuario.
   * 
   * @param codigo
   * @param descripcion
   * @param precioCompra
   * @param precioVenta
   * @param stock
   * @param iva
   * @throws ValorNegativoStockException
   * @throws IvaInvalidoException
   * @throws PrecioNegativoCompraException
   * @throws PrecioNegativoVentaException
   * @throws codigoNoExisteException
   */
  public void modificarArticulo(int codigo, String descripcion, double precioCompra, double precioVenta, int stock,
      TipoIva iva) throws ValorNegativoStockException, IvaInvalidoException, PrecioNegativoCompraException,
      PrecioNegativoVentaException, codigoNoExisteException {
    getCodigo(codigo).modificarArticulo(descripcion, precioCompra, precioVenta, stock, iva);

  }

  /**
   * método que incrementa el stock según el codigo que le pasa el usuario
   * 
   * @param codigo
   * @param cantidad
   * @throws ValorNegativoStockException
   * @throws codigoNoExisteException
   */
  public void incrementarStock(int codigo, int cantidad) throws ValorNegativoStockException, codigoNoExisteException {

    getCodigo(codigo).incrementarStock(cantidad);

  }

  /**
   * método que decrementa el stock según el codigo que le pasa el usuario
   * 
   * @param codigo
   * @param cantidad
   * @throws ValorNegativoStockException
   * @throws codigoNoExisteException
   */
  public void decrementarStock(int codigo, int cantidad) throws ValorNegativoStockException, codigoNoExisteException {

    getCodigo(codigo).decrementarStock(cantidad);

  }

  /**
   * sobrecarga del constructor (únicamente codigo)
   * 
   * @param codigo
   * @return
   * @throws codigoNoExisteException
   */
  public Articulo getCodigo(int codigo) throws codigoNoExisteException {

    try {
      return almacen.get(almacen.indexOf(new Articulo(codigo)));
    } catch (IndexOutOfBoundsException e) {
      throw new codigoNoExisteException("El codigo del articulo no existe en el almacen");
    }

  }

  /**
   * metodo toString
   * 
   * Muestra el almacen
   */
  @Override
  public String toString() {
    return "Almacen \n" + almacen;
  }
  
  /**
   *metodo booleano que devuelve true si el almacen esta vacio y false si no lo esta.
   *
   * @return boolean
   */
  public boolean isEmpty() {
     return almacen.isEmpty();
  }

}
