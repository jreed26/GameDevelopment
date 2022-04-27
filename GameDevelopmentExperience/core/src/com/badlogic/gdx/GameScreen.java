package com.badlogic.gdx;
import java.util.Random;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controller.KeyboardController;
import com.badlogic.gdx.entity.components.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.entity.systems.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends ScreenAdapter {

	AdventureGame game;
	private static int numObjects = 25; // # of objecst on screen at any one point.
	public static final int pixCoef = 32;
	private Entity[] entArr;
	private int entTracker = 0;
	static Screen thisInstance;
	OrthographicCamera cam;
	Box2DModel model;
	Box2DDebugRenderer debugRenderer;
	KeyboardController controller;
	SpriteBatch batch;
	TextureAtlas atlas;
	TextureRegion playerTexture;
	PooledEngine engine;
	World world;
	BodyFactory bodyFactory;
	RenderSystem rSystem;
	public Body player;
    float refX = 300;
    float refY = 150;
    float refRadius = 50;
    float x,y,width,height;

    float xSpeed = 4;
    float ySpeed = 3;
	private AdventureGame parent;
	Random ran;
	private GameScreen(AdventureGame game) {
		
		
		System.out.println(RenderSystem.getScreenSizeInMeters());
		System.out.println(RenderSystem.getScreenSizeInPixesl());
		parent = game;
		debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);
		controller = new KeyboardController();
		batch = new SpriteBatch();
		rSystem = new RenderSystem(batch);
		
		cam = rSystem.getCam();
		
		model = new Box2DModel(controller, cam);
		world = model.world;
		world.setContactListener(new Box2DContactListener(model));
		bodyFactory = BodyFactory.getInstance(world);
		batch.setProjectionMatrix(cam.combined);
		
		this.engine = new PooledEngine();
		
		engine.addSystem(new AnimationSystem());
        engine.addSystem(rSystem);
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsDebugSystem(world, rSystem.getCam()));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new PlayerControlSystem(controller));
        
       
        System.out.println(Gdx.graphics.isContinuousRendering());
        System.out.println(Gdx.graphics.toString());
 
		createPlayer();
		cam.position.x = player.getPosition().x ;
		cam.position.y = player.getPosition().y;
		ran = new Random();
		entTracker = 0;
		entArr = new Entity[numObjects];
		
	
		createFloor();}
	
	public static Screen getInstance(AdventureGame game) {
		if(thisInstance == null) {
			thisInstance = new GameScreen(game);
		}
		return thisInstance;
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);
		
	}
	public void levelLoader(String fileName) {
		
	}
	public void clearEntities() {
		engine.removeAllEntities();
	}
	public void createPlayer() {
		TextureAtlas at = new TextureAtlas("dPlayerAssets/HubPlayerAssets/RedDiamondAtlas/RedDiamondHub.atlas");
		TextureRegion playerTex = at.findRegion("TempTexture");
		
		Entity ent = engine.createEntity();
		BodyComponent bComponent = engine.createComponent(BodyComponent.class);
		TransformComponent tComponent = engine.createComponent(TransformComponent.class);
		TextureComponent texComponent = engine.createComponent(TextureComponent.class);
		PlayerComponent pComponent = engine.createComponent(PlayerComponent.class);
		CollisionComponent cComponent = engine.createComponent(CollisionComponent.class);
		TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
		StateComponent sComponent = engine.createComponent(StateComponent.class);
		
		bComponent.body = bodyFactory.makeCirclePolyBody(10, 10,2*pixCoef,BodyFactory.PLAYER, BodyType.DynamicBody,false);
		
		tComponent.pos.set(10,10,0);
		typeComponent.type = TypeComponent.PLAYER;
		sComponent.set(StateComponent.STATE_NORMAL);
		bComponent.body.setUserData(ent);
		texComponent.currentRegion = playerTex;
		this.player = bComponent.body;
		//bodyFactory.makeConeSensor(bComponent.body, 10);
		ent.add(bComponent);
		ent.add(sComponent);
		ent.add(tComponent);
		ent.add(texComponent);
		ent.add(pComponent);
		ent.add(cComponent);
		ent.add(typeComponent);
		
		engine.addEntity(ent);
	}
	
	
	
	private void createPlatform(float x, float y) {
		
		if(entTracker < numObjects) {
		Entity ent = engine.createEntity();
		BodyComponent bComponent = engine.createComponent(BodyComponent.class);
		TypeComponent tComponent = engine.createComponent(TypeComponent.class);
		int pick = ran.nextInt(3);
		switch(pick) {
		case 0:
			
		bComponent.body = bodyFactory.makeBoxPolyBody(x, y, 4*pixCoef, 4*pixCoef, BodyFactory.STATIC, BodyType.StaticBody, true);
		bComponent.thisBodyType = BodyType.StaticBody;
		tComponent.type = TypeComponent.SCENERY;
		break;
		case 1:
		bComponent.body = bodyFactory.makeCirclePolyBody(x, y, (float) ran.nextInt(16) + 1 * pixCoef, BodyFactory.STATIC, BodyType.StaticBody, true);
		bComponent.thisBodyType = BodyType.StaticBody;
		tComponent.type = TypeComponent.SCENERY;
			break;
		case 2:
			bComponent.body = bodyFactory.makeBoxPolyBody(x, y, 4*pixCoef, 1f*pixCoef, bodyFactory.ENEMY, BodyType.KinematicBody, false);
			bComponent.thisBodyType = BodyType.KinematicBody;
			tComponent.type = TypeComponent.BOUNCY;
			break;
		}
		
		
		//TextureComponent texComponent = engine.createComponent(TextureComponent.class);
		
		tComponent.type = TypeComponent.SCENERY;
		bComponent.body.setUserData(ent);
		
		
		ent.add(bComponent);
		ent.add(tComponent);
		for(int i = 0; i < numObjects; i++) {
			if(entArr[i] == null) {
				entArr[i] = ent;
				entTracker++;
				break;
			}
		}
		engine.addEntity(ent);
		
	}
	
	}
	
	public void setCamToPlayer() {
		
	}
	
	public void createFloor() {
		Entity ent = engine.createEntity();
		BodyComponent bComponent = engine.createComponent(BodyComponent.class);
		bComponent.body = bodyFactory.makeBoxPolyBody(0, 0, 10000, 20f, BodyFactory.STATIC,BodyType.StaticBody, false);
		bComponent.thisBodyType = BodyType.StaticBody;
		TypeComponent tComponent = engine.createComponent(TypeComponent.class);
		tComponent.type = TypeComponent.SCENERY;
		bComponent.body.setUserData(ent);
		ent.add(bComponent);
		ent.add(tComponent);
		engine.addEntity(ent);
		
		ent = engine.createEntity();
		bComponent = engine.createComponent(BodyComponent.class);
		bComponent.body = bodyFactory.makeBoxPolyBody(0, 0, 1f, 100, BodyFactory.STATIC,BodyType.StaticBody, false);
		bComponent.thisBodyType = BodyType.StaticBody;
		tComponent = engine.createComponent(TypeComponent.class);
		tComponent.type = TypeComponent.SCENERY;
		bComponent.body.setUserData(ent);
		ent.add(bComponent);
		ent.add(tComponent);
		engine.addEntity(ent);
		
		ent = engine.createEntity();
		bComponent = engine.createComponent(BodyComponent.class);
		bComponent.body = bodyFactory.makeBoxPolyBody(5000, 0, 1f, 100, BodyFactory.STATIC,BodyType.StaticBody, false);
		bComponent.thisBodyType = BodyType.StaticBody;
		tComponent = engine.createComponent(TypeComponent.class);
		tComponent.type = TypeComponent.SCENERY;
		bComponent.body.setUserData(ent);
		ent.add(bComponent);
		ent.add(tComponent);
		engine.addEntity(ent);
		
		
	}
	
	
	
	@Override
	public void render(float delta) {
		model.logicStep(delta);
		
		for(int i = 0; i < numObjects; i++) {

			if(entArr[i] != null && (entArr[i].getComponent(BodyComponent.class).body.getPosition().x < cam.position.x - RenderSystem.FRUSTUM_WIDTH/2)) {
				engine.removeEntity(entArr[i]);
				entArr[i] = null;
				entTracker--;
				System.out.println("Remove Entity: " + i);
				createPlatform(cam.position.x + ran.nextInt((int)RenderSystem.FRUSTUM_WIDTH/2) + RenderSystem.FRUSTUM_WIDTH/2,ran.nextInt((int)RenderSystem.FRUSTUM_WIDTH/2));
				System.out.println("Added Entity: " + i);
				break;
				}
			}

		Gdx.gl.glClearColor(0f, 0f, 0f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		engine.update(delta);
	}
	
	//----------------------------------------------TESTING METHODS ONLY -------------------------------------------------------------
	
	
	public static Entity testCreatePlayer(String playerID) {
	
		PooledEngine testEngine = new PooledEngine();
		Entity ent = testEngine.createEntity();
		BodyComponent bComponent = testEngine.createComponent(BodyComponent.class);
		TransformComponent tComponent = testEngine.createComponent(TransformComponent.class);
		
		PlayerComponent pComponent = testEngine.createComponent(PlayerComponent.class);
		pComponent.setPlayerID(playerID);
		
		CollisionComponent cComponent = testEngine.createComponent(CollisionComponent.class);
		TypeComponent typeComponent = testEngine.createComponent(TypeComponent.class);
		StateComponent sComponent = testEngine.createComponent(StateComponent.class);
		
		bComponent.thisBodyType = BodyType.DynamicBody;
		tComponent.pos.set(10,10,0);
		typeComponent.type = TypeComponent.PLAYER;
		sComponent.set(StateComponent.STATE_NORMAL);
		ent.add(bComponent);
		ent.add(sComponent);
		ent.add(tComponent);
		ent.add(pComponent);
		ent.add(cComponent);
		ent.add(typeComponent);
		
		return ent;
	}
	
	
public static Entity testCreatePlatform( int choice) {
		PooledEngine testEngine = new PooledEngine();
		Random testRan = new Random();
		Entity ent = testEngine.createEntity();
		BodyComponent bComponent = testEngine.createComponent(BodyComponent.class);
		TypeComponent tComponent = testEngine.createComponent(TypeComponent.class);
		switch(choice) {
		case 0:
			bComponent.thisBodyType = BodyType.StaticBody;
			tComponent.type = TypeComponent.SCENERY;
			break;
		case 1: //If circle, radius = height arg
			bComponent.thisBodyType = BodyType.StaticBody;
			tComponent.type = TypeComponent.SCENERY;
			break;
		case 2:
			bComponent.thisBodyType = BodyType.KinematicBody;
			tComponent.type = TypeComponent.BOUNCY;
			break;
		}
	
		ent.add(bComponent);
		ent.add(tComponent);

		return ent;
		
		}
	
}
