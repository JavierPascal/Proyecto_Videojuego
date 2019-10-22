package mx.itesm.jonapalu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Item_Falso {
    private int ID;
    private Texture texturaItem;
    private Sprite sprite;

    public Item_Falso(int ID, float x, float y){
        this.ID = ID;
        if(ID == 00){
            this.texturaItem = new Texture("Texturas/null.png");
        }else if(ID == 01){
            this.texturaItem = new Texture("Texturas/texTierra.png");
        }else if(ID == 02){
            this.texturaItem = new Texture("Texturas/texPiedra.png");
        }
        sprite = new Sprite(texturaItem);
        sprite.setPosition( x, y);
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void mover(float DX){
        sprite.setPosition(sprite.getX() - DX, sprite.getY());
    }

    public void moverA(float x, float y){
        sprite.setPosition(x, y);
    }

    public float getX(){
        return sprite.getX();
    }

    public float getY(){
        return sprite.getY();
    }

    public Texture getTexture(){
        return texturaItem;
    }
    public int getID(){
        return ID;
    }
}
