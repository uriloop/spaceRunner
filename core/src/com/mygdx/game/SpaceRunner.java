package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class SpaceRunner extends ApplicationAdapter {
	SpriteBatch batch;
	Mundo mundo;
	static Long frames;
	BitmapFont font;
	ScoreBoard scoreboard;
	boolean fin=false;
	boolean nouJoc=false;
	boolean pausa=false;
	Temporizador titolTemp=new Temporizador(200,false);

	@Override
	public void create () {
		Assets.load();

		batch = new SpriteBatch();
		mundo= new Mundo();
		frames=1L;
		scoreboard = new ScoreBoard();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		font.getData().setScale(2f);

		mundo.reiniciar();

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			pausa=!pausa;
		}
		if (!pausa) {
			frames++;

			mundo.update();
		}

		if (Mundo.gameOver                 ){
			/*if (mundo.puntos>)*/
			int result = scoreboard.update((int)mundo.puntos);

			if(result == 1) {
				//pausa=false;

				mundo.reiniciar();
			} else if (result == 2) {
				Gdx.app.exit();
			}

		}

		batch.begin();

		mundo.render(batch);
		batch.draw(Assets.hud,0,0);
		font.draw(batch, "" + mundo.player.vidas, 340, 40);
		font.draw(batch, "" + (int)mundo.puntos, 480, 40);
		if(pausa){
			batch.draw(Assets.titol,212,575);
			batch.draw(Assets.pause,300,430);
			batch.draw(Assets.esc,600,6);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
				Gdx.app.exit();
			}
		}
		if(Mundo.gameOver)scoreboard.render(batch,font);

/*
		if (Mundo.gameOver){
			if (!fin){
				fin=true;
				scoreboard.guardarPuntuacion((int)mundo.puntos);
			}
			batch.draw(taula,175,350);
			batch.draw(titol,212,775);
			scoreboard.render(batch, font);

			batch.draw(esc,600,6);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
				Gdx.app.exit();
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
				mundo.reiniciar();

				fin=false;
				scoreboard.eliminarLlista();
			}

		}
		*/


		batch.end();
	}


	

}
