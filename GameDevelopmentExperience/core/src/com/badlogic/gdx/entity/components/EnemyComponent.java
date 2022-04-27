package com.badlogic.gdx.entity.components;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;


public class EnemyComponent implements Component{
	public static final int DIRECTION_LEFT = 0;
	public boolean directionLeft = false;
	public boolean directionUp = false;

	public float leashLength = 100;
	public boolean isDead = false;
	public float startingPosX = 0;
	public float startingPosY = 0;
	
	public float enemyHealth = 100;
	public float enemySpeed = 5;
	public float enemyStrength = 1;
	public float enemyIntellect = 1;
	
	

	
	public void damage(int dam) {
		this.enemyHealth -= dam;
		isDead = (this.enemyHealth <= 0) ? true:false;
	}




}
