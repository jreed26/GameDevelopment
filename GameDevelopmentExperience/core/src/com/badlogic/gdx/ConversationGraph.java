package com.badlogic.gdx;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class ConversationGraph extends ConversationGraphSubject{
	
	private Hashtable<String, Conversation> conversations;
	private Hashtable<String, ArrayList<ConversationChoice>> associatedChoices;
	private String currentConversationID = null;
	
	public ConversationGraph(Hashtable<String, Conversation> conversations, String rootID) {
		setConversations(conversations);
		setCurrentConversation(rootID);
		
	}

	public ConversationGraph() {
	}

	public void setConversations(Hashtable<String, Conversation> conversations) {
		if( conversations.size() < 0) {
			throw new IllegalArgumentException("Can't have a negative number of conversations!");
		}
		
		this.conversations = conversations;
		this.associatedChoices = new Hashtable <String, ArrayList<ConversationChoice>>(conversations.size());
		
			for( Conversation conversation: conversations.values()) {
						associatedChoices.put(conversation.getId(),
								new ArrayList<ConversationChoice>());
			}
			this.conversations = conversations;
	}
	
	public ArrayList<ConversationChoice>getCurrentChoices(){
		return associatedChoices.get(currentConversationID);
		
	}
	
	public String getCurrentConversationID() {
		return this.currentConversationID;
	}
	
	public boolean isValid(String conversationID) {
			if( conversations.get(conversationID) == null)
				return false;
			return true;
	}
	
	public boolean isReachable(String sourceID, String sinkID) {
		if( !isValid(sourceID) || !isValid(sinkID)) 
			return false;
				if(conversations.get(sourceID) == null)
					return false;
				
				ArrayList<ConversationChoice> list = associatedChoices.get(sourceID);
				if( list == null) 
					return false;
				for(ConversationChoice choice: list) {
					if(choice.getSourceId().equalsIgnoreCase(sourceID) &&
							choice.getDestinationId().equalsIgnoreCase(sinkID)) {
						return true;
					}
		}
				return false;
				
				
	}
	
	public Conversation getConversationByID(String id) {
		if( !isValid(id)) {
			System.out.println("ID " + id + " not valid :(");
			return null;
		}
		return conversations.get(id);
	}
	
	public void setCurrentConversation(String id) {
		Conversation conversation = getConversationByID(id);
		if( conversation == null)
			return;
		if(currentConversationID == null || currentConversationID.equalsIgnoreCase(id) || isReachable(currentConversationID, id)) {
			currentConversationID = id;
		}
			else {
				System.out.println("New conversation node[" + id + "] not reachable from current node [" + currentConversationID + "]");
			
			}
		}
	public void addChoice(ConversationChoice conversationChoice) {
		ArrayList<ConversationChoice>
		list = associatedChoices.get(conversationChoice.getSourceId());
			if( list == null)
				return;
			list.add(conversationChoice);
	}
	public String displayCurrentConversation() {
		return conversations.get(currentConversationID).getDialog();
	}
	
	public String toString() {
		StringBuilder outputString = new StringBuilder();
			int numberTotalChoices = 0;
			Set<String> keys =
				associatedChoices.keySet();
				for(String id: keys) {
					outputString.append(String.format("[%s]: ", id));
					
					for(ConversationChoice choice : associatedChoices.get(id)) {
						numberTotalChoices++;
						outputString.append(String.format("%s", choice.getDestinationId()));
					}
					outputString.append(System.getProperty("line.separator"));
				}
				outputString.append(String.format("Number of conversations: %d", 
						conversations.size()));
				
				outputString.append(System.getProperty("line.separator"));
				
					return outputString.toString();
	}
	public String toJson() {
		return null;
		
	}

	public void notify(ConversationGraph graph, Object conversationCommandEvent) {
		
	}
	
	}
	


