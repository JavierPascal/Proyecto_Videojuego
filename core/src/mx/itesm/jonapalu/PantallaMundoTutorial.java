package mx.itesm.jonapalu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

class PantallaMundoTutorial extends Pantalla {

    private final Juego juego;
    private Stage fasesMenu;

    //States
    private EstadoJuego estadoJuego = EstadoJuego.ANIMACION;
    private EscenaPausa escenaPausa;
    private AssetManager manager;


    private ImageButton btnPausa;
    private ImageButton btnCrafteo;
    private ImageButton btnEspada;
    private ImageButton btnPico;
    private ImageButton btnPala;
    private ImageButton btnRegresarC;
    private ImageButton btnTierra;
    private ImageButton btnMadera;
    private ImageButton btnPiedra;
    private ImageButton btnDios;
    private ImageButton btnCuadroTXT;
    private ImageButton btnPerder;

    private boolean Espada = true;
    private boolean Pico = true;
    private boolean Pala = true;
    private boolean Crafteando = false;
    private boolean picar = false;
    private boolean moviendo = false;
    private boolean Derecha = true;

    private Texture Mano;
    private Texture ManoIzquierda;
    private float dy = 0;

    private Array<Texture> BloquesF = new Array<>(4);
    private Array<Integer> BloquesFx = new Array<>(4);
    private Array<Integer> BloquesFy = new Array<>(4);

    private Texture textura1;
    private Texture textura2;
    private Texture textura3;
    private Texture textura4;
    private Texture textura5;
    private Texture Enemigo1 = new Texture("Personajes/enemigo2.png");
    private Texture Enemigo2 = new Texture("Personajes/enemigo1.png");

    private int intText = 0;
    private int Tutorial = 0;
    private int Perder = 0;

    private float Perx = 0;
    private float Pery = 0;


    private static final float RADIO = 10f ;
    private World mundo;
    private Body body;
    private Box2DDebugRenderer debugRenderer;

    //Mapa
    private OrthogonalTiledMapRenderer mapaRenderer;
    private TiledMap mapa;

    private Personaje personaje;


    public PantallaMundoTutorial(Juego juego) {
        this.juego = juego;
        manager = juego.getManager();
    }

