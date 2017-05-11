import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.*;
import java.net.URL;

/**
 * Created by kassem on 4/5/17.
 */
public class GameAudioPlayer implements Serializable {

    AudioClip audio;

    public void playLoopAudio(String fileName) throws FileNotFoundException, IOException {
        URL url = GameAudioPlayer.class.getResource(fileName);
        audio = Applet.newAudioClip(url);
        audio.loop();
    }

    public void playAudio(String fileName) throws FileNotFoundException, IOException {
        URL url = GameAudioPlayer.class.getResource(fileName);
        audio = Applet.newAudioClip(url);
        audio.play();
    }

    public void stopAudio() {
        if (audio != null) {
            audio.stop();
        }
    }
}
