package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.layout.Background;
import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

public class Level4State extends Level3State{

	private static final long serialVersionUID = 1L;

	public Level4State(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic, InputHandler inputHandler,
			GraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
	}
	protected boolean moveRight = true;
	
	@Override
	public void doGettingReady() {
		setCurrentState(GETTING_READY);
		getGameLogic().drawGetReady();
		repaint();
		LevelLogic.delay(2000);
		//Changes music from "menu music" to "ingame music"
		MegaManMain.audioClip.close();
//		MegaManMain.audioFile = new File("audio/mainGame.wav");
		MegaManMain.audioFile = new File("audio/song4.wav");
//		System.out.println(SoundManager.SOUND_ON); //Debug mute on Level 1
		try {
			MegaManMain.audioStream = AudioSystem.getAudioInputStream(MegaManMain.audioFile);
			MegaManMain.audioClip.open(MegaManMain.audioStream);
			if (SoundManager.SOUND_ON) {
				MegaManMain.audioClip.start();
				MegaManMain.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	};
	
	@Override
	public void doStart() {
		super.doStart();
		newPlatforms(getNumPlatforms());
	}
	
	@Override
	public void updateScreen(){
		super.updateScreen();
		drawPlatforms();
	}
	
	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(0,0);
			if (i < 5) platforms[i].setLocation(20+ i*50, SCREEN_HEIGHT/2 + 140 - i*40);
			if (i == 5) platforms[i].setLocation(100+ i*50, SCREEN_HEIGHT/2 + 140 - i*30);
			if (i > 5) platforms[i].setLocation(100+ i*50, SCREEN_HEIGHT/2 + 140 - i*30)  ;
		}
		return platforms;
	}
	
	@Override
	protected void drawPlatforms() {
		//draw platforms
//		numPlatforms = 8;
		Graphics2D g2d = getGraphics2D();
		for(int i=0; i<getNumPlatforms(); i++){
			getGraphicsManager().drawPlatform(platforms[i], g2d, this, i);
		}
		// If platform #5 (5-1=4), then move it
			if (platforms[4].getX() == 0) {
				moveRight = true;
			}
			else if (platforms[4].getX() == SCREEN_HEIGHT) {
				moveRight = false; //move left
			}
			
			if (moveRight) { //move right
				platforms[4].translate(1, 0);
			}
			else { //move left
				platforms[4].translate(-1, 0);
			}		
			
	}
	//Added Background Image
	protected Background greenbakcgroundImg;
	protected void clearScreen() {
		// clear screen
		Graphics2D g2d = getGraphics2D();
		((GraphicsManager)getGraphicsManager()).drawgreenBackground(greenbakcgroundImg, g2d, this);
	}
	
	@Override
	public boolean isLevelWon() {
		return levelAsteroidsDestroyed >= 9;
	}
		
}
