package mx.itesm.jonapalu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mx.itesm.jonapalu.Juego;

public class Personaje {
    private Texture texturaPersonaje;
    private Sprite sprite;
    private boolean Moviendo;
    int dx;

    public Personaje(int x, int y){
        this.texturaPersonaje = new Texture("Personajes/personaje.png");
        sprite = new Sprite(texturaPersonaje);
        this.Moviendo = false;
        sprite.setPosition(x,y);
    }
    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void moverX(float x){
        if(Moviendo) {
            //Debes de comprobar si te puedes mover ( si hay un bloque abajo de donde te quieres mover y si no hay ningun bloque enfrente
            if(x > sprite.getX()){
                dx = -2;
            }else {
                dx = 2;
            }
            sprite.setPosition(sprite.getX() - dx, sprite.getY());
        }
    }

    public void moverY(float y){
        int dy = 2;
        sprite.setPosition(sprite.getX(), sprite.getY() - dy);
    }

    public float getX(){
        return sprite.getX();
    }
    public float getY(){ return sprite.getY();}
    public boolean getMoviendo(){
        return Moviendo;
    }

    public static double numeroRandom(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }
    public void dejarDeMover(){
        this.Moviendo = false;
    }
    public void empezarAMover(){
        this.Moviendo = true;
    }
}
