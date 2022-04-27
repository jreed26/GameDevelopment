package com.badlogic.gdx;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
public class Player extends Entity{

	private Texture placeHolderTexture, headTexture, bodyTexture, armOneTexture,armTwoTexture,legOneTexture,legTwoTexture;
	private Rectangle[] bodyParts;
	private int numParts;
	private float playerHeight, playerWidth, wingSpan;
	
	public Player(int startX, int startY) {
		super((float)100.0,1,1,1,1);
		numParts = 6;
		bodyParts = new Rectangle[numParts];
		
		/*
		 * While defining the size of the players body part sizes
		 * i am using scientific data concerning proportion of sizes
		 * for instance on average a human is 8 heads tall
		 * and the wingspan is nearly equal to the height.
		 * 
		 * If instead of explicitly defining each individual body part with
		 * unique values, we base them off of core values like head = 1/8 height
		 * if will be much easier to adjust later as we can just adjust those 
		 * core values.
		 */
		playerHeight = 200;
		wingSpan = playerHeight;
		playerWidth = (float).424 * playerHeight;
		
		//Head = 1/8 height, and 1/2 width
		bodyParts[0] = new Rectangle(startX,startY,(playerWidth/2),(playerHeight/8));
		//Remaining height 7/8
		//Torso
		bodyParts[1] = new Rectangle((startX),(startX - playerHeight/8), playerWidth, playerHeight/8 * 3);
		//remaining height 4/8
		//Between arms much also have playerHeight-playerWidth
		bodyParts[2] = new Rectangle(
						(startX - (playerWidth/2)), 
						(startY - playerHeight/8),
						((playerHeight - playerWidth)/2),
						(playerWidth/4));
		
		bodyParts[3] = new Rectangle(
				(startX + (playerWidth/2)), 
				(startY - playerHeight/8),
				((playerHeight - playerWidth)/2),
				(playerWidth/4));
		
		bodyParts[4] = new Rectangle(
				(startX -playerWidth/2),
				(startY - playerHeight/2),
				(playerWidth/2),
				(playerHeight/2));
									
		bodyParts[5] = new Rectangle(
				(startX),
				(startY - playerHeight/2),
				(playerWidth/2),
				(playerHeight/2));
										
	}
	
	
	public Player(Float aHealth, int aStr, int aInt, int aAgi, int aStam) {
		super(aHealth, aStr, aInt, aAgi, aStam);
		
	}

	@Override
	void attack(Entity ToBeAttacked) {
		float damageDone = this.str; // Just Strength For Now, later we can add more influences.
		ToBeAttacked.recieveDamage(damageDone);
		
	}

	@Override
	void block(float Damage) {	float damageToBlock = (Damage-this.stam);}

	void moveParts(float refX, float refY) {
		for(int i = 0; i < bodyParts.length;i++) {
			bodyParts[i].x += refX;
			bodyParts[i].y += refY;
		}
	}
	
	ArrayList<Rectangle> getPartsToDraw() {
		ArrayList<Rectangle> tempList = new ArrayList<Rectangle>();
		for(Rectangle rec: bodyParts) {
			tempList.add(rec);
		}
		return tempList;
	}
	
}
