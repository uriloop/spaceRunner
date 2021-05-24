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
	Shop shop;
	static Long frames;
	BitmapFont font;
	ScoreBoard scoreboard;
	boolean pausa=false;
	Texture space;


	@Override
	public void create () {
		Assets.load();
		shop= new Shop();
		batch = new SpriteBatch();
		mundo= new Mundo();
		frames=1L;
		scoreboard = new ScoreBoard();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		font.getData().setScale(2f);
		mundo.reiniciar();
		space= new Texture("altres/space.png");
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
		}else{
			shop.update();
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
			batch.draw(Assets.titol,212,680);
			batch.draw(Assets.pause,300,600);
			shop.render(batch);
			batch.draw(Assets.esc,600,6);
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
				Gdx.app.exit();
			}
		}else{
			batch.draw(space,600,12,80,40);
		}
		if(Mundo.gameOver)scoreboard.render(batch,font);

		batch.end();
	}


	

}
