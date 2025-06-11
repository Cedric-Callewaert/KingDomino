package gui;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ButtonFX1 extends Button {

    @Override
    public void fire() {
    	MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("sounds/select.mp3").toURI().toString()));
    	mediaPlayer.setVolume(BackgroundAudioManager.volume);
        mediaPlayer.play();
    	
        super.fire();
    }
}
