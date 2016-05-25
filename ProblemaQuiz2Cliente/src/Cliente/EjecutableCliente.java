
package Cliente;
import java.awt.Dimension;
import javax.swing.JFrame;
/**
 *
* @author Juan Camilo Mora, Luisa becerra, Felipe Villamil y Diego Alejandro Herrera
 */
public class EjecutableCliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        JFrame frame = new JFrame();
        // es el nombre que aparecera en la ventana
        frame.setTitle("JUGADOR");
        //tamaño de la ventana
        frame.setSize(new Dimension(500,500));
        //se añade el tablero
        frame.add(new TableroCliente());
        //el boton  de cerrar
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Se hace visible la ventana
        frame.setVisible(true);
    }
    
}
