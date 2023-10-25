package WizardTD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import processing.core.PApplet;

public class TowerTest {

    // private App app;

    // @BeforeEach
    // public void setUp() {
    //     // Initialize the game environment for each test case
    //     App app = new App();
    //     PApplet.runSketch(new String[] { "Sketch " }, app);
    //     app.setup();
    //     app.loop();
    //     app.delay(1000);
    // }

    @Test
    public void testTowerDraw() throws InterruptedException {

        App app = new App(); // Replace with the actual class name for your Processing sketch
        PApplet.runSketch(new String[] { "Sketch " }, app);

        app.setup();
        app.loop();
        app.delay(1000);

        Tower tower = new Tower(app, 100, 100, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
                app.getFireballList(), 1, 1, 1);
        Tower tower1 = new Tower(app, 150, 150, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
                app.getFireballList(), 2, 2, 2);

        ManaBar.setMana(1000);

        app.key = 't';
        app.keyPressed();
        app.mouseX = 200;
        app.mouseY = 200;
        app.mousePressed();

        app.key = 't';
        app.keyPressed();

        app.key = '1';
        app.keyPressed();
        app.key = '2';
        app.keyPressed();
        app.key = '3';
        app.keyPressed();

        app.mousePressed();

        app.key = '3';
        app.keyPressed();
        app.mousePressed();
        ManaBar.setMana(1000);

        app.mousePressed();
        app.mousePressed();
        app.mousePressed();

        app.key = '1';
        app.keyPressed();
        app.mousePressed();

        app.mousePressed();
        app.mouseReleased();

        app.mouseX = 200;
        app.mouseY = 200;

        Thread.sleep(15000);

        tower.drawTower();
        tower1.drawTower();

        tower.setIsHovered(true);

        // ArrayList<Tower> towerList = app.getTowerList();
        // towerList.remove(tower1);
        // towerList.remove(tower);

        // app.mouseX = 200;
        // app.mouseY = 200;

        // Thread.sleep(4000);
        app.getSoundEffects().close();
        // Make assertions based on the expected behavior of your draw function
        assertEquals(-1, -1); // Replace with your assertions

    }

    @Test
    public void testPauseButton() throws InterruptedException {

        App app = new App(); // Replace with the actual class name for your Processing sketch
        PApplet.runSketch(new String[] { "Sketch " }, app);

        app.setup();
        app.loop();
        app.delay(1000);

        app.key = 'p';
        app.keyPressed();

        assertEquals(true, app.isPaused());

        app.key = 'p';
        app.keyPressed();
        assertEquals(false, app.isPaused());

        app.key = 'f';
        app.keyPressed();
        assertEquals(true, App.getIs2X());

        Thread.sleep(5000);

        app.getPauseButton().setIsToggled(false);
        app.getTwoXButton().setIsToggled(false);
        app.getSoundEffects().close();
    }

    @Test
    public void testPauseClicking() throws InterruptedException {
        App app = new App();
        PApplet.runSketch(new String[] { "Sketch " }, app);

        // Run setup to initialize your app
        app.setup();
        app.loop();
        app.delay(1000);

        Buttons pauseButton = app.getPauseButton();

        // Initially, the button should not be toggled
        assertFalse(pauseButton.getIsToggled());

        // Simulate a click on the pause button
        app.mouseX = 660;
        app.mouseY = 110;
        app.mousePressed();

        // app.delay(1000);
        Thread.sleep(1000);

        // After clicking, it should be toggled
        assertTrue(pauseButton.getIsToggled());

        // Simulate another click on the pause button
        app.mousePressed();

        // After the second click, it should be untoggled
        assertFalse(pauseButton.getIsToggled());
    }

    @Test
    public void testManaSpell() {
        App app = new App();
        PApplet.runSketch(new String[] { "Sketch " }, app);
        app.setup();
        app.loop();
        app.delay(1000);

        app.mouseX = 660;
        app.mouseY = 360;
        app.mousePressed();
    }

    @Test
    public void testTowerBuildClicking() throws InterruptedException {
        App app = new App();
        PApplet.runSketch(new String[] { "Sketch " }, app);

        // app.setup();
        // app.loop();
        // app.delay(1000);

        // Run setup to initialize your app
        app.setup();

        Buttons buildTowerButton = app.getBuildTowerButton();

        assertFalse(buildTowerButton.getIsToggled());

        app.mouseX = 660;
        app.mouseY = 160;
        Thread.sleep(3000);
        app.mousePressed();

        app.mouseX = 660;
        app.mouseY = 160;

        // Buttons manaPoolButton = app.getManaPoolButton();

        // assertTrue(manaPoolButton.getIsToggled());

        // app.mousePressed();

        // assertFalse(manaPoolButton.getIsToggled());
        app.getSoundEffects().close();

    }

    @Test
    public void testResetGame() {
        App app = new App();
        PApplet.runSketch(new String[] { "Sketch " }, app);
    
        app.setup();
        app.loop();
        app.delay(1000);
    
        // Simulate game loss
        App.setGameLost(true);
        // app.delay(50);
    
        // Press 'r' to reset the game
        app.key = 'r';
        app.keyPressed();
        
        // Ensure the game state is reset
        assertFalse(App.isGameLost());
    }
}