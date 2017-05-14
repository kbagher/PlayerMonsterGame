import sun.audio.AudioStream;

import java.io.*;

/**
 * Audio player class.
 * <p>
 * Plays game audio
 */
public class GameAudioPlayer implements Serializable {

    private transient AudioStream audio;
    private transient static byte audioContent[];
    private transient static ByteArrayInputStream in;

    /**
     * Play coin audio in a different thread to avoid
     * blocking the UI
     *
     * @throws IOException
     */
    public static synchronized void playCoinAudio() throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    if (audioContent == null) {
                        File file = new File("./assets/coin.wav");
                        FileInputStream fin = null;
                        fin = new FileInputStream(file);
                        audioContent = new byte[(int) file.length()];
                        fin.read(audioContent);
                        in = new ByteArrayInputStream(audioContent);
                    }
                    in.reset();
                    sun.audio.AudioPlayer.player.start(new AudioStream(in));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * Play audio.
     *
     * @param fileName the file name
     *
     * @throws IOException the io exception
     */
    public void playAudio(String fileName) throws IOException {
        InputStream in;
        in = new FileInputStream("./assets/" + fileName);
        audio = new AudioStream(in);
        sun.audio.AudioPlayer.player.start(audio);
    }

    /**
     * Stop the currently playing audio.
     */
    public void stopAudio() {
        if (audio != null) {
            sun.audio.AudioPlayer.player.stop(audio);
        }
    }
}
