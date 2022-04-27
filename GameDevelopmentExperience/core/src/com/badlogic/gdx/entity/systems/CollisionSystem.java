package com.badlogic.gdx.entity.systems;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.entity.components.AnimationComponent;
import com.badlogic.gdx.entity.components.BodyComponent;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.entity.components.CollisionComponent;
import com.badlogic.gdx.entity.components.EnemyComponent;
import com.badlogic.gdx.entity.components.PlayerComponent;
import com.badlogic.gdx.entity.components.StateComponent;
import com.badlogic.gdx.entity.components.TypeComponent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CollisionSystem extends IteratingSystem{

	ComponentMapper<CollisionComponent> collisionMapper;
	ComponentMapper<PlayerComponent> playerMapper;
	ComponentMapper<EnemyComponent> enemyMapper;
	@SuppressWarnings("unchecked")
	public CollisionSystem() {
		super(Family.all(CollisionComponent.class, PlayerComponent.class).get());
		
		collisionMapper = ComponentMapper.getFor(CollisionComponent.class); 
		playerMapper = ComponentMapper.getFor(PlayerComponent.class);
		enemyMapper = ComponentMapper.getFor(EnemyComponent.class);
	}
	
	@Override
	protected void processEntity(Entity ent, float delta) {
		CollisionComponent colC = collisionMapper.get(ent);
		EnemyComponent eC = enemyMapper.get(ent);
		PlayerComponent pC = playerMapper.get(ent);
		Entity colEnt = colC.collisionEntity;
		TypeComponent type = ent.getComponent(TypeComponent.class);

		
		
		if(type.type == TypeComponent.PLAYER || type.type == TypeComponent.BULLET) { //Player Collided With ->
			if(colEnt != null) {
				TypeComponent collidedWithType = colEnt.getComponent(TypeComponent.class);

				if(collidedWithType != null) {
				switch(collidedWithType.type){
				case TypeComponent.ENEMY: // Player Collideed with enemy
					colEnt.getComponent(EnemyComponent.class).damage(50);
					break;
				case TypeComponent.SCENERY: // Player Collided with scenery
						ent.getComponent(StateComponent.class).set(StateComponent.STATE_NORMAL);
					break;
				case TypeComponent.OTHER:
					//Player collided other action
					break;
				case TypeComponent.BOUNCY:
					break;
				case TypeComponent.BULLET: //Player fired bullet, bullet hasnt left player yet
					break;
				default:
					
				}
				//Collision Handled, reset component
				colC.collisionEntity = null;
				}
			}
		}else if(type.type == TypeComponent.ENEMY) {
			if(colEnt != null) {
				TypeComponent collidedWithType = colEnt.getComponent(TypeComponent.class);

				switch(collidedWithType.type) {
				case TypeComponent.PLAYER: // Enemy Hit Player
					break;
				case TypeComponent.ENEMY: //Enemy Hit Enemy
					break;
				case TypeComponent.SCENERY:
					break;
				case TypeComponent.BULLET:
					colEnt.getComponent(BodyComponent.class).isDead = true;
					ent.getComponent(EnemyComponent.class).damage(20);
					break;
				default:
					
				}
				colC.collisionEntity = null;
			}
		}
	}
}
