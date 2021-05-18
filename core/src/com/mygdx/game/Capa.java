package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Capa {

    Texture textura;
    float x,y;
    Capa(String path){
        textura= new Texture(path);
        x=0;
        y=0;
    }

    Capa (String path,float y){
        textura= new Texture(path);
        x=0;
        this.y=y;
    }

    void update(float velocitat){
        if (y<-2600){    // bucle imatge
            y=0;
        }
        y-=velocitat;
    }


    public void render(SpriteBatch batch) {

        batch.draw(textura,x,y);

    }
}
