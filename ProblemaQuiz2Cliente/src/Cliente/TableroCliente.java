
package Cliente;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TableroCliente extends JPanel implements ActionListener, KeyListener{
    private Timer timer; 
    private ArrayList<Circulo> circulo;
    private Carro personajeJugador1;
    private Carro personajeJugador2;
    private Socket jugador ;
    /*Metodo de entrada y salida, ya que la clase cliente recibe "en este caso enteros"
    y devuelve hacia el servidor*/
    private DataInputStream entrada;
    private DataOutputStream salida;
    private int puntajeJugador1 = 0;
    private int puntajeJugador2 =0;
    
    public TableroCliente(){
        try {
            this.setFocusable(true);
            this.addKeyListener(this);
            this.personajeJugador1 = new Carro(250,400);
            this.personajeJugador2= new Carro(250,400);
            this.circulo = new ArrayList<Circulo>();
             this.circulo.add(new Circulo(211,25));
            this.circulo.add(new Circulo(101,320));
            this.circulo.add(new Circulo(310,385));
            this.circulo.add(new Circulo(210,125)); 
            this.circulo.add(new Circulo(101,215));
            this.circulo.add(new Circulo(110,150));
            this.circulo.add(new Circulo(110,120));
            this.circulo.add(new Circulo(215,250));
            /*Si se desea conectar con una computadora diferente
            deberas cambiar el localhost por la IP del servidor*/
            jugador=new Socket("localhost",8000);
            System.out.println("LA CONEXION ESTA LISTA");
            
            this.salida = new DataOutputStream(jugador.getOutputStream());
            this.entrada = new DataInputStream (jugador.getInputStream());
            Thread proceso1 = new Thread(new Hilo(jugador,personajeJugador2));
            proceso1.start();
            this.timer = new Timer(50, this);
            this.timer.start();
        } catch (IOException ex) {
            Logger.getLogger(TableroCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Circulo c: this.circulo)
           c.dibujar(g,this);         
        this.personajeJugador1.dibujar(g,this);
        g.setColor(Color.BLUE);
        g.drawString("PUNTAJE JUGADOR 1  " + puntajeJugador1, 40, 50);
        this.personajeJugador2.dibujar(g, this);
        g.setColor(Color.MAGENTA);
        g.drawString("PUNTAJE JUGADOR 2   " + puntajeJugador2, 40, 30); 
        /*isEmpity es el metodo para saber si el ARRAYLIST de la clase circulo esta vacio
         *entonces en caso de que esta vacio, el juego habra terminado y debera mostrar el
         *GANADOR 
         */
         if (this.circulo.isEmpty()){
            if (puntajeJugador2>puntajeJugador1)
                g.drawString("HA GANADO EL JUGADOR 2, TIENE " + puntajeJugador2 +" PUNTOS", 200, 200); 
            else if (puntajeJugador1>puntajeJugador2)
                g.drawString("HA GANADO EL JUGADOR 1, TIENE " + puntajeJugador1 +" PUNTOS", 200, 200); 
            else if (puntajeJugador1 == puntajeJugador2)
                g.drawString("NO HAY GANADOR, HAN EMPATADO", 200, 200); 
         }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        validarColisiones();
         for(Circulo c: this.circulo)
            c.mover();
            repaint();       
    }  
    public void validarColisiones(){
        Rectangle recPersonaje= this.personajeJugador1.obtenerRectangulo();
        Rectangle recPersonaje2= this.personajeJugador2.obtenerRectangulo();
        ArrayList<Circulo> copia = (ArrayList<Circulo>) this.circulo.clone();
        for(Circulo c : circulo){
           Rectangle RecCir = c.obtenerRectangulo();
           if(recPersonaje.intersects(RecCir)){
               copia.remove(c);
               this.puntajeJugador1++;
           }else if (recPersonaje2.intersects(RecCir)){
               copia.remove(c);
               this.puntajeJugador2++;
           }               
           this.circulo=copia;     
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {     
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int codigo=-1;
        try {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            codigo=1;
        }

        if (key == KeyEvent.VK_LEFT) {
            codigo=1;
        }

        if (key == KeyEvent.VK_RIGHT) {
           codigo=2;
        }

        if (key == KeyEvent.VK_UP) {
           codigo=3;
        }

        if (key == KeyEvent.VK_DOWN) {
           codigo=4;
        }
         this.personajeJugador1.keyPressed(e);
         this.salida.writeInt(codigo);
         this.salida.flush();
        } catch (IOException ex) {
            Logger.getLogger(TableroCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
