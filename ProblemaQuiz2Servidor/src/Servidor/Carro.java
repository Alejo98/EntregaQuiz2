/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Carro {
    private int x;
    private int y;
    private Image imagen;
    
    public Carro(int x, int y) {
        this.x = x;
        this.y = y;
        this.imagen = cargarImagen("Personaje.png");
        //this.imagen = cargarImagen("free_radical_game_sprites.png");
    }
    public Carro(){
        this.x=20;
        this.y=20;
    }
    public void dibujar(Graphics g, JPanel panel){
       g.setColor(Color.red);
       g.drawImage(imagen, x, y, x+32, y+32, 0, 0,180,180, panel);
       //g.drawImage(imagen, x, y, x+32, y+32, 32*9, 0, 32*9+32, 32, panel);
    }
    protected Image cargarImagen(String imagen) {
        ImageIcon img = new ImageIcon(imagen);
        Image image = img.getImage();
        return image;
    }
    public void mover(){
       this.x+=1;
    }    
    public Rectangle obtenerRectangulo(){
       return new Rectangle(x, y, 20, 20);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }
    
}
