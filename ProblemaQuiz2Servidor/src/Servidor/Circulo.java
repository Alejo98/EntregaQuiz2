/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author PERSONAL
 */
public class Circulo {
    private int x;
    private int y;
    private Image imagen;
    private int sec;
    
    public Circulo (int x, int y){
        this.x=x;
        this.y=y;
        this.sec=0;
        this.imagen= cargarImagen ("FullCoins.png");
    }
    public Circulo(){
        this.x=20;
        this.y=20;
    }
    public void dibujar(Graphics g, JPanel panel){
        g.setColor(Color.red);
        g.drawImage(imagen, x, y, x+16, y+16, 16*this.sec, 0, 16*this.sec + 16, 16, panel);
        
    }
    public void mover(){
       if(this.sec==8){
         this.sec =0;
       }
       else       
           this.sec++;
    }
    protected Image cargarImagen(String imagen) {
        ImageIcon img = new ImageIcon(imagen);
        Image image = img.getImage();
        return image;
    }
    public Rectangle obtenerRectangulo(){
       return new Rectangle(x, y, 20, 20);
    }
}
