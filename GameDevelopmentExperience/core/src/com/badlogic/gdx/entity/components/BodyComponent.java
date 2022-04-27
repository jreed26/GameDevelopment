package com.badlogic.gdx.entity.components;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class BodyComponent implements Component{
	public Body body;
	public BodyType thisBodyType;
	public String stage = "";
	public boolean isDead = false;
	public String type = "";
	
	public String getBodyType() {
		return type;
	}

}
