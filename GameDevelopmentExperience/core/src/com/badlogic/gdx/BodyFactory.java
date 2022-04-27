package com.badlogic.gdx;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {

	private World world;
	static BodyFactory thisInstance;
	public static final int PLAYER = 0;
	public static final int ENEMY = 3;
	public static final int STATIC = 1;
	public static final int DYNAMIC = 2;
	public static final int BULLET = 3;
	
	private final float DEGTORAD = 0.0174533f;
	
	private BodyFactory(World world) {
		this.world = world;
	}
	
	public static BodyFactory newFactory(World world) {
		return new BodyFactory(world);
	}
	
	public static BodyFactory getInstance(World world) {
		if(thisInstance == null) {
			thisInstance = new BodyFactory(world);
		}
		return thisInstance;
	}
	
	static public FixtureDef makeFixture(int materialType, Shape shape) {
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		
		switch(materialType) {
		case 0:
			fixDef.density = 3f;
			fixDef.friction = 0f;
			fixDef.restitution = 0f;
			break;
		case 1:
			fixDef.density = .25f;
			fixDef.friction = 1f;
			fixDef.restitution = 0.0f; 
			break;
		case 2:
			fixDef.density = .25f;
			fixDef.friction = 1f;
			fixDef.restitution = 1f; 
			break;
		case 3:
			fixDef.density = .25f;
			fixDef.friction = 0f;
			fixDef.restitution = 0f;
			break;
		default:
			fixDef.density = 0f;
			fixDef.friction =1f;
			fixDef.restitution = 0.3f;
		}
		return fixDef;
		
	}


public Body makeCirclePolyBody(float posx, float posy, float radius, int material, BodyType bodyType, boolean fixedRotation){
	// create a definition
	BodyDef boxBodyDef = new BodyDef();
	boxBodyDef.type = bodyType;
	boxBodyDef.position.x = posx;
	boxBodyDef.position.y = posy;
	boxBodyDef.fixedRotation = fixedRotation;
		
	//create the body to attach said definition
	Body boxBody = world.createBody(boxBodyDef);
	CircleShape circleShape = new CircleShape();
	circleShape.setRadius(radius);
	boxBody.createFixture(makeFixture(material,circleShape));
	circleShape.dispose();
	return boxBody;
}
	
public Body makeBoxPolyBody(float posx, float posy, float width, float height,int material, BodyType bodyType, boolean fixedRotation){
	// create a definition
	BodyDef boxBodyDef = new BodyDef();
	boxBodyDef.type = bodyType;
	boxBodyDef.position.x = posx;
	boxBodyDef.position.y = posy;
	boxBodyDef.fixedRotation = fixedRotation;
		
	//create the body to attach said definition
	Body boxBody = world.createBody(boxBodyDef);
	PolygonShape poly = new PolygonShape();
	poly.setAsBox(width, height);
	boxBody.createFixture(makeFixture(material,poly));
	poly.dispose();
 
	return boxBody;
}


public Body makeBoxPolyBodySensor(float posx, float posy, float width, float height,int material, BodyType bodyType, boolean fixedRotation){
	// create a definition
	BodyDef boxBodyDef = new BodyDef();
	boxBodyDef.type = bodyType;
	boxBodyDef.position.x = posx;
	boxBodyDef.position.y = posy;
	boxBodyDef.fixedRotation = fixedRotation;
		
	//create the body to attach said definition
	Body boxBody = world.createBody(boxBodyDef);
	PolygonShape poly = new PolygonShape();
	poly.setAsBox(width, height);
	FixtureDef boxFixture = makeFixture(material,poly);
	boxFixture.isSensor = true;
	boxBody.createFixture(boxFixture);
	
	poly.dispose();
 
	return boxBody;
}


public Body makeBulletBody(float x, float y) {
	// create a definition
	BodyDef boxBodyDef = new BodyDef();
	boxBodyDef.type = BodyType.DynamicBody;
	boxBodyDef.position.x = x;
	boxBodyDef.position.y = y;
	boxBodyDef.fixedRotation = true;
		
	//create the body to attach said definition
	Body boxBody = world.createBody(boxBodyDef);
	
	CircleShape circleShape = new CircleShape();
	circleShape.setRadius(3f);
	FixtureDef bulFixture = makeFixture(BULLET, circleShape);
	bulFixture.isSensor = true;
	boxBody.createFixture(bulFixture);
	circleShape.dispose();
	//boxBody.setBullet(true);
	return boxBody;
}

public Body makePolygonShapeBody(Vector2[] vertices, float posx, float posy, int material, BodyType bodyType){
	BodyDef boxBodyDef = new BodyDef();
	boxBodyDef.type = bodyType;
	boxBodyDef.position.x = posx;
	boxBodyDef.position.y = posy;
	Body boxBody = world.createBody(boxBodyDef);
		
	PolygonShape polygon = new PolygonShape();
	polygon.set(vertices);
	boxBody.createFixture(makeFixture(material,polygon));
	polygon.dispose();
	
	return boxBody;
}


public void makeConeSensor(Body body, float size){ //Useful for enemy sight.
		
	FixtureDef fixtureDef = new FixtureDef();
	fixtureDef.isSensor = true; // will add in future
		
	PolygonShape polygon = new PolygonShape();
		
	float radius = size;
	Vector2[] vertices = new Vector2[5];
	vertices[0] = new Vector2(0,0);
	for (int i = 2; i < 6; i++) {
	    float angle = (float) (i / 6.0 *160 * DEGTORAD); // convert degrees to radians
	    vertices[i-1] = new Vector2( radius * ((float)Math.cos(angle)), radius * ((float)Math.sin(angle)));
	    System.out.println(((float)Math.cos(angle)) + ": " + ((float)Math.sin(angle)));
	}
	polygon.set(vertices);
	fixtureDef.shape = polygon;
	body.createFixture(fixtureDef);
	polygon.dispose();
}
	






public void makeAllFixturesSensors(Body body) {
	for(Fixture fix: body.getFixtureList()) {
		fix.setSensor(true);
	}
}

}
