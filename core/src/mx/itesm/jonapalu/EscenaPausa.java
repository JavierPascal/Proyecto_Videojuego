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
    private ImageButton btnRegresar;
    private ImageButton btnSonido;
    private ImageButton Pausa;
    private boolean pausa = false;
    private AssetManager manager;
    private final Juego juego;

    private ImageButton btnSilencio;



    public EscenaPausa(final Juego juego, Viewport view, SpriteBatch batch, final Stage fasesMenu){
        this.view = view;
        this.batch = batch;
        this.juego = juego;
        this.fasesMenu = fasesMenu;
        manager = juego.getManager();
        Pixmap pixmap =  new Pixmap((int)(juego.ANCHO),(int)(juego.ALTO), Pixmap.Format.RGBA8888);
        pixmap.setColor(0,0,0,.2f);
        pixmap.fillRectangle(0,0,pixmap.getWidth(),pixmap.getHeight());
        Texture rectangleTexture = new Texture(pixmap);

        Image rectangleImage = new Image(rectangleTexture);
        rectangleImage.setPosition(juego.ANCHO/2-pixmap.getWidth()/2,juego.ALTO/2-pixmap.getHeight()/2);
        this.addActor(rectangleImage);

        //Boton de Sonido
        Texture texturabtnSonido = manager.get("Configuracion/Sonido.png");
        TextureRegionDrawable trdSonido = new TextureRegionDrawable
                (new TextureRegion(texturabtnSonido));

        btnSonido = new ImageButton(trdSonido);
        btnSonido.setPosition(Juego.ANCHO/2 - (btnSonido.getWidth()/2), Juego.ALTO/2- (btnSonido.getHeight()/2));
               //Funcionamiento
        btnSonido.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                fasesMenu.clear();
                Juego.Musica.play();
                fasesMenu.addActor(btnSilencio);
                fasesMenu.addActor(btnCerrar);
                fasesMenu.addActor(btnRegresar);
                fasesMenu.addActor(Pausa);

            }
        });

        // Boton de Silencio
        Texture texturabtnSilencio = manager.get("Configuracion/Silencio.png");
        TextureRegionDrawable trdSilencio = new TextureRegionDrawable
                (new TextureRegion(texturabtnSilencio));

        btnSilencio = new ImageButton(trdSilencio);
        btnSilencio.setPosition(Juego.ANCHO/2 - (btnSilencio.getWidth()/2), Juego.ALTO/2- (btnSilencio.getHeight()/2));
        //Funcionamiento
        btnSilencio.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                fasesMenu.clear();
                Juego.Musica.pause();
                fasesMenu.addActor(btnSonido);
                fasesMenu.addActor(btnCerrar);
                fasesMenu.addActor(btnRegresar);
                fasesMenu.addActor(Pausa);

            }
        });

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
        //Boton de Salir
        Texture texturabtnRegresar = manager.get("Botones/btnExit.png");
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
        Pausa.setPosition(Juego.ANCHO/2-Pausa.getWidth()/2, Juego.ALTO - Pausa.getHeight() - 10);

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
    if(Juego.Musica.isPlaying()) {
        fasesMenu.addActor(btnSilencio);
    }
    else{
        fasesMenu.addActor(btnSonido);
    }
}
}

