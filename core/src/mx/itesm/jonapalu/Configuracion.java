package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class Configuracion implements Screen {

    private final Juego juego;

    //Efectos
    public Array<Sound> arrdesonidos;

    //Fases
    private Stage fasesMenu;

    //private Table tabla = new Table();
    //private String tiempo;
    //private CharSequence s;


    private SpriteBatch batch;
    private Viewport vista;
    private OrthographicCamera camara;

    private ImageButton btnSilencio;

    //Fondo Textura
    private Texture texturaFondo;
    private AssetManager manager;

    public Configuracion(Juego juego) {
        this.juego = juego;
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
        camara.position.set(Juego.ANCHO / 2, Juego.ALTO / 2, 0);
        camara.update();
        vista = new StretchViewport(Juego.ANCHO, Juego.ALTO, camara);
        batch = new SpriteBatch();
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

        final ImageButton btnRegresar = new ImageButton(trdRegresar, trdRegresarPress);
        btnRegresar.setPosition(10, Juego.ALTO - btnRegresar.getHeight() - 10);
        //Funcionamiento
        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });
        //Boton de Sonido
        Texture texturabtnSonido = manager.get("Configuracion/Sonido.png");
        TextureRegionDrawable trdSonido = new TextureRegionDrawable
                (new TextureRegion(texturabtnSonido));

        Texture texturabtnSonidoPressed = manager.get("Configuracion/Sonido.png");
        TextureRegionDrawable trdSonidoPress = new TextureRegionDrawable
                (new TextureRegion(texturabtnSonidoPressed));

        final ImageButton btnSonido = new ImageButton(trdSonido, trdSonidoPress);
        btnSonido.setPosition(Juego.ANCHO/2 - (btnSonido.getWidth()/2), Juego.ALTO/2- (btnSonido.getHeight()/2));
        //Funcionamiento
        btnSonido.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                fasesMenu.clear();
                Juego.Musica.play();
                fasesMenu.addActor(btnSilencio);
                fasesMenu.addActor(btnRegresar);

            }
        });

        // Boton de Silencio
        Texture texturabtnSilencio = manager.get("Configuracion/Silencio.png");
        TextureRegionDrawable trdSilencio = new TextureRegionDrawable
                (new TextureRegion(texturabtnSilencio));

        Texture texturabtnSilencioPressed = manager.get("Configuracion/Silencio.png");
        TextureRegionDrawable trdSilencioPress = new TextureRegionDrawable
                (new TextureRegion(texturabtnSilencioPressed));

        btnSilencio = new ImageButton(trdSilencio, trdSilencioPress);
        btnSilencio.setPosition(Juego.ANCHO/2 - (btnSilencio.getWidth()/2), Juego.ALTO/2- (btnSilencio.getHeight()/2));

        //Funcionamiento
        btnSilencio.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                fasesMenu.clear();
                Juego.Musica.pause();
                fasesMenu.addActor(btnRegresar);
                fasesMenu.addActor(btnSonido);
            }
        });


        //Anadir botones
        fasesMenu.addActor(btnRegresar);
        if(Juego.Musica.isPlaying()) {
            fasesMenu.addActor(btnSilencio);
        }
        else{
            fasesMenu.addActor(btnSonido);
        }


        //Cargar las entradas
        Gdx.input.setInputProcessor(fasesMenu);

    }
    @Override
    public void render(float delta) {

        juego.sumar(delta);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0, 0);

        batch.end();
        fasesMenu.draw();

    }

    @Override
    public void resize(int width, int height) {
        //tabla.setSize(width, height);
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
        manager.unload("Configuracion/Sonido.png");
        manager.unload("Configuracion/Silencio.png");
        manager.unload("Botones/btnRegresar.png");
        manager.unload("HUD/fondoGris.png");



    }
    private class ProcesadorEntrada implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return true;


        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}