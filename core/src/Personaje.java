import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mx.itesm.jonapalu.Juego;

public class Personaje {
    private Texture texturaPersonaje;
    private Sprite sprite;

    public Personaje(){
        this.texturaPersonaje = new Texture("Texturas/texNube.png");
        sprite = new Sprite(texturaPersonaje);
        sprite.setPosition(Juego.ANCHO, Juego.ALTO - texturaPersonaje.getHeight() - 50);
    }
    public void render(SpriteBatch batch){

        sprite.draw(batch);
    }

    public void mover(float dx, float dy){
        sprite.setPosition(sprite.getX() - dx, sprite.getY() + dy);
    }

    public float getX(){
        return sprite.getX();
    }

    public static double numeroRandom(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }
}
