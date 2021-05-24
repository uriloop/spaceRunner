package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class Shop {

    Texture balesDobles,vida;
    Texture exit, musica;

    public Shop(){
      balesDobles= new Texture("shop/bales.png");
        vida= new Texture("shop/vides.png");
        exit=new Texture("altres/exit.png");
        musica= new Texture("altres/musicC.png");
       //velocitat= new Texture("shop/velocitat.png");
    }

    public void update(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.V)){
            if (Mundo.puntos>100001&&Player.vidas<3){
                Mundo.puntos-=100000;
                Player.vidas+=1;
            }
        }else if (Gdx.input.isKeyJustPressed(Input.Keys.B)){
            if (Mundo.puntos>5001&& !Mundo.balesDobles){
                Mundo.puntos-=5000;
                Mundo.balesDobles=true;

            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)){
            Mundo.musicaNum++;
            if (Mundo.musicaNum==4){
                Mundo.musicaNum=0;
            }
        }
        /*else if (Gdx.input.isKeyJustPressed(Input.Keys.C)){
            if (Mundo.puntos>10000){

            }
        }
        */

    }


    public void render(SpriteBatch batch) {
        batch.draw(balesDobles, 200, 450);
        batch.draw(vida, 200, 350);
        batch.draw(musica, 650, 30);
        batch.draw(exit, 600, 70);
    }
}
