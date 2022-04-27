package com.badlogic.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.entity.systems.RenderSystem;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

import com.badlogic.gdx.AdventureGame;



public class TitleScreen extends ScreenAdapter{
	private static int rTracker = 0;
	private static boolean tReverse = false;
	AdventureGame parent;
	
	//Screen Dimensions
	private static final int BACKGROUND_Y = 900;
	private static final int BACKGROUND_X = 1600;
	//Title Sequence Dimensions
	private static final int TSQ_WIDTH = 671;
	private static final int TSQ_HEIGHT = 328;
	private static final int TSQ_Y = (int)(Gdx.graphics.getHeight() - TSQ_HEIGHT);
	private static final int TSQ_X = (int) Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/2;
	private static final int EXIT_BUTTON_WIDTH = 454;
	private static final int EXIT_BUTTON_HEIGHT = 128;
	
	private static final int PLAY_BUTTON_WIDTH = 483;
	private static final int PLAY_BUTTON_HEIGHT = 128;

	private static final int SETTINGS_BUTTON_WIDTH = 888;
	private static final int SETTINGS_BUTTON_HEIGHT = 128;
	private static final int EXIT_BUTTON_Y = (int)(Gdx.graphics.getHeight() - (float)(Gdx.graphics.getHeight() *4.0/5));
	private static final int PLAY_BUTTON_Y = (int)(Gdx.graphics.getHeight() - (float)(Gdx.graphics.getHeight() *2.0/5));
	private static final int SETTINGS_BUTTON_Y = (int)(Gdx.graphics.getHeight() - (float)(Gdx.graphics.getHeight() *3.0/5));
	private static final int BACKGROUND_START_Y = 5;
	private static final int BACKGROUND_START_X = 0;
	
	private static Screen thisInstance;
	TextureAtlas playButtonAtlas, exitButtonAtlas, settingsButtonAtlas, TS1Atlas, TS2Atlas;
	Texture boopy;
	TextureRegion playButtonActive,playButtonInactive,
				exitButtonActive, exitButtonInactive,
				settingsButtonActive,settingsButtonInactive,
				titleSequenceStart, titleSequenceEnd;
				
	TextureRegion[] playButtons;
	TextureRegion[] playButtonRetake;
	TextureRegion[] settingsButtons;
	TextureRegion[] exitButtons;
	TextureRegion[] titleSequenceFrames;
	
	Texture background;

	public static Sound selectSound;

	private Random ran = new Random();
	
	
	
	private TitleScreen(AdventureGame game) {
		parent = game;
		
		loadTextures();

	    initAtlases();
	    initSettingsButton();
	    initPlayButton();
	    initExitButton();
	    initTitleSequence();
	    

	    
	    
		background = new Texture("AssortedImages/LargePNGS/Menu2.png");
	
		final TitleScreen titleScreen = this;
		
		

	}
	
	public static Screen getInstance(AdventureGame game) {
		if(thisInstance == null) {
			thisInstance = new TitleScreen(game);
		}
		return thisInstance;
	}
	
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .50f, .75f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		parent.batch.begin();
		Gdx.graphics.setContinuousRendering(true);
		Gdx.graphics.setForegroundFPS(60);
		
