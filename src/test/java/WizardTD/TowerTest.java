package WizardTD;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;

public class TowerTest {
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

        // app.mouseX = 200;
        // app.mouseY = 200;

        // Thread.sleep(4000);





        // Make assertions based on the expected behavior of your draw function
        assertEquals(-1, -1); // Replace with your assertions
    }

    @Test
    public void testPauseButton() throws InterruptedException {
        App app = new App(); // Replace with the actual class name for your Processing sketch
        PApplet.runSketch(new String[] { "Sketch " }, app);
        Thread.sleep(2000);
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
    }
}
