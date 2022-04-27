package com.badlogic.gdx.entity.components;

import java.util.ArrayList;

import com.badlogic.ashley.core.Component;

public class InventoryComponent implements Component{

	

	ArrayList<ItemComponent> items = new ArrayList<ItemComponent>();
	int currency = 0;
	
	
	
	public boolean addItem(ItemComponent item) {
		if(items.size() < 30) {
			items.add(item);
			return true;
		}
		return false;
	}
	
	public int findItem(int itemID) {
	for(int i = 0; i < items.size();i++) {
		if(items.get(i).hashCode() == itemID) {
			return i;
			}
		}
	return -1;
	}
	
	public ItemComponent getItem(int index) {
		return (index == -1) ? null:items.get(index);
	}
	
}
