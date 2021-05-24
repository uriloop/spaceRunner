package com.mygdx.game;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fondo {

    Capa capa1,capa2,capa3,capa4,capa44,capa22,capaFixe;



    Fondo(){

        capa1=new Capa("fondo/capa1.png");
        capa2= new Capa("fondo/capa2.png");
        capa3= new Capa("fondo/capa3.png");
        capa22= new Capa("fondo/capa2.png",2000);
        capaFixe=new Capa("fondo/capaFixe.png");
        capa4= new Capa ("fondo/capa4.png");
        capa44= new Capa ("fondo/capa4.png",2000);
    }

    void update(float velocitat){


        capa3.update(velocitat/150f);
        capa4.update(velocitat/30f);
        capa2.update(velocitat/80f);
        capa1.update(velocitat/200f);
        capa22.update(velocitat/80f);
        capa44.update(velocitat/30f);


    }

    public void render(SpriteBatch batch) {
        capaFixe.render(batch);
        capa3.render(batch);
        capa2.render(batch);
        capa22.render(batch);
        capa1.render(batch);
        capa4.render(batch);
        capa44.render(batch);

    }
}
