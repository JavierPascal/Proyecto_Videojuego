package mx.itesm.jonapalu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;


public class EscenaPausa extends Stage{

    private Viewport view;
    private Stage fasesMenu;
    private SpriteBatch batch;
    private ImageButton btnCerrar;
    private boolean pausa = false;


    public EscenaPausa(Juego juego, Viewport view, SpriteBatch batch, final Stage fasesMenu){
        this.view = view;
        this.batch = batch;
        this.fasesMenu = fasesMenu;
        Pixmap pixmap =  new Pixmap((int)(juego.ANCHO),(int)(juego.ALTO), Pixmap.Format.RGBA8888);
        pixmap.setColor(0,0,0,.5f);
        pixmap.fillRectangle(0,0,pixmap.getWidth(),pixmap.getHeight());
        Texture rectangleTexture = new Texture(pixmap);

        Image rectangleImage = new Image(rectangleTexture);
        rectangleImage.setPosition(juego.ANCHO/2-pixmap.getWidth()/2,juego.ALTO/2-pixmap.getHeight()/2);
        this.addActor(rectangleImage);



        //Boton de Cerrar
        Texture texturabtnCerrar = new Texture("Botones/btnCerrar.png");
        TextureRegionDrawable trdCerrar = new TextureRegionDrawable
                (new TextureRegion(texturabtnCerrar));

        btnCerrar = new ImageButton(trdCerrar);
        btnCerrar.setPosition(Juego.ANCHO - 10 - btnCerrar.getWidth() , Juego.ALTO - btnCerrar.getHeight() - 10);
        //Funcionamiento
        btnCerrar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pausa = true;
                fasesMenu.clear();
            }



        });


}
public boolean getPausa(){
        return pausa;
}
public void setPausa(){
        pausa = false;
}
public void addBotton(){
    fasesMenu.addActor(btnCerrar);
}
}

