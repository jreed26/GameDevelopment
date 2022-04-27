package com.badlogic.gdx;

public abstract class Entity {

	float Health;
	int str,intellect,agility,stam;
	
	public Entity(Float aHealth, int aStr, int aInt, int aAgi, int aStam) {
		setHealth(aHealth);
		setStrength(aStr);
		setIntellect(aInt);
		setAgility(aAgi);
		setStamina(aStam);
	}
	
	abstract void attack(Entity toBeAttacked);
	abstract void block(float Damage);
	
	public boolean dead() {return Health <= 0;}
	
	public void recieveDamage(float damageToRecieve) {this.Health -= damageToRecieve;}
	
	
	public float getHealth() {return Health;}
	public int getStrength() {return str;}
	public int getIntellect() {return intellect;}
	public int getAgility() {return agility;}
	public int getStamina() {return stam;}
	
	
	public void setHealth(float health) {Health = health;}
	public void setStrength(int str) {this.str = str;}
	public void setIntellect(int intellect) {this.intellect = intellect;}
	public void setAgility(int agility) {this.agility = agility;}
	public void setStamina(int stam) {this.stam = stam;}
	
	
	
}
