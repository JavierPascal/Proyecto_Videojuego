package mx.itesm.jonapalu;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Personaje extends Objeto{
    private static final int BOX_SIZE = 32;
    private static final float PLAYER_DENSITY = 1.0f;
    public static final float JUMP_FORCE = 250f;
    public static final float RUN_FORCE = 5f;
    public static final String PLAYER_IMG_PATH1 = "Personajes/personaje.png";
    private static final float PLAYER_START_X = 8f;
    private static final float PLAYER_START_Y = 18f;
    private Body body;
    private int vida = 100;
    private Animation<TextureRegion> spriteAnimado;
    private float timerAnimacion;
    protected EstadoMovimiento estadoMovimiento = EstadoMovimiento.QUIETO;

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

    public Personaje(Texture textura, float x, float y) {
        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);
        // La divide en 4 frames de 32x64
        TextureRegion[][] texturaPersonaje = texturaCompleta.split(32,64);
        // Crea la animación con tiempo de 0.25 segundos entre frames.
        spriteAnimado = new Animation(0.1f, texturaPersonaje[0][3], texturaPersonaje[0][2], texturaPersonaje[0][1] );
        // Animación infinita
        spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite con el personaje quieto (idle)
        Sprite sprite = new Sprite(texturaPersonaje[0][0]);    // QUIETO
        sprite.setPosition(x,y);    // Posición inicial
    }

    // Dibuja el personaje
    public void dibujar(SpriteBatch batch) {
        // Dibuja el personaje dependiendo del estadoMovimiento

        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case MOV_IZQUIERDA:
                timerAnimacion += Gdx.graphics.getDeltaTime();
                // Frame que se dibujará
                TextureRegion region = spriteAnimado.getKeyFrame(timerAnimacion);
                if (estadoMovimiento==EstadoMovimiento.MOV_IZQUIERDA) {
                    if (!region.isFlipX()) {
                        region.flip(true,false);
                    }
                } else {
                    if (region.isFlipX()) {
                        region.flip(true,false);
                    }
                }
                batch.draw(region,sprite.getX(),sprite.getY());
                break;
            case QUIETO:
            case INICIANDO:
                sprite.draw(batch); // Dibuja el sprite estático
                break;
        }
    }

    public void moverPersonaje(float x, float y, String Siguiente){
        //Crear el movimiento del personaje
        //Cuando termine el movimiento
        if(Siguiente == "Cavar"){
            Cavar();
        }
        else if(Siguiente == "Picar"){
            Picar();
        }
        else if(Siguiente == "Golpear"){
            Golpear();
        }
    }
    public void Picar(){
        //Animacion con el pico
    }
    public void Cavar(){
        //Animacion con la mano
    }
    public void Golpear(){
        //Animacion con la espada
    }
    public boolean hacerDaño(int Daño){
        vida -= Daño;
        if(vida <= 0){
            return true;
        }
        return false;
    }

    public void setEstadoMovimiento(EstadoMovimiento estadoMovimiento) {
        this.estadoMovimiento = estadoMovimiento;
    }

    public EstadoMovimiento getEstadoMovimiento() {
        return estadoMovimiento;
    }

    public enum EstadoMovimiento {
        INICIANDO,
        QUIETO,
        MOV_IZQUIERDA,
        MOV_DERECHA,
    }
}