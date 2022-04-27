package com.badlogic.gdx.entity.systems;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
 



public class PhysicsDebugSystem extends IteratingSystem{
	
	private Box2DDebugRenderer debugR;
	private World world;
	private OrthographicCamera cam;
	
	public PhysicsDebugSystem(World world, OrthographicCamera cam) {
		super(Family.all().get());
		debugR = new Box2DDebugRenderer(true,false,false,false,false,false);
		
		this.world = world;
		this.cam = cam;
	}
	
	@Override 
	public void update(float delta) {
		super.update(delta);
		debugR.render(world, cam.combined);
	}
	
	@Override
	protected void processEntity(Entity ent, float delta) {
		
	}
}
