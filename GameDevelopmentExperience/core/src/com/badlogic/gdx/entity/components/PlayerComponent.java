package com.badlogic.gdx.entity.components;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class PlayerComponent implements Component{
	private String playerID = "";
	public boolean isDead = false; 
	public boolean hasSpeedBoots = false;
	public void setPlayerID(String ID) {
		this.playerID = ID;}
	public String getPlayerID() {return this.playerID;}
	
	public float playerHealth = 100;
	public float playerStrength = 5;
	public float playerIntellect = 5;
	public float playerSpeed = 5;
	public float shotSpeed = 0.5f; //Shooting delay
	public float timeSinceLastShot = 0f;
	

	

}
