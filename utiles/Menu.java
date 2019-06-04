/**Clase Menu
 * 
 * @author Rafael Infante
 */

package utiles;

public class Menu {
  private String titulo = null;
  private String opciones[] = null;
  private int numOpciones = 0;

  /**
   * Constructor
   * 
   * @param titulo   título del menu
   * @param opciones opciones del menu
   */
  public Menu(String titulo, String[] opciones) {
    this.titulo = titulo;
    this.opciones = opciones;
    this.numOpciones = this.opciones.length;
  }

  /**
   * Gestiona el menu. Consiste en mostrar las opcines y recoger la opcion
   * seleccionada por el usuario
   * 
   * @return opcion valida del menu
   */
  public int gestionar() {
    mostrar();
    return recogerOpcion();
  }

  /**
   * Muestra las opciones del menu
   * 
   */
  private void mostrar() {
    int i = 1;
    System.out.println("***" + titulo + "***");
    for (String elemento : opciones) {
      System.out.println("(" + (i++) + ") " + elemento);
    }
  }

  /**
   * Recoge la opcion valida del menu
   * 
   * @return opcion valida
   */
  private int recogerOpcion() {
    int opcion = -1;
    do {
      System.out.println("Introduce una opción válida [1, " + numOpciones + "]");

      opcion = Teclado.leerEntero();

    } while (opcion < 1 || opcion > numOpciones);
    return opcion;
  }
}
