import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kassem on 4/5/17.
 */
public class GameAudioPlayer {
    private AudioStream audio;

    public void playAudio(String fileName) throws FileNotFoundException, IOException {
        InputStream in = null;
        in = new FileInputStream("./" + fileName);
        audio = new AudioStream(in);
        sun.audio.AudioPlayer.player.start(audio);
    }

    public void stopAudio(){
        if (audio!=null){
            System.out.println("stop");
            sun.audio.AudioPlayer.player.stop(audio);
        }
    }
}
