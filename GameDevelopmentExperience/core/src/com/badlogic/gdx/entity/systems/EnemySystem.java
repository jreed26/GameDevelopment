package com.badlogic.gdx.entity.systems;

import com.badlogic.gdx.TitleScreen;
import com.badlogic.gdx.assetManager.AgAssetManager;
import com.badlogic.gdx.entity.components.BodyComponent;
import com.badlogic.gdx.entity.components.CollisionComponent;
import com.badlogic.gdx.entity.components.EnemyComponent;
import com.badlogic.gdx.entity.components.TextureComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class EnemySystem extends IteratingSystem{

	private ComponentMapper<EnemyComponent> enemyMapper;
	private ComponentMapper<BodyComponent> bodyMapper;
	
	
	@SuppressWarnings("unchecked")
	public EnemySystem() {
		super(Family.all(EnemyComponent.class).get());
		enemyMapper = ComponentMapper.getFor(EnemyComponent.class);
		bodyMapper = ComponentMapper.getFor(BodyComponent.class);
	}
	@Override
	protected void processEntity(Entity ent, float delta) {
		EnemyComponent eC = enemyMapper.get(ent);
		BodyComponent bC = bodyMapper.get(ent);
		if(eC.isDead) {
			bC.isDead = true;
			if(AgAssetManager.enemyDeath != null) {
				AgAssetManager.enemyDeath.play();
			}
			ent.remove(TextureComponent.class);
			return;
		}
		//Distance from original position
		float distanceTraveledX = Math.abs(eC.startingPosX - bC.body.getPosition().x);
		float distanceTraveledY = Math.abs(eC.startingPosY - bC.body.getPosition().y);
		
		if(eC.directionLeft) {
			if(distanceTraveledX > eC.leashLength) {
				eC.directionLeft = !eC.directionLeft;
			}	
		}else {
			if(distanceTraveledX > eC.leashLength) {
				eC.directionLeft = !eC.directionLeft;
			}
		}
		
		if(eC.directionUp) {
			if(distanceTraveledY > eC.leashLength) {
				eC.directionUp = !eC.directionUp; 
			}
		}else {
			if(distanceTraveledY > eC.leashLength) {
				eC.directionUp = !eC.directionUp; 
			}
		}
		
		float speedX = (eC.directionLeft) ? -0.9f:0.9f;
		//float speedY = (eC.directionUp) ? 0.9f:-0.9f;

		bC.body.setTransform(bC.body.getPosition().x + speedX, bC.body.getPosition().y, bC.body.getAngle());
		
		
	}
	
	
}
