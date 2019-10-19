package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Mundo implements Screen {

    private final Juego juego;
    private SpriteBatch batch;
    private OrthographicCamera camara;
    private Viewport vista;

    //Fondo Sprite
    private Array<Nuve> arrNuves;
    private Array<Item_Falso> arrItem;

    //Fondo Textura
    private Texture texturaFondo;
    private Texture persona;
    private Array<Texture> arrSpriteNuves;

    private int dx1 = 0;
    private int dx2 = 0;
    private int per1 = (int) numeroRandom(0, Juego.ANCHO);
    private int per2 = (int) numeroRandom(0, Juego.ANCHO);

    private int numeroMundo;

    public Mundo(Juego juego, int numeroMundo){
        this.juego = juego;
        this.numeroMundo = numeroMundo;
    }

    @Override
    public void show() {
        configuracionVista();
        crearFondo();
        cargarTexturas();
    }

    private void configuracionVista() {
        camara = new OrthographicCamera();
        camara.position.set( Juego.ANCHO / 2, Juego.ALTO / 2, 0);
        camara.update();
        vista = new FitViewport(Juego.ANCHO, Juego.ALTO, camara);
        batch = new SpriteBatch();
        //Codigo provisional para crear el Mundo


    }

    private void crearFondo() {
        //Texturas
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

    private void cargarTexturas() {
        texturaFondo = new Texture( "PantallaMenu/fondo/fondoCielo.png");
        persona = new Texture("items/persona.png");
    }

    @Override
    public void render(float delta) {
        moverNuves();

        clearScreen();

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0 , 0);

        personaje(per1, 1);
        personaje(per2, 2);

        //Render Nuves
        for (Nuve nuve: arrNuves) {
            nuve.render(batch);
        }

        //Render Suelo
        for (Item_Falso item:arrItem) {
            item.render(batch);
        }

        batch.end();
    }

    private void personaje(int x, int numPer){
        if (numPer == 1){
            dx1 += 2;
            batch.draw(persona, (float) (x + dx1), 320);
            if(dx1 + x >= Juego.ANCHO) {
                dx1 = (int) (-x) - persona.getWidth();
            }
        }else if (numPer == 2){
            dx2 += 2;
            batch.draw(persona, (float) (x + dx2), 320);
            if(dx2 + x >= Juego.ANCHO){
                dx2 = (int) (- x) - persona.getWidth();
            }
        }
    }

    private void moverNuves() {
        for (Nuve nuve : arrNuves) {
            nuve.mover((float) numeroRandom(1, 4), 0);
            if (nuve.getX() < 0 - nuve.getTexture().getWidth()) {
                arrNuves.removeIndex(arrNuves.indexOf(nuve, true));
                arrNuves.add(new Nuve(arrSpriteNuves.get((int) numeroRandom(0, arrSpriteNuves.size - 1))));
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

    }
    public static double numeroRandom(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }
}
