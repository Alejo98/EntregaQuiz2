
package Servidor;
import java.awt.Dimension;
import javax.swing.JFrame;
/**
 *
 * @author Juan Camilo Mora, Luisa becerra, Felipe Villamil y Diego Alejandro Herrera
 */
public class EjecutableServidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        // nombre de la ventana
        frame.setTitle("SERVIDOR");
        //tamaño de la ventana
        frame.setSize(new Dimension(500,500));
        //lugar donde aparecera la ventana
        frame.setLocation(500, 10);
        // añadiendo el tablero del servidor
        frame.add(new TableroServidor());
        //boton cerrar
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //ventana visible
        frame.setVisible(true);
        // TODO code application logic here
    }
    
}
