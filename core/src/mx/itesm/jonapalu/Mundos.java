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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class Mundos implements Screen {

    private final Juego juego;
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    //Fondo Textura
    private Texture texturaFondo;
    private AssetManager manager;

    //Botones de Mundos
    public int mundos;
    public Array<ImageButton> arrBotonesMundo;

    //Fases
    private Stage fasesMenu;

    public Mundos(Juego juego) {
        this.juego = juego;
        manager = juego.getManager();
    }

    @Override
    public void show() {
        cargarMundo();
        configuracionVista();
        cargarTexturas();
        crearMenu();
    }

    private void cargarMundo() {
        mundos = 0;
        //Hacer el contador

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
                juego.setScreen(new PantallaMenu(juego));
            }
        });
        //Boton Nuevo Mundo
        Texture texturaBtnAgregarMundo = manager.get("Botones/btnAgregarMundo.png");
        TextureRegionDrawable trdNuevoMundo = new TextureRegionDrawable
                (new TextureRegion(texturaBtnAgregarMundo));

        Texture texturaBtnAgregarMundoPressed = manager.get("Botones/btnAgregarMundoPressed.png");
        TextureRegionDrawable trdNuevoMundoPress = new TextureRegionDrawable
                (new TextureRegion(texturaBtnAgregarMundoPressed));

        ImageButton btnNuevoMundo = new ImageButton(trdNuevoMundo, trdNuevoMundoPress);
        btnNuevoMundo.setPosition(Juego.ANCHO - btnNuevoMundo.getWidth() - 20, 10);
        //Funcionamiento
        btnNuevoMundo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen((new PantallaCargando(juego, TipoPantalla.CREARMUNDO)));
            }
        });

        //Boton Mundo provicional
        final int numMundo = 1;

        Texture texturaBtnMundos = manager.get("Botones/btnMundo1.png");
        TextureRegionDrawable trdMundo = new TextureRegionDrawable
                (new TextureRegion(texturaBtnMundos));

        Texture texturaBtnMundosPressed = manager.get("Botones/btnMundo1Pressed.png");
        TextureRegionDrawable trdMundoPress = new TextureRegionDrawable
                (new TextureRegion(texturaBtnMundosPressed));

        ImageButton btnMundo = new ImageButton(trdMundo, trdMundoPress);
        btnMundo.setPosition(50, Juego.ALTO - 3*btnMundo.getHeight());
        //Funcionamiento
        btnMundo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new Mundo(juego, numMundo));
            }
        });
//Boton Tutorial
        Texture texturaBtnTutorial = manager.get("Botones/btnTutorial.png");
        TextureRegionDrawable trdTutorial = new TextureRegionDrawable
                (new TextureRegion(texturaBtnTutorial));

        Texture texturaBtnTutorialPressed = manager.get("Botones/btnTutorialPressed.png");
        TextureRegionDrawable trdTutorialPressed = new TextureRegionDrawable
                (new TextureRegion(texturaBtnTutorialPressed));

        ImageButton btnTutorial = new ImageButton(trdTutorial, trdTutorialPressed);
        btnTutorial.setPosition(50, Juego.ALTO  - 10 - 4*(btnTutorial.getHeight()));
        //Funcionamiento
        btnTutorial.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen((new PantallaCargando(juego, TipoPantalla.TUTORIAL)));
            }
        });

        //Anadir botones
        fasesMenu.addActor(btnRegresar);
        fasesMenu.addActor(btnNuevoMundo);
        fasesMenu.addActor(btnMundo);
        fasesMenu.addActor(btnTutorial);


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
        manager.unload("Botones/btnAgregarMundo.png");
        manager.unload("Botones/btnAgregarMundoPressed.png");
        manager.unload("HUD/btnMundos.png");
        manager.unload("HUD/btnMundosPress.png");
        manager.unload("Botones/btnTutorial.png");
        manager.unload("Botones/btnTutorial.png");

    }
}
