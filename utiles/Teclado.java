package utiles;

import java.util.Scanner;

/**
 * Clase Teclado para la lectura de datos por teclado
 * 
 * @author Rafael Infante
 * @version 1.0
 */
public class Teclado {
  
  private static Scanner sc = new Scanner(System.in);
  
  public static String leerCadena() {
    return sc.nextLine();
  }
  
  public static String leerCadena(String msj) {
    System.out.println(msj);
    return leerCadena();
  }
  
  public static char leerCaracter() {
    do {
      try {
        return leerCadena().charAt(0);
      } catch (IndexOutOfBoundsException e) {
        System.err.println("\nEl dato introducido no es un caracter\n");
      }
    } while (true);
  }
  
  public static char leerCaracter(String msj) {
    System.out.println(msj);
    return leerCaracter();
  }
  
  public static int leerEntero() {
    do {
      try {
        return Integer.parseInt(leerCadena());
      } catch (NumberFormatException e) {
        System.err.println("\nEl dato introducido no es un numero entero");
      }
      
    } while (true);
  }
  
  public static int leerEntero(String msj) {
    System.out.println(msj);
    return leerEntero();
  }
  
  public static double leerDecimal() {
    do {
      try {
        return Double.parseDouble(leerCadena());
      } catch (NumberFormatException e) {
        System.err.println("\nEl dato introducido no es un numero decimal\n");
      }
    } while (true);
  }
  
  public static double leerDecimal(String msj) {
    System.out.println(msj);
    return leerDecimal();
  }
  
}