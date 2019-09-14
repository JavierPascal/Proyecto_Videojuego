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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class informacion implements Screen {
    private final Juego juego;
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    private Texture texturaFondo;

    //Fases
    private Stage fasesMenu;

    public informacion(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture( "mundos/HUD/fondoGris.png");
        configuracionVista();
        crearBotones();
    }

    private void configuracionVista() {
        camara = new OrthographicCamera();
        camara.position.set( Juego.ANCHO / 2, Juego.ALTO / 2, 0);
        camara.update();
        vista = new FitViewport(Juego.ANCHO, Juego.ALTO, camara);
        batch = new SpriteBatch();
    }

    private void crearBotones() {
        fasesMenu = new Stage(vista);
        //Boton Aventura
        final TextureRegionDrawable trdNosotros = new TextureRegionDrawable(new TextureRegion(new Texture("Fotitos/nosotros.jpeg")));
        final TextureRegionDrawable trdNosotrosPress = new TextureRegionDrawable(new TextureRegion(new Texture("Fotitos/nosotrosPress.jpeg")));
        final ImageButton btnNosotros = new ImageButton(trdNosotros, trdNosotrosPress);
        btnNosotros.setPosition(50, 50);
        //Funcionamiento
        btnNosotros.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });
        fasesMenu.addActor(btnNosotros);

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
}
