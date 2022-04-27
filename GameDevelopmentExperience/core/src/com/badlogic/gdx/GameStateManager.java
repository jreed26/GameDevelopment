package com.badlogic.gdx;

public class GameStateManager {
	public static final int TITLE_STATE = 0;
	public static final int SETTINGS_STATE = 1;
	public static final int CHARACTER_SELECT = 2;
	public static final int OVERWORLD_HUB = 3;
	public static final int STAGE_ONE = 4;
	public static final int STAGE_TWO = 5;
	public static final int STAGE_THREE = 6;
	
	
	public static int CURRENT_STATE;
	
	
	public static final String HubMapPath = "TileMaps/StageTileMaps/bigmap.tmx";
	public static final String Stage1MapPath = "TileMaps/StageTileMaps/firstroom.tmx";
	public static final String Stage2MapPath = "TileMaps/StageTileMaps/secondroom.tmx";
	public static final String Stage3MapPath = "TileMaps/StageTileMaps/thirdroom.tmx";
	
	public static int getState() {return CURRENT_STATE;}
	public static void setState(int state) {CURRENT_STATE = state;}
	public static String getStageFilePath() {
		switch(CURRENT_STATE) {
		case OVERWORLD_HUB:

			return HubMapPath;
			
		case STAGE_ONE:

			return Stage1MapPath;
			
		case STAGE_TWO:

			return Stage2MapPath;
				
		case STAGE_THREE:
			return Stage3MapPath;
			
		default:

			return HubMapPath;
		}
	}
	
	
}
