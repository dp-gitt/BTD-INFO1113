package WizardTD;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundEffects {
    // private Clip backgroundMusic;
    private Clip towerPlaceSound;
    private Clip towerUpgradeSound;
    private Clip towerShotSound;

    public SoundEffects() {
        try {
            // // Load background music
            // AudioInputStream backgroundStream =
            // AudioSystem.getAudioInputStream(getClass().getResource("background_music.wav"));
            // backgroundMusic = AudioSystem.getClip();
            // backgroundMusic.open(backgroundStream);

            // Load tower place ound
            AudioInputStream towerPlaceStream = AudioSystem
                    .getAudioInputStream(getClass().getResource("tower_place.wav"));
            towerPlaceSound = AudioSystem.getClip();
            towerPlaceSound.open(towerPlaceStream);

            AudioInputStream towerUpgradeStream = AudioSystem
                    .getAudioInputStream(getClass().getResource("upgrade_tower_sound.wav"));
            towerUpgradeSound = AudioSystem.getClip();
            towerUpgradeSound.open(towerUpgradeStream);

            AudioInputStream towerShotStream = AudioSystem
                    .getAudioInputStream(getClass().getResource("tower_shoot.wav"));
            towerShotSound = AudioSystem.getClip();
            towerShotSound.open(towerShotStream);

        } catch (Exception e) {

        }
    }

    public void close() {
        // if (backgroundMusic != null) {
        // backgroundMusic.close();
        // }
            towerPlaceSound.close();


            towerUpgradeSound.close();


            towerPlaceSound.close();


    }
    
    // public void playBackgroundMusic() {
    // if (backgroundMusic != null && !backgroundMusic.isRunning()) {
    // backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    // }
    // }

    public void playTowerPlaceSound() {

            towerPlaceSound.setFramePosition(0);
            towerPlaceSound.start();

    }

    public void playTowerUpgradeSound() {

            towerUpgradeSound.setFramePosition(0);
            towerUpgradeSound.start();

    }

    public void playTowerShotSound() {

            towerShotSound.setFramePosition(0);
            towerShotSound.start();

    }
}
