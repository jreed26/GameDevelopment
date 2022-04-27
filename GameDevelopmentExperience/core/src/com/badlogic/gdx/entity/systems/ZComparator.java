package com.badlogic.gdx.entity.systems;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.entity.components.TransformComponent;

import java.util.Comparator;

public class ZComparator implements Comparator<Entity>{
	private ComponentMapper<TransformComponent> transformMapper;
	
	public ZComparator() {
		transformMapper = ComponentMapper.getFor(TransformComponent.class);
	}
	
	@Override
	public int compare(Entity entA, Entity entB) {
		float az = transformMapper.get(entA).pos.z;
		float bz = transformMapper.get(entB).pos.z;
		int res = 0;
		
		res = (az > bz) ? 1:-1;
		return res;
	}
	
}
