package com.badlogic.gdx.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class ItemComponent implements Component{
	public int price;
	public float strScaling;
	public float intScaling;
	public float speScaling;
	public float hpScaling;
	TextureRegion texture;
	Body body;
	
	
}
