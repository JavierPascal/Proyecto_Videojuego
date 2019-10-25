package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Mundo implements Screen {

    private final Juego juego;
    private SpriteBatch batch;
    private OrthographicCamera camara;
    private Viewport vista;
    private EstadosJuego Activo = EstadosJuego.JUGANDO;

    //Fondo Sprite
    private Array<Nuve> arrNuves;
    private Array<Item_Falso> arrItem;

    //Fondo Textura
    private Texture texturaFondo;
    private Texture persona;

    private int dx1 = 0;
    private int dx2 = 0;
    private int per1 = (int) numeroRandom(0, Juego.ANCHO);
    private int per2 = (int) numeroRandom(0, Juego.ANCHO);

    //Mundo
    private OrthogonalTiledMapRenderer rendererMap;
    private TiledMap map;

    //Audio
    private Music backAudio;
    private Sound fx;

    private int numeroMundo;

    public Mundo(Juego juego, int numeroMundo){
        this.juego = juego;
        this.numeroMundo = numeroMundo;
    }

    @Override
    public void show() {
        cargarMapa();
        audio();
        configuracionVista();
        crearFondo();
        cargarTexturas();
    }

    private void cargarMapa() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("Map/jonapaluMap.tmx", TiledMap.class);
        manager.finishLoading(); //Segundo Plano
        map = manager.get("Map/jonapaluMap.tmx");
        rendererMap = new OrthogonalTiledMapRenderer(map);
    }

    private void audio() {
        AssetManager manager = new AssetManager();
        manager.load("Audios/jonapalu.mp3", Music.class);
        manager.load("Audios/moneda.mp3", Sound.class);

        //Read Audios
        backAudio = manager.get("Audios/jonapalu.mp3");
        fx = manager.get("Audios/caida.mp3");

        backAudio.setLooping(true);
        backAudio.play();
        backAudio.setVolume(.2f);
    }

    private void configuracionVista() {
        camara = new OrthographicCamera();
        camara.position.set( Juego.ANCHO / 2, Juego.ALTO / 2, 0);
        camara.update();
        vista = new FitViewport(Juego.ANCHO, Juego.ALTO, camara);
        batch = new SpriteBatch();
        //Codigo provisional para crear el Mundo

    }

    private void crearFondo() {
        //Texturas
        //Nuves en pantalla
        arrNuves = new Array<>( 6);
        for (int i = 0; i < 2; i++){
            Nuve nuve = new Nuve((int) numeroRandom(0,Juego.ANCHO));
            arrNuves.add(nuve);
        }
        //Items en pantalla
        arrItem = new Array<> (4 * 22);
        for(int columna = 0; columna < 23; columna++) {
            for (int fila = 0; fila < 5; fila++) {
                int ID;
                if(fila == 4){
                    ID = 01;
                }
                else if(fila <4){
                    ID = 02;
                }
                else{
                    ID = 0;
                }
                Item_Falso item = new Item_Falso(ID, columna * 64, fila * 64);
                arrItem.add(item);
            }
        }
    }

    private void cargarTexturas() {
        texturaFondo = new Texture( "HUD/fondoCielo.png");
        persona = new Texture("HUD/persona.png");
    }

    @Override
    public void render(float delta) {
        if(Activo==EstadosJuego.JUGANDO){

            moverNuves();
            juego.sumar(delta);
            clearScreen();

            batch.setProjectionMatrix(camara.combined);

            rendererMap.setView(camara);
            rendererMap.render();

            batch.begin();
            batch.draw(texturaFondo, 0 , 0);

            //Render Nuves
            for (Nuve nuve: arrNuves) {
                nuve.render(batch);
            }

            //Render Suelo
            for (Item_Falso item:arrItem) {
                item.render(batch);
            }


        }else{

            System.out.println("hola luis mi");

        }


        batch.end();
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
        JUGANDO
    }
}
