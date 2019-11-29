package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class CrearMundo implements Screen {

    private final Juego juego;
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;
    private String nombre;

    //Fondo Textura
    private Texture texturaFondo;

    //Modo de juego
    private modoDeJuego modo = modoDeJuego.Aventura;

    //Fases
    private Stage fasesMenu;

    //Manager
    private AssetManager manager;

    public CrearMundo(Juego juego) {
        this.juego = juego;
        manager = juego.getManager();
    }

    @Override
    public void show(){
        configuracionVista();
        cargarTexturas();
        crearMenu();
    }

    private void configuracionVista() {
        camara = new OrthographicCamera();
        camara.position.set( Juego.ANCHO / 2, Juego.ALTO / 2, 0);
        camara.update();
        vista = new StretchViewport(Juego.ANCHO, Juego.ALTO, camara);
        batch = new SpriteBatch();
    }

    private void cargarTexturas() {
        //Fondo
        texturaFondo = manager.get("HUD/fondoGris.png");
    }
    private void crearMenu() {
        fasesMenu = new Stage(vista);

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
                juego.setScreen(new PantallaCargando(juego, TipoPantalla.MUNDOS));

            }
        });

        //Boton de Aventura
        Texture texturabtnAventura = manager.get("Botones/btnAventura.png");
        TextureRegionDrawable trdBtnAventura = new TextureRegionDrawable
                (new TextureRegion(texturabtnAventura));

        Texture texturabtnAventuraPressed = manager.get("Botones/btnAventuraPressed.png");
        TextureRegionDrawable trdBtnAventuraPressed = new TextureRegionDrawable
                (new TextureRegion(texturabtnAventuraPressed));

        ImageButton btnAventura = new ImageButton(trdBtnAventura, trdBtnAventuraPressed);
        btnAventura.setPosition(200, Juego.ALTO - 50 - btnAventura.getHeight());

        //Funcionamiento
        btnAventura.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                modo = modoDeJuego.Aventura;
            }
        });

        //Boton de Creativo
        Texture texturaBtnCreativo = manager.get("Botones/btnCreativo.png");
        TextureRegionDrawable trdBtnCreativo = new TextureRegionDrawable
                (new TextureRegion(texturaBtnCreativo));

        Texture texturaBtnCreativoPressed = manager.get("Botones/btnCreativoPressed.png");
        TextureRegionDrawable trdBtnCreativoPressed = new TextureRegionDrawable
                (new TextureRegion(texturaBtnCreativoPressed));

        ImageButton btnCreativo = new ImageButton(trdBtnCreativo, trdBtnCreativoPressed);
        btnCreativo.setPosition(200 +  btnCreativo.getWidth() + 10, Juego.ALTO - 50 - btnCreativo.getHeight());

        //Funcionamiento
        btnAventura.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                modo = modoDeJuego.Creativo;
            }
        });

        //Boton Crear Mundo
        TextureRegionDrawable trdCrearMundo = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnAgregarMundo.png")));
        TextureRegionDrawable trdCrearMundoPress = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnAgregarMundoPressed.png")));
        ImageButton btnCrearMundo = new ImageButton(trdCrearMundo, trdCrearMundoPress);
        btnCrearMundo.setPosition(Juego.ANCHO - btnCrearMundo.getWidth() - 10, 10);
        //Funcionamiento
        btnCrearMundo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Debes de comprobar si el Mundo ya existe o no
                juego.setScreen(new Mundo(juego, nombre));
            }
        });

        //Anadir botones
        fasesMenu.addActor(btnRegresar);
        fasesMenu.addActor(btnAventura);
        fasesMenu.addActor(btnCreativo);
        fasesMenu.addActor(btnCrearMundo);



        //Cargar las entradas
        Gdx.input.setInputProcessor(fasesMenu);

    }

    @Override
    public void render(float delta) {
        clearScreen();
        juego.sumar(delta);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0 , 0);

        batch.end();
        fasesMenu.draw();


    }

    private void clearScreen() {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {

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
        manager.unload("HUD/fondoGris.png");
        manager.unload("Botones/btnRegresar.png");
        manager.unload("Botones/btnCreativo.png");
        manager.unload("Botones/btnCreativoPressed.png");
        manager.unload("Botones/btnAventura.png");
        manager.unload("Botones/btnAventuraPressed.png");

    }
    private enum modoDeJuego {
        Aventura,
        Creativo

    }
}
