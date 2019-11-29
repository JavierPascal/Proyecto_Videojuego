package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaCreativo extends Pantalla{

    private static final float RADIO = 10f ;
    private World mundo;
    private Body body;
    private Box2DDebugRenderer debugRenderer;
    private final Juego juego;

    private TiledMap mapa;
    private OrthogonalTiledMapRenderer mapaRenderer;


    private Personaje personaje;

    private AssetManager manager;
    //Botones
    private ImageButton btnPausa;
    private ImageButton btnCrafteo;
    private ImageButton btnEspada;
    private ImageButton btnPico;
    private ImageButton btnPala;
    private ImageButton btnRegresarC;
    private ImageButton btnPiedra;


    private Stage fasesMenu;

    //States
    private EstadoJuego estadoJuego = EstadoJuego.ANIMACION;
    private EscenaPausa escenaPausa;

    private boolean Espada = true;
    private boolean Pico = true;
    private boolean Pala = true;
    private boolean Crafteando = false;
    private boolean picar = false;


    public PantallaCreativo(Juego juego){
        this.juego = juego;
        manager = juego.getManager();


    }

    @Override
    public void show() {
        crearMundo();
        crearObjetos();
        cargarMapa();
        definirParedes();
        crearPersonaje();
        crearPausa();

        InputMultiplexer multi = new InputMultiplexer();
        multi.addProcessor(fasesMenu);
        multi.addProcessor(new ProcesadorEntrada());
        Gdx.input.setInputProcessor(multi);


    }

    private void crearPausa() {

        fasesMenu = new Stage(vista);

        //Boton de Pausa
        Texture texturabtnPausa = new Texture("Botones/btnPausa.png");
        TextureRegionDrawable trdPausa = new TextureRegionDrawable
                (new TextureRegion(texturabtnPausa));

        btnPausa = new ImageButton(trdPausa);
        btnPausa.setPosition(Juego.ANCHO - 10 - btnPausa.getWidth(), Juego.ALTO - btnPausa.getHeight() - 10);
        //Funcionamiento
        btnPausa.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                fasesMenu.clear();
                escenaPausa.addBotton();
                estadoJuego = EstadoJuego.PAUSA;
            }

        });
        //Boton de Regresar
        Texture texturabtnRegresar = new Texture("Botones/btnRegresar.png");
        TextureRegionDrawable trdRegresarC = new TextureRegionDrawable(new TextureRegion(texturabtnRegresar));
        btnRegresarC = new ImageButton(trdRegresarC);
        btnRegresarC.setPosition(10, Juego.ALTO - btnRegresarC.getHeight() - 10);
        //Funcionamiento
        btnRegresarC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                estadoJuego = EstadoJuego.TUTORIAL;
                fasesMenu.clear();
                if (Crafteando) {
                    fasesMenu.addActor(btnCrafteo);
                }
                fasesMenu.addActor(btnPausa);
                if (picar) {
                    fasesMenu.addActor(btnPiedra);
                }
            }
        });

        //Boton de Crafteo
        Texture texturabtnCrafteo = new Texture("Botones/btnCrafteo.png");
        TextureRegionDrawable trdCrafteo = new TextureRegionDrawable
                (new TextureRegion(texturabtnCrafteo));

        btnCrafteo = new ImageButton(trdCrafteo);
        btnCrafteo.setPosition(Juego.ANCHO - 10 - btnCrafteo.getWidth(), 10);
        //Funcionamiento
        btnCrafteo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                fasesMenu.clear();
                estadoJuego = EstadoJuego.CRAFTEO;
                if (Espada) {
                    fasesMenu.addActor(btnEspada);
                }
                if (Pico) {
                    fasesMenu.addActor(btnPico);
                }
                if (Pala) {
                    fasesMenu.addActor(btnPala);
                }
                fasesMenu.addActor(btnRegresarC);
            }

        });

        escenaPausa = new EscenaPausa(juego, vista, batch, fasesMenu);
        fasesMenu.addActor(btnPausa);
        mapa = manager.get("Mapas/mapaTutorial.tmx");
        mapaRenderer = new OrthogonalTiledMapRenderer(mapa);

        InputMultiplexer multi = new InputMultiplexer();
        multi.addProcessor(fasesMenu);
        Gdx.input.setInputProcessor(multi);
    }

    private void crearPersonaje() {
        Texture texture = new Texture("Personajes/personaje.png");
        personaje = new Personaje(texture,0,0);
    }

    private void definirParedes() {
        ConvertidorMapa.crearCuerpos(mapa,mundo);

    }

    private void cargarMapa() {
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.finishLoading(); //Segundo Plano
        mapa = manager.get("Mapas/mapaTutorial.tmx");
        mapaRenderer = new OrthogonalTiledMapRenderer(mapa);
    }

    private void crearObjetos() {
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.position.set(5,700);
        body = mundo.createBody(bodydef);

        CircleShape circulo =  new CircleShape();
        circulo.setRadius(RADIO);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circulo;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.1f;

        body.createFixture(fixtureDef);
        circulo.dispose();

        BodyDef bodyPisoDef = new BodyDef();
        bodyPisoDef.type = BodyDef.BodyType.StaticBody;
        bodyPisoDef.position.set(ANCHO/4,10);

        Body bodyPiso =  mundo.createBody(bodyPisoDef);

        PolygonShape pisoShape =  new PolygonShape();
        pisoShape.setAsBox(ANCHO/4,10);
        bodyPiso.createFixture(pisoShape,0);
    }

    private void crearMundo() {
        Box2D.init();
        Vector2 gravedad = new Vector2(0,-400);
        mundo = new World(gravedad, true);
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render(float delta) {
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        personaje.getSprite().setPosition(x-4*RADIO,y-RADIO);

        borrarPantalla();

        batch.setProjectionMatrix(camara.combined);

        mapaRenderer.setView(camara);
        mapaRenderer.render();
        fasesMenu.draw();

        debugRenderer.render(mundo,camara.combined);

        batch.begin();
        personaje.render(batch);

        if (estadoJuego == EstadoJuego.PAUSA) {
            fasesMenu.draw();
            escenaPausa.draw();
            if (escenaPausa.getPausa()) {
                fasesMenu.addActor(btnPausa);
                fasesMenu.addActor(btnPiedra);
                if (Crafteando) {
                    fasesMenu.addActor(btnCrafteo);
                }
                estadoJuego = EstadoJuego.TUTORIAL;
                escenaPausa.setPausa();
            }
        }
        batch.end();

        mundo.step(1/60f, 6,12);


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
            float x = body.getPosition().x;
            float y = body.getPosition().y;

            body.applyLinearImpulse(1000,0,x,y,true);
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
