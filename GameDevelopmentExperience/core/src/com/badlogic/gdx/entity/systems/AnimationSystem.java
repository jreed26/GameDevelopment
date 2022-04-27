package com.badlogic.gdx.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.entity.components.AnimationComponent;

import com.badlogic.gdx.entity.components.StateComponent;
import com.badlogic.gdx.entity.components.TextureComponent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class AnimationSystem extends IteratingSystem{
	ComponentMapper<TextureComponent> textureMapper;
	ComponentMapper<AnimationComponent> animationMapper;
	ComponentMapper<StateComponent> stateMapper;
	
	
	@SuppressWarnings("unchecked")
	public AnimationSystem() {
		super(Family.all(TextureComponent.class, AnimationComponent.class, StateComponent.class).get());
		
		textureMapper = ComponentMapper.getFor(TextureComponent.class);
		animationMapper = ComponentMapper.getFor(AnimationComponent.class);
		stateMapper = ComponentMapper.getFor(StateComponent.class);
	}
	
	@Override
	protected void processEntity(Entity entity, float delta) {
		AnimationComponent aniC = animationMapper.get(entity);
		StateComponent stateC = stateMapper.get(entity);
		
		if(aniC.animations.containsKey(stateC.get())) {
			TextureComponent texC = textureMapper.get(entity);
            //texC.currentRegion = (TextureRegion) aniC.animations.get(stateC.get()).getKeyFrame(stateC.time, stateC.looping);
			
		}
		stateC.time += delta;
	}
}
