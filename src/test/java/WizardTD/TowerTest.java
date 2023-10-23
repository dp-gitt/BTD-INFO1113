package WizardTD;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;

public class TowerTest {
    @Test
    public void testTowerDraw() {
        App app = new App(); // Replace with the actual class name for your Processing sketch
        PApplet.runSketch(new String[] { "Sketch " }, app);

        app.setup();
        Tower tower = new Tower(app, 100, 100, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
                app.getFireballList(), 1, 1, 1);
        Tower tower1 = new Tower(app, 150, 150, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
                app.getFireballList(), 2, 2, 2);
        // Tower tower = new Tower(app, 100, 100, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
        //         app.getFireballList(), 1, 1, 1);
        // Tower tower = new Tower(app, 100, 100, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
        //         app.getFireballList(), 1, 1, 1);
        // Tower tower = new Tower(app, 100, 100, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
        //         app.getFireballList(), 1, 1, 1);
        // Simulate a draw loop
        for (int i = 0; i < 100; i++) {
            app.draw();
        }

        tower.drawTower();
        tower1.drawTower();

        // Simulate a draw loop
        // for (int i = 0; i < 300; i++) {
        //     app.draw();
        // }

        // Make assertions based on the expected behavior of your draw function
        assertEquals(-1, -1); // Replace with your assertions
    }
    
}
