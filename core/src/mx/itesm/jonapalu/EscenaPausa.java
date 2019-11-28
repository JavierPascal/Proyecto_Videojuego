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

import javax.xml.soap.Text;


public class EscenaPausa extends Stage{

    private Viewport view;
    private Stage fasesMenu;
    private SpriteBatch batch;
    private ImageButton btnCerrar;
    private ImageButton btnRegresar;
    private ImageButton Pausa;
    private boolean pausa = false;
    private AssetManager manager;
    private final Juego juego;



    public EscenaPausa(final Juego juego, Viewport view, SpriteBatch batch, final Stage fasesMenu){
        this.view = view;
        this.batch = batch;
        this.juego = juego;
        this.fasesMenu = fasesMenu;
        manager = juego.getManager();
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
        //Boton de Regresar
        Texture texturabtnRegresar = manager.get("Botones/btnRegresar.png");
        TextureRegionDrawable trdRegresar = new TextureRegionDrawable
                (new TextureRegion(texturabtnRegresar));
        btnRegresar = new ImageButton(trdRegresar);
        btnRegresar.setPosition(10, Juego.ALTO - btnRegresar.getHeight() - 10);

        //Funcionamiento
        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pausa=true;
                fasesMenu.clear();
                juego.setScreen(new PantallaMenu(juego));

            }
        });
        //Pausa
        Texture texturaPausa = manager.get("Botones/Pausa.png");
        TextureRegionDrawable trdPausa = new TextureRegionDrawable(new TextureRegion(texturaPausa));
        Pausa = new ImageButton(trdPausa);
        Pausa.setPosition(Juego.ANCHO/2-Pausa.getWidth()/2, Juego.ALTO/2-Pausa.getHeight()/2);

}
public boolean getPausa(){
        return pausa;
}
public void setPausa(){
        pausa = false;
}
public void addBotton(){
    fasesMenu.addActor(btnCerrar);
    fasesMenu.addActor(btnRegresar);
    fasesMenu.addActor(Pausa);
}
}

