package com.badlogic.gdx.entity.components;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TypeComponent implements Component{
	public static final int PLAYER = 0;
	public static final int ENEMY = 1;
	public static final int SCENERY = 2;
	public static final int OTHER = 3;
	public static final int BOUNCY = 4;
	public static final int STAGEONETRIGGER = 5;
	public static final int STAGETWOTRIGGER = 6;
	public static final int STAGEOVER = 7;
	public static final int BULLET = 8;
	public int type = OTHER;

	
	public String getType() {
		switch(type) {
		case PLAYER:
			return "Player";
		case ENEMY:
			return "Enemy";
		case SCENERY:
			return "Scenery";
		case OTHER:
			return "Other";
		case BULLET:
			return "Bullet";
		default:
			return "Unspecfied";
		}
	}
	
}
