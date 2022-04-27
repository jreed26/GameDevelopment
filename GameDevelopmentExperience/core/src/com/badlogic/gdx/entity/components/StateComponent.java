package com.badlogic.gdx.entity.components;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class StateComponent implements Component{
	public static final int STATE_NORMAL = 0;
	public static final int STATE_JUMPING = 1;
	public static final int STATE_FALLING = 2;
	public static final int STATE_MOVING = 3;
	public static final int STATE_HIT = 4;
	public static final int STATE_SLEEPING = 5;
	private int state = STATE_NORMAL;

	public static final int STATE_HUB = 6;
	public float time = 0.0f;
	public boolean looping = false;
	
	public void set(int newState) {
		state = newState;
		time = 0.0f;
	}
	
	public int get() {return state;}

	
}
