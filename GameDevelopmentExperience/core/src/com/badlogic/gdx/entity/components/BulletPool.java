package com.badlogic.gdx.entity.components;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class BulletPool extends Pool<Entity>{

	public BulletPool(int init, int max) {
		super(init, max);
	}
	
	public BulletPool() {
		super();
	}
	
	@Override
	protected Entity newObject() {
		Entity en = new Entity();
	
		
		
		return null;
	}

}
