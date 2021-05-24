package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Explosion {
    Texture[] texturas= new Texture[4];
    Temporizador tempsExplo=new Temporizador(40,false);
    Animacion animacion;
    float x,y,vel,h,w;


    public Explosion(float x, float y) {
        this.x=x;
        this.y=y;
        h=10;
        w=10;
        texturas[0]= new Texture("obstacles/exp1.png");
        texturas[1]= new Texture("obstacles/exp2.png");
        texturas[2]= new Texture("obstacles/exp3.png");
        texturas[3]= new Texture("obstacles/exp4.png");
        animacion= new Animacion(40,texturas);

    }

    public void update(float velocitat) {
        y-=velocitat/50;
    }


    public void render(SpriteBatch batch) {

        batch.draw(animacion.obtenerFrame(),x,y);

    }



}
