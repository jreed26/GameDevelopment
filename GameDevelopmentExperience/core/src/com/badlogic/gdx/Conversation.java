package com.badlogic.gdx;

public class Conversation {

	
	String id;
	String dialog = "";
	
	
	public String getDialog() {
		return dialog;
	}
	
	public void setId(String newId) {
		id = newId;
	}

	public String getId() {
		return id;
	}
	
	public void setDialog(String newDialog) {
		dialog = newDialog;
	}

	public Conversation get(String conversationID) {
		return null;
	}

}
