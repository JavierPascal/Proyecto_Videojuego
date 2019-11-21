package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
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

public class Instrucciones extends Pantalla {

    private Juego juego;
    private Music audioFondo;
    private Texture texturaFondo;
    private Stage InstruccionesStage;

    public Instrucciones(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        AssetManager manager = new AssetManager();

        crearBotones();
        cargarTexturas();
        configuracionVista();

        //Read Audios
        //audioFondo = manager.get("Audios/marioBros.mp3");

    }

    private void configuracionVista() {
        camara = new OrthographicCamera();
        camara.position.set( Juego.ANCHO / 2, Juego.ALTO / 2, 0);
        camara.update();
        vista = new StretchViewport(Juego.ANCHO, Juego.ALTO, camara);
        batch = new SpriteBatch();
    }

    private void cargarTexturas() {
        texturaFondo = new Texture("Texturas/fondoInstrucciones.png");

    }

    private void crearBotones() {
        InstruccionesStage = new Stage(vista);
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
        InstruccionesStage.addActor(btnRegresar);
        //Cargar las entradas
        Gdx.input.setInputProcessor(InstruccionesStage);

    }

    @Override
    public void render(float delta) {

        borrarPantalla();
        juego.sumar(delta);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0 , 0);
        batch.end();
        InstruccionesStage.draw();


    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
