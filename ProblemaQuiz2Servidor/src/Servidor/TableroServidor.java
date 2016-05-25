
package Servidor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
* @author Juan Camilo Mora, Luisa becerra, Felipe Villamil y Diego Alejandro Herrera
 */
public class TableroServidor extends JPanel implements ActionListener{
    private Timer timer; 
    private ArrayList<Circulo> circulo;
    private Carro personajeJugador1;
    private Carro personajeJugador2;
    private  ServerSocket serverSocket;
    private Socket Jugador1;
    private Socket Jugador2;
    private int puntajeJugador1 = 0;
    private int puntajeJugador2 = 0;
    //a diferencia del cliente el cual tienee entrada y salida este solo exporta la salida
    //en la linea 66 se ve como se envia la inormacion a cada cliente 
    DataOutputStream salida1;
    DataOutputStream salida2;
    
    public TableroServidor(){
        try{
            this.personajeJugador1= new Carro(250,400);
            this.serverSocket = new ServerSocket(8000);
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
            // ServerSocket servidor = new ServerSocket(8000, 5);
            // Socket coneccionEntrante = null;
            // while (true){
            // coneccionEntrante = servidor.accept();
            // coneccionEncargada (coneccionEntrante);
            // }
            //
            Jugador1=this.serverSocket.accept(); // Acepta el jugador 
            //Esperando la conexion del jugador
            this.salida1=new DataOutputStream(Jugador1.getOutputStream());
            System.out.println("CLIENTE 1: CONECTADO");
            Jugador2=this.serverSocket.accept();//Acepta el jugador 
            //Esperando la conexion del jugador
            this.salida2=new DataOutputStream(Jugador2.getOutputStream());
            System.out.println("CLIENTE 2: CONECTADO");
            //Proceso en el cual se le envia la informacion a cada cliente de los movimientos
            //del otro jugador
            Thread proceso1 = new Thread(new Hilo(Jugador1,salida2,personajeJugador1));
            proceso1.start();
            Thread proceso2 = new Thread(new Hilo(Jugador2,salida1,personajeJugador2));
            proceso2.start();

        }catch (IOException ex) {
            Logger.getLogger(TableroServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.timer = new Timer(50, this);
        this.timer.start();
        
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
        Rectangle recPersonaje1= this.personajeJugador1.obtenerRectangulo();
        Rectangle recPersonaje2= this.personajeJugador2.obtenerRectangulo();
        ArrayList<Circulo> copia = (ArrayList<Circulo>) this.circulo.clone();
        for(Circulo c : circulo){
           Rectangle RecCir = c.obtenerRectangulo();
           if(recPersonaje1.intersects(RecCir)){
               copia.remove(c);
               this.puntajeJugador1++;
           }else if (recPersonaje2.intersects(RecCir)){
               copia.remove(c);
               this.puntajeJugador2++;
           }               
           this.circulo=copia;              
        }
    }
    
}

