/*excepcion para el precio tanto de compra como de venta */
package ej1_almacen;

public class ValorNegativoStockException extends Exception {

	public ValorNegativoStockException(String string) {
		
		super(string);
		
	}
}
