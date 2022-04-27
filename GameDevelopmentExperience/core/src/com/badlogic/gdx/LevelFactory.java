package com.badlogic.gdx;

import com.badlogic.gdx.entity.components.*;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class LevelFactory {

	private BodyFactory bodyFactory;
	public World world;
	
	private PooledEngine engine;
	public int currentLevel = 0;
	
	TiledMap hubMap;
	TiledMapTileLayer boundsLayer, spawnLayer, shopKeepLayer, stageLayer, enemyLayer, endLayer;
	
	public LevelFactory(PooledEngine en,World world) {
		engine = en;
		this.world = world;
		//world = new World(new Vector2(0,-10f), true);
	//	world.setContactListener(new Box2DContactListener());
		bodyFactory = BodyFactory.getInstance(world);
		hubMap = new TmxMapLoader().load(GameStateManager.getStageFilePath());		
	
		//spawnLayer = (TiledMapTileLayer) hubMap.getLayers().get(8);
		//stageLayer = (TiledMapTileLayer) hubMap.getLayers().get("StageTrigger");
		
	}
	
	
	
	public void setFinishLine() {

		int offSetX = 8, offSetY = 8;
		endLayer = (TiledMapTileLayer) hubMap.getLayers().get("End");

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
		spawnLayer = (TiledMapTileLayer) hubMap.getLayers().get("Spawn");

		for(int i = 0 ; i <spawnLayer.getWidth();i++) {
			for(int j = 0; j < spawnLayer.getHeight();j++) {
				if(spawnLayer.getCell(i, j) != null) {
					int x = (i) *16+offSetX;
					int y = (j)*16+offSetY;
					createPlayer(x,y);
					return;
				}
			}
		}
	}
	
	
	
	public void generateBounds() {
		boundsLayer = (TiledMapTileLayer)hubMap.getLayers().get("BoundsLayer");

		int offSetX = 8, offSetY = 8;
		for(int i = 0; i < boundsLayer.getWidth(); i++) {
			for(int j = 0; j < boundsLayer.getHeight(); j++) {
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
	
	public void spawnEnemies() {
		enemyLayer = (TiledMapTileLayer) hubMap.getLayers().get("EnemySpawn");

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
	
	
	
	public Entity createBullet(float x, float y, float xVel, float yVel) {
		Entity ent = engine.createEntity();
		BodyComponent bc = engine.createComponent(BodyComponent.class);
		TransformComponent tc = engine.createComponent(TransformComponent.class);
		TextureComponent texC = engine.createComponent(TextureComponent.class);
		TypeComponent typeC = engine.createComponent(TypeComponent.class);
		CollisionComponent colC = engine.createComponent(CollisionComponent.class);
		BulletComponent bulC = engine.createComponent(BulletComponent.class);
		
		bc.body = bodyFactory.makeBulletBody(x, y);
		bodyFactory.makeAllFixturesSensors(bc.body);
		tc.pos.set(x, y, 0);
		//texC.currentRegion = Add Bullet Texture
		typeC.type = TypeComponent.BULLET;
		bc.body.setUserData(ent);
		bulC.xVelocity = xVel;
		bulC.yVelocity = yVel;
		
		ent.add(bc);
		ent.add(tc);
		ent.add(texC);
		ent.add(typeC);
		ent.add(colC);
		ent.add(bulC);
		
		System.out.println("BulletFired");
		engine.addEntity(ent);
		
		return ent;
		
	}
	

	public Entity createPlayer(int x, int y) {
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
		
		return ent;
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
		
		ent.add(body);
		ent.add(tx);
		ent.add(tex);
		ent.add(en);
		ent.add(type);
		ent.add(c);
		
		engine.addEntity(ent);

	}
	
	
}
