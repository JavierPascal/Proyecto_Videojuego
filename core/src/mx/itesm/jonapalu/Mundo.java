package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.physics.box2d.World;

import java.io.File;

public class Mundo implements Screen {

    private static final float SCALE = 2.0f;
    public static final float PIXEL_PER_METER = 32f;
    private static final float TIME_STEP = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;
    private Box2DDebugRenderer box2DDebugRenderer;
    private Personaje personaje;
    private static final float VELOCITY_Y = -9.85f;
    private static final float VELOCITY_X = 0f;
    private Texture textureP;

    float mouseX;
    float mouseY;

    private final Juego juego;
    private SpriteBatch batch;
    private OrthographicCamera camara;
    private Viewport vista;
    private EstadosJuego Activo;
    private Stage fasesMenu;
    private Array<Float> arrMov;

    //Fondo Sprite
    private Array<Nuve> arrNuves;
    private Array<Item_Falso> arrItem;

    //Fondo Textura
    private Texture texturaError;
    private Texture texturaMano;

    //Personajes
    //private Array<Personaje> arrPersonajes;

    //Mundo
    private OrthogonalTiledMapRenderer rendererMap;
    private TiledMap map;
    private int FaseTutorial;

    //Audio
    private Music backAudio;
    private Sound fx;

    private int numeroMundo;
    private boolean MensajeError = false;

    private World world;

    public Mundo(Juego juego, int numeroMundo){
        this.Activo = EstadosJuego.TUTORIAL;
        this.juego = juego;
        this.numeroMundo = numeroMundo;
        this.FaseTutorial = 0;
        this.mouseY = -300;
        this.mouseX = -300;
    }

    @Override
    public void show() {
        configuracionVista();
        crearBotones();
        cargarMapa();
        audio();
        cargarTexturas();
        box2d();
    }

    private void box2d() {
        personaje = new Personaje(world);
        world = new World(new Vector2(0, -10), true);
    }

    private void configuracionVista() {
        camara = new OrthographicCamera();
        camara.position.set( Juego.ANCHO / 2, Juego.ALTO / 2, 0);
        camara.update();
        vista = new StretchViewport(Juego.ANCHO, Juego.ALTO, camara);
        batch = new SpriteBatch();
    }

    private void crearBotones() {
        fasesMenu = new Stage(vista);

        //Boton de Pausa
        TextureRegionDrawable trdPausa = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnPausa.png")));
        TextureRegionDrawable trdPausaPress = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnPausa.png")));
        ImageButton btnPausa = new ImageButton(trdPausa, trdPausaPress);
        btnPausa.setPosition(Juego.ANCHO - btnPausa.getWidth() - 10, Juego.ALTO - btnPausa.getHeight() - 10);
        //Funcionamiento
        btnPausa.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Activo = EstadosJuego.PAUSADO;
            }
        });
        //Boton de Crafteo
        TextureRegionDrawable trdCrafteo = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnCrafteo.png")));
        TextureRegionDrawable trdCrafteoPress = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnCrafteo.png")));
        ImageButton btnCrafteo = new ImageButton(trdCrafteo, trdCrafteoPress);
        btnCrafteo.setPosition(Juego.ANCHO - btnCrafteo.getWidth() - 10, btnCrafteo.getHeight() + 10);
        //Funcionamiento
        btnCrafteo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Activo = EstadosJuego.CRAFTEANDO;
            }
        });
        fasesMenu.addActor(btnPausa);
        fasesMenu.addActor(btnCrafteo);
    }

    private void cargarMapa() {
        /*AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("Map/jonapaluMap.tmx", TiledMap.class);
        manager.finishLoading(); //Segundo Plano
        map = manager.get("Map/jonapaluMap.tmx");
        rendererMap = new OrthogonalTiledMapRenderer(map);

         */
    }

    private void audio() {
        /*AssetManager manager = new AssetManager();
        manager.load("Audios/jonapalu.mp3", Music.class);
        manager.load("Audios/moneda.mp3", Sound.class);

        Read Audios
        backAudio = manager.get("Audios/jonapalu.mp3");
        fx = manager.get("Audios/caida.mp3");

        backAudio.setLooping(true);
        backAudio.play();
        backAudio.setVolume(.2f);*/
    }

    private void cargarTexturas() {
        textureP = new Texture(personaje.PLAYER_IMG_PATH);
    }

    @Override
    public void render(float delta) {
        if(Activo==EstadosJuego.JUGANDO){

            moverNuves();
            juego.sumar(delta);
            clearScreen();
            batch.setProjectionMatrix(camara.combined);
            batch.begin();
            CargarPantalla();
            batch.end();
            fasesMenu.draw();

        }else if (Activo == EstadosJuego.PAUSADO){

            System.out.println("Pausado");

        }else if(Activo == EstadosJuego.CRAFTEANDO){
            System.out.println("Crafteando");
        }
    }

    private void update() {
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        cameraUpdate();
    }
    private void cameraUpdate() {
        Vector3 position = camara.position;
        position.x = personaje.getBody().getPosition().x * PIXEL_PER_METER;
        position.y = personaje.getBody().getPosition().y * PIXEL_PER_METER;
        camara.position.set(position);
        camara.update();
    }

    private void CargarPantalla() {
        batch.draw(textureP, personaje.getBody().getPosition().x * PIXEL_PER_METER - (textureP.getWidth() / 2), personaje.getBody().getPosition().y * PIXEL_PER_METER - (textureP.getHeight() / 2));

        //Render Suelo
        for (Item_Falso item:arrItem) {
            item.render(batch);
        }
        //Render Nuves
        for (Nuve nuve: arrNuves) {
            nuve.render(batch);
        }
    }

    private void moverNuves() {
        for (Nuve nuve : arrNuves) {
            nuve.mover( (float)0.5, 0);
            if (nuve.getX() < 0 - nuve.getTexture().getWidth()) {
                arrNuves.removeIndex(arrNuves.indexOf(nuve, true));
                arrNuves.add(new Nuve());
            }
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
        camara.setToOrtho(false, width / SCALE, height / SCALE);
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
        textureP.dispose();
        batch.dispose();
        box2DDebugRenderer.dispose();
        world.dispose();
    }
    private enum EstadosJuego{
        PAUSADO,
        JUGANDO,
        CRAFTEANDO,
        TUTORIAL
    }
}
