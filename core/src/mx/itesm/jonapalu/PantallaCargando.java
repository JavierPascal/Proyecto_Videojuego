package mx.itesm.jonapalu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class PantallaCargando extends Pantalla{

    private final float TIEMPO_ENTRE_FRAME = 0.05f;
    private Sprite spriteCargando;
    private float timerAnimacion = TIEMPO_ENTRE_FRAME;

    private AssetManager manager; //unico en el juego

    private Juego juego;
    private TipoPantalla siguientePantalla;
    private int avance;

    private Texture texturaCargando;

    public PantallaCargando(Juego juego, TipoPantalla siguiente) {
        this.juego = juego;
        this.siguientePantalla = siguiente;
    }

    @Override
    public void show() {
        texturaCargando = new Texture("Texturas/loading.png");
        spriteCargando = new Sprite(texturaCargando);
        spriteCargando.setPosition
                (ANCHO/2-spriteCargando.getWidth()/2,ALTO/2-spriteCargando.getHeight()/2);
     manager = juego.getManager();
     cargarRecursosPantalla(); //Cargar elementos
    }

    private void cargarRecursosPantalla() {
        switch (siguientePantalla){
            case MENU:
                cargarRecursosMenu();
                break;
            case JUEGO:
                cargarRecursosJuego();
                break;
            case ACERCA:
                cargarRecursosAcerca();
                break;
            case CONFIGURACION:
                cargarRecursosConfig();
                break;
            case INSTRUCCIONES:
                cargarRecursosInstrucciones();
                break;
            case MUNDOS:
                cargarRecursosMundos();
                break;
            case TUTORIAL:
                cargarRecursosTutorial();
                break;

        }

    }

    private void cargarRecursosTutorial() {
        //manager.load("Audios/marioBros.mp3", Music.class);
        //manager.load("Audios/moneda.mp3", Sound.class);
        manager.load("Mapas/mapaTutorial.tmx", TiledMap.class);
        manager.load("Texturas/Tileset.png", Texture.class);




    }

    private void cargarRecursosMundos() {
        manager.load("HUD/fondoGris.png",Texture.class);
        manager.load("Botones/btnRegresar.png",Texture.class);
        manager.load("Botones/btnAgregarMundo.png",Texture.class);
        manager.load("Botones/btnAgregarMundoPressed.png",Texture.class);
        manager.load("HUD/btnMundos.png",Texture.class);
        manager.load("HUD/btnMundosPress.png",Texture.class);
        manager.load("Botones/btnTutorial.png",Texture.class);
        manager.load("Botones/btnTutorialPressed.png",Texture.class);



    }

    private void cargarRecursosInstrucciones() {
    }

    private void cargarRecursosConfig() {
        manager.load("Botones/btnRegresar.png",Texture.class);

    }

    private void cargarRecursosAcerca() {
    }

    private void cargarRecursosJuego() {
    }

    private void cargarRecursosMenu() {
        manager.load("Texturas/fondoMenu.png", Texture.class);

        manager.load("Botones/logo.png",Texture.class);

        manager.load("Botones/btnJugar.png",Texture.class);
        manager.load("Botones/btnJugarPressed.png",Texture.class);

        manager.load("Botones/btnAcercaDe.png",Texture.class);
        manager.load("Botones/btnAcercaDePressed.png",Texture.class);

        manager.load("Botones/btnJugar.png",Texture.class);
        manager.load("Botones/btnJugar.png",Texture.class);

    }

    @Override
    public void render(float delta) {
        borrarPantalla(.218f,.165f,.32f);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteCargando.draw(batch);
        batch.end();
        timerAnimacion -= delta;
        if (timerAnimacion <= 0){
            spriteCargando.rotate(-20);
            timerAnimacion = TIEMPO_ENTRE_FRAME;
        }
        actualizarCargaRecursos();

    }

    private void actualizarCargaRecursos() {
        if (manager.update()){
            switch (siguientePantalla){
                case MENU:
                    juego.setScreen(new PantallaMenu(juego));
                    break;
                case MUNDOS:
                    juego.setScreen(new Mundos(juego));
                    break;
                case CONFIGURACION:
                    juego.setScreen(new Configuracion(juego));
                    break;
                case ACERCA:
                    juego.setScreen(new Informacion(juego));
                    break;
                case INSTRUCCIONES:
                    juego.setScreen(new Instrucciones(juego));
                    break;
                case TUTORIAL:
                    juego.setScreen(new PantallaMundoTutorial(juego));
                    break;

            }
        }
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
