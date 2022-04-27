package com.badlogic.gdx.assetManager;
import com.badlogic.gdx.GameStateManager;
import com.badlogic.gdx.assetManager.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AgAssetManager {

	public final AssetManager manager = new AssetManager();
	public final String preString = "MenuAssets/Atlases/";
	public final String gameAssets= "GameAssets/GameAtlas.atlas";
	public final String playAsset = preString + "PlayButtonAtlas/PlayButtonAtlas.atlas";
	public final String exitAsset = preString + "ExitButtonAtlas/ExitButtonAtlas.atlas";
	public final String settingsAsset = preString + "SettingsButtonAtlas/SettingsButtonAtlas.atlas";
	public final String BackButtonAsset = preString + "BackButtonAtlas/BackButtonAtlas.atlas";
	public final String TitleSequencePart1 = preString + "TitleSequenceAtlasPt1/TSAP1.atlas";
	public final String TitleSequencePart2 = preString + "TitleSequenceAtlasPt2/TSAP2.atlas";
	public final String SettingsAtlas = "SettingsAssets/SettingsButtonsAtlas/SettingsOptionsAtlas.atlas";
	public final String musicTest = "SoundAssets/GameMusic.mp3";
	public final String stage1Song = "SoundAssets/stage1Song.wav";
	public final String stage2Song = "SoundAssets/stage2Song.wav";
	public final String hubSong = "SoundAssets/hubSong.wav";
	public final String soundTest1 = "SoundAssets/selectSound2.wav";
	public final String soundTest2 = "SoundAssets/laserGun.mp3";
	public final String soundTest3 = "SoundAssets/jump.wav";
	public final String soundTest4 = "SoundAssets/playerDMG.wav";
	public static Music currentMusic;
	public static Sound selectSound;
	public static Sound shooting;
	public static Sound jump;
	public static Sound enemyDeath;
	
	public void initMiscSounds() {
		selectSound =this.manager.get("SoundAssets/selectSound2.wav");
		shooting = this.manager.get("SoundAssets/laserGun.mp3");
		jump = this.manager.get("SoundAssets/jump.wav");
		enemyDeath = this.manager.get("SoundAssets/playerDMG.wav");
	}
	
	public void queueAddImages() {
		manager.load(gameAssets, TextureAtlas.class);
		manager.load(playAsset,TextureAtlas.class);
		manager.load(exitAsset, TextureAtlas.class);
		manager.load(settingsAsset,TextureAtlas.class);
		

	}
	
	public void queueAddSettingsMenuAssets() {
		manager.load(BackButtonAsset, TextureAtlas.class);
		manager.load(SettingsAtlas, TextureAtlas.class);
	}
	
	public void queueAddTitleSequence() {
		manager.load(TitleSequencePart1, TextureAtlas.class);
		manager.load(TitleSequencePart2, TextureAtlas.class);
	}
	
	public void queueAddMainMenuAssets() {
		manager.load(playAsset,TextureAtlas.class);
		manager.load(exitAsset, TextureAtlas.class);
		manager.load(settingsAsset,TextureAtlas.class);
	}
	
	public void queueAddMusic() {
		manager.load(musicTest, Music.class);
		manager.load(stage1Song, Music.class);
		manager.load(stage2Song, Music.class);
		manager.load(hubSong, Music.class);
	}
	
	public void queueAddSoundsMenu() {
		manager.load(soundTest1, Sound.class);
		manager.load(soundTest2, Sound.class);
		manager.load(soundTest3, Sound.class);
		manager.load(soundTest4, Sound.class);
	}
	
	
	
	public void setStageMusic(){
		switch(GameStateManager.getState()) {
		case GameStateManager.TITLE_STATE:
			currentMusic = this.manager.get(musicTest, Music.class);
			currentMusic.setVolume(0.2f);
			currentMusic.setLooping(true);
			currentMusic.play();
			break;
		case GameStateManager.OVERWORLD_HUB:
			currentMusic.stop();

			currentMusic = this.manager.get(hubSong, Music.class);
			currentMusic.setVolume(0.2f);
			currentMusic.setLooping(true);
			currentMusic.play();
			break;
		case GameStateManager.STAGE_ONE:
			currentMusic.stop();

			currentMusic = this.manager.get(stage1Song, Music.class);
			currentMusic.setVolume(0.2f);
			currentMusic.setLooping(true);
			currentMusic.play();
			break;
		case GameStateManager.STAGE_TWO:
			currentMusic.stop();

			currentMusic = this.manager.get(stage2Song, Music.class);
			currentMusic.setVolume(0.2f);
			currentMusic.setLooping(true);
			currentMusic.play();
			break;
		default:
			currentMusic.stop();
		}
	}
	public void playSelectSound() {selectSound.play();}
	public void playJumpSound() {jump.play();}
	public void playGunSound() {shooting.play();}
	public void playDeathSound() {enemyDeath.play();}
}
