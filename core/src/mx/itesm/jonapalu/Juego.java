package mx.itesm.jonapalu;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.io.File;

public class Juego extends Game {

	public static final float ALTO = 720;
	public static final float ANCHO = 1280;
	public static float tiempo = 0;
    private final AssetManager manager = new AssetManager();


    @Override
	public void create () {
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        setScreen(new PantallaCargando(this, TipoPantalla.MENU));


    }
    public void sumar(float delta) {
        tiempo += delta;
        System.out.println(tiempo);
    }

    public AssetManager getManager() {
	    return manager;
    }
}
