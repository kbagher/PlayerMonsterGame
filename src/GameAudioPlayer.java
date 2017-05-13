import sun.audio.AudioStream;
import java.io.*;

/**
 * Audio player class.
 * <p>
 * Plays game audio
 */
public class GameAudioPlayer implements Serializable {

    private transient AudioStream audio;

    /**
     * Play audio.
     *
     * @param fileName the file name
     * @throws IOException the io exception
     */
    public synchronized void playAudio(String fileName) throws IOException {
        InputStream in;
        in = new FileInputStream("./assets/" + fileName);
        audio = new AudioStream(in);
        sun.audio.AudioPlayer.player.start(audio);
    }

    /**
     * Stop the currently playing audio.
     */
    public void stopAudio() {
        if (audio!=null){
            sun.audio.AudioPlayer.player.stop(audio);
        }
    }
}
