
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.InputStream;


public class Graphics extends Application {
        Stage window;
        String song;
        InputStream in;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)throws Exception {
        Parent openScreen = FXMLLoader.load(getClass().getResource("openScene.fxml"));

        Scene openning = new Scene(openScreen, 600, 500 );
        window = primaryStage;
        window.setTitle("window");
        window.setScene(openning);

        String musicFile = "halo_theme.mp3";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        
        window.show();

    }
}
