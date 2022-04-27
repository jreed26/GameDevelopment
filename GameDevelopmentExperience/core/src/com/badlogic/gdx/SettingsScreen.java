package com.badlogic.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.Random;

public class SettingsScreen extends ScreenAdapter {

	AdventureGame game;
	private static Screen thisInstance;
	//Setup button's width height and Y here
	TextureAtlas BackButtonAtlas, settingsAtlas;
    TextureRegion[] BackButtons;
    TextureRegion backButtonInactive;
    TextureRegion backButtonActive;
    TextureRegion volumeText, windowText, volumeSliderBackDrop, volumeSlider;
    TextureRegion[] settings;
    Texture background;
    AdventureGame parent;
	Random ran;
	private static final int BACKGROUND_Y = 900;
	private static final int BACKGROUND_X = 1600;
	private static final int BACK_BUTTON_WIDTH = 487;
	private static final int BACK_BUTTON_HEIGHT = 128;
	private static final int VOLUME_TEXT_HEIGHT = 61;
	private static final int VOLUME_TEXT_WIDTH = 289;
	private static final int WINDOW_TEXT_HEIGHT = 61;
	private static final int WINDOW_TEXT_WIDTH = 289;
	private static final int VOLUME_SLIDER_HEIGHT = 61;
	private static final int VOLUME_SLIDER_WIDTH = 11;
	private static final int VOLUME_BACKDROP_HEIGHT = 61;
	private static final int VOLUME_BACKDROP_WIDTH = 289;
	private static final int BACK_BUTTON_Y = 0;
	
	private static int volumeTextX, volumeTextY, sliderBoxX,sliderBoxY, sliderX,sliderY, windowsTextX, windowsTextY;
	

	

	private SettingsScreen( AdventureGame game) {
		parent = game;
        ran = new Random();
        loadSettingsTextures();
        initSettingsAtlas();
        initSettingsButtons();
        initSettingsScreen();
        initBackButtonAtlas();
        initBackButton();
        background = new Texture("AssortedImages/LargePNGS/BlankMNU.png");
	}
	
	public static Screen getInstance(AdventureGame game) {
		if(thisInstance == null) {
			thisInstance = new SettingsScreen(game);
		}
		return thisInstance;
	}

	
	public void initSettingsAtlas() {
		settingsAtlas = parent.assistantManager.manager.get(parent.assistantManager.SettingsAtlas);
	
		
	}
	
	public void initSettingsButtons() {
		volumeText = settingsAtlas.findRegion("VolumeSettingsButton");
		windowText = settingsAtlas.findRegion("WindowSettingsButton");
		volumeSliderBackDrop = settingsAtlas.findRegion("VolumeSliderBackDrop");
		volumeSlider = settingsAtlas.findRegion("VolumeSlider");
		
	}
	
	public void initSettingsScreen() {
		int startX =  Gdx.graphics.getWidth()/2 , midX = Gdx.graphics.getWidth()*3/4;
		volumeTextX = startX;
		volumeTextY = Gdx.graphics.getHeight() - VOLUME_TEXT_HEIGHT*3;
		sliderBoxX = midX;
		sliderBoxY = Gdx.graphics.getHeight() - VOLUME_TEXT_HEIGHT*3;
		sliderX = (int)(parent.assistantManager.currentMusic.getVolume() * VOLUME_BACKDROP_WIDTH) + sliderBoxX;
		sliderY = sliderBoxY;
		windowsTextX = startX;
		windowsTextY = Gdx.graphics.getHeight() - VOLUME_TEXT_HEIGHT *4;
	}
	
	public void loadSettingsTextures() {

		parent.assistantManager.queueAddSettingsMenuAssets();
        parent.assistantManager.manager.finishLoading();
	}
	
	public void initBackButtonAtlas() {
		BackButtonAtlas = parent.assistantManager.manager.get(parent.assistantManager.BackButtonAsset, TextureAtlas.class);
		
        
	}
	
	public void initBackButton() {
		BackButtons = new TextureRegion[5];
		backButtonInactive = BackButtonAtlas.findRegion("BackBase");
		backButtonActive = backButtonInactive;
		BackButtons[0] = backButtonInactive;
		for(int i = 1; i < 5; i++) {
			BackButtons[i] = BackButtonAtlas.findRegion("BackG" + i);
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.25f, .50f, .75f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        parent.batch.begin();
      
		parent.batch.draw(background,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        
        
        
        //Buttons and other things to be added here for the settings menu
        int x =0;
        
        
        AdventureGame.defaultVolume = ran.nextFloat();
        sliderX = (int)(parent.assistantManager.currentMusic.getVolume() * VOLUME_BACKDROP_WIDTH) + sliderBoxX;
        
        if(Gdx.input.getX() < sliderBoxX && Gdx.input.getX() > sliderBoxX 
        		&& Gdx.input.getY() < sliderBoxY && Gdx.input.getY() > sliderBoxY + volumeSliderBackDrop.getRegionHeight()) {
        	float newVol = 0;
        	if(Gdx.input.getX() > sliderBoxX) {
        		newVol = Gdx.input.getX() - sliderBoxX;
        		newVol /= volumeSliderBackDrop.getRegionWidth();
        		sliderX = Gdx.input.getX();
        		parent.defaultVolume = newVol;
        	}else {
        		newVol =  sliderBoxX - Gdx.input.getX() ;
        		newVol /= volumeSliderBackDrop.getRegionWidth();
        		sliderX = Gdx.input.getX();
        		parent.defaultVolume = newVol;
        	}
        	   parent.batch.draw(volumeSlider, sliderX, sliderY);
        }
        		
        
        
        if(Gdx.input.getX() < x + BACK_BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < BACK_BUTTON_Y + BACK_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > BACK_BUTTON_Y) {
            backButtonActive = BackButtons[ran.nextInt(5)];
        	parent.batch.draw(backButtonActive, x, BACK_BUTTON_Y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
            if(Gdx.input.justTouched()) {
            	TitleScreen.selectSound.play();
                GameStateManager.setState(GameStateManager.TITLE_STATE);
            }}
            else {
                parent.batch.draw(backButtonInactive, x, BACK_BUTTON_Y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
            }
        parent.batch.draw(volumeText, volumeTextX, volumeTextY);
        parent.batch.draw(windowText, windowsTextX, windowsTextY);
        //parent.batch.draw(volumeSlider, sliderX, sliderY);
        //parent.batch.draw(volumeSliderBackDrop, sliderBoxX, sliderBoxY);
      
        parent.batch.end();
        if(GameStateManager.getState() != GameStateManager.SETTINGS_STATE) {
        	AdventureGame.setScreen(parent);
        }

    }

	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	
	/*
	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}
	*/
	
	
}
