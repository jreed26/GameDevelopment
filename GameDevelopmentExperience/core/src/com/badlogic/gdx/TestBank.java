package com.badlogic.gdx;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Entity.*;
import com.badlogic.gdx.entity.components.*;
import com.badlogic.gdx.entity.systems.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.GameScreen;

public class TestBank {
	public StateComponent state = new StateComponent();
	public BodyType bType = null;
	public String playerID = "";
	public int type = 0;
	
	
	
	public void initTestOne() {
		state.set(StateComponent.STATE_NORMAL);
		bType = BodyType.DynamicBody;
		playerID = "ThisISaPlayerID";
		type = TypeComponent.PLAYER;
	}

	@Test
	public void testOne() {
		initTestOne();
		Entity playerEnt = GameScreen.testCreatePlayer(playerID);
		assertEquals(playerEnt.getComponent(StateComponent.class).get(), state.get());
		assertEquals(playerEnt.getComponent(BodyComponent.class).thisBodyType, bType);
		assertEquals(playerEnt.getComponent(PlayerComponent.class).getPlayerID(), playerID);
		assertEquals(playerEnt.getComponent(TypeComponent.class).type, type);
	}
	
	public void initTestTwo() {
		state = null;
		bType = BodyType.StaticBody;
		type = TypeComponent.SCENERY;
	}
	
	
	@Test
	public void testTwo() {
		initTestTwo();
		Entity squarePlatformEnt = GameScreen.testCreatePlatform(0);
		
		assertEquals(squarePlatformEnt.getComponent(BodyComponent.class).thisBodyType, bType);
		assertEquals(squarePlatformEnt.getComponent(TypeComponent.class).type, type);
	}
	

	public void initTestThree() {
		state = null;
		bType = BodyType.StaticBody;
		type = TypeComponent.SCENERY;
	}
	@Test
	public void testThree() {
		initTestThree();
		Entity circlePlatformEnt = GameScreen.testCreatePlatform(1);
		
		assertEquals(circlePlatformEnt.getComponent(BodyComponent.class).thisBodyType, bType);
		assertEquals(circlePlatformEnt.getComponent(TypeComponent.class).type, type);
	}
	
	public void initTestFour() {
		state = null;
		bType = BodyType.KinematicBody;
		type = TypeComponent.BOUNCY;
	}
	@Test
	public void testFour() {
		initTestFour();
		Entity bouncyPlatformEnt = GameScreen.testCreatePlatform(2);
		
		assertEquals(bouncyPlatformEnt.getComponent(BodyComponent.class).thisBodyType, bType);
		assertEquals(bouncyPlatformEnt.getComponent(TypeComponent.class).type, type);
	}
	
	
	
}