    @Override
        public void show() {

        fasesMenu = new Stage(vista);

        crearMundo();

        cargarMapa();
        CrearBotonesItems();
        CrearBotonesTutorial();
        crearObjetos();
        definirParedes();
        crearPersonaje();
        texturas();
        crearBloques();



        Mano = new Texture("Botones/btnMano.png");
        ManoIzquierda = new Texture("Botones/btnManoIzquierda.png");

        //Boton de Dios
        final TextureRegionDrawable trdDios = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnDiosP.png")));
        btnDios = new ImageButton(trdDios);
        btnDios.setPosition(10, Juego.ALTO - btnDios.getHeight() - 10);
        //Funcionamiento
        btnDios.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                intText += 1;
            }
        });
        //Boton de CuadroTXT
        TextureRegionDrawable trdCuadroTXT = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/CuadroTXT.png")));
        btnCuadroTXT = new ImageButton(trdCuadroTXT);
        btnCuadroTXT.setPosition(10 + btnDios.getWidth(), Juego.ALTO - btnCuadroTXT.getHeight() - 10);
        //Funcionamiento
        btnCuadroTXT.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                intText += 1;
            }
        });

        fasesMenu.addActor(btnCuadroTXT);
        fasesMenu.addActor(btnDios);

        //Boton de Perder
        final TextureRegionDrawable trdPerder = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnPerder.png")));
        btnPerder = new ImageButton(trdPerder);
        btnPerder.setPosition( 0, 0);
        //Funcionamiento
        btnPerder.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });


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
        mapa = manager.get("Mapas/mapaTutorial.tmx");
        mapaRenderer = new OrthogonalTiledMapRenderer(mapa);

        InputMultiplexer multi = new InputMultiplexer();
        multi.addProcessor(fasesMenu);
        Gdx.input.setInputProcessor(multi);
    }

    private void crearBloques() {
        BloquesF.add(new Texture("Texturas/texPasto.png"));
        BloquesF.add(new Texture("Texturas/texPasto.png"));
        BloquesF.add(new Texture("Texturas/texMadera.png"));
        BloquesF.add(new Texture("Texturas/texPiedra.png"));
        BloquesFx.add(960);
        BloquesFx.add(960-64);
        BloquesFx.add(576);
        BloquesFx.add(960);
        BloquesFy.add(256);
        BloquesFy.add(256);
        BloquesFy.add(320);
        BloquesFy.add(256 - 64);


    }

    private void texturas() {
        textura1 = new Texture("Botones/txt1.png");
        textura2 = new Texture("Botones/txt2.png");
        textura3 = new Texture("Botones/txt3.png");
        textura4 = new Texture("Botones/txt4.png");
        textura5 = new Texture("Botones/txt5.png");


    }

    private void crearPersonaje() {
        Texture texture = new Texture("Personajes/personaje.png");
        personaje = new Personaje(texture,0,0);
    }

    private void definirParedes() {
        ConvertidorMapa.crearCuerpos(mapa,mundo);
    }

    private void cargarMapa() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("Mapas/mapaTutorial.tmx", TiledMap.class);
        manager.finishLoading(); //Segundo Plano
        mapa = manager.get("Mapas/mapaTutorial.tmx");
        mapaRenderer = new OrthogonalTiledMapRenderer(mapa);
    }

    private void crearObjetos() {
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.position.set(832,400);
        body = mundo.createBody(bodydef);

        PolygonShape circulo = new PolygonShape();
        circulo.setAsBox(30, 30);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circulo;
        fixtureDef.density = 0.01f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.0f;

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
        Vector2 gravedad = new Vector2(0,-100);
        mundo = new World(gravedad, true);
        debugRenderer = new Box2DDebugRenderer();
    }

    private void CrearBotonesTutorial() {
        //Boton Primera Tierra
        Texture texturabtnTierra = new Texture("Texturas/texPasto.png");
        TextureRegionDrawable trdTierra = new TextureRegionDrawable(new TextureRegion(texturabtnTierra));
        btnTierra = new ImageButton(trdTierra);
        btnTierra.setPosition(960, 256);
        //Funcionamiento
        btnTierra.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Tutorial += 1;
                fasesMenu.clear();
                moverPersonaje(x, y);
                if (Tutorial == 2) {

                    BloquesFx.set(1,10000);
                    fasesMenu.addActor(btnMadera);
                } else {
                    BloquesFx.set(0,10000);
                    btnTierra.setPosition(btnTierra.getX() - 64, btnTierra.getY());
                    fasesMenu.addActor(btnTierra);
                }
                fasesMenu.addActor(btnPausa);
            }
        });
        //Boton Primera Madera
        Texture texturabtnMadera = new Texture("Texturas/texMadera.png");
        TextureRegionDrawable trdMadera = new TextureRegionDrawable(new TextureRegion(texturabtnMadera));
        btnMadera = new ImageButton(trdMadera);
        btnMadera.setPosition(576, 320);
        //Funcionamiento
        btnMadera.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                moverPersonaje(x, y);
                Tutorial += 1;
                //personaje.moverPersonaje(x,y, "Cavar");
                //Quitar Madera del tmx
                fasesMenu.clear();
                BloquesFx.set(2,10000);
                fasesMenu.addActor(btnPausa);
                fasesMenu.addActor(btnCrafteo);
                Crafteando = true;
            }
        });
        //Boton Primera Piedra
        Texture texturabtnPiedra = new Texture("Texturas/texPiedra.png");
        TextureRegionDrawable trdPiedra = new TextureRegionDrawable(new TextureRegion(texturabtnPiedra));
        btnPiedra = new ImageButton(trdPiedra);
        btnPiedra.setPosition(960, 256 - 64);
        btnPiedra.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                moverPersonaje(x, y);
                Tutorial += 1;
                BloquesFx.set(3,10000);
                //personaje.moverPersonaje(x,y, "Picar");
                //Quitar piedra del tmx
                fasesMenu.clear();
                fasesMenu.addActor(btnPausa);
                fasesMenu.addActor(btnCrafteo);
                picar = false;
            }
        });
    }


    private void CrearBotonesItems() {
        //Boton de Espada
        Texture texturabtnEspada = new Texture("Items/espada.png");
        TextureRegionDrawable trdEspada = new TextureRegionDrawable(new TextureRegion(texturabtnEspada));
        btnEspada = new ImageButton(trdEspada);
        btnEspada.setPosition(128, 128);
        btnEspada.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                fasesMenu.clear();
                estadoJuego = EstadoJuego.TUTORIAL;
                Espada = false;
                fasesMenu.addActor(btnCrafteo);
                fasesMenu.addActor(btnPausa);
                if (picar) {
                    fasesMenu.addActor(btnPiedra);
                }
            }
        });
        //Boton de Pico
        Texture texturabtnPico = new Texture("Items/pico.png");
        TextureRegionDrawable trdPico = new TextureRegionDrawable(new TextureRegion(texturabtnPico));
        btnPico = new ImageButton(trdPico);
        btnPico.setPosition(256 + 64, 128);
        btnPico.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                fasesMenu.clear();
                estadoJuego = EstadoJuego.TUTORIAL;
                Pico = false;
                fasesMenu.addActor(btnPiedra);
                fasesMenu.addActor(btnCrafteo);
                fasesMenu.addActor(btnPausa);
                picar = true;
                Tutorial += 1;
            }
        });
        //Boton de Pala
        Texture texturabtnPala = new Texture("Items/pala.png");
        TextureRegionDrawable trdPala = new TextureRegionDrawable(new TextureRegion(texturabtnPala));
        btnPala = new ImageButton(trdPala);
        btnPala.setPosition(384 + 128, 128);
        btnPala.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                fasesMenu.clear();
                estadoJuego = EstadoJuego.TUTORIAL;
                Pala = false;
                fasesMenu.addActor(btnCrafteo);
                fasesMenu.addActor(btnPausa);
                if (picar) {
                    fasesMenu.addActor(btnPiedra);
                }
            }
        });
    }

    @Override
    public void render(float delta) {

        borrarPantalla(1, 0, 0);

        float x = body.getPosition().x;
        float y = body.getPosition().y;
        personaje.getSprite().setPosition( x - 32 ,y -32);

        batch.setProjectionMatrix(camara.combined);

        mapaRenderer.setView(camara);
        mapaRenderer.render();

        debugRenderer.render(mundo,camara.combined);
        fasesMenu.draw();
        batch.begin();
        if(Tutorial == 5 && Perder >= 200) {

        }else{
            personaje.render(batch);
        }

        if(moviendo) {
            if (Derecha) {
                body.applyLinearImpulse(-10, 0, x, y, true);
                if(personaje.getSprite().getX() >= Perx){
                    moviendo = false;
                }
            } else {
                body.applyLinearImpulse(10, 0, x, y, true);
                if(personaje.getSprite().getX() <= Perx){
                    moviendo = false;
                }
            }

        }

        for( int i = 0; i < BloquesF.size; i++){
            batch.draw(BloquesF.get(i), BloquesFx.get(i), BloquesFy.get(i));
        }

        if (estadoJuego == EstadoJuego.ANIMACION) {
            if(intText == 0){
                fasesMenu.addActor(btnCuadroTXT);
                batch.draw(textura1, 10 + btnDios.getWidth(), Juego.ALTO - btnCuadroTXT.getHeight() - 10);
            }if(intText == 1){
                fasesMenu.addActor(btnCuadroTXT);
                batch.draw(textura2, 10 + btnDios.getWidth(), Juego.ALTO - btnCuadroTXT.getHeight() - 10);
            }if(intText == 2){
                fasesMenu.addActor(btnCuadroTXT);
                batch.draw(textura3, 10 + btnDios.getWidth(), Juego.ALTO - btnCuadroTXT.getHeight() - 10);
            }if(intText == 3){
                fasesMenu.addActor(btnCuadroTXT);
                batch.draw(textura4, 10 + btnDios.getWidth(), Juego.ALTO - btnCuadroTXT.getHeight() - 10);
            }if(intText == 4){
                fasesMenu.addActor(btnCuadroTXT);
                batch.draw(textura5, 10 + btnDios.getWidth(), Juego.ALTO - btnCuadroTXT.getHeight() - 10);
            }
            if (intText == 5) {
                fasesMenu.clear();
                fasesMenu.addActor(btnPausa);
                estadoJuego = EstadoJuego.TUTORIAL;
                fasesMenu.addActor(btnTierra);
            }
        }
        if (estadoJuego == EstadoJuego.TUTORIAL) {
            if (Tutorial == 0) {
                dy += 0.2f;
                batch.draw(Mano, 940, 320 + dy);
                if (dy >= 3) {
                    dy = 0;
                }
            }
            if (Tutorial == 1) {
                dy += 0.2f;
                batch.draw(Mano, 940 - 64, 320 + dy);
                if (dy >= 3) {
                    dy = 0;
                }
            }
            if (Tutorial == 2) {
                dy += 0.2f;
                batch.draw(ManoIzquierda, 576 + 64 + dy, 320);
                if (dy >= 3) {
                    dy = 0;
                }
            }
            if (Tutorial == 3) {
                dy += 0.2f;
                batch.draw(Mano, Juego.ANCHO - Mano.getWidth(), btnCrafteo.getHeight() + 10 + dy);
                if (dy >= 3) {
                    dy = 0;
                }
            }
            if (Tutorial == 4) {
                dy += 0.2f;
                batch.draw(Mano, 940, 320 - 64 + dy);
                if (dy >= 3) {
                    dy = 0;
                }
            }
            if (Tutorial == 5) {
                //Spawnear enemigos y matar al personaje
                Perder += 1;
                if(Perder >= 200){
                    personaje.getSprite().setPosition(20000,-200);
                    fasesMenu.addActor(btnPerder);
                }
                else{
                    batch.draw(Enemigo2, 0 + Perder,320 );
                    batch.draw(Enemigo1, Juego.ANCHO - Perder,320 );
                }
            }


        }
        if (estadoJuego == EstadoJuego.CRAFTEO) {
            if (Tutorial == 3) {
                dy += 0.2f;
                batch.draw(Mano, 256 + 64, 128 + 128 + dy);
                if (dy >= 3) {
                    dy = 0;
                }
            }

        }


        if (estadoJuego == EstadoJuego.PAUSA) {
            fasesMenu.draw();
            escenaPausa.draw();
            if (escenaPausa.getPausa()) {
                if (Tutorial == 0) {
                    fasesMenu.addActor(btnTierra);
                }
                if (Tutorial == 1) {
                    fasesMenu.addActor(btnTierra);
                }
                if (Tutorial == 2) {
                    fasesMenu.addActor(btnMadera);
                }
                if (Tutorial == 4) {
                    fasesMenu.addActor(btnPiedra);
                }
                fasesMenu.addActor(btnPausa);
                if (Crafteando) {
                    fasesMenu.addActor(btnCrafteo);
                }
                estadoJuego = EstadoJuego.TUTORIAL;
                escenaPausa.setPausa();
            }
        }
        batch.end();
        mundo.step(1/60f, 6,6);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        manager.unload("Botones/btnExit.png");
        manager.unload("Mapas/mapaTutorial.tmx");
        manager.unload("Texturas/Tileset.png");
        manager.unload("Botones/Pausa.png");


    }
    public void moverPersonaje(float dx, float dy) {
        Perx = dx;
        Pery = dy;
        moviendo = true;
        if (dx > Perx) {
            Derecha = true;
        }
        else{
            Derecha = false;
        }

    }
}
