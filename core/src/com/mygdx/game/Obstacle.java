package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;




public class Obstacle {

    public int altura;
    public int punts;
    float x,y,w,h;
    Texture[] roca= new Texture[3];
    Texture[] alien= new Texture[2];
    Texture[] ship= new Texture[3];
    String tipus;
    Animacion anim;
    Multiusos fer;
    boolean mort;
    int vidas;
    Temporizador canviDireccio;

    Obstacle(String tipus){
        // la x serà un random entre la posició 0 i la amplePantalla
        fer= new Multiusos();
        // x= fer.random.nextInt(800);
        x=fer.random(50,730);
        y=900;
        mort=false;

        this.tipus=tipus;

        // Texturas    amb selector

        alien[0]=new Texture(Gdx.files.internal("obstacles/alien1.png"));
        alien[1]= new Texture(Gdx.files.internal("obstacles/alien2.png"));



        roca[0]=new Texture(Gdx.files.internal("obstacles/roca1.png"));
        roca[1]= new Texture(Gdx.files.internal("obstacles/roca2.png"));
        roca[2]= new Texture(Gdx.files.internal("obstacles/roca3.png"));
        /*
        alien[0]=new Texture(Gdx.files.internal("obstacles/alien1.png"));
        alien[1]= new Texture(Gdx.files.internal("obstacles/alien2.png"));
        alien[2]= new Texture(Gdx.files.internal("obstacles/alien3.png"));
        alien[3]= new Texture(Gdx.files.internal("obstacles/alien4.png"));
        */

        ship[0]=new Texture(Gdx.files.internal("obstacles/ship1.png"));
        ship[1]= new Texture(Gdx.files.internal("obstacles/ship2.png"));
        ship[2]= new Texture(Gdx.files.internal("obstacles/ship3.png"));

        switch (tipus){
            case "roca":
                anim=new Animacion(20,roca);
                h=50;
                w=75;
                vidas=1;
                punts=1000;
                canviDireccio=new Temporizador(1,false);
                break;
            case "alien":
                anim=new Animacion(30,alien);
                altura=50;
                h=50;
                w=120;
                vidas=2;
                punts+=3000;
                canviDireccio=new Temporizador(15,true);
                break;
            case "ship":
                anim= new Animacion(20,ship);
                altura=100;
                h=50;
                w=120;
                punts+=6000;
                vidas=3;
                canviDireccio=new Temporizador(20,true);
                break;
            default:
                anim=new Animacion(20,alien);
                altura=50;
                h=50;
                w=50;
                punts+=100;
                vidas=2;
                canviDireccio=new Temporizador(10,false);
                break;
        }
    }

    void update (float vel){
        y-=vel/50;

        x+=movimentAleatori();

    }

    float moviment=0;
    private float movimentAleatori() {

        if (canviDireccio.suena()){
            switch (tipus){
                case "alien":
                    moviment=fer.random(-5,2);

                    break;
                case "roca":
                    moviment=fer.random(-1f,1f);
                    break;
                case "ship":
                    moviment=fer.random(-6,6);
                    break;
                default:
                    break;
            }

        }

        if (x>780-w||x<10)moviment*=(-1);


        return moviment;
    }

    void render (SpriteBatch batch){
        batch.draw(anim.obtenerFrame(),x,y);
    }


}
