package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class CrearMundo implements Screen {

    private final Juego juego;
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    //Fondo Textura
    private Texture texturaFondo;

    //Modo de juego
    private modoDeJuego modo = modoDeJuego.Aventura;

    //Fases
    private Stage fasesMenu;

    //botones
    private String btnCreativoTextura;
    private String btnAventuraTextura;

    //Mundos
    private Array<ImageButton> botonesMundo;
    private Array<Mundo> Mundos;

    public CrearMundo(Juego juego) {
        this.juego = juego;
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
        vista = new FitViewport(Juego.ANCHO, Juego.ALTO, camara);
        batch = new SpriteBatch();
    }

    private void cargarTexturas() {
        //Fondo
        texturaFondo = new Texture( "HUD/fondoGris.png");
        //Botones
        btnCreativoTextura = "Botones/btnCreativo.png";
        btnAventuraTextura = "Botones/btnAventura.png";
    }
    private void crearMenu() {
        fasesMenu = new Stage(vista);
        //Boton de Regresar
        TextureRegionDrawable trdRegresar = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnRegresar.png")));
        TextureRegionDrawable trdRegresarPress = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnRegresarPressed.png")));
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
        //Boton Aventura
        final TextureRegionDrawable trdAventura = new TextureRegionDrawable(new TextureRegion(new Texture(btnAventuraTextura)));
        final TextureRegionDrawable trdAventuraPress = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnAventuraPressed.png")));
        final ImageButton btnAventura = new ImageButton(trdAventura, trdAventuraPress);
        btnAventura.setPosition(50, Juego.ALTO - 50 - btnAventura.getHeight());
        //Funcionamiento
        btnAventura.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                btnCreativoTextura = "Botones/btnCreativo.png";
                btnAventuraTextura = "Botones/btnAventuraPressed.png";
                modo = modoDeJuego.Aventura;
            }
        });
        //Boton Creativo
        TextureRegionDrawable trdCreativo = new TextureRegionDrawable(new TextureRegion(new Texture(btnCreativoTextura)));
        TextureRegionDrawable trdCreativoPress = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnCreativoPressed.png")));
        ImageButton btnCreativo = new ImageButton(trdCreativo, trdCreativoPress);
        btnCreativo.setPosition(50 +  btnCreativo.getWidth() + 10, Juego.ALTO - 50 - btnCreativo.getHeight());
        //Funcionamiento
        btnCreativo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                btnCreativoTextura = "Botones/btnCreativoPressed.png";
                btnAventuraTextura = "Botones/btnAventura.png";
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
                juego.setScreen(new Mundo(juego, 1));
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

    }
    private enum modoDeJuego {
        Aventura,
        Creativo

    }
}
