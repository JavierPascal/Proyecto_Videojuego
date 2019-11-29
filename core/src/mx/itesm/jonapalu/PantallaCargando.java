package mx.itesm.jonapalu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

public class PantallaCargando extends Pantalla {

    private final float TIEMPO_ENTRE_FRAME = 0.1f;
    private Sprite spriteCargando;
    private float timerAnimacion = TIEMPO_ENTRE_FRAME;

    private AssetManager manager; //unico en el juego

    private Juego juego;
    private TipoPantalla siguientePantalla;

    private Texture texturaCargando;
    private Texture texturaFondo;
    private Array<Sprite> spritesCargando;
    private int start;

    public PantallaCargando(Juego juego, TipoPantalla siguiente) {
        this.juego = juego;
        this.siguientePantalla = siguiente;
    }

    @Override
    public void show() {
        manager = juego.getManager();
        spritesCargando = new Array<>(5);
        for (int i = 0; i < 5; i++) {
            texturaCargando = new Texture("Cargando/loading" + i + ".png");
            spriteCargando = new Sprite(texturaCargando);
            spriteCargando.setPosition(ANCHO / 2 - spriteCargando.getWidth() / 2, ALTO / 2 - spriteCargando.getHeight() / 2);
            spritesCargando.add(spriteCargando);


        }
        texturaFondo = new Texture("HUD/fondoGris.png");


        cargarRecursosPantalla(); //Cargar elementos
    }

    private void cargarRecursosPantalla() {
        switch (siguientePantalla) {
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
            case CREARMUNDO:
                cargarRecursosCrearMundo();
                break;
            case CREATIVO:
                cargarRecursosPantallaCreativo();
                break;

        }

    }

    private void cargarRecursosPantallaCreativo() {
        manager.load("Mapas/mapaTutorial.tmx", TiledMap.class);
        manager.load("Botones/btnPausa.png", Texture.class);
        manager.load("Botones/btnRegresar.png", Texture.class);
        manager.load("Botones/btnCrafteo.png", Texture.class);
        manager.load("Configuracion/Sonido.png", Texture.class);
        manager.load("Configuracion/Silencio.png", Texture.class);
        manager.load("Botones/btnCerrar.png", Texture.class);
        manager.load("Botones/btnExit.png", Texture.class);
        manager.load("Botones/Pausa.png", Texture.class);



    }

    private void cargarRecursosCrearMundo() {
        manager.load("HUD/fondoGris.png", Texture.class);
        manager.load("Botones/btnRegresar.png", Texture.class);
        manager.load("Botones/btnCreativo.png", Texture.class);
        manager.load("Botones/btnCreativoPressed.png", Texture.class);
        manager.load("Botones/btnAventura.png", Texture.class);
        manager.load("Botones/btnAventuraPressed.png", Texture.class);


    }

    private void cargarRecursosTutorial() {
        manager.load("Botones/btnMano.png", Texture.class);
        manager.load("Botones/btnManoIzquierda.png", Texture.class);
        manager.load("Mapas/mapaTutorial.tmx", TiledMap.class);
        manager.load("Texturas/Tileset.png", Texture.class);
        manager.load("Botones/Pausa.png", Texture.class);
        manager.load("Botones/btnExit.png", Texture.class);
        manager.load("Configuracion/Silencio.png", Texture.class);
        manager.load("Configuracion/Sonido.png",Texture.class);

    }

    private void cargarRecursosMundos() {
        manager.load("HUD/fondoGris.png", Texture.class);
        manager.load("Botones/btnRegresar.png", Texture.class);
        manager.load("Botones/btnAgregarMundo.png", Texture.class);
        manager.load("Botones/btnAgregarMundoPressed.png", Texture.class);
        manager.load("Botones/btnMundo1.png", Texture.class);
        manager.load("Botones/btnMundo1Pressed.png", Texture.class);
        manager.load("Botones/btnTutorial.png", Texture.class);
        manager.load("Botones/btnTutorialPressed.png", Texture.class);
        manager.load("Botones/btnCreativo.png", Texture.class);



    }

    private void cargarRecursosInstrucciones() {
        manager.load("Audios/Musica.mp3", Music.class);
    }

    private void cargarRecursosConfig() {
        manager.load("Botones/btnRegresar.png", Texture.class);
        manager.load("HUD/fondoGris.png", Texture.class);
        manager.load("Botones/btnRegresar.png", Texture.class);
        manager.load("Configuracion/Silencio.png", Texture.class);
        manager.load("Configuracion/Sonido.png", Texture.class);
        manager.load("Audios/Musica.mp3", Music.class);

    }

    private void cargarRecursosAcerca() {
        manager.load("HUD/fondoGris.png", Texture.class);
        manager.load("Fotitos/nosotros.jpeg", Texture.class);
        manager.load("Fotitos/nosotrosPress.jpeg", Texture.class);
        manager.load("Botones/btnRegresar.png", Texture.class);

    }

    private void cargarRecursosJuego() {

    }

    private void cargarRecursosMenu() {
        manager.load("Texturas/fondoMenu.png", Texture.class);

        manager.load("Botones/logo.png", Texture.class);

        manager.load("Botones/btnJugar.png", Texture.class);
        manager.load("Botones/btnJugarPressed.png", Texture.class);

        manager.load("Botones/btnAcercaDe.png", Texture.class);
        manager.load("Botones/btnAcercaDePressed.png", Texture.class);

        manager.load("Botones/btnJugar.png", Texture.class);
        manager.load("Botones/btnJugar.png", Texture.class);


    }

    @Override
    public void render(float delta) {
        borrarPantalla(.218f, .165f, .32f);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        spritesCargando.get(start).draw(batch);
        batch.end();
        timerAnimacion -= delta;
        if (timerAnimacion <= 0) {
            start += 1;
            timerAnimacion = TIEMPO_ENTRE_FRAME;
            if (start > 3) {
                start = 0;
            }
        }
        actualizarCargaRecursos();

    }

    private void actualizarCargaRecursos() {
        if (manager.update()) {
            switch (siguientePantalla) {
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
                case CREARMUNDO:
                    juego.setScreen(new CrearMundo(juego));
                    break;
                case CREATIVO:
                    juego.setScreen(new PantallaCreativo(juego));

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

