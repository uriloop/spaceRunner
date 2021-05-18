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

    double distancia;
    Obstacle proba= new Obstacle("alien");
    static boolean gameOver=true;
    Temporizador generaObstacle;
    Fondo fondo=new Fondo();
    Player player;
    List<Bala> balas= new ArrayList<>();
    Sound dispar,auu,rocE;
    List<Obstacle> obstacles;
    Multiusos fer=new Multiusos();
    double distanciaEntreObstacles;
                  ///  Accelera nau= new Accelera();       ///  intenció: Tots els moviments d'acceleracio pe metodes
    float velocitat,velMax,velMin;
    int quefa;
    Boolean alternaBalas;
    Music bgMusica,bgMusica2,actualMus;
    float puntos=0;

    Temporizador tempMort;







    Mundo(){
        distancia=0;     /// Aixó és per a que es generin els obstacles depenent de la distancia recorreguda.
        distanciaEntreObstacles=10000;

        generaObstacle=new Temporizador(200,true);
        quefa=1;

        velocitat=100;
        velMax=420;

        velMin=velocitat;

        player = new Player();
        alternaBalas=false;
        bgMusica= Gdx.audio.newMusic(Gdx.files.internal("fondo/futureWorlds.mp3"));
        bgMusica2= Gdx.audio.newMusic(Gdx.files.internal("fondo/futureWorlds.mp3"));
        actualMus=fer.random(bgMusica,bgMusica2);
        actualMus.play();
        actualMus.setLooping(true);
        dispar= Gdx.audio.newSound(Gdx.files.internal("dispars/blaster.ogg"));
        auu=Gdx.audio.newSound(Gdx.files.internal("obstacles/auu.ogg"));
        rocE=Gdx.audio.newSound(Gdx.files.internal("obstacles/rocE.ogg"));

        obstacles= new ArrayList<>();
        obstacles.add(new Obstacle("alien"));

        tempMort= new Temporizador(100,true);
        tempMort.ferSonar();


    }

    public void update() {
        if (velocitat<velMin)velocitat=velMin;
        // classe accelera per gestionar aquest codi.  EN CONSTRUCIO
        if (Gdx.input.isKeyPressed(Input.Keys.W)){    // Accelera
            if(velocitat<=velMax){
                velocitat+=8;
                quefa=3;
            }
        }else if (Gdx.input.isKeyPressed(Input.Keys.S)){    //  Frena
            if(velocitat>velMin){
                velocitat-=8;
                quefa=0;
            }
        }else{      // Frena lleugerament
            if(velocitat>velMin){
                velocitat-=5f;
                quefa=1;
            }
        }

        // ______________________________________________

        fondo.update(velocitat);


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
        //       Alternar les bales
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)){
            if (alternaBalas){
                balas.add(new Bala(player.x+35,player.y+39));

            }else{
                balas.add(new Bala(player.x+98,player.y+39));
            }
            dispar.play(3);
            alternaBalas=!alternaBalas;
        }

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

        hola:
        for (Obstacle obstacle: obstacles){
            if (fer.solapen(obstacle.x, obstacle.y, obstacle.w, obstacle.h, player.x, player.y, player.w, player.h)){
                if(tempMort.suena()){
                    player.morir();
                    obstacle.vidas-=3;
                    tempMort.activar();
                    break hola;

                }

            }
        }



        // aumentar els obstacles segons la distancia recorreguda




 /*       if ((int)distancia%1000==0){
            if(distanciaEntreObstacles>50)
            distanciaEntreObstacles-=10;
        }


        if (distancia%distanciaEntreObstacles==0){
            obstacles.add(new Obstacle("alien"));

        }
*/
        ///   gestio d'obstacles
        boolean esRoca, esAlien;
        esAlien=false;
        esRoca= false;

        if (!gameOver){
            List<Obstacle> eliminarObs= new ArrayList<>();
            for (Obstacle obstacle: obstacles){
                obstacle.update(velocitat);
                if (obstacle.vidas<1){
                    eliminarObs.add(obstacle);
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

            if (esAlien)auu.play();
            if (esRoca)rocE.play();
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
        if (Temporizador.framesJuego%240==0&& velMin<400){
            velMin+=20;
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
    }

    public void render(SpriteBatch batch) {

        fondo.render(batch);

        for (Obstacle obstacle:obstacles){
            obstacle.render(batch);

        }

        player.render(batch);

        for (Bala bala: balas){
            bala.render(batch);
        }

    }
}
