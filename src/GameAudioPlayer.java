import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import java.io.*;

/**
 * Created by kassem on 4/5/17.
 */
public class GameAudioPlayer implements Serializable {
    private transient AudioStream audio;

    public void playAudio(String fileName) throws FileNotFoundException, IOException {
        InputStream in = null;
        in = new FileInputStream("./" + fileName);
        audio = new AudioStream(in);
        sun.audio.AudioPlayer.player.start(audio);
    }

    public void stopAudio() {
        if (audio != null) {
            System.out.println("stop");
            sun.audio.AudioPlayer.player.stop(audio);
        }
    }
}
