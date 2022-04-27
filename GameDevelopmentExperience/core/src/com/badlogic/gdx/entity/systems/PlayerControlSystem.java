package com.badlogic.gdx.entity.systems;


import java.util.Random;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.BodyFactory;
import com.badlogic.gdx.GameStateManager;
import com.badlogic.gdx.controller.KeyboardController;
import com.badlogic.gdx.entity.components.BodyComponent;
import com.badlogic.gdx.entity.components.PlayerComponent;
import com.badlogic.gdx.entity.components.StateComponent;
import com.badlogic.gdx.entity.components.TextureComponent;
import com.badlogic.gdx.entity.components.TypeComponent;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
 

public class PlayerControlSystem extends IteratingSystem{
	ComponentMapper<PlayerComponent> playerMapper;
	ComponentMapper<BodyComponent> bodyMapper;
	ComponentMapper<StateComponent> stateMapper;
	ComponentMapper<TypeComponent> typeMapper;
	KeyboardController controller;
	
	Random ran = new Random();
	
	@SuppressWarnings("unchecked")
	public PlayerControlSystem(KeyboardController controller) {
		super(Family.all(PlayerComponent.class).get());
		this.controller = controller;
		playerMapper = ComponentMapper.getFor(PlayerComponent.class);
		bodyMapper = ComponentMapper.getFor(BodyComponent.class);
		stateMapper = ComponentMapper.getFor(StateComponent.class);
		typeMapper = ComponentMapper.getFor(TypeComponent.class);
	}
	
	@Override
	protected void processEntity(Entity ent, float delta) {
		PlayerComponent player = playerMapper.get(ent);
		BodyComponent bodyC = bodyMapper.get(ent);
		StateComponent stateC = stateMapper.get(ent);
		TextureComponent texC = ent.getComponent(TextureComponent.class);
		TypeComponent typeC = typeMapper.get(ent);
		BodyFactory bodyFactory = BodyFactory.getInstance(bodyC.body.getWorld());
		if(GameStateManager.getState() == GameStateManager.OVERWORLD_HUB) {
			//If In Hub, Different Control Schema;
			
			
			if(controller.left){
				bodyC.body.setLinearVelocity(-196,bodyC.body.getLinearVelocity().y);
			
			//	bodyC.body.setLinearVelocity(MathUtils.lerp(bodyC.body.getLinearVelocity().x, -10f, 0.2f),bodyC.body.getLinearVelocity().y);
				
			}
			if(controller.right){
				bodyC.body.setLinearVelocity(196,bodyC.body.getLinearVelocity().y);
				
				//bodyC.body.setLinearVelocity(MathUtils.lerp(bodyC.body.getLinearVelocity().x, 10f, 0.2f),bodyC.body.getLinearVelocity().y);
			}
			
			if(controller.down){
				bodyC.body.setLinearVelocity(bodyC.body.getLinearVelocity().x,-196);
	
				//bodyC.body.setLinearVelocity(bodyC.body.getLinearVelocity().x, MathUtils.lerp(bodyC.body.getLinearVelocity().y, -10f, 0.2f));
			}
			
			if(controller.up){
				bodyC.body.setLinearVelocity(bodyC.body.getLinearVelocity().x,196);
		
				//bodyC.body.setLinearVelocity(bodyC.body.getLinearVelocity().x,MathUtils.lerp(bodyC.body.getLinearVelocity().y, 10f, 0.2f));
				
			}
			if(!controller.up && !controller.down) {
				bodyC.body.setLinearVelocity((float) (bodyC.body.getLinearVelocity().x * 1.5),0);
			}
			if(!controller.left && !controller.right) {
				bodyC.body.setLinearVelocity(0,(float) (bodyC.body.getLinearVelocity().y * 1.5));
			}
			if (controller.none){
				bodyC.body.setLinearVelocity(0,0);
			
				//bodyC.body.setLinearVelocity(MathUtils.lerp(bodyC.body.getLinearVelocity().x, 0f, 1.0f),MathUtils.lerp(bodyC.body.getLinearVelocity().y, -0f, 1.0f));
			}
		}
		
		
		
		
		else if(GameStateManager.getState() != GameStateManager.OVERWORLD_HUB & typeC.type == TypeComponent.PLAYER) {
			
			if((controller.up || controller.space) && 
					(stateC.get() == StateComponent.STATE_NORMAL || stateC.get() == StateComponent.STATE_MOVING)){
				bodyC.body.setLinearVelocity(bodyC.body.getLinearVelocity().x, 250);
				stateC.set(StateComponent.STATE_JUMPING);
			}
			
			if(!bodyC.body.isAwake()) {
				ent.getComponent(StateComponent.class).set(StateComponent.STATE_SLEEPING);
			}
			
			
			if(bodyC.body.getLinearVelocity().y > 0) {
				stateC.set(StateComponent.STATE_FALLING);
				
			}
			else if(bodyC.body.getLinearVelocity().y == 0) {
				if(stateC.get() == StateComponent.STATE_FALLING) {
					stateC.set(StateComponent.STATE_NORMAL);
				}
				if(bodyC.body.getLinearVelocity().x != 0) {
					stateC.set(StateComponent.STATE_MOVING);
				}
			}
	
			if(controller.left){
				
				float speedCoefficient = (controller.hasBoots) ? -450:-75;
				bodyC.body.setLinearVelocity(speedCoefficient  ,			bodyC.body.getLinearVelocity().y);
				player.hasSpeedBoots = controller.hasBoots;
				
			}
			 if(controller.right){
				float speedCoefficient = (controller.hasBoots) ? 450:75;
				bodyC.body.setLinearVelocity(speedCoefficient  ,bodyC.body.getLinearVelocity().y);
				player.hasSpeedBoots = controller.hasBoots;
			}
			
			 if(!controller.left && !controller.right){
			
				bodyC.body.setLinearVelocity(MathUtils.lerp(bodyC.body.getLinearVelocity().x, 0, .2f),bodyC.body.getLinearVelocity().y);
			}
			 
			 if(controller.down) {
					bodyC.body.applyLinearImpulse(0, bodyC.body.getMass() *-250, bodyC.body.getPosition().x,bodyC.body.getPosition().y, true);

			 }
			
		
			
		}
		

		
		
	}
}
