package gui;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class BackgroundAudioManager {

	public static double volume = 0.5;
    private static MediaPlayer mediaPlayer;
    private static Media calmMedia;
    private static Media epicMedia;
    public static Stage primStage;
    // Load the audio files
    private static String calmFilePath = new File("sounds/chill1.mp3").toURI().toString();
    private static String epicFilePath = new File("sounds/epic1.mp3").toURI().toString();

    static {
        calmMedia = new Media(calmFilePath);
        epicMedia = new Media(epicFilePath);
    }
    
    public static void updateVolume() {
    	mediaPlayer.setVolume(volume);
    }

    public static void startCalm() {
        if (mediaPlayer != null && mediaPlayer.getMedia() == calmMedia && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            return; // If calm audio is already playing, do nothing
        }
        stop(); // Stop any currently playing audio
        mediaPlayer = new MediaPlayer(calmMedia);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set to loop indefinitely
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();
    }

    public static void startEpic() {
        if (mediaPlayer != null && mediaPlayer.getMedia() == epicMedia && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            return; // If epic audio is already playing, do nothing
        }
        stop(); // Stop any currently playing audio
        mediaPlayer = new MediaPlayer(epicMedia);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set to loop indefinitely
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();
    }

    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }     
}
