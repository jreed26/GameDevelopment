package com.badlogic.gdx;


import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;

import javax.swing.GroupLayout.Alignment;



public class ConversationUI extends Window{

	private static final String TAG = ConversationUI.class.getName();
	private static Skin uiSkin;
	private Label dialogText;
	private List<ConversationChoice> listItems;
	private ConversationGraph graph;
	private String currentEntityID;
	private TextButton closeButton;
	private Json json;
	
	public ConversationUI(){
		
		
		super("dialog", uiSkin, "solidbackground");
		
		Skin uiSkin = new Skin();
		
		json = new Json();
		graph = new ConversationGraph();
		
		dialogText = new Label("No conversation", uiSkin);
		dialogText.setWrap(true);
		dialogText.setAlignment(getColumns()/2, getRows()/2);
		closeButton = new TextButton("X", uiSkin);
		
		listItems = new List<ConversationChoice>(uiSkin);
		
		//pass list items of conversationchoice items to scrollpane to be displayed to the player
		ScrollPane scrollPane = new ScrollPane(listItems, uiSkin, "inventoryPane");
			scrollPane.setOverscroll(false, false);
			scrollPane.setFadeScrollBars(false);
			scrollPane.setScrollingDisabled(true, false);
			scrollPane.setForceScroll(true, false);
			scrollPane.setScrollBarPositions(false, true);
			
			this.add();
			this.add(closeButton);
			this.row();
			this.defaults().expand().fill();
			this.add(dialogText).pad(10,10,10,10);
			this.row();
			this.add(scrollPane).pad(10,10,10,10);
			this.pack();
			
			listItems.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					ConversationChoice choice = (ConversationChoice) listItems.getSelected();
						if(choice == null)
							return;
						graph.notify(graph, choice.getConversationCommandEvent());
						populateConversationDialog(choice.getDestinationId());
				}
				
			}
			);
	}
	public TextButton getCloseButton() {
		return closeButton;
	}
	
	public String getCurrentEntityID(){
		return currentEntityID;
	}
	
	public void loadConversation(EntityConfig entityConfig) {
		String fullFilenamePath = entityConfig.getConversationConfigPath();
			this.setTitle(" ");
			clearDialog();
				if(fullFilenamePath.isEmpty() || !Gdx.files.internal(fullFilenamePath).exists()) {
						Gdx.app.debug(TAG, "Conversation file doesn't exist :(");
						return;
				}
				
		currentEntityID = entityConfig.getEntityID();
		this.setTitle(entityConfig.getEntityID());
		//need to make Json - dont have it yet 
			ConversationGraph graph = json.fromJson(ConversationGraph.class, Gdx.files.internal(fullFilenamePath));
				setConversationGraph(graph);
	}
	private void setTitle(String string) {
		
	}
	public void setConversationGraph(ConversationGraph graph) {
		if(graph != null)
			graph.removeAllObservers();
		this.graph = graph;
		
		populateConversationDialog(graph.getCurrentConversationID());
	}
	
	public ConversationGraph getCurrentConversation() {
		return this.graph;
	}
	
	private void populateConversationDialog(String conversationID) {
		clearDialog();
		
		Conversation conversation = graph.getConversationByID(conversationID);
			if(conversation == null)
				return;
			graph.setCurrentConversation(conversationID);
			dialogText.setText(conversation.getDialog());
			ArrayList<ConversationChoice> choices = graph.getCurrentChoices();
			if(choices == null)
				return;
			
			listItems.setItems((ConversationChoice[]) choices.toArray());
			listItems.setSelectedIndex(-1);
	}
	
	private void clearDialog() {
		dialogText.setText(" ");
		listItems.clearItems();
	}
}
