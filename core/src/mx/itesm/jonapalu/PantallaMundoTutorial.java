package mx.itesm.jonapalu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

class PantallaMundoTutorial extends Pantalla {

    private final Juego juego;
    private Stage fasesMenu;

    //Map
    private OrthogonalTiledMapRenderer mapaRenderer;
    private TiledMap mapa;
    //Audio
    private Music audioFondo;
    private Sound fx;
    //States
    private EstadoJuego estadoJuego;
    private EscenaPausa escenaPausa;
    private ParticleEffect pe;
    private AssetManager manager;


    private ImageButton btnPausa;
    private ImageButton btnRegresar;

    public PantallaMundoTutorial(Juego juego) {
        this.juego = juego;
        manager = juego.getManager();
    }

    @Override
    public void show() {
        fasesMenu = new Stage(vista);

        //Boton de Pausa
        Texture texturabtnPausa = new Texture("Botones/btnPausa.png");
        TextureRegionDrawable trdPausa = new TextureRegionDrawable
                (new TextureRegion(texturabtnPausa));

        btnPausa = new ImageButton(trdPausa);
        btnPausa.setPosition(Juego.ANCHO - 10 - btnPausa.getWidth(), Juego.ALTO - btnPausa.getHeight() - 10);
        //Funcionamiento
        btnPausa.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                fasesMenu.clear();
                escenaPausa.addBotton();
                estadoJuego = EstadoJuego.PAUSA;
            }

        });

        fasesMenu.addActor(btnPausa);
        escenaPausa = new EscenaPausa(juego, vista, batch, fasesMenu);
        mapa = manager.get("Mapas/mapaTutorial.tmx");
        mapaRenderer = new OrthogonalTiledMapRenderer(mapa);
        Gdx.input.setInputProcessor(fasesMenu);
        //Read Audios
        /*audioFondo = manager.get("Audios/marioBros.mp3");
        fx = manager.get("Audios/moneda.mp3");
        audioFondo.setLooping(true);
        audioFondo.play();
        audioFondo.setVolume(.2f);*/

    }

    @Override
    public void render(float delta) {

        borrarPantalla(1, 0, 0);

        batch.setProjectionMatrix(camara.combined);
        mapaRenderer.setView(camara);
        mapaRenderer.render();
        batch.begin();
        batch.end();
        fasesMenu.draw();

        if (estadoJuego == EstadoJuego.PAUSA) {
            escenaPausa.draw();
            if (escenaPausa.getPausa()) {
                fasesMenu.addActor(btnPausa);
                estadoJuego = EstadoJuego.JUGANDO;
                escenaPausa.setPausa();
            }
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        manager.unload("Botones/btnRegresar.png");
        manager.unload("Mapas/mapaTutorial.tmx");
        manager.unload("Texturas/Tileset.png");


    }
}
