package com.badlogic.gdx.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.TitleScreen;
import com.badlogic.gdx.assetManager.AgAssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;


public class KeyboardController implements InputProcessor{

	public boolean left,right,up,down,shift, none, space;
	public boolean fire;
	public boolean mouse1Down,mouse2Down,mouse3Down;
	public boolean hasBoots = false;
	public boolean mouseDragged;
	public Vector2 mouseCoords = new Vector2();
	
	@Override
	public boolean keyDown(int keycode) {
		boolean keyProcessed = false;
		
		
		if(keycode == Keys.NUM_1) {
			hasBoots = !hasBoots;
			keyProcessed = true;
		}
		
		if(keycode == Keys.F) {
			fire = true;
			if(AgAssetManager.shooting != null) {
				AgAssetManager.shooting.play();
			}
			System.out.println("F Pressed");
			keyProcessed = true;
		}
		
		if(keycode == Keys.SHIFT_LEFT) {
			shift = true;
			keyProcessed = true;
		}
		
		if(keycode == Keys.SPACE) {
			space = true;
			if(AgAssetManager.jump != null) {
				AgAssetManager.jump.play();
			}
			keyProcessed = true;
		}
		
		switch(keycode) {
		case Keys.LEFT: case Keys.A:
			left = true;
			none = false;
			keyProcessed = true;
			break;
		case Keys.RIGHT: case Keys.D:
			right = true;
			none = false;
			keyProcessed = true;
			break;
		case Keys.UP: case Keys.W:
			up = true;
			none = false;
			keyProcessed = true;
			break;
		case Keys.DOWN: case Keys.S:
			down = true;
			none = false;
			keyProcessed = true;
			break;
		case Keys.SPACE:
			space = true;
			keyProcessed = true;
			break;
		case Keys.F:
			fire = true;
			keyProcessed = true;
		default:
			none = true;
			keyProcessed = true;
		}
		return keyProcessed;
	}

	@Override
	public boolean keyUp(int keycode) {
		boolean keyProcessed = false;
		
		if(keycode == Keys.F) {
			fire = false;
			System.out.println("F Released");
			keyProcessed = true;
		}
		
		if(keycode == Keys.SHIFT_LEFT) {
			shift = false;
			none = false;
			keyProcessed = true;
		}
		
		if(keycode == Keys.SPACE) {
			space = false;
			keyProcessed = true;
		}
		
		switch(keycode) {
		case Keys.LEFT: case Keys.A:
			left = false;
			keyProcessed = true;
			break;
		case Keys.RIGHT: case Keys.D:
			right = false;
			keyProcessed = true;
			break;
		case Keys.UP: case Keys.W:
			up = false;
			keyProcessed = true;
			break;
		case Keys.DOWN: case Keys.S:
			down = false;
			keyProcessed = true;
			break;
		case Keys.SPACE:
			space = false;
			keyProcessed = true;
			break;
		case Keys.F:
			fire = false;
			keyProcessed = true;
		default:
			none = true;
			keyProcessed = true;
		}
		none = (!left && !right && !up && !down);
		return keyProcessed;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(button == 0) {
			mouse1Down = true;
		}else if (button == 1) {
			mouse2Down = true;
		}else if (button == 2) {
			mouse3Down = true;
		}
		mouseCoords.x = screenX;
		mouseCoords.y = screenY;
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		mouseDragged = false;
		if(button == 0) {
			mouse1Down = false;
		}else if (button == 1) {
			mouse2Down = false;
		}else if (button == 2) {
			mouse3Down = false;
		}
		mouseCoords.x = screenX;
		mouseCoords.y = screenY;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		mouseDragged = true;
		mouseCoords.x = screenX;
		mouseCoords.y = screenY;
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouseCoords.x = screenX;
		mouseCoords.y = screenY;
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}
	


}
