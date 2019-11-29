package mx.itesm.jonapalu;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.io.File;

public class Juego extends Game {

    public static Music Musica;   // Fondo, largo
    private Sound efecto1;       // Corto
	public static final float ALTO = 720;
	public static final float ANCHO = 1280;
	public static float tiempo = 0;
    private final AssetManager manager = new AssetManager();


    @Override
	public void create () {
        //cargar  audios
        manager.load("Audios/Musica.mp3", Music.class);
        manager.load("Audios/Efecto1.mp3", Sound.class);
        //manager.load("Audios/Efecto2.mp3", Sound.class);
        manager.load("Audios/Efecto3.mp3", Sound.class);
        manager.finishLoading();    // Segundo plano */
        // Leer audios
        Musica = manager.get("Audios/Musica.mp3");
        efecto1 = manager.get("Audios/Efecto1.mp3");
        Musica.setLooping(true);
        Musica.play();
        Musica.setVolume(0.2f);
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        setScreen(new PantallaCargando(this, TipoPantalla.MENU));


    }
    public void sumar(float delta) {
        tiempo += delta;
    }

    public AssetManager getManager() {
	    return manager;
    }
}

/*REvisar Franja Blanca en menù principal DONE
* Pantalla perder
* Enemigos*/