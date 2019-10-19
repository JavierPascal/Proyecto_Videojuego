package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class Configuracion implements Screen {

    private final Juego juego;

    //Fases
    private Stage fasesMenu;

    private SpriteBatch batch;
    private Viewport vista;
    private OrthographicCamera camara;

    //Fondo Textura
    private Texture texturaFondo;

    public Configuracion(Juego juego){
        this.juego =juego;
    }

    @Override
    public void show() {
        configuracionVista();
        cargarTexturas();
        crearMenu();
    }

    private void cargarTexturas() {
        //Fondo
        texturaFondo = new Texture( "Mundos/HUD/fondoGris.png");
    }

    private void configuracionVista() {
        camara = new OrthographicCamera();
        camara.position.set( Juego.ANCHO / 2, Juego.ALTO / 2, 0);
        camara.update();
        vista = new FitViewport(Juego.ANCHO, Juego.ALTO, camara);
        batch = new SpriteBatch();
    }
    private void crearMenu() {

        fasesMenu = new Stage(vista);
        //Boton de Regresar
        TextureRegionDrawable trdRegresar = new TextureRegionDrawable(new TextureRegion(new Texture("Mundos/boton/btnRegresar.png")));
        TextureRegionDrawable trdRegresarPress = new TextureRegionDrawable(new TextureRegion(new Texture("Mundos/boton/btnRegresarPress.png")));
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

        //Anadir botones
        fasesMenu.addActor(btnRegresar);

        //Cargar las entradas
        Gdx.input.setInputProcessor(fasesMenu);
    }

    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0 , 0);

        batch.end();
        fasesMenu.draw();
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
}
