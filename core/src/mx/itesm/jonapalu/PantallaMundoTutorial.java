package mx.itesm.jonapalu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

class PantallaMundoTutorial extends Pantalla {

    private Juego juego;
    //Map
    private OrthogonalTiledMapRenderer mapaRenderer;
    private TiledMap mapa;
    //Audio
    private Music audioFondo;
    private Sound fx;
    //States
    private EstadoJuego estadoJuego;
    private EscenaPausa escenaPausa;
    private ParticleEffect pe;

    public PantallaMundoTutorial(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        AssetManager manager = juego.getManager();
        mapa = manager.get("Mapas/mapaTutorial.tmx");
        mapaRenderer = new OrthogonalTiledMapRenderer(mapa);
        //Read Audios
        /*audioFondo = manager.get("Audios/marioBros.mp3");
        fx = manager.get("Audios/moneda.mp3");
        audioFondo.setLooping(true);
        audioFondo.play();
        audioFondo.setVolume(.2f);*/

        Gdx.input.setInputProcessor( new EntryProcessor());

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
                //audioFondo.pause();
                if (escenaPausa == null) {
                    escenaPausa = new EscenaPausa(juego, vista, batch);
                }
            } else{
                estadoJuego = EstadoJuego.JUGANDO;
               //audioFondo.play();
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
