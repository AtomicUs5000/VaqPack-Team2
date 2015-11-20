/*-----------------------------------------------------------------------------*
 * VP_Sounds.java
 * - Static sounds object
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 5000
 *-----------------------------------------------------------------------------*/
package vaqpack;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public final class VP_Sounds {
    
    /*------------------------------------------------------------------------*
     * VP_Sounds()
     * - Private constructor. Cannot be instantiated
     * No Parameters.
     *------------------------------------------------------------------------*/
    private VP_Sounds()  {
    }
    
    /*------------------------------------------------------------------------*
     * play()
     * - Plays a specific sound.
     * - Parameter integer soundNumber corresponds to a sound file to play.
     * - No return
     *------------------------------------------------------------------------*/
    protected static void play(int soundNumber) {
        String sound = "";
        switch (soundNumber) {
            case -2: sound = "src/alertError.wav"; break;
            case -1: sound = "src/error.wav"; break;
            case  0: sound = "src/genButton.wav"; break;
        }
        try {
            AudioInputStream audioInputStream;
            audioInputStream = AudioSystem.getAudioInputStream(new File(sound).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            // do nothing, eat it
        }
    }
}