		parent.batch.draw(background, BACKGROUND_START_X, BACKGROUND_START_Y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		int x = 0;
		parent.batch.draw(titleSequenceFrames[rTracker],TSQ_X, TSQ_Y, TSQ_WIDTH, TSQ_HEIGHT);
		
		rTracker = (rTracker == 19) ? 19: rTracker +1;
		if(parent.batch.totalRenderCalls % 500 < 18) {
			rTracker = 0;
		}
	
		/*
		 * The input referenced below is an active update of the users cursor
		 * if cursor is located on button, change button to active png
		 */
		if(Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > EXIT_BUTTON_Y) {
			
			exitButtonActive = exitButtons[ran.nextInt(5)];
			parent.batch.draw(exitButtonActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			if(Gdx.input.justTouched()) { //if user clicks exit button, exit.
				Gdx.app.exit();
			}}
			else {
				parent.batch.draw(exitButtonInactive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			}
		
		//Setting up the PLAY button

		
		if(Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > PLAY_BUTTON_Y) {
			playButtonActive = playButtons[ran.nextInt(4)];
			parent.batch.draw(playButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			if(Gdx.input.justTouched() ) {
				parent.assistantManager.playSelectSound();
				parent.assistantManager.currentMusic.stop();
				GameStateManager.setState(GameStateManager.OVERWORLD_HUB);
				
			}
			
		
		}
			else {
				parent.batch.draw(playButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			}
		
		//Setting up the SETTINGS button

		
		if(Gdx.input.getX() < x + SETTINGS_BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < SETTINGS_BUTTON_Y + SETTINGS_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > SETTINGS_BUTTON_Y) {
			settingsButtonActive = settingsButtons[ran.nextInt(5)];
			parent.batch.draw(settingsButtonActive, x, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
			if(Gdx.input.justTouched()) {
				selectSound.play();
				GameStateManager.setState(GameStateManager.SETTINGS_STATE);
			}}
			else {
				parent.batch.draw(settingsButtonInactive, x, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
			}
		
		//End Drawing Process
        parent.batch.end();
        if(GameStateManager.getState() != GameStateManager.TITLE_STATE) {
        	AdventureGame.setScreen(parent);
        }
        
	}

	private void loadTextures() {
		parent.assistantManager.queueAddTitleSequence();
		parent.assistantManager.manager.finishLoading();
		parent.assistantManager.queueAddMainMenuAssets();
		parent.assistantManager.manager.finishLoading();
	    parent.assistantManager.queueAddMusic();
	    parent.assistantManager.manager.finishLoading();
	    parent.assistantManager.queueAddSoundsMenu();
	    parent.assistantManager.manager.finishLoading();
	}
	

	
	private void initTitleSequence() {
		String prefix = "GA";
		titleSequenceStart = TS1Atlas.findRegion(prefix+ "Base");
		titleSequenceEnd = TS2Atlas.findRegion(prefix + "Final");
		titleSequenceFrames = new TextureRegion[20];
		titleSequenceFrames[0] = titleSequenceStart;
		titleSequenceFrames[19] = titleSequenceEnd;
		for(int i = 1; i < 19; i++) {
			titleSequenceFrames[i] = (i <= 9) ? TS1Atlas.findRegion(prefix + i): TS2Atlas.findRegion(prefix+i); 
		}
	}
	
	
	private void initAtlases() {
		String pre = "MenuAssets/Atlases/";
		playButtonAtlas = parent.assistantManager.manager.get(pre+ "PlayButtonAtlas/PlayButtonAtlas.atlas", TextureAtlas.class);
		exitButtonAtlas = parent.assistantManager.manager.get(pre + "ExitButtonAtlas/ExitButtonAtlas.atlas", TextureAtlas.class);
		settingsButtonAtlas = parent.assistantManager.manager.get(pre + "SettingsButtonAtlas/SettingsButtonAtlas.atlas", TextureAtlas.class);
		TS1Atlas = parent.assistantManager.manager.get(pre + "TitleSequenceAtlasPt1/TSAP1.atlas");
		TS2Atlas = parent.assistantManager.manager.get(pre + "TitleSequenceAtlasPt2/TSAP2.atlas");
	}

	
	private void initExitButton() {

	    exitButtons = new TextureRegion[5];
	    exitButtonInactive = exitButtonAtlas.findRegion("ExitBase");
	    exitButtons[0] = exitButtonInactive;
	    
	    for(int i = 1; i < 5; i++) {
	    	String exitButtonS = "ExitG" + i;
	    	exitButtons[i] = exitButtonAtlas.findRegion(exitButtonS);
	    }
	    exitButtonActive = exitButtons[1];
	}
	
	private void initSettingsButton() {
		settingsButtons = new TextureRegion[5];
		settingsButtonInactive = settingsButtonAtlas.findRegion("SettingsBase");
		settingsButtons[0] = settingsButtonInactive;
		settingsButtonActive = settingsButtonInactive;
		
		for(int i = 1; i < 5; i++) {
			String settingsButtonS = "SettingsG" + i;
			settingsButtons[i] = settingsButtonAtlas.findRegion(settingsButtonS);
		}

		
	}
	
	private void initPlayButton() {
		playButtons = new TextureRegion[4];
		boopy = new Texture("Boopy.png");
		playButtonInactive = playButtonAtlas.findRegion("PlayBase");
		playButtons[0] = playButtonInactive;
		playButtonActive = playButtonInactive;
		for(int i = 1; i < 4;i++) {
			String playButtonS = "PlayG" + i;
			playButtons[i] = playButtonAtlas.findRegion(playButtonS);
		}
		
		
		
	}
	
	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}
	
	
}