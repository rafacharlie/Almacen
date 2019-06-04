/*excepcion ivan, salta al no insertar un valor correcto*/
package ej1_almacen;

public class IvaInvalidoException extends Exception{

public IvaInvalidoException(String string) {
		
		super(string);
		
	}
}
