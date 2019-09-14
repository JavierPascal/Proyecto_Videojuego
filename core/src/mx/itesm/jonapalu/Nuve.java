package mx.itesm.jonapalu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Nuve {
    private Texture texturaNuve;
    private Sprite sprite;

    public Nuve(Texture texturaNuve){
        this.texturaNuve = texturaNuve;
        sprite = new Sprite(texturaNuve);
        sprite.setPosition(Juego.ANCHO, Juego.ALTO - texturaNuve.getHeight() - 50);
    }

    public Nuve(Texture texturaNuve, int x){
        this.texturaNuve = texturaNuve;
        sprite = new Sprite(texturaNuve);
        sprite.setPosition(x, Juego.ALTO - texturaNuve.getHeight() - 20);
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
}
