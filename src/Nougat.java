import sun.audio.AudioPlayer;

import java.io.IOException;
import java.io.Serializable;

/**
 * Nougat
 */
public class Nougat implements Serializable {
    /**
     * Nougat consumption status
     */
    private boolean consumed;

    /**
     * Instantiates a new Nougat.
     */
    public Nougat() {
        consumed = false;
    }

    /**
     * Chck if the bougat has been consumed by the user or not
     *
     * @return consume status
     */
    public boolean isConsumed() {
        return consumed;
    }

    /**
     * consume nougat and retrieve it's energy value
     *
     * @return nougat energy
     */
    public int consume() {
        if (!consumed) {
            consumed = true;
            try {
                GameAudioPlayer.playCoinAudio();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return Game.settings.nougatEnergy;
        }
        return 0;
    }
}
