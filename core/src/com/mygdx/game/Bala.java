package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bala {

    Texture[] texturas= new Texture[4];

    Animacion animacion;
    float x,y,vel,h,w;


    public Bala(float x, float y) {
        this.x=x;
        this.y=y;
        h=10;
        w=5;
        vel=50;
        texturas[0]= new Texture("dispars/bala1.png");
        texturas[1]= new Texture("dispars/bala2.png");
        texturas[2]= new Texture("dispars/bala3.png");
        texturas[3]= new Texture("dispars/bala4.png");
        animacion= new Animacion(50,texturas);

    }

    public void update(float velocitat) {

        vel=100;
        vel-=velocitat/6;
        y+=vel;
    }


    public void render(SpriteBatch batch) {

        batch.draw(animacion.obtenerFrame(),x,y);

    }
}
