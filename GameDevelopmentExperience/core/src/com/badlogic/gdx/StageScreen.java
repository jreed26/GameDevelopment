package com.badlogic.gdx;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.HubScreen.Pair;
import com.badlogic.gdx.controller.KeyboardController;
import com.badlogic.gdx.entity.components.BodyComponent;
import com.badlogic.gdx.entity.components.BulletComponent;
import com.badlogic.gdx.entity.components.BulletPool;
import com.badlogic.gdx.entity.components.CollisionComponent;
import com.badlogic.gdx.entity.components.EnemyComponent;
import com.badlogic.gdx.entity.components.PlayerComponent;
import com.badlogic.gdx.entity.components.StateComponent;
import com.badlogic.gdx.entity.components.TextureComponent;
import com.badlogic.gdx.entity.components.TransformComponent;
import com.badlogic.gdx.entity.components.TypeComponent;
import com.badlogic.gdx.entity.systems.AnimationSystem;
import com.badlogic.gdx.entity.systems.BulletSystem;
import com.badlogic.gdx.entity.systems.CollisionSystem;
import com.badlogic.gdx.entity.systems.EnemySystem;
import com.badlogic.gdx.entity.systems.PhysicsDebugSystem;
import com.badlogic.gdx.entity.systems.PhysicsSystem;
import com.badlogic.gdx.entity.systems.PlayerControlSystem;
import com.badlogic.gdx.entity.systems.RenderSystem;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.bullet.collision.btElement;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;




