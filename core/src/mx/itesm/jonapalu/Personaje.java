package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Personaje {
    private Sprite sprite;
    private Animation walkingAnimation;
    private float walkingTimer = 0;

    public Personaje(Texture texture, float x, float y){
        TextureRegion texturaCompleta = new TextureRegion(texture);
        TextureRegion[][] texturas = texturaCompleta.split(64,128);

        walkingAnimation = new Animation(0.2f, texturas[0][3], texturas[0][2], texturas[0][1 ]);
        walkingAnimation.setPlayMode(Animation.PlayMode.LOOP);

        sprite = new Sprite(texturas[0][0]); //idle
        sprite.setPosition(x,y);

    }
    public void render(SpriteBatch batch){
        walkingTimer += Gdx.graphics.getDeltaTime();
        TextureRegion texture = (TextureRegion) walkingAnimation.getKeyFrame(walkingTimer);
        batch.draw(texture,sprite.getX(),sprite.getY());
    }

    public Sprite getSprite() {
        return sprite;
    }
}