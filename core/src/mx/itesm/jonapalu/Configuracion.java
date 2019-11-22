package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private Stage MenuFases;

    private Table tabla = new Table();
    private String tiempo;




    private SpriteBatch batch;
    private Viewport vista;
    private OrthographicCamera camara;

    //Fondo Textura
    private Texture texturaFondo;
    private AssetManager manager;

    public Configuracion(Juego juego){
        this.juego =juego;
        manager = juego.getManager();
    }

    @Override
    public void show() {
        configuracionVista();
        cargarTexturas();
        crearMenu();
    }

    private void cargarTexturas() {
        //Fondo
        texturaFondo = manager.get("HUD/fondoGris.png");

    }

    private void configuracionVista() {
        camara = new OrthographicCamera();
        camara.position.set( Juego.ANCHO / 2, Juego.ALTO / 2, 0);
        camara.update();
        vista = new StretchViewport(Juego.ANCHO, Juego.ALTO, camara);
        batch = new SpriteBatch();
    }
    private void crearMenu() {

        MenuFases = new Stage(vista);

        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        Label.LabelStyle estilo = new Label.LabelStyle();
        estilo.font = new BitmapFont(Gdx.files.internal("data/default.fnt"));
        tabla.setFillParent(true);
        Label nombreLabel = new Label("Nombre:", skin);
        tabla.defaults().width(100); // Hace que todas las celdas esten en default.
        tabla.add(nombreLabel);
        tabla.row();
        tabla.add();

        fasesMenu = new Stage(vista);
        //Boton de Regresar
        TextureRegionDrawable trdRegresar = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnRegresar.png")));
        ImageButton btnRegresar = new ImageButton(trdRegresar, trdRegresar);
        btnRegresar.setPosition(10, Juego.ALTO - btnRegresar.getHeight() - 10);
        //Funcionamiento
        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        //Anadir botones
        fasesMenu.addActor(btnRegresar);
        MenuFases.addActor(tabla);

        //Cargar las entradas
        Gdx.input.setInputProcessor(fasesMenu);
        Gdx.input.setInputProcessor(MenuFases);
    }

    @Override
    public void render(float delta) {
        tiempo = "12";
        juego.sumar(delta);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0 , 0);

        batch.end();
        fasesMenu.draw();
        MenuFases.draw();
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
        manager.unload("Botones/btnRegresar.png");
        manager.unload("HUD/fondoGris.png");


    }
}