public class StageScreen extends ScreenAdapter{
	public static boolean stage1Trig = false, stage2Trig = false, stage3Trig = false;
	static Screen thisInstance;
	Random ran = new Random();
	AdventureGame parent;
	ArrayList<Pair> boundCoordinates;
	TextureAtlas atlas;
	TextureRegion playerTexture;
	PooledEngine engine;
	World world;
	BodyFactory bodyFactory;
	OrthographicCamera cam;
	Box2DModel model;
	Box2DDebugRenderer debugRenderer;
	KeyboardController controller;
	SpriteBatch batch;
	RenderSystem rSystem;
	TiledMap hubMap;
	TiledMapTileLayer boundsLayer, spawnLayer, shopKeepLayer, stageLayer, enemyLayer, endLayer;
	TiledMapRenderer mapRender;
	Entity player;
	LevelFactory levelFactory;
	public static int xSpawn, ySpawn;
	private StageScreen(AdventureGame game) {
		this.parent = game;
		this.parent = game;
		boundCoordinates = new ArrayList<Pair>();
		debugRenderer = new Box2DDebugRenderer(false,false,false,false,false,false);
		controller = new KeyboardController();
		batch = new SpriteBatch();
		rSystem = new RenderSystem(batch);
		cam = rSystem.getCam();
		model = new Box2DModel(controller, cam);
		world = model.world;
		world.setContactListener(new Box2DContactListener(model));
		bodyFactory = BodyFactory.newFactory(world);
		batch.setProjectionMatrix(cam.combined);
		
		this.engine = new PooledEngine();
		
		levelFactory = new LevelFactory(engine,world);
		
		engine.addSystem(new AnimationSystem());
        engine.addSystem(rSystem);
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsDebugSystem(world, rSystem.getCam()));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new PlayerControlSystem(controller));
		engine.addSystem(new EnemySystem());
       
		createPlayer(100,100);
		engine.addSystem(new BulletSystem(player));

		hubMap = new TmxMapLoader().load(GameStateManager.getStageFilePath());
		mapRender = new OrthogonalTiledMapRenderer(hubMap);
		
	
		
		boundsLayer = (TiledMapTileLayer)hubMap.getLayers().get("BoundsLayer");
		spawnLayer = (TiledMapTileLayer) hubMap.getLayers().get("Spawn");
		enemyLayer = (TiledMapTileLayer) hubMap.getLayers().get("EnemySpawn");
		endLayer = (TiledMapTileLayer) hubMap.getLayers().get("End");
		generateBounds();
		spawnPlayer();
		spawnEnemies();
		setFinishLine();

		Gdx.input.setInputProcessor(controller);
		
		
	}
	
	public void show() {
		Gdx.input.setInputProcessor(controller);
	}
	
	public static Screen getInstance(AdventureGame game) {
		
		thisInstance = new StageScreen(game);
		return thisInstance;
	}
	
	public void setFinishLine() {

		int offSetX = 8, offSetY = 8;

		for(int i = 0; i < endLayer.getWidth(); i++) {
			for(int j = 0; j < endLayer.getWidth(); j++) {
				if(endLayer.getCell(i, j) != null) {
					Entity bounds = engine.createEntity();
					BodyComponent bC = engine.createComponent(BodyComponent.class);
					CollisionComponent cC = engine.createComponent(CollisionComponent.class);
					TypeComponent tC = engine.createComponent(TypeComponent.class);
					bC.body = bodyFactory.makeBoxPolyBodySensor((i) *16+offSetX, (j)*16+offSetY, 8, 8, BodyFactory.STATIC, BodyType.StaticBody, false);
					tC.type = TypeComponent.STAGEOVER;
					bC.body.setUserData(bounds);
					bC.type = "Finish Line";
					bounds.add(bC);	
					bounds.add(tC); 
					bounds.add(cC);
					
					engine.addEntity(bounds);
				}
				
			}
		}

	}
	
	public void spawnPlayer() {
		int offSetX = 8, offSetY = 8;
		
		for(int i = 0 ; i <spawnLayer.getWidth();i++) {
			for(int j = 0; j < spawnLayer.getHeight();j++) {
				if(spawnLayer.getCell(i, j) != null) {
					int x = (i) *16+offSetX;
					int y = (j)*16+offSetY;
					player.getComponent(BodyComponent.class).body.getPosition().set(x, y);
					//player.getComponent(BodyComponent.class).body.getPosition().y = y;
					return;
				}
			}
		}
	}
	
	
	
	public void generateBounds() {
	
		int offSetX = 8, offSetY = 8;
		for(int i = 0; i < boundsLayer.getWidth(); i++) {
			for(int j = 0; j < boundsLayer.getHeight(); j++) {
				if(boundsLayer.getCell(i,j) != null) {
					Entity bounds = engine.createEntity();
					BodyComponent bC = engine.createComponent(BodyComponent.class);
					CollisionComponent cC = engine.createComponent(CollisionComponent.class);
					TypeComponent tC = engine.createComponent(TypeComponent.class);
					bC.body = bodyFactory.makeBoxPolyBody((i) *16+offSetX, (j)*16+offSetY, 8, 8, BodyFactory.STATIC, BodyType.StaticBody, false);
					bC.type = "Obstacle";
					tC.type = TypeComponent.SCENERY;
					bounds.add(bC);	
					bounds.add(tC); 
					bounds.add(cC);
					
					engine.addEntity(bounds);
				}
			}

			
		}
	}
	
	public void spawnEnemies() {
		int offSetX = 8, offSetY = 8;
		TextureAtlas at = new TextureAtlas("PlayerAssets/HubPlayerAssets/RedDiamondAtlas/RedDiamondHub.atlas");
		TextureRegion playerTex = at.findRegion("TempTexture");
		for(int i = 0 ; i <enemyLayer.getWidth();i++) {
			for(int j = 0; j < enemyLayer.getHeight();j++) {
				if(enemyLayer.getCell(i, j) != null) {
					int x = (i) *16+offSetX;
					int y = (j)*16+offSetY;
					createEnemy(playerTex, x,y);
				}
			}
		}
	}
	
	public void createEnemy(TextureRegion texture, float x, float y) {
		Entity ent = engine.createEntity();
		BodyComponent body = engine.createComponent(BodyComponent.class);
		TransformComponent tx = engine.createComponent(TransformComponent.class);
		TextureComponent tex = engine.createComponent(TextureComponent.class);
		EnemyComponent en = engine.createComponent(EnemyComponent.class);
		TypeComponent type = engine.createComponent(TypeComponent.class);
		CollisionComponent c = engine.createComponent(CollisionComponent.class);
		body.body = bodyFactory.makeCirclePolyBody(x, y, 12, BodyFactory.ENEMY, BodyType.KinematicBody, true);
		tx.pos.set(x,y,0);
		tex.currentRegion = texture;
		en.startingPosX = x;
		type.type = TypeComponent.ENEMY;
		body.body.setUserData(ent);
		body.type = "Enemy";
		ent.add(body);
		ent.add(tx);
		ent.add(tex);
		ent.add(en);
		ent.add(type);
		ent.add(c);
		
		engine.addEntity(ent);

	}
	
	
	public void createPlayer(int x, int y) {
		TextureAtlas at = new TextureAtlas("PlayerAssets/HubPlayerAssets/RedDiamondAtlas/RedDiamondHub.atlas");
		TextureRegion playerTex = at.findRegion("TempTexture");
		
		Entity ent = engine.createEntity();
		BodyComponent bComponent = engine.createComponent(BodyComponent.class);
		TransformComponent tComponent = engine.createComponent(TransformComponent.class);
		TextureComponent texComponent = engine.createComponent(TextureComponent.class);

		PlayerComponent pComponent = engine.createComponent(PlayerComponent.class);
		CollisionComponent cComponent = engine.createComponent(CollisionComponent.class);
		TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
		StateComponent sComponent = engine.createComponent(StateComponent.class);
		
		bComponent.body = bodyFactory.makeCirclePolyBody(x,y, 12,BodyFactory.PLAYER, BodyType.DynamicBody,false);
		
		tComponent.pos.set(x,y,0);
		typeComponent.type = TypeComponent.PLAYER;
		sComponent.set(StateComponent.STATE_HUB);
		bComponent.body.setUserData(ent);
		bComponent.type = "Player";
		texComponent.currentRegion = playerTex;
		//bodyFactory.makeConeSensor(bComponent.body, 10);
		ent.add(bComponent);
		ent.add(sComponent);
		ent.add(tComponent);
		ent.add(texComponent);
		ent.add(pComponent);
		ent.add(cComponent);
		ent.add(typeComponent);
		player = ent;
		engine.addEntity(ent);
	}
	
	public void createBullet(float x, float y, float xVel, float yVel) {
		Entity ent = engine.createEntity();
		BodyComponent bc = engine.createComponent(BodyComponent.class);
		TransformComponent tc = engine.createComponent(TransformComponent.class);
		//TextureComponent texC = engine.createComponent(TextureComponent.class);
		TypeComponent typeC = engine.createComponent(TypeComponent.class);
		CollisionComponent colC = engine.createComponent(CollisionComponent.class);
		BulletComponent bulC = engine.createComponent(BulletComponent.class);
		
		bc.body = bodyFactory.makeBulletBody(x, y);
		
		tc.pos.set(x, y, 0);
		//texC.currentRegion = playerTex;
		typeC.type = TypeComponent.BULLET;
		bc.body.setUserData(ent);
		bc.isDead = false;
		bulC.xVelocity = xVel;
		bulC.yVelocity = yVel;
		
		ent.add(bc);
		ent.add(tc);
		//ent.add(texC);
		ent.add(typeC);
		ent.add(colC);
		ent.add(bulC);
		engine.addEntity(ent);
		System.out.println("BulletFired");

	}
	
	
	public void moveCam() {
		
	}
	
	
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
   
        model.logicStep(delta);
        cam.update();
        mapRender.setView(cam);
        mapRender.render();
        if(controller.fire ) {
        	int speed = player.getComponent(BodyComponent.class).body.getLinearVelocity().x < 0 ? -250:250;
        	createBullet(player.getComponent(BodyComponent.class).body.getPosition().x+5, player.getComponent(BodyComponent.class).body.getPosition().y +5, speed,0);
        	controller.fire = false;
        }
        engine.update(delta);
        
        if(GameStateManager.getState() == GameStateManager.OVERWORLD_HUB) {
        			
		        	AdventureGame.setScreen(parent);
		        }		 
        
	}
	

	
	
	public class Pair{
		public int x, y;
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}

