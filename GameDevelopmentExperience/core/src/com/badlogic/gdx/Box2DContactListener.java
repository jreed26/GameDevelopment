package com.badlogic.gdx;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.entity.components.*;
import com.badlogic.gdx.math.Vector2;

public class Box2DContactListener implements ContactListener{

	private Box2DModel parent;
	
	public Box2DContactListener(Box2DModel Parent) {
		this.parent = Parent;
	}
	
	public Box2DContactListener() {
		this.parent = null;
	}
	

	
	private void toTheMoon(Fixture fixOne, Fixture fixTwo) {
		System.out.println("Adding Force");
		fixTwo.getBody().applyForceToCenter(new Vector2(0, -10000), true);
	}
	
	@Override
	public void beginContact(Contact contact) {
		//System.out.println("Contact");
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if(fa.getBody().getUserData() instanceof Entity){
			Entity ent = (Entity) fa.getBody().getUserData();
			System.out.println(ent.getComponent(TypeComponent.class).getType());
			
			
			if(fb.getBody().getUserData() instanceof Entity) {
				Entity ent2 = (Entity)fb.getBody().getUserData();
				if(ent2.getComponent(TypeComponent.class).type == TypeComponent.PLAYER) {
			if(ent.getComponent(TypeComponent.class).type == TypeComponent.STAGEONETRIGGER) {
				GameStateManager.setState(GameStateManager.STAGE_ONE);
				return;
			}else if (ent.getComponent(TypeComponent.class).type == TypeComponent.STAGETWOTRIGGER) {
				GameStateManager.setState(GameStateManager.STAGE_TWO);
				return;
			}
			else if(ent.getComponent(TypeComponent.class).type == TypeComponent.STAGEOVER) {
				GameStateManager.setState(GameStateManager.OVERWORLD_HUB);
				fb.getBody().getPosition().x = 800;
				fb.getBody().getPosition().y = 400;
				return;
				}
			}else if(ent.getComponent(TypeComponent.class).type == TypeComponent.ENEMY) {
				
				
				if(ent2.getComponent(TypeComponent.class).type == TypeComponent.BULLET) { //This block of code is responsible for bullets hitting enemies.
					ent2.getComponent(BodyComponent.class).isDead = true;
					ent.getComponent(EnemyComponent.class).damage(30);
					}
				}else if(ent.getComponent(TypeComponent.class).type == TypeComponent.BULLET) {
					ent.getComponent(BodyComponent.class).isDead = true;
				}
			
			
		}
			
			
			
			
			StateComponent a = ent.getComponent(StateComponent.class);
			if(ent.getComponent(TypeComponent.class).type == TypeComponent.PLAYER) {
			ent.getComponent(StateComponent.class).set(StateComponent.STATE_NORMAL);}
			
			entityCollision(ent,fb);
			return;
			
			
			
		}else if(fb.getBody().getUserData() instanceof Entity){
			
			Entity ent = (Entity) fb.getBody().getUserData();
			
			if(fb.getBody().getUserData() instanceof Entity) {
				Entity ent2 = (Entity)fb.getBody().getUserData();
				if(ent2.getComponent(TypeComponent.class).type == TypeComponent.PLAYER) {
			
			if(ent.getComponent(TypeComponent.class).type == TypeComponent.STAGEONETRIGGER) {
				GameStateManager.setState(GameStateManager.STAGE_ONE);
				return;
			}else if (ent.getComponent(TypeComponent.class).type == TypeComponent.STAGETWOTRIGGER) {
				GameStateManager.setState(GameStateManager.STAGE_TWO);
				return;
			}else if(ent.getComponent(TypeComponent.class).type == TypeComponent.STAGEOVER) {
				GameStateManager.setState(GameStateManager.OVERWORLD_HUB);
				fa.getBody().getPosition().x = 800;
				fa.getBody().getPosition().y = 400;
				return;
				}
			}else if(ent.getComponent(TypeComponent.class).type == TypeComponent.BULLET) {
				ent.getComponent(BodyComponent.class).isDead = true;
			}
			}
			
			entityCollision(ent,fa);
			return;
		}
	}

	@Override
	public void endContact(Contact contact) {
	//	System.out.println("Exiting Contact");
		Fixture fixOne = contact.getFixtureA();
		Fixture fixTwo = contact.getFixtureB();
		
	}

	private void entityCollision(Entity ent, Fixture fb) {
		if(fb.getBody().getUserData() instanceof Entity){
			Entity colEnt = (Entity) fb.getBody().getUserData();
			
			CollisionComponent col = ent.getComponent(CollisionComponent.class);
			CollisionComponent colb = colEnt.getComponent(CollisionComponent.class);
			
			
			
			if(col != null){
				col.collisionEntity = colEnt;
			}else if(colb != null){
				colb.collisionEntity = ent;
			}
		}
	}
	
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}