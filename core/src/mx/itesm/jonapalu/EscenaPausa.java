package mx.itesm.jonapalu;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

public class EscenaPausa extends Stage{

    private Viewport view;
    private Stage fasesMenu;
    private SpriteBatch batch;

    public EscenaPausa(Juego juego, Viewport view, SpriteBatch batch){
        this.view = view;
        this.batch = batch;
        Pixmap pixmap =  new Pixmap((int)(juego.ANCHO*0.7f),(int)(juego.ALTO*0.8f), Pixmap.Format.RGBA8888);
        pixmap.setColor(0,0,0,.5f);
        pixmap.fillRectangle(0,0,pixmap.getWidth(),pixmap.getHeight());
        Texture rectangleTexture = new Texture(pixmap);

        Image rectangleImage = new Image(rectangleTexture);
        rectangleImage.setPosition(juego.ANCHO/2-pixmap.getWidth()/2,juego.ALTO/2-pixmap.getHeight()/2);
        this.addActor(rectangleImage);
    }
}
