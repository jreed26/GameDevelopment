package com.badlogic.gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controller.KeyboardController;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


public class Box2DModel {
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera cam;
	public World world;
	public Body bodyd;
	public Body bodys;
	public Body bodyk;
	public Body player;
	public BodyFactory thisInstance;
	public boolean isSwimming = false, isJumping = false;
	public KeyboardController controller;
	
	public Box2DModel(KeyboardController controller, OrthographicCamera camera) {
		world = new World(new Vector2(0,-10f),true);
		world.setContactListener(new Box2DContactListener(this));
		this.controller = controller;
		this.cam = camera;
	}
	
	
	
	public void logicStep(float delta) {
		
		world.step(delta, 5, 3);
	}
	
	public void createDyanmicObject() {
		//Initializing new Body Definition (Dynamic in this case)
		BodyDef bDef = new BodyDef();
		bDef.type = BodyDef.BodyType.DynamicBody;
		bDef.position.set(0,0);
		
		//Adding it to the world
		bodyd = world.createBody(bDef);
		
		//Setting the sahpe of the body
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1, 1);
		
		//Creating physical properties for the body so it can interact in the world.
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 1;
		
		//Creating the physical object
		bodyd.createFixture(shape, 0.0f);
		
		shape.dispose();
	}
	
	public void createStaticObject() {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyDef.BodyType.StaticBody;
		bDef.position.set(0,-10);
		
		bodys = world.createBody(bDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50,1);
		
		bodys.createFixture(shape,0.0f);
		
		shape.dispose();
	
	}
	
	public void createKinematicObject() {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyDef.BodyType.KinematicBody;
		bDef.position.set(0,-12);
		
		bodyk = world.createBody(bDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1,1);
		
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 1f;
		
		bodys.createFixture(shape,0.0f);
		
		shape.dispose();
		
		bodyk.setLinearVelocity(0, 0.75f);
	}
	
	public boolean pointIntersectsBody(Body body, Vector2 mouseLocation) {
		Vector3 mousePos = new Vector3(mouseLocation, 0);
		cam.unproject(mousePos);
		if(body.getFixtureList().first().testPoint(mousePos.x, mousePos.y)) {
			return true;
		}
		return false;
		
	}
	
	
}
