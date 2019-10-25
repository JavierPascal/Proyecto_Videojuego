package mx.itesm.jonapalu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Nuve {
    private Texture texturaNuve;
    private Sprite sprite;

    public Nuve(){
        this.texturaNuve = new Texture("Texturas/texNube.png");
        sprite = new Sprite(texturaNuve);
        sprite.setPosition(Juego.ANCHO, Juego.ALTO - texturaNuve.getHeight() - 50);
    }

    public Nuve(int x){
        this.texturaNuve = new Texture("Texturas/texNube.png");
        sprite = new Sprite(texturaNuve);
        sprite.setPosition(x, Juego.ALTO - texturaNuve.getHeight() - 20 - (int)numeroRandom(0,40));
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

    public Texture getTexture(){
        return texturaNuve;
    }

    public static double numeroRandom(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }
}
