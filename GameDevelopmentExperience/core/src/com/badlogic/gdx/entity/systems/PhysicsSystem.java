package com.badlogic.gdx.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.entity.components.BodyComponent;
import com.badlogic.gdx.entity.components.TransformComponent;
import com.badlogic.gdx.entity.components.TypeComponent;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


public class PhysicsSystem extends IteratingSystem{
	private static final float MAX_STEP_TIME = 1/45f;
	private static float accumulator = 0f;
	
	private World world;
	private Array<Entity> bodyQueue;
	
	private ComponentMapper<BodyComponent> bodyMapper = ComponentMapper.getFor(BodyComponent.class);
	private ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);
	
	@SuppressWarnings("unchecked")
	public PhysicsSystem(World world) {
		super(Family.all(BodyComponent.class, TransformComponent.class).get());
		this.world = world;
		this.bodyQueue = new Array<Entity>();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		float frameTime = Math.min(delta, 0.25f);
		accumulator += frameTime;
		if(accumulator >= MAX_STEP_TIME) {
			world.step(MAX_STEP_TIME, 6, 2);
			accumulator -= MAX_STEP_TIME;
		}
		
		for(Entity ent: bodyQueue) {
			
			TransformComponent tC = transformMapper.get(ent);
			BodyComponent bC = bodyMapper.get(ent);
			Vector2 pos = bC.body.getPosition();
			
			tC.pos.x = pos.x;
			tC.pos.y = pos.y;
			tC.rotation = bC.body.getAngle() * MathUtils.radiansToDegrees;
			
			
			
			
			
		if(bC.isDead) {
			world.destroyBody(bC.body);
		
			this.getEngine().removeEntity(ent);
		
		
			
			}
		}
		bodyQueue.clear();
		}
	
	
	@Override
	protected void processEntity(Entity ent, float delta) {
		bodyQueue.add(ent);
	}
}

