package com.badlogic.gdx;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controller.KeyboardController;
import com.badlogic.gdx.entity.components.BodyComponent;
import com.badlogic.gdx.entity.components.CollisionComponent;
import com.badlogic.gdx.entity.components.PlayerComponent;
import com.badlogic.gdx.entity.components.StateComponent;
import com.badlogic.gdx.entity.components.TextureComponent;
import com.badlogic.gdx.entity.components.TransformComponent;
import com.badlogic.gdx.entity.components.TypeComponent;
import com.badlogic.gdx.entity.systems.AnimationSystem;
import com.badlogic.gdx.entity.systems.CollisionSystem;
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
import java.util.ArrayList;
import java.util.Iterator;




public class HubScreen extends ScreenAdapter{
	public static boolean stage1Trig = false, stage2Trig = false, stage3Trig = false;
	static Screen thisInstance;
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
	TiledMapTileLayer boundsLayer, spawnLayer, shopKeepLayer, stageOneTriggerLayer, stageTwoTriggerLayer;
	TiledMapRenderer mapRender;
	private HubScreen(AdventureGame game) {
		this.parent = game;
		boundCoordinates = new ArrayList<Pair>();
		debugRenderer = new Box2DDebugRenderer(false,false,false,false,false,false);
		controller = new KeyboardController();
		batch = new SpriteBatch();
		rSystem = new RenderSystem(batch);
		cam = rSystem.getCam();
		model = new Box2DModel(controller, cam);
		world = model.world;
		world.setGravity(new Vector2(0,0f));
		world.setContactListener(new Box2DContactListener(model));
		bodyFactory = BodyFactory.newFactory(world);
		batch.setProjectionMatrix(cam.combined);
		
		this.engine = new PooledEngine();
		
		engine.addSystem(new AnimationSystem());
        engine.addSystem(rSystem);
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsDebugSystem(world, rSystem.getCam()));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new PlayerControlSystem(controller));
		
       
		createPlayer(801,447);
		hubMap = new TmxMapLoader().load(GameStateManager.getStageFilePath());
		mapRender = new OrthogonalTiledMapRenderer(hubMap);
		
	
	
		stageOneTriggerLayer = (TiledMapTileLayer) hubMap.getLayers().get("StageOneTrigger");
		stageTwoTriggerLayer = (TiledMapTileLayer) hubMap.getLayers().get("StageTwoTrigger");
		boundsLayer = (TiledMapTileLayer)hubMap.getLayers().get("BoundsLayer");
		generateBounds();
		generateStageOneTrigger();
		generateStageTwoTrigger();
		Gdx.input.setInputProcessor(controller);
		
		
	}
	
	public void show() {
		Gdx.input.setInputProcessor(controller);
		
	}
	
	public static Screen getInstance(AdventureGame game) {
		thisInstance = new HubScreen(game);
		return thisInstance;

	}
	
	
	public void generateStageOneTrigger() {
		int offSetX = 8, offSetY = 8;
		for(int i = 1; i < stageOneTriggerLayer.getWidth(); i++) {
			for(int j = 1; j < stageOneTriggerLayer.getHeight(); j++) {
					if(stageOneTriggerLayer.getCell(i, j) != null) {
						Entity bounds = engine.createEntity();
						BodyComponent bC = engine.createComponent(BodyComponent.class);
						CollisionComponent cC = engine.createComponent(CollisionComponent.class);
						TypeComponent tC = engine.createComponent(TypeComponent.class);
						tC.type = TypeComponent.STAGEONETRIGGER;
						bC.body = bodyFactory.makeBoxPolyBodySensor((i) *16+offSetX, (j)*16+offSetY, 8, 8, BodyFactory.STATIC, BodyType.StaticBody, false);
						bC.stage = "stage1";
						bC.body.setUserData(bounds);
						bounds.add(bC);	
						bounds.add(tC); 
						bounds.add(cC);
						engine.addEntity(bounds);
					}
				
				}
			}
			
		
	}
	
	public void generateStageTwoTrigger() {
		int offSetX = 8, offSetY = 8;
		for(int i = 1; i < stageTwoTriggerLayer.getWidth(); i++) {
			for(int j = 1; j < stageTwoTriggerLayer.getHeight(); j++) {
					if(stageTwoTriggerLayer.getCell(i, j) != null) {
						Entity bounds = engine.createEntity();
						BodyComponent bC = engine.createComponent(BodyComponent.class);
						CollisionComponent cC = engine.createComponent(CollisionComponent.class);
						TypeComponent tC = engine.createComponent(TypeComponent.class);
						tC.type = TypeComponent.STAGETWOTRIGGER;
						bC.body = bodyFactory.makeBoxPolyBodySensor((i) *16+offSetX, (j)*16+offSetY, 8, 8, BodyFactory.STATIC, BodyType.StaticBody, false);
						bC.stage = "stage2";
						bC.body.setUserData(bounds);
						bounds.add(bC);	
						bounds.add(tC); 
						bounds.add(cC);
						engine.addEntity(bounds);
					}
				
				}
			}
			
		
	}
	
	
	public void generateBounds() {
		int offSetX = 8, offSetY = 8;
		for(int i = 1; i < boundsLayer.getWidth(); i++) {
			for(int j = 1; j < boundsLayer.getHeight(); j++) {
				if(boundsLayer.getCell(i,j) != null) {
					Entity bounds = engine.createEntity();
					BodyComponent bC = engine.createComponent(BodyComponent.class);
					CollisionComponent cC = engine.createComponent(CollisionComponent.class);
					TypeComponent tC = engine.createComponent(TypeComponent.class);
					bC.body = bodyFactory.makeBoxPolyBody((i) *16+offSetX, (j)*16+offSetY, 8, 8, BodyFactory.STATIC, BodyType.StaticBody, false);
					bounds.add(bC);	
					bounds.add(tC); 
					bounds.add(cC);
					
					engine.addEntity(bounds);
				}
			}

			
		}
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
		
		bComponent.body = bodyFactory.makeCirclePolyBody(x,y, 8,BodyFactory.PLAYER, BodyType.DynamicBody,false);
		
		tComponent.pos.set(x,y,0);
		typeComponent.type = TypeComponent.PLAYER;
		sComponent.set(StateComponent.STATE_HUB);
		bComponent.body.setUserData(ent);
		texComponent.currentRegion = playerTex;
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
        engine.update(delta);
        if(GameStateManager.getState()!= GameStateManager.OVERWORLD_HUB) {
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
