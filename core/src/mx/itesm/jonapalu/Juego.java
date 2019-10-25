package mx.itesm.jonapalu;


import com.badlogic.gdx.Game;

import java.io.File;

public class Juego extends Game {

	public static final float ALTO = 720;
	public static final float ANCHO = 1280;
	public static float tiempo = 0;


	@Override
	public void create () {
        setScreen(new PantallaMenu(this));



    }
    public void sumar(float delta) {
        tiempo += delta;
        System.out.println(tiempo);


    }
}
