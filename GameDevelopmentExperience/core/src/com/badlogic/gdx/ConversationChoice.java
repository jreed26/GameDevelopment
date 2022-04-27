package com.badlogic.gdx;

public class ConversationChoice {

	String sourceId;
	String destinationId;
	String choicePhrase;
	String conversationCommandEvent;
	 
	
	
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String newSourceId) {
		sourceId = newSourceId;
	}

	public String getDestinationId() {
		return destinationId;
	}
	public void setDestinationId(String newDestinationId) {
		destinationId = newDestinationId;
	}
	public String getChoicePhrase() {
		return choicePhrase;
		
	}
	public void setChoicePhrase(String newChoicePhrase) {
		choicePhrase = newChoicePhrase;
	}
	public String getConversationCommand() {
		return conversationCommandEvent;
	}
	public <choiceCommand> void setConversationCommandEvent(choiceCommand ConversationCommandEvent) {
		
	}
	public String toString() {
		return null ;
		
	}
	public Object getConversationCommandEvent() {
		return null;
	}
	
	
	
	

}
