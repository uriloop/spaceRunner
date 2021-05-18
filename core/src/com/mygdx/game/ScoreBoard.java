package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ScoreBoard {


    static int puntuacionMinima;
    char[] nombre = {'A', 'A','A'};  // 65:A -> 90:Z
    int index = 0;  // 0=1a letra; 1=2a letra; 2=3a letra
    public boolean saved;


    class Score {
        String nombre;
        int puntuacion;

        public Score(String nombre, int puntuacion) {
            this.nombre = nombre;
            this.puntuacion = puntuacion;
        }
    }


    List<Score> scoreList = new ArrayList<>();
    /*void render(SpriteBatch batch, BitmapFont font){


        scoreList.sort(new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {

                return o2.puntuacion - o1.puntuacion;
            }
        });



        font.draw(batch, "* Hall of Fame *", 300, 720);

        for (int i = 0; i < 5 && i < scoreList.size(); i++) {
            font.draw(batch, scoreList.get(i).nombre, 320, 660-i*30);
            font.draw(batch, ""+scoreList.get(i).puntuacion, 400, 660-i*30);
        }
    }*/

    void guardarPuntuacion(int puntuacion){

        String nom="";
        for (char car: nombre){
            nom+=car;
        }

        try {
            FileWriter fileWriter = new FileWriter("scores.txt", true);
            fileWriter.write(nom+"," + puntuacion + "\n");
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        leerPuntuaciones();
    }

    void leerPuntuaciones(){
        try {
            Scanner scanner = new Scanner(new File("scores.txt"));
            scanner.useDelimiter(",|\n");
            scoreList.clear();
            while(scanner.hasNext()){
                String nombre = scanner.next();
                int puntos = scanner.nextInt();

                scoreList.add(new Score(nombre, puntos));
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void render(SpriteBatch batch, BitmapFont font) {

        batch.draw(Assets.taula,175,350);
        batch.draw(Assets.titol,212,775);

        if(!saved) {
            font.draw(batch, "ENTER YOUR NAME", 275, 650);

            font.getData().setScale(3);
            font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            for (int i = 0; i < 3; i++) {
                if(index == i){
                    font.setColor(Color.RED);
                }
                font.draw(batch, ""+ nombre[i], 340+40*i, 540);
                font.setColor(Color.WHITE);
            }
            font.getData().setScale(2);
        }else {

            //ordenar();

            scoreList.sort(new Comparator<Score>() {
                @Override
                public int compare(Score o1, Score o2) {

                    return o2.puntuacion - o1.puntuacion;
                }
            });







            font.draw(batch, "* Hall of Fame *", 300, 720);

            for (int i = 0; i < 5 && i < scoreList.size(); i++) {
                font.draw(batch, scoreList.get(i).nombre, 320, 660-i*30);
                font.draw(batch, ""+scoreList.get(i).puntuacion, 400, 660-i*30);
            }

        }

    }

    private void ordenar() {

        List<Score> guardada= scoreList;
        List<Score> aMostrar=new ArrayList<>();
        Score provisional= guardada.get(0);

        for (int i = 0; i < guardada.size() || i < 5; i++) {
            for (Score score:guardada){
                if (provisional.puntuacion<scoreList.get(i).puntuacion){
                    provisional=score;
                }
            }
            aMostrar.add(provisional);
            scoreList.remove(provisional);
            guardada=scoreList;


        }




    }


    int update(int puntos){

        if(index < 3 && Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            nombre[index]++;
            if(nombre[index] > 90) {
                nombre[index] = 65;
            }
        }
        if(index < 3 && Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            nombre[index]--;
            if(nombre[index] < 65) {
                nombre[index] = 90;
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            /*if(index == 3) {
                return 1;
            }*/
            index++;
        }



        if(index > 2 && Gdx.input.isKeyJustPressed(Input.Keys.SPACE) ) {
            saved = false;
            index = 0;
            return 1;
        }
        if(index > 2 && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            saved = false;
            index = 0;
            return 2;
        }

        if(index > 2 && !saved) {
            guardarPuntuacion(puntos);
            saved = true;
        }

        return 0;
    }
}
