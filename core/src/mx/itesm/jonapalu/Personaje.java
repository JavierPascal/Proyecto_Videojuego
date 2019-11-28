package mx.itesm.jonapalu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Personaje {
    private static final int BOX_SIZE = 32;
    private static final float PLAYER_DENSITY = 1.0f;
    public static final float JUMP_FORCE = 250f;
    public static final float RUN_FORCE = 5f;
    public static final String PLAYER_IMG_PATH = "Personajes/personaje.png";
    private static final float PLAYER_START_X = 8f;
    private static final float PLAYER_START_Y = 18f;
    private com.badlogic.gdx.physics.box2d.Body body;
    private int vida = 100;
    //private Texture texture = new Texture(Gdx.files.internal("Personajes/BarraSalud.jpg"));

    public Personaje(World world) {
        createBoxBody(world, PLAYER_START_X, PLAYER_START_Y);
    }

    private void createBoxBody(World world, float x, float y) {
        BodyDef bdef = new BodyDef();
        bdef.fixedRotation = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(x, y);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(BOX_SIZE / Juego.ANCHO / 2, BOX_SIZE / Juego.ALTO/ 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = PLAYER_DENSITY;
        body = world.createBody(bdef);
        body.createFixture(fixtureDef).setUserData(this);



    }
    public Body getBody() {
        return body;
    }
}
