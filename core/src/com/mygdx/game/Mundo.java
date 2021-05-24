package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Mundo {


    static boolean balesDobles=false;
    Temporizador tempBalasDobles= new Temporizador(1200,false);
    double distancia;

    static boolean gameOver=true;
    Temporizador generaObstacle;
    Fondo fondo=new Fondo();
    Player player;
    List<Bala> balas= new ArrayList<>();
    Sound auu,rocE;
    List<Obstacle> obstacles;
    Multiusos fer=new Multiusos();
    double distanciaEntreObstacles;
                  ///  Accelera nau= new Accelera();       ///  intenció: Tots els moviments d'acceleracio pe metodes
    float velocitat,velMax,velMin;
    int quefa;
    Boolean alternaBalas;
    Music bgMusica,bgMusica2,bgMusica3,bgMusica4;
    Music actualMus;
    static float puntos=0;

    Temporizador tempMort;
    List<Music> musiques;

    List<Explosion> explosions=new ArrayList<>();
    boolean reiniciaTemp=false;
    static int musicaNum;





    Mundo(){
        distancia=0;     /// Aixó és per a que es generin els obstacles depenent de la distancia recorreguda.
        distanciaEntreObstacles=10000;

        generaObstacle=new Temporizador(200,true);
        quefa=1;

        velocitat=80;
        velMax=400;

        velMin=velocitat;

        player = new Player();
        alternaBalas=false;


        bgMusica= Gdx.audio.newMusic(Gdx.files.internal("fondo/futureWorlds.mp3"));
        bgMusica2= Gdx.audio.newMusic(Gdx.files.internal("fondo/Space Journey.mp3"));
        bgMusica3= Gdx.audio.newMusic(Gdx.files.internal("fondo/Empire of War.mp3"));
        bgMusica4= Gdx.audio.newMusic(Gdx.files.internal("fondo/Robotik.mp3"));

        actualMus= fer.random(bgMusica,bgMusica2,bgMusica3,bgMusica4);
        actualMus.play();
        actualMus.setLooping(true);

        auu=Gdx.audio.newSound(Gdx.files.internal("obstacles/auu.ogg"));
        rocE=Gdx.audio.newSound(Gdx.files.internal("obstacles/rocE.ogg"));

        obstacles= new ArrayList<>();
        obstacles.add(new Obstacle("alien"));

        tempMort= new Temporizador(100,true);
        tempMort.ferSonar();

        musiques=new ArrayList<>();
        musiques.add(bgMusica);
        musiques.add(bgMusica2);
        musiques.add(bgMusica3);
        musiques.add(bgMusica4);

        for (int i = 0; i < musiques.size(); i++) {
            if (actualMus==musiques.get(i)){
                musicaNum=i;

            }
        }
    }

    public void update() {
        acelerador();
        fondo.update(velocitat);
        disparar();
        generarObtacles();
        colisiones();
        explosiones();
        distancia += (int) velocitat;
        Temporizador.framesJuego++;
        if (!player.muerto) {
            if(velocitat>velMin*2){
                puntos += (int) (velMin / 80f);
            }else {
                puntos += (int) (velocitat / 50f);
            }
        }

        if(player.vidas<1){
            gameOver=true;


        }else{
            player.update(quefa);
        }

    }

    private void explosiones() {
        List<Explosion> eliminarExp= new ArrayList<>();
        for (Explosion explo : explosions){
            explo.update(velocitat);
            if (explo.y>900){
                eliminarExp.add(explo);
            }else if (explo.tempsExplo.suena()){
                eliminarExp.add(explo);
            }
        }
        //       Eliminar les bales
        for (Explosion explo: eliminarExp){
            explosions.remove(explo);
        }
    }

    private void disparar() {
        //       Alternar les bales   o doble dispar

        if (tempBalasDobles.suena()){
            balesDobles=false;
            reiniciaTemp=false;
        }


        if (balesDobles){
            if (!reiniciaTemp){
                tempBalasDobles.activar();
                reiniciaTemp=true;
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.M)){
                balas.add(new Bala(player.x+35,player.y+20));
                balas.add(new Bala(player.x+98,player.y+20));

            }
        }else{
            if (Gdx.input.isKeyJustPressed(Input.Keys.M)){
                if (alternaBalas){
                    balas.add(new Bala(player.x+35,player.y+39));

                }else{
                    balas.add(new Bala(player.x+98,player.y+39));
                }

                alternaBalas=!alternaBalas;
            }
        }


    }

    private void generarObtacles() {
        // Obstacles/enemics
        if (generaObstacle.suena()){
            obstacles.add(new Obstacle(fer.random("alien","roca","ship")));   ///  Aquest random mola!
            // obstacles.add(new Obstacle("alien"));
            generaObstacle.activar();
        }
        if (distancia%10000==0&&distanciaEntreObstacles>300){
            distanciaEntreObstacles-=20;
        }
        if (puntos%5000==0&& puntos>5000){
            generaObstacle.frecuencia--;
        }

        if (distancia%distanciaEntreObstacles==0){
            obstacles.add(new Obstacle(fer.random("alien","roca","ship")));
        }

    }

    private void colisiones() {


        ///   gestio d'obstacles
        List<Bala> eliminarBala= new ArrayList<>();
        for (Bala bala : balas){
            bala.update(velocitat);
            if (bala.y>900){
                eliminarBala.add(bala);
            }
        }
        //       Eliminar les bales
        for (Bala bala: eliminarBala){
            balas.remove(bala);
        }


        boolean esRoca, esAlien;
        esAlien=false;
        esRoca= false;

        if (!gameOver){
            List<Obstacle> eliminarObs= new ArrayList<>();
            for (Obstacle obstacle: obstacles){
                obstacle.update(velocitat);
                if (obstacle.vidas<1){
                    eliminarObs.add(obstacle);
                    explosions.add(new Explosion(obstacle.x, obstacle.y));
                    puntos+=obstacle.punts;
                    if (obstacle.tipus.equals("alien")||obstacle.tipus.equals("ship")) esAlien=true;
                    if (obstacle.tipus.equals("roca")||obstacle.tipus.equals("ship")) esRoca=true;
                }else if (obstacle.y <= -obstacle.altura){
                    eliminarObs.add(obstacle);
                }
            }


            for (Obstacle obstacle:eliminarObs){

                obstacles.remove(obstacle);
            }

            if (esAlien)auu.play(0.22f);
            if (esRoca)rocE.play(0.6f);
            esRoca=false;
            esAlien=false;


            for (Obstacle obstacle:obstacles){
                for (Bala bala: balas){
                    if (fer.solapen(bala.x,bala.y,bala.w,bala.h,obstacle.x,obstacle.y, obstacle.w, obstacle.h)){
                        eliminarBala.add(bala);
                        obstacle.vidas--;
                    }
                }
            }

            for (Obstacle obstacle:eliminarObs){
                obstacles.remove(obstacle);
            }
            for (Bala bala: eliminarBala) {
                balas.remove(bala);

            }
        }
        for (Obstacle obstacle: obstacles){
            if (fer.solapen(obstacle.x, obstacle.y, obstacle.w, obstacle.h, player.x, player.y, player.w, player.h)){
                if(tempMort.suena()){
                    player.morir();
                    obstacle.vidas-=3;
                    tempMort.activar();
                    break ;

                }

            }
        }

    }

    private void acelerador() {

        if (velocitat<velMin)velocitat=velMin;

        if (Gdx.input.isKeyPressed(Input.Keys.W)){    // Accelera
            if(velocitat<=velMax){
                velocitat+=10;
                quefa=3;
            }
        }else if (Gdx.input.isKeyPressed(Input.Keys.S)){    //  Frena
            if(velocitat>velMin){
                velocitat-=8;
                quefa=0;
            }
        }else{      // Frena lleugerament
            if(velocitat>velMin){
                velocitat-=5;
                quefa=1;
            }
        }
        if (Temporizador.framesJuego%500==0&& velMin<600){
            velMin+=15;
            velMax+=2;
        }
    }

    public void reiniciar(){
        gameOver=false;
        distancia=0;     /// Aixó és per a que es generin els obstacles depenent de la distancia recorreguda.
        Temporizador.framesJuego=0;
        quefa=1;
        puntos=0;
        velocitat=100;
        velMax=420;
        player.muerto=false;
        velMin=velocitat;
        generaObstacle.activar();
        generaObstacle.frecuencia=160;
        player.reiniciar();
        alternaBalas=false;

        obstacles.clear();
        balas.clear();

        tempMort.ferSonar();


        actualitzarMusica();

    }

    private void actualitzarMusica() {
        actualMus.dispose();
        actualMus= fer.random(bgMusica,bgMusica2,bgMusica3,bgMusica4);
        for (int i = 0; i < musiques.size(); i++) {
            if (actualMus==musiques.get(i)){
                musicaNum=i;
            }
        }
        actualMus.play();
        actualMus.isLooping();
    }

    public void render(SpriteBatch batch) {

        fondo.render(batch);

        for (Obstacle obstacle:obstacles){
            obstacle.render(batch);

        }

        for (Explosion explo: explosions){
            explo.render(batch);
        }
        player.render(batch);

        for (Bala bala: balas){
            bala.render(batch);
        }

        if (actualMus!=musiques.get(musicaNum)){

            actualMus.dispose();
            actualMus=musiques.get(musicaNum);
            actualMus.play();
            actualMus.setLooping(true);
        }

    }
}
