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
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class Informacion implements Screen {
    private final Juego juego;
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    private Texture texturaFondo;

    //Fases
    private Stage fasesMenu;

    private AssetManager manager;



    public Informacion(Juego juego) {
        this.juego = juego;
        manager = juego.getManager();

    }

    @Override
    public void show() {
        texturaFondo = manager.get("HUD/fondoGris.png");
        configuracionVista();
        crearBotones();
    }

    private void configuracionVista() {
        camara = new OrthographicCamera();
        camara.position.set( Juego.ANCHO / 2, Juego.ALTO / 2, 0);
        camara.update();
        vista = new StretchViewport(Juego.ANCHO, Juego.ALTO, camara);
        batch = new SpriteBatch();
    }

    private void crearBotones() {
        fasesMenu = new Stage(vista);
        //Boton Imagen de nosotros asi bien guapa
        Texture texturaNosotros = manager.get("Fotitos/nosotros.jpeg");
        TextureRegionDrawable trdNosotros = new TextureRegionDrawable
                (new TextureRegion(texturaNosotros));

        Texture texturaNosotrosPressed = manager.get("Fotitos/nosotrosPress.jpeg");
        TextureRegionDrawable trdNosotrosPressed = new TextureRegionDrawable
                (new TextureRegion(texturaNosotrosPressed));

        ImageButton btnNosotros = new ImageButton(trdNosotros, trdNosotrosPressed);
        btnNosotros.setPosition(0+btnNosotros.getWidth()/2, 150+btnNosotros.getHeight()/2);
        //Funcionamiento
        btnNosotros.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });

        //Botón Imagen de integrantes
        final TextureRegionDrawable trdIntegrantes = new TextureRegionDrawable(new TextureRegion(new Texture("Fotitos/Integrantes.png")));
        final TextureRegionDrawable trdIntegrantesPressed = new TextureRegionDrawable(new TextureRegion(new Texture("Fotitos/Integrantes.png")));
        final ImageButton btnIntegrantes = new ImageButton(trdIntegrantes, trdIntegrantesPressed);
        btnIntegrantes.setPosition(0+btnNosotros.getWidth()/2, 100);
        //Funcionamiento
        btnNosotros.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });

        //Boton de Regresar
        Texture texturabtnRegresar = manager.get("Botones/btnRegresar.png");
        TextureRegionDrawable trdRegresar = new TextureRegionDrawable
                (new TextureRegion(texturabtnRegresar));

        Texture texturabtnRegresarPressed = manager.get("Botones/btnRegresar.png");
        TextureRegionDrawable trdRegresarPressed = new TextureRegionDrawable
                (new TextureRegion(texturabtnRegresarPressed));

        ImageButton btnRegresar = new ImageButton(trdRegresar, trdRegresarPressed);
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
        fasesMenu.addActor(btnNosotros);
        fasesMenu.addActor(btnRegresar);
        fasesMenu.addActor(btnIntegrantes);

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
        manager.unload("Fotitos/nosotros.jpeg");
        manager.unload("Fotitos/nosotrosPress.jpeg");


    }
}
