package com.badlogic.gdx;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assetManager.AgAssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class AdventureGame extends Game {
	static final float PPM = 16.0f;


	public static final int WIDTH = 720;
	public static final int HEIGHT = 480;

	public static float defaultVolume = 0.2f;

	public static AgAssetManager assistantManager = new AgAssetManager();
	private static Screen currentScreen;
	public static Music stage1Song;
	public static Music stage2Song;
	public static Music hubSong;
	public static Sound selectSound;
	public static Sound shooting;
	public static Sound jump;
	public static Sound enemyDeath;
	PooledEngine engine;
	SpriteBatch batch;
	Texture img;
	BitmapFont font;
	ShapeRenderer shape;
	
	/*
	 * The structure of the game is as follows
	 * THe Game Class (This one) will be responsible for holding/creating/disposing all of the assets and objects
	 * but will not do any of the actual logic.
	 * Think about this class like the toolbox,
	 * the screens use the tools from this toolbox, and then do actions with them.
	 */
	
	
	
	@Override
	public void create () {
		shape = new ShapeRenderer();
		font = new BitmapFont();
		batch = new SpriteBatch();
		engine = new PooledEngine();
		
		
		GameStateManager.setState(GameStateManager.TITLE_STATE);
		setScreen(this);
		assistantManager.queueAddMainMenuAssets();
		assistantManager.queueAddImages();
		assistantManager.queueAddMusic();
		assistantManager.queueAddSettingsMenuAssets();
		assistantManager.queueAddSoundsMenu();
		assistantManager.queueAddTitleSequence();
		assistantManager.initMiscSounds();
		assistantManager.manager.finishLoading();

	}
	
	
	
	public static void setScreen(AdventureGame game) {
	
		switch(GameStateManager.getState()) {
		
		case GameStateManager.TITLE_STATE:
			currentScreen = TitleScreen.getInstance(game);
			break;
		case GameStateManager.SETTINGS_STATE:
			currentScreen.hide();
			currentScreen = SettingsScreen.getInstance(game);
			break;
		case GameStateManager.OVERWORLD_HUB:
			currentScreen.hide();
			currentScreen = HubScreen.getInstance(game);
			break;
		case GameStateManager.STAGE_ONE:
			
			currentScreen.hide();
			currentScreen = StageScreen.getInstance(game);
			break;
		case GameStateManager.STAGE_TWO:
		
			currentScreen.hide();
			currentScreen = StageScreen.getInstance(game);
			break;
		default:
			currentScreen = TitleScreen.getInstance(game);
		}
		assistantManager.setStageMusic();
		game.setScreen(currentScreen);
	}
	
	
	

	private void initMusicStage1() {
	    stage1Song = assistantManager.manager.get("SoundAssets/stage1Song.wav", Music.class);
        
	    stage1Song.setVolume(defaultVolume);
		stage1Song.setLooping(true);
		//stage1Song.play();
	}
	
	private void initMusicStage2() {
	    stage2Song = assistantManager.manager.get("SoundAssets/stage2Song.wav", Music.class);
        
	    stage2Song.setVolume(defaultVolume);
		stage2Song.setLooping(true);
		//stage2Song.play();
	}
	
	private void initMusicHub() {
	    hubSong = assistantManager.manager.get("SoundAssets/hubSong.wav", Music.class);
        
	    hubSong.setVolume(defaultVolume);
		hubSong.setLooping(true);
		//stage2Song.play();
	}
	
	private void initSounds1() {
		selectSound = assistantManager.manager.get("SoundAssets/selectSound2.wav");
		shooting = assistantManager.manager.get("SoundAssets/laserGun.mp3");
		jump = assistantManager.manager.get("SoundAssets/jump.wav");
		enemyDeath = assistantManager.manager.get("SoundAssets/playerDMG.wav");
		//long sound2ID = soundTest2.play(0.1f);
		//soundTest2.setVolume(sound2ID, 0.1f);
		//selectSound.play();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		shape.dispose();
		font.dispose();
	}
}
