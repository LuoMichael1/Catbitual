// Use to play music and sound effects
// used this website for help with the music
// https://www.geeksforgeeks.org/play-audio-file-using-java/
// unfortunatly it is limited to wav files for the most part

import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    private Clip clip;

    // play a sound and you can specifiy how many times it repeats. Setting how many
    // times it loops to -1 will make it loop forever
    public Sound(String filePath, int loop) {
        try {
            clip = AudioSystem.getClip();
            InputStream is = Sound.class.getResourceAsStream(filePath);
            if (is == null) {
                throw new RuntimeException("Sound not found: " + filePath);
            }
            clip.open(AudioSystem.getAudioInputStream(is));
            if (loop == -1) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				clip.loop(loop);
			}

        } catch (Exception e) {
            System.out.println("Something went wrong with starting the sound" + e);
        }
    }

    public void stop() {
        clip.stop();
    }

    public void start() {
        clip.start();
    }

}
