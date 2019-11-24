package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class Configuracion implements Screen {

    private final Juego juego;

    //Fases
    private Stage fasesMenu;

    private Table tabla = new Table();
    private String tiempo;
    private CharSequence s;

    //Sonido

    private Music audioFondo;   // Fondo, largo
    private Sound efecto;       // Corto


    private SpriteBatch batch;
    private Viewport vista;
    private OrthographicCamera camara;

    //Fondo Textura
    private Texture texturaFondo;
    private AssetManager manager;

    public Configuracion(Juego juego) {
        this.juego = juego;
        manager = juego.getManager();
    }

    @Override
    public void show() {
        configuracionVista();
        cargarTexturas();
        crearMenu();
        /*/ Cargar audios
        manager.load("audios/marioBros.mp3", Music.class);
        manager.load("audios/moneda.mp3", Sound.class);
        manager.finishLoading();    // Segundo plano
        mapa = manager.get("mapaMario.tmx");
        mapa = manager.get("mapaMario.tmx");
        rendererMapa = new OrthogonalTiledMapRenderer(mapa);
        // Leer audios
        audioFondo = manager.get("audios/marioBros.mp3");
        efecto = manager.get("audios/moneda.mp3");

        audioFondo.setLooping(true);
        audioFondo.play();
        audioFondo.setVolume(0.2f);

         */

    }


    private void cargarTexturas() {
        //Fondo
        texturaFondo = manager.get("HUD/fondoGris.png");

    }

    private void configuracionVista() {
        camara = new OrthographicCamera();
        camara.position.set(Juego.ANCHO / 2, Juego.ALTO / 2, 0);
        camara.update();
        vista = new StretchViewport(Juego.ANCHO, Juego.ALTO, camara);
        batch = new SpriteBatch();
    }

    private void crearMenu() {

        fasesMenu = new Stage(vista);

        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        Label.LabelStyle estilo = new Label.LabelStyle();
        estilo.font = new BitmapFont(Gdx.files.internal("data/default.fnt"));
        tabla.setFillParent(true);
        Label nombreLabel = new Label("Nombre:", skin);
        Label tiempoLabel = new Label("Tiempo", skin);
        tabla.align(360); // Hace que todas las celdas esten en default.
        tabla.add(nombreLabel);
        tabla.add(tiempoLabel);
        tabla.row();

        tabla.debug();
        //tabla.add(s);


        //Boton de Regresar
        Texture texturabtnRegresar = manager.get("Botones/btnRegresar.png");
        TextureRegionDrawable trdRegresar = new TextureRegionDrawable
                (new TextureRegion(texturabtnRegresar));

        Texture texturabtnRegresarPressed = manager.get("Botones/btnRegresar.png");
        TextureRegionDrawable trdRegresarPress = new TextureRegionDrawable
                (new TextureRegion(texturabtnRegresarPressed));

        ImageButton btnRegresar = new ImageButton(trdRegresar, trdRegresarPress);
        btnRegresar.setPosition(10, Juego.ALTO - btnRegresar.getHeight() - 10);
        //Funcionamiento
        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        /*Boton sonido
        Texture texturasonido = manager.get("Botones/btnSonido.png");
        TextureRegionDrawable sonido = new TextureRegionDrawable
                (new TextureRegionDrawable(texturasonido));

        ImageButton btnsonido = new ImageButton(sonido);
        btnsonido.setPosition(Juego.ANCHO / 2, 0);

        //Funcionamiento
        btnsonido.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

            }
        });

        //Boton Silencio
        Texture texturasilencio =manager.get("Botones/btnSilencio.png");
        TextureRegionDrawable silencio = new TextureRegionDrawable
                (new TextureRegionDrawable(texturasilencio));


        ImageButton btnsilencio = new ImageButton(silencio);
        btnsilencio.setPosition(Juego.ANCHO / 2, 0);

        //Funcionamiento
        btnsilencio.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });
        */
        //Anadir botones
        fasesMenu.addActor(btnRegresar);
        fasesMenu.addActor(tabla);
        //fasesMenu.addActor(btnsonido);
        //fasesMenu.addActor(btnsilencio);


        //Cargar las entradas
        Gdx.input.setInputProcessor(fasesMenu);
    }

    @Override
    public void render(float delta) {
        s = "tiempo";
        tiempo = Float.toString(delta);
        juego.sumar(delta);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0, 0);

        batch.end();
        fasesMenu.draw();

    }

    @Override
    public void resize(int width, int height) {
        tabla.setSize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        manager.unload("Botones/btnRegresar.png");
        manager.unload("HUD/fondoGris.png");
        //manager.unload("Botones/btnSonido.png");
        //manager.unload("Botones/btnSilencio.png");


    }
}
