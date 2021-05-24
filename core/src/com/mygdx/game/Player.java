package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {

    float x,y,margeLateral,ample,w,h;
    Texture[] mort2,accelera,frena,normal,costat1Frena,costat1Accelera,costat1Normal,costat2Frena,costat2Accelera,costat2Normal;
    Animacion acceleraA,mort,frenaA,normalA,costat1FrenaA,costat1AcceleraA,costat1NormalA,costat2FrenaA,costat2AcceleraA,costat2NormalA;
    static int vidas=3;
    boolean muerto=false;
    Animacion actual;
    Sound alarm;
    boolean playso= false;


    Temporizador respawn=new Temporizador(100,true);


    Player(){

        w=120;
        h=50;

        margeLateral=10;
        ample=740;
        actual= normalA;
        x=ample/2-(w/2-24);
        y=80;
        vidas=3;
        mort2=new Texture[3];
        accelera= new Texture[2];
        frena= new Texture[2];
        normal= new Texture[2];
        costat1Accelera= new Texture[2];
        costat2Accelera= new Texture[2];
        costat1Frena= new Texture[2];
        costat2Frena= new Texture[2];
        costat1Normal= new Texture[2];
        costat2Normal= new Texture[2];
        accelera[0]= new Texture("nau/accelera1.png");
        accelera[1]= new Texture("nau/accelera2.png");
        frena[0]= new Texture("nau/frena1.png");
        frena[1]= new Texture("nau/frena2.png");
        normal[0]= new Texture("nau/normal1.png");
        normal[1]= new Texture("nau/normal2.png");
        costat1Accelera[0]= new Texture("nau/costat1accelera1.png");
        costat1Accelera[1]= new Texture("nau/costat1accelera2.png");
        costat2Accelera[0]= new Texture("nau/costat2accelera1.png");
        costat2Accelera[1]= new Texture("nau/costat2accelera2.png");
        costat1Normal[0]= new Texture("nau/costat1normal1.png");
        costat1Normal[1]= new Texture("nau/costat1normal2.png");
        costat2Normal[0]= new Texture("nau/costat2normal1.png");
        costat2Normal[1]= new Texture("nau/costat2normal2.png");
        costat1Frena[0]= new Texture("nau/costat1frena1.png");
        costat1Frena[1]= new Texture("nau/costat1frena2.png");
        costat2Frena[0]= new Texture("nau/costat2frena1.png");
        costat2Frena[1]= new Texture("nau/costat2frena2.png");
        mort2[0]= new Texture("nau/mort.png");
        mort2[1]= new Texture("nau/mort1.png");
        mort2[2]= new Texture("nau/mort2.png");


        acceleraA=new Animacion(20,accelera);
        frenaA= new Animacion(20,frena);
        normalA= new Animacion(20,normal);
        costat1AcceleraA= new Animacion(20,costat1Accelera);
        costat2AcceleraA= new Animacion(20,costat2Accelera);
        costat1FrenaA= new Animacion(20,costat1Frena);
        costat2FrenaA= new Animacion(20,costat2Frena);
        costat1NormalA= new Animacion(20,costat1Normal);
        costat2NormalA= new Animacion(20,costat2Normal);
        mort = new Animacion(20, mort2);

        alarm= Gdx.audio.newSound(Gdx.files.internal("nau/alarm.ogg"));


        //norm= Gdx.audio.newSound(Gdx.files.internal("nau/normal.ogg"));

    }

    public void update(int quefa) {

    if (muerto){
        x=ample/2-(w/2-24);
        actual=mort;
        if (respawn.suena()) {
            muerto = false;
            playso=false;
        }


    }else {
        if (quefa == 0) {
            actual = frenaA;

            //norm.setLooping(norm.play(), true);
        } else if (quefa == 1) {
            actual = normalA;

            //norm.setLooping(norm.play(), true);
        } else {
            actual = acceleraA;

            //norm.setLooping(norm.play(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            actual = costat1NormalA;
            if (x > margeLateral - 25) {
                x -= 10;
                if (quefa == 0) {
                    actual = costat1FrenaA;
                } else if (quefa == 1) {
                    actual = costat1NormalA;
                } else {
                    actual = costat1AcceleraA;
                }

            }


        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            actual = costat2NormalA;
            if (x < ample - margeLateral - 50) {
                x += 10;
                if (quefa == 0) {
                    actual = costat2FrenaA;
                } else if (quefa == 1) {
                    actual = costat2NormalA;
                } else {
                    actual = costat2AcceleraA;
                }
            }
        }

    }

    }

    public void render(SpriteBatch batch) {
         batch.draw(actual.obtenerFrame(),x,y);


    }

    public void reiniciar(){
        actual= normalA;
        x=ample/2-(w/2-24);
        y=80;
        vidas=3;


    }

    public void morir(){
        if (!playso){
            alarm.play();
            playso=true;
        }

            vidas--;
            muerto=true;
            respawn.activar();




    }







}
