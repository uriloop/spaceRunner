package com.mygdx.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Multiusos {

    String random(String... opcions){     /// Afegeix tantes opcions com vulguis i et retorna una d'elles a l'atzar
        double num=Math.random()*opcions.length;
        int enter= (int) num;
        return opcions[enter];
    }

    float random(float primer,float ultim){   // Retorna un float entre dos floats a l'atzar.
        float interval= ultim-primer;
        float resultat= interval*(float)Math.random();
        return resultat;
    }

    Music random(Music... musica){

int pos= (int)(random(0,musica.length*10));
    if (pos>8)pos=1;
    else pos/=10;
       return musica[pos];
    }

    static Random random = new Random();

    static boolean solapen(float x, float y, float w, float h, float x2, float y2, float w2, float h2) {
        return !(x > x2 + w2) && !(x + w < x2) && !(y > y2 + h2) && !(y + h < y2);
    }



}
