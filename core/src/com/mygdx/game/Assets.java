package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

    public static Texture hud,taula,titol,pause, esc;

    static void load() {
        hud= new Texture(Gdx.files.internal("altres/hud.png"));
        taula=new Texture(Gdx.files.internal("altres/hall.png"));
        titol= new Texture(Gdx.files.internal("altres/titol.png"));
        pause= new Texture(Gdx.files.internal("altres/pause.png"));
        esc=new Texture(Gdx.files.internal("altres/esc.png"));
    }
}
