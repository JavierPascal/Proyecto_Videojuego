package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Mundo extends Pantalla {

    private Juego juego;
    private Stage fasesMenu;

    //Map
    private OrthogonalTiledMapRenderer mapaRenderer;
    private TiledMap mapa;
    private String nombre;

    //States
    private EstadoJuego estadoJuego;
    private EscenaPausa escenaPausa;
    private ParticleEffect pe;

    public Mundo(Juego juego, String nombre) {
        this.juego = juego;
        this.nombre = nombre;
    }

    @Override
    public void show() {
        AssetManager manager = juego.getManager();
        mapa = manager.get("Mapas/" + nombre + ".tmx");
        mapaRenderer = new OrthogonalTiledMapRenderer(mapa);
        Gdx.input.setInputProcessor( new Mundo.EntryProcessor());

    }

    @Override
    public void render(float delta) {

        borrarPantalla(1,0,0);

        batch.setProjectionMatrix(camara.combined);
        mapaRenderer.setView(camara);
        mapaRenderer.render();
        batch.begin();
        batch.end();


        if (estadoJuego == EstadoJuego.PAUSA) {
            escenaPausa.draw();
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

    private class EntryProcessor implements InputProcessor {
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
            //fx.play();
            //Pause
            if (estadoJuego ==EstadoJuego.JUGANDO){
                estadoJuego = EstadoJuego.PAUSA;
                if (escenaPausa == null) {
                    escenaPausa = new EscenaPausa(juego,vista,batch, fasesMenu);
                }
            } else{
                estadoJuego = EstadoJuego.JUGANDO;
            }

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