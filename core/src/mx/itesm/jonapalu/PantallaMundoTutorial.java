package mx.itesm.jonapalu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class PantallaMundoTutorial extends Pantalla {

    private Juego juego;
    private Stage fasesMenu;

    //Map
    private OrthogonalTiledMapRenderer mapaRenderer;
    private TiledMap mapa;

    //States
    private EstadoJuego estadoJuego = EstadoJuego.ANIMACION;
    private EscenaPausa escenaPausa;
    private ParticleEffect pe;

    private ImageButton btnPausa;
    private ImageButton btnCrafteo;
    private ImageButton btnEspada;
    private ImageButton btnPico;
    private ImageButton btnPala;
    private ImageButton btnRegresarC;
    private ImageButton btnTierra;
    private ImageButton btnMadera;
    private ImageButton btnPiedra;

    private boolean Espada = true;
    private boolean Pico = true;
    private boolean Pala = true;
    private boolean Crafteando = false;
    private boolean picar = false;

    private Texture Mano;
    private Texture ManoIzquierda;
    private float dy = 0;


    private int intText = 0;
    private int Tutorial = 0;

    private Personaje personaje;

    public PantallaMundoTutorial(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        Mano = new Texture("Botones/btnMano.png");
        ManoIzquierda = new Texture("Botones/btnManoIzquierda.png");
        fasesMenu = new Stage(vista);

        //Boton de Dios
        final TextureRegionDrawable trdDios = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnDiosP.png")));
        final ImageButton btnDios = new ImageButton(trdDios);
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
        ImageButton btnCuadroTXT = new ImageButton(trdCuadroTXT);
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

        CrearBotonesItems();
        CrearBotonesTutorial();
        //Boton de Pausa
        Texture texturabtnPausa = new Texture("Botones/btnPausa.png");
        TextureRegionDrawable trdPausa = new TextureRegionDrawable
                (new TextureRegion(texturabtnPausa));

        btnPausa = new ImageButton(trdPausa);
        btnPausa.setPosition(10, Juego.ALTO - btnPausa.getHeight() - 10);
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
                if(Crafteando) {
                    fasesMenu.addActor(btnCrafteo);
                }
                fasesMenu.addActor(btnPausa);
                if(picar){
                    fasesMenu.addActor(btnPiedra);
                }
            }
        });

        //Boton de Crafteo
        Texture texturabtnCrafteo = new Texture("Botones/btnCrafteo.png");
        TextureRegionDrawable trdCrafteo = new TextureRegionDrawable
                (new TextureRegion(texturabtnCrafteo));

        btnCrafteo = new ImageButton(trdCrafteo);
        btnCrafteo.setPosition(Juego.ANCHO - 10 - btnCrafteo.getWidth() , 10);
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
                if(Pico){
                    fasesMenu.addActor(btnPico);
                }
                if(Pala){
                    fasesMenu.addActor(btnPala);
                }
                fasesMenu.addActor(btnRegresarC);
            }

        });
        escenaPausa = new EscenaPausa(juego, vista, batch, fasesMenu);
        AssetManager manager = juego.getManager();
        mapa = manager.get("Mapas/mapaTutorial.tmx");
        mapaRenderer = new OrthogonalTiledMapRenderer(mapa);
        Gdx.input.setInputProcessor(fasesMenu);
    }

    private void CrearBotonesTutorial() {
        //Boton Primera Tierra
        Texture texturabtnTierra = new Texture("Texturas/texPasto.png");
        TextureRegionDrawable trdTierra = new TextureRegionDrawable(new TextureRegion(texturabtnTierra));
        btnTierra = new ImageButton(trdTierra);
        btnTierra.setPosition(960,256);
        //Funcionamiento
        btnTierra.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Tutorial += 1;
                //personaje.moverPersonaje(x,y, "Cavar");
                //Quitar tierra del tmx (x,y)
                fasesMenu.clear();
                if(Tutorial == 2){
                    fasesMenu.addActor(btnMadera);
                }
                else{
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
        btnMadera.setPosition(576 , 320);
        //Funcionamiento
        btnMadera.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Tutorial += 1;
                //personaje.moverPersonaje(x,y, "Cavar");
                //Quitar Madera del tmx
                fasesMenu.clear();
                fasesMenu.addActor(btnPausa);
                fasesMenu.addActor(btnCrafteo);
                Crafteando = true;
            }
        });
        //Boton Primera Piedra
        Texture texturabtnPiedra = new Texture("Texturas/texPiedra.png");
        TextureRegionDrawable trdPiedra = new TextureRegionDrawable(new TextureRegion(texturabtnPiedra));
        btnPiedra = new ImageButton(trdPiedra);
        btnPiedra.setPosition(960,256 - 64);
        btnPiedra.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Tutorial += 1;
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
        TextureRegionDrawable trdEspada = new TextureRegionDrawable (new TextureRegion(texturabtnEspada));
        btnEspada = new ImageButton(trdEspada);
        btnEspada.setPosition(128,128);
        btnEspada.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                fasesMenu.clear();
                estadoJuego = EstadoJuego.TUTORIAL;
                Espada = false;
                fasesMenu.addActor(btnCrafteo);
                fasesMenu.addActor(btnPausa);
                if(picar){
                    fasesMenu.addActor(btnPiedra);
                }
            }});
        //Boton de Pico
        Texture texturabtnPico = new Texture("Items/pico.png");
        TextureRegionDrawable trdPico = new TextureRegionDrawable(new TextureRegion(texturabtnPico));
        btnPico = new ImageButton(trdPico);
        btnPico.setPosition(256 + 64,128);
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
            }});
        //Boton de Pala
        Texture texturabtnPala = new Texture("Items/pala.png");
        TextureRegionDrawable trdPala = new TextureRegionDrawable(new TextureRegion(texturabtnPala));
        btnPala = new ImageButton(trdPala);
        btnPala.setPosition(384 + 128,128);
        btnPala.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                fasesMenu.clear();
                estadoJuego = EstadoJuego.TUTORIAL;
                Pala = false;
                fasesMenu.addActor(btnCrafteo);
                fasesMenu.addActor(btnPausa);
                if(picar){
                    fasesMenu.addActor(btnPiedra);
                }
            }});
    }

    @Override
    public void render(float delta) {

        borrarPantalla(1,0,0);

        batch.setProjectionMatrix(camara.combined);
        mapaRenderer.setView(camara);
        mapaRenderer.render();
        batch.begin();
        if(estadoJuego == EstadoJuego.ANIMACION){
            if(intText == 5){
                fasesMenu.clear();
                fasesMenu.addActor(btnPausa);
                estadoJuego = EstadoJuego.TUTORIAL;
                fasesMenu.addActor(btnTierra);
                //personaje = new Personaje(World);
            }
        }
        if(estadoJuego == EstadoJuego.TUTORIAL){
            if(Tutorial == 0){
                dy += 0.2f;
                batch.draw(Mano,940,320 + dy);
                if(dy >= 3){
                    dy = 0;
                }
            }
            if(Tutorial == 1){
                dy += 0.2f;
                batch.draw(Mano,940 - 64,320 + dy);
                if(dy >= 3){
                    dy = 0;
                }
            }
            if(Tutorial == 2){
                dy += 0.2f;
                batch.draw(ManoIzquierda,576 + 64 + dy, 320);
                if(dy >= 3){
                    dy = 0;
                }
            }
            if(Tutorial == 3){
                dy += 0.2f;
                batch.draw(Mano,Juego.ANCHO - Mano.getWidth(),btnCrafteo.getHeight() + 10 + dy);
                if(dy >= 3){
                    dy = 0;
                }
            }
            if(Tutorial == 4){
                dy += 0.2f;
                batch.draw(Mano,940,320 - 64 + dy);
                if(dy >= 3){
                    dy = 0;
                }
            }
            if(Tutorial == 5){
                //Spawnear enemigos y matar al personaje
            }
        }
        if(estadoJuego == EstadoJuego.CRAFTEO){
            if(Tutorial == 3){
                dy += 0.2f;
                batch.draw(Mano,256 + 64 ,128 + 128 + dy);
                if(dy >= 3){
                    dy = 0;
                }
            }
        }
        if (estadoJuego == EstadoJuego.PAUSA) {
            escenaPausa.draw();
            if (escenaPausa.getPausa()){
                fasesMenu.addActor(btnPausa);
                if(Crafteando) {
                    fasesMenu.addActor(btnCrafteo);
                }
                estadoJuego = EstadoJuego.JUGANDO;
                escenaPausa.setPausa();
            }
        }

        batch.end();
        fasesMenu.draw();
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
