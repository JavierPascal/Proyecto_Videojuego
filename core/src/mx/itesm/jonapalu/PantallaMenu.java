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
import com.badlogic.gdx.utils.Array;

import java.util.Hashtable;

import mx.itesm.jonapalu.Items.Item;

class PantallaMenu implements Screen {

    private final Juego juego;
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    //Fondo Textura
    private Texture texturaFondo;
    private Array<Texture> arrSpriteNuves;

    //Fondo Sprite
    private Array<Nuve> arrNuves;
    private Array<Item_Falso> arrItem;

    //Fases
    private Stage fasesMenu;

    //Items
    private Hashtable<Integer, Item> Items;

    //Timer
    float tiempo = 0;

    public PantallaMenu(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        configuracionVista();
        cargarTexturas();
        crearMenu();
        crearFondo();
        cargarItems();
    }

    private void configuracionVista() {
        camara = new OrthographicCamera();
        camara.position.set( Juego.ANCHO / 2, Juego.ALTO / 2, 0);
        camara.update();
        vista = new FitViewport(Juego.ANCHO, Juego.ALTO, camara);
        batch = new SpriteBatch();
    }

    private void cargarTexturas() {

    }

    private void crearMenu() {
        fasesMenu = new Stage(vista);
        //Boton de Jugar
        TextureRegionDrawable trdJugar = new TextureRegionDrawable(new TextureRegion(new Texture("PantallaMenu/boton/btnJugar.png")));
        TextureRegionDrawable trdJugarPress = new TextureRegionDrawable(new TextureRegion(new Texture("PantallaMenu/boton/btnJugarPress.png")));
        ImageButton btnJugar = new ImageButton(trdJugar, trdJugarPress);
        btnJugar.setPosition(Juego.ANCHO / 2 - btnJugar.getWidth() / 2, Juego.ALTO / 2 - btnJugar.getHeight() / 2);
        //Funcionamiento
        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new Mundos(juego));
            }
        });

        //Boton Configuracion
        TextureRegionDrawable trdConf = new TextureRegionDrawable(new TextureRegion(new Texture("PantallaMenu/boton/btnConf.png")));
        TextureRegionDrawable trdConfPress = new TextureRegionDrawable(new TextureRegion(new Texture("PantallaMenu/boton/btnConfPress.png")));
        ImageButton btnConf = new ImageButton(trdConf, trdConfPress);
        btnConf.setPosition(10, 10);
        //Funcionamiento
        btnConf.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new Configuracion(juego));
            }
        });

        //Boton Informacion
        TextureRegionDrawable trdInf = new TextureRegionDrawable(new TextureRegion(new Texture("PantallaMenu/boton/btnInf.png")));
        TextureRegionDrawable trdInfPress = new TextureRegionDrawable(new TextureRegion(new Texture("PantallaMenu/boton/btnInfPress.png")));
        ImageButton btnInf = new ImageButton(trdInf, trdInfPress);
        btnInf.setPosition(juego.ANCHO - 10 - btnInf.getWidth(), 10);
        //Funcionamiento
        btnInf.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new Informacion(juego));
            }
        });

        //Anadir botones
        fasesMenu.addActor(btnJugar);
        fasesMenu.addActor(btnConf);
        fasesMenu.addActor(btnInf);


        //Cargar las entradas
        Gdx.input.setInputProcessor(fasesMenu);
    }

    private void crearFondo() {
        //Texturas
        //Fondo
        texturaFondo = new Texture( "PantallaMenu/fondo/fondoCielo.png");
        //Nuve
        arrSpriteNuves = new Array<>( 6);
        for (int cantidadNuves = 0; cantidadNuves < 6; cantidadNuves++) {
            String nombre = "PantallaMenu/fondo/nuve" + Integer.toString(cantidadNuves) + ".png";
            Texture texturaNuve = new Texture(nombre);
            arrSpriteNuves.add(texturaNuve);
        }
        //Nuves en pantalla
        arrNuves = new Array<>( 6);
        for (int i = 0; i < 6; i++){
            Nuve nuve = new Nuve(arrSpriteNuves.get((int) numeroRandom(0,arrSpriteNuves.size - 1)), (int) numeroRandom(0,Juego.ANCHO));
            arrNuves.add(nuve);
        }
        //Items en pantalla
        arrItem = new Array<> (4 * 22);
        for(int columna = 0; columna < 23; columna++) {
            for (int fila = 0; fila < 5; fila++) {
                int ID;
                if(fila == 4){
                    ID = 01;
                }
                else if(fila <4){
                    ID = 02;
                }
                else{
                    ID = 0;
                }
                Item_Falso item = new Item_Falso(ID, columna * 64, fila * 64);
                arrItem.add(item);
            }
        }
    }

    private void cargarItems() {

    }

    @Override
    public void render(float delta) {

        movimientoFondo(delta);

        clearScreen();

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0 , 0);
        
        //Render Nuves
        for (Nuve nuve: arrNuves) {
            nuve.render(batch);
        }

        //Render Suelo
        for (Item_Falso item:arrItem) {
            item.render(batch);
        }

        batch.end();
        fasesMenu.draw();

    }

    private void movimientoFondo(float delta) {
        for (Nuve nuve : arrNuves) {
            nuve.mover((float) numeroRandom(1, 4), 0);
            if (nuve.getX() < 0 - nuve.getTexture().getWidth()) {
                arrNuves.removeIndex(arrNuves.indexOf(nuve, true));
                arrNuves.add(new Nuve(arrSpriteNuves.get((int) numeroRandom(0, arrSpriteNuves.size - 1))));
            }
        }
        for (Item_Falso item : arrItem) {
            item.mover(2);
            if (item.getX() < 0 - item.getTexture().getWidth()) {
                int ID = item.getID();
                int index = arrItem.indexOf(item, true);
                float y = item.getY();
                arrItem.removeIndex(index);
                arrItem.add(new Item_Falso(ID, Juego.ANCHO - 1 - (1 / delta / 60), y));
            }
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
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
        texturaFondo.dispose();
    }


    public static double numeroRandom(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }
}
