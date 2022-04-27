package com.badlogic.gdx.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.entity.components.BodyComponent;
import com.badlogic.gdx.entity.components.BulletComponent;
import com.badlogic.gdx.entity.components.TypeComponent;
import com.badlogic.gdx.math.Vector2;

public class BulletSystem extends IteratingSystem{
	private Entity player;
	private ComponentMapper<BodyComponent> bodyMapper;
	private ComponentMapper<BulletComponent> bulletMapper;
	private static float shotDelay = 500f;
	@SuppressWarnings("unchecked")
	public BulletSystem(Entity Player) {
		super(Family.all(BulletComponent.class).get());
		bodyMapper = ComponentMapper.getFor(BodyComponent.class);
		bulletMapper = ComponentMapper.getFor(BulletComponent.class);
		this.player = Player;
	}
	
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
			
		
		BodyComponent bulletBody = bodyMapper.get(entity);
		BulletComponent bullet = bulletMapper.get(entity);
		Vector2 velocity = new Vector2(bullet.xVelocity, bullet.yVelocity);
		
		bulletBody.body.setLinearVelocity(velocity);
		
		BodyComponent playerBody = player.getComponent(BodyComponent.class);
		float playerX = playerBody.body.getPosition().x;
		float playerY = playerBody.body.getPosition().y;
		
		float bulletX = bulletBody.body.getPosition().x;
		float bulletY = bulletBody.body.getPosition().y;
		
		if(Math.abs(bulletX - playerX) > 300) {
			bulletBody.isDead = true;
		}
		
	}
	}

