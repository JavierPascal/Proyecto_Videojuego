package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Hashtable;

import mx.itesm.jonapalu.Items.Item;

class PantallaMenu implements Screen {

    private final Juego juego;
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    //Fondo
    private Texture texturaFondo;
    private int deltaFondoX = 1;


    //Fondo Sprite
    private Array<Item_Falso> arrItem;

    //Stages
    private Stage menuStage;

    //Items
    private Hashtable<Integer, Item> Items;

    //Audio
    private AssetManager manager;


    public PantallaMenu(Juego juego) {
        this.juego = juego;
        manager = juego.getManager();
    }

    @Override
    public void show() {
        configuracionVista();
        cargarTexturas();
        crearMenu();
        cargarItems();

    }

    private void configuracionVista() {
        camara = new OrthographicCamera();
        camara.position.set(Juego.ANCHO / 2, Juego.ALTO / 2, 0);
        camara.update();
        vista = new StretchViewport(Juego.ANCHO, Juego.ALTO, camara);
        batch = new SpriteBatch();
    }

    private void cargarTexturas() {

        texturaFondo = manager.get("Texturas/fondoMenu.png");
        texturaFondo.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

    }

    private void crearMenu() {
        menuStage = new Stage(vista);

        //Logo
        Texture texturaLogo = manager.get("Botones/logo.png");
        TextureRegionDrawable trdLogo = new TextureRegionDrawable
                (new TextureRegion(texturaLogo));

        ImageButton btnLogo = new ImageButton(trdLogo, trdLogo);
        btnLogo.setPosition(Juego.ANCHO / 2 - btnLogo.getWidth() / 2, Juego.ALTO - btnLogo.getHeight() - 20);

        //Boton de Jugar
        Texture texturaBtnJugar = manager.get("Botones/btnJugar.png");
        TextureRegionDrawable trdJugar = new TextureRegionDrawable
                (new TextureRegion(texturaBtnJugar));

        Texture texturaBtnJugarPressed = manager.get("Botones/btnJugarPressed.png");
        TextureRegionDrawable trdJugarPress = new TextureRegionDrawable
                (new TextureRegion(texturaBtnJugarPressed));

        ImageButton btnJugar = new ImageButton(trdJugar, trdJugarPress);
        btnJugar.setPosition(Juego.ANCHO / 2 - btnJugar.getWidth() / 2, Juego.ALTO - 4 * btnJugar.getHeight());

        //Funcionamiento
        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen((new PantallaCargando(juego, TipoPantalla.MUNDOS)));
            }
        });

        //Boton Configuracion
        TextureRegionDrawable trdConf = new TextureRegionDrawable
                (new TextureRegion(new Texture("Botones/btnConf.png")));

        TextureRegionDrawable trdConfPress = new TextureRegionDrawable
                (new TextureRegion(new Texture("Botones/btnConfPressed.png")));

        ImageButton btnConf = new ImageButton(trdConf, trdConfPress);
        btnConf.setPosition(10, 10);

        //Funcionamiento
        btnConf.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen((new PantallaCargando(juego, TipoPantalla.CONFIGURACION)));
            }
        });

        //Boton Acerca de
        TextureRegionDrawable trdAcercaDe = new TextureRegionDrawable
                (new TextureRegion(new Texture("Botones/btnAcercaDe.png")));
        TextureRegionDrawable trdAcercaDePressed = new TextureRegionDrawable
                (new TextureRegion(new Texture("Botones/btnAcercaDePressed.png")));
        ImageButton btnAcercaDe = new ImageButton(trdAcercaDe, trdAcercaDePressed);
        btnAcercaDe.setPosition(Juego.ANCHO / 2 - btnAcercaDe.getWidth() / 2, Juego.ALTO - 5 * btnJugar.getHeight() - 40);
        //Funcionamiento
        btnAcercaDe.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, TipoPantalla.ACERCA));
            }
        });

        //Boton Instrucciones
        TextureRegionDrawable trdInstrucciones = new TextureRegionDrawable
                (new TextureRegion(new Texture("Botones/btnInstrucciones.png")));
        TextureRegionDrawable trdInstruccionesPressed = new TextureRegionDrawable
                (new TextureRegion(new Texture("Botones/btnInstruccionesPressed.png")));
        ImageButton btnInstrucciones = new ImageButton(trdInstrucciones, trdInstruccionesPressed);
        btnInstrucciones.setPosition(Juego.ANCHO / 2 - btnInstrucciones.getWidth() / 2, Juego.ALTO - 6 * btnJugar.getHeight() - 80);
        //Funcionamiento
        btnInstrucciones.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new Instrucciones(juego));
            }
        });


        //Anadir botones
        menuStage.addActor(btnLogo);
        menuStage.addActor(btnJugar);
        menuStage.addActor(btnConf);
        menuStage.addActor(btnAcercaDe);
        menuStage.addActor(btnInstrucciones);


        //Cargar las entradas
        Gdx.input.setInputProcessor(menuStage);
    }

    private void cargarItems() {

    }

    @Override
    public void render(float delta) {

        //movimientoFondo(delta);

        clearScreen();
        juego.sumar(delta);

        batch.setProjectionMatrix(camara.combined);
        deltaFondoX++;
        batch.begin();
        batch.draw(texturaFondo, 0, 0, deltaFondoX, 0, (int) Juego.ANCHO, (int) Juego.ALTO);
        batch.end();
        menuStage.draw();


    }

    private void movimientoFondo(float delta) {
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
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
        //texturaFondo.dispose();
        manager.unload("Texturas/mapaMundo.png");
        manager.unload("Botones/btnJugar.png");
        manager.unload("Botones/btnJugarPressed.png");

    }


    public static double numeroRandom(double min, double max) {
        double x = (int) (Math.random() * ((max - min) + 1)) + min;
        return x;
    }
}