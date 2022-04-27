package com.badlogic.gdx;
//For the Purpose of generating the text files for levels
import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import java.io.IOException;

public class levelGenerator {
	static int numLevels, stage;
	public static File nFile;
	public static FileWriter writer;
	public static Random ran;
	public static void createStageFile(int stage ) {
		
		try {
		nFile = new File("C:\\Users\\reed2\\Desktop\\projfile2\\AdventureGame\\core\\assets\\StageFiles\\Stage:"+ stage + ".txt");
		nFile.createNewFile();
		}catch(IOException e){
			
		}
		writeToStageFile();
	}
	
	public static void writeToStageFile() {
		ran = new Random();
		int stageLength = 1;
		try {
			writer = new FileWriter(nFile);
			while(stageLength < 20) {
			for(int i = 0; i < 3;i ++) {
			int y = (ran.nextInt(3) + 1)*2;
			int x = (ran.nextInt(stageLength) + 1);
			writer.append("" + x + ":" + y + "\n");
			}
			stageLength++;
			}
			writer.close();
		}catch(IOException e) {
			
		}
		
	}
	
}
