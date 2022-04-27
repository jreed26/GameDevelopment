package com.badlogic.gdx.entity.systems;

 
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.AdventureGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controller.KeyboardController;
import com.badlogic.gdx.entity.components.BodyComponent;
import com.badlogic.gdx.entity.components.PlayerComponent;
import com.badlogic.gdx.entity.components.StateComponent;
import com.badlogic.gdx.entity.components.TextureComponent;
import com.badlogic.gdx.entity.components.TransformComponent;
import com.badlogic.gdx.entity.components.TypeComponent;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
 
import java.util.Comparator;

import org.w3c.dom.Text;

public class RenderSystem extends SortedIteratingSystem{

	public static float PPM = 2.0f;
	public static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth()/PPM;
	public static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight()/PPM;

	
    public static final float PIXELS_TO_METERS = 1.0f / PPM;
	
	private static Vector2 meterDimensions = new Vector2();
	private static Vector2 pixelDimensions = new Vector2();
	
	 public static Vector2 getScreenSizeInMeters(){
	        meterDimensions.set(Gdx.graphics.getWidth()*PIXELS_TO_METERS,
	                            Gdx.graphics.getHeight()*PIXELS_TO_METERS);
	        return meterDimensions;
	    }
	 
	 public static Vector2 getScreenSizeInPixesl(){
	        pixelDimensions.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        return pixelDimensions;
	    }
	 
	 public static float PixelsToMeters(float pixelValue){
	        return pixelValue * PIXELS_TO_METERS;
	    }
	 
	 private SpriteBatch batch;
	 private Array<Entity> renderQueue;
	 private Comparator<Entity> comparator;
	 private OrthographicCamera cam;
	 
	 private ComponentMapper<TextureComponent> textureMapper;
	 private ComponentMapper<TransformComponent> transformMapper;
	 private ComponentMapper<TypeComponent> typeMapper;
	 private ComponentMapper<BodyComponent> bodyMapper;
	 
	 @SuppressWarnings("unchecked")
	 public RenderSystem(SpriteBatch batch) {
		 super(Family.all(TransformComponent.class, TextureComponent.class).get(), new ZComparator());
	
	 textureMapper = ComponentMapper.getFor(TextureComponent.class);
	 transformMapper = ComponentMapper.getFor(TransformComponent.class);
	 typeMapper = ComponentMapper.getFor(TypeComponent.class);
	 bodyMapper = ComponentMapper.getFor(BodyComponent.class);
	 renderQueue = new Array<Entity>();
	
	 this.batch = batch;
	 
	 cam = new OrthographicCamera(FRUSTUM_WIDTH,FRUSTUM_HEIGHT);
	 cam.position.set(FRUSTUM_WIDTH/2f, FRUSTUM_HEIGHT/2f, 0);
	 }
	 
	 @Override
	 public void update(float delta) {
		 super.update(delta);
		 BodyComponent bC = null;
		 StateComponent sC = null;
		 
		// renderQueue.sort(comparator);
		 
		 cam.update();
		 batch.setProjectionMatrix(cam.combined);
		 batch.enableBlending();
		 batch.begin();
		 
		 for(Entity ent: renderQueue) {
			 TextureComponent tex = textureMapper.get(ent);
			 TransformComponent tx = transformMapper.get(ent);
			 TypeComponent type = typeMapper.get(ent);
			 BodyComponent body = bodyMapper.get(ent);
			 if( bC == null && type.type == TypeComponent.PLAYER) {
				 bC = body;
				 sC = ent.getComponent(StateComponent.class);
			 }
			 
			 if(tex.currentRegion == null || tx.hidden) {
				 continue;
			 }
			 
			 float width = tex.currentRegion.getRegionWidth()/4;
			 float height = tex.currentRegion.getRegionHeight()/4;
			 
			 float originX = width/2f;
			 float originY = height/2f;
			 
			 batch.draw(tex.currentRegion, (tx.pos.x - originX), (tx.pos.y - originY),
					 originX,originY, width,height,
					 PixelsToMeters(tx.scale.x), PixelsToMeters(tx.scale.y), tx.rotation);
			 if(type.type == TypeComponent.PLAYER) {
				 PlayerComponent player = ent.getComponent(PlayerComponent.class);
				 
				 if(player.hasSpeedBoots) {
					 int x = body.body.getLinearVelocity().x < 0 ? -1:1;
					 batch.draw(tex.currentRegion, (tx.pos.x - originX - (x * 3)), (tx.pos.y - originY + 5),
							 originX,originY, width /2,height/2,
							 PixelsToMeters(tx.scale.x), PixelsToMeters(tx.scale.y), tx.rotation);
					 batch.draw(tex.currentRegion, (tx.pos.x - originX - (x * 5)), (tx.pos.y - originY +10),
							 originX,originY, width/4,height/4,
							 PixelsToMeters(tx.scale.x), PixelsToMeters(tx.scale.y), tx.rotation);
			
				 }
					 
				 
				 
			 }
			
		 }
		 
		 
		 cam.position.x += (bC.body.getPosition().x + (delta) - cam.position.x) *PIXELS_TO_METERS;
		
			 cam.position.y += (bC.body.getPosition().y + (delta) - cam.position.y) *PIXELS_TO_METERS;
		 
		 cam.update();
		 
		 batch.end();
		 renderQueue.clear();
	 }
	 
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		renderQueue.add(entity);
	}

	public OrthographicCamera getCam() {return cam;}
	
}
