package com.badlogic.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class EndScreen extends ScreenAdapter{
	
	AdventureGame game;
	
	public EndScreen(AdventureGame game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputAdapter() {
			
			@Override
			public boolean keyDown(int keyCode) {
				if(keyCode == Input.Keys.ESCAPE) {
					System.exit(0);
				}else {
				  game.setScreen(TitleScreen.getInstance(game));
				}
				
				return true;
			}
		});
	}	
	
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(.25f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.batch.begin();
        game.font.draw(game.batch, "This is the end Screen", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Press ESC to exit, anything else to restart", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();

	}
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	
	
	
}
