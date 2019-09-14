package mx.itesm.jonapalu;


import com.badlogic.gdx.Game;

public class Juego extends Game {

	public static final float ALTO = 720;
	public static final float ANCHO = 1280;
	
	@Override
	public void create () {
		setScreen(new pantallaMenu( this));
	}
}
