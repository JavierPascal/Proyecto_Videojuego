package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Mundo implements Screen {

    private final Juego juego;
    private SpriteBatch batch;
    private OrthographicCamera camara;
    private Viewport vista;
    private EstadosJuego Activo = EstadosJuego.JUGANDO;
    private Stage fasesMenu;
    private Array<Float> arrMov;

    //Fondo Sprite
    private Array<Nuve> arrNuves;
    private Array<Item_Falso> arrItem;

    //Fondo Textura
    private Texture texturaError;

    //Personajes
    //private Array<Personaje> arrPersonajes;
    private Personaje personaje;

    //Mundo
    private OrthogonalTiledMapRenderer rendererMap;
    private TiledMap map;

    //Audio
    private Music backAudio;
    private Sound fx;

    private int numeroMundo;
    private boolean MensajeError = false;

    public Mundo(Juego juego, int numeroMundo){
        this.juego = juego;
        this.numeroMundo = numeroMundo;
    }

    @Override
    public void show() {
        configuracionVista();
        crearBotones();
        cargarMapa();
        audio();
        crearFondo();
        cargarTexturas();
    }

    private void configuracionVista() {
        camara = new OrthographicCamera();
        camara.position.set( Juego.ANCHO / 2, Juego.ALTO / 2, 0);
        camara.update();
        vista = new FitViewport(Juego.ANCHO, Juego.ALTO, camara);
        batch = new SpriteBatch();
        //Codigo provisional para crear el Mundo

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
        AssetManager manager = new AssetManager();
        /*manager.load("Audios/jonapalu.mp3", Music.class);
        manager.load("Audios/moneda.mp3", Sound.class);

        Read Audios
        backAudio = manager.get("Audios/jonapalu.mp3");
        fx = manager.get("Audios/caida.mp3");

        backAudio.setLooping(true);
        backAudio.play();
        backAudio.setVolume(.2f);*/
    }

    private void crearFondo() {

        //Personajes
        /*arrPersonajes = new Array<>(10);
        for (int i = 0; i < 2; i++){
            Personaje personaje = new Personaje( (int)Juego.ANCHO - 10, (int)Juego.ALTO - 10);
            arrPersonajes.add(personaje);
        }*/
        personaje = new Personaje(320, 320);
        //Movimiento de personajes
        arrMov = new Array<>(6);
        //Nuves en pantalla
        arrNuves = new Array<>( 6);
        for (int i = 0; i < 2; i++){
            Nuve nuve = new Nuve((int) numeroRandom(0,Juego.ANCHO));
            arrNuves.add(nuve);
        }
        //Items en pantalla
        arrItem = new Array<> (4 * 22);
        for(int columna = 0; columna < 40; columna++) {
            for (int fila = 0; fila < 11; fila++) {
                int ID;
                if (fila == 4) {
                    ID = 01;
                } else if (fila < 4) {
                    ID = 02;
                } else {
                    ID = 00;
                }
                Item_Falso item = new Item_Falso(ID, columna * 64, fila * 64);
                arrItem.add(item);
            }
        }
    }

    private void cargarTexturas() {
        texturaError = new Texture("HUD/ErrorP.png");
    }

    @Override
    public void render(float delta) {
        if(Activo==EstadosJuego.JUGANDO){
            //Movimiento del personaje
            if(Gdx.input.justTouched()) {
                personaje.empezarAMover();
                arrMov.add((float)10);
            }
            moverPersonajes();
            moverNuves();
            juego.sumar(delta);
            clearScreen();

            batch.setProjectionMatrix(camara.combined);

            //rendererMap.setView(camara);
            //rendererMap.render();

            batch.begin();
            if(MensajeError){
                batch.draw(texturaError, Juego.ANCHO - 20, Juego.ALTO - 20);
                //Tras 10 segundos quitar el mensaje
            }

            //Render Suelo
            for (Item_Falso item:arrItem) {
                item.render(batch);
            }
            //Render Nuves
            for (Nuve nuve: arrNuves) {
                nuve.render(batch);
            }
            //Render Personajes
            /*for (Personaje personaje : arrPersonajes) {
                personaje.render(batch);
            }
            */
            personaje.render(batch);

        }else if (Activo == EstadosJuego.PAUSADO){

            System.out.println("Pausado");

        }else if(Activo == EstadosJuego.CRAFTEANDO){
            System.out.println("Crafteando");
        }


        batch.end();
    }

    private void moverPersonajes() {
        for(float x: arrMov){
            personaje.mover(x,0);
            if(personaje.getX() == x){
                arrMov.pop();
                personaje.dejarDeMover();
            }
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

    }
    public static double numeroRandom(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    private enum EstadosJuego{
        PAUSADO,
        JUGANDO,
        CRAFTEANDO
    }
}
