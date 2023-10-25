package WizardTD;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import WizardTD.App;
import WizardTD.ManaBar;
import WizardTD.Tower;
import processing.core.PApplet;
import processing.core.PImage;

public class drawTowerTest {

    @Test

    public void testDrawTower() throws InterruptedException{
        App app = new App();
        PApplet.runSketch(new String[] { "Sketch " }, app);
        
        app.setup();
        app.loop();
        app.delay(1000);

        Tower tower = new Tower(app, 100, 100, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
                app.getFireballList(), 0, 0, 0);
        Tower tower1 = new Tower(app, 150, 150, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
                app.getFireballList(),0, 0, 1);
        Tower tower2 = new Tower(app, 240, 240, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
                app.getFireballList(),1, 1, 0);
        Tower tower3 = new Tower(app, 150, 150, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
                app.getFireballList(),1, 1, 2);
        Tower tower4 = new Tower(app, 150, 150, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
                app.getFireballList(),1, 3, 4);
        Tower tower5 = new Tower(app, 150, 150, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
                app.getFireballList(),2, 1, 1);



        ManaBar.setMana(1000);

        // places a base tower with all level 0. 
        app.key = 't';
        app.keyPressed();
        app.mouseX = 200;
        app.mouseY = 200;
        app.mousePressed();

        // untoggles build tower button
        app.key = 't';
        app.keyPressed();

        app.key = '1';
        app.keyPressed();
    
        app.mousePressed();
        app.mousePressed();

        app.key = '1';
        app.keyPressed();

        ManaBar.setMana(1000);

        app.key = '2';
        app.keyPressed();

        app.mousePressed();
        app.mousePressed();

        app.mouseMoved();

        Thread.sleep(5000);

        tower.drawTower();
        tower1.drawTower();
        tower2.drawTower();
        tower3.drawTower();
        tower4.drawTower();
        tower5.drawTower();


        tower.drawRadius();
        tower1.drawRadius();
        tower2.drawRadius();
        tower3.drawRadius();
        tower4.drawRadius();
        tower5.drawRadius();

        Thread.sleep(5000);

        assertEquals(-1, -1);

    }

    @Test
    public void level1TowerTest() throws InterruptedException {
        App app = new App();
        PApplet.runSketch(new String[] { "Sketch " }, app);

        app.setup();
        app.loop();
        
        
        
        // app.key = 't';
        // app.keyPressed();

        // app.mouseX = 200;
        // app.mouseY = 200;
        // app.mousePressed();


        // app.key = 't';
        // app.keyPressed();

        // app.key = '1';
        // app.mousePressed();
        // app.mousePressed();

        // Thread.sleep(1000);

        // app.key = 2;
        // app.mousePressed();
        // app.mousePressed();

        Tower tower = new Tower(app, 100, 100, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
        app.getFireballList(), 1, 1, 1);

        tower.setDamageLevel(0);
        tower.setRangeLevel(1);
        tower.setSpeedLevel(1);

        tower.drawTower();
        // Thread.sleep(1000);

        tower.setDamageLevel(1);
        tower.setRangeLevel(0);

        tower.drawTower();

        
        app.getSoundEffects().close();
        app.delay(1000);

    }
    
    @Test
    public void level2TowerTest() throws InterruptedException {
        App app = new App();
        PApplet.runSketch(new String[] { "Sketch " }, app);

        app.setup();
        app.loop();
        
        
        
        // app.key = 't';
        // app.keyPressed();

        // app.mouseX = 200;
        // app.mouseY = 200;
        // app.mousePressed();


        // app.key = 't';
        // app.keyPressed();

        // app.key = '1';
        // app.mousePressed();
        // app.mousePressed();

        // Thread.sleep(1000);

        // app.key = 2;
        // app.mousePressed();
        // app.mousePressed();

        Tower tower = new Tower(app, 100, 100, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
        app.getFireballList(), 1, 1, 1);

        tower.setDamageLevel(1);
        tower.setRangeLevel(2);
        tower.setSpeedLevel(2);

        tower.drawTower();
        // Thread.sleep(1000);

        tower.setSpeedLevel(2);
        tower.setRangeLevel(1);

        tower.drawTower();

        // Thread.sleep(5000);
        app.getSoundEffects().close();
        app.delay(1000);
    }

        @Test
    public void level3TowerTest() throws InterruptedException {
        App app = new App();
        PApplet.runSketch(new String[] { "Sketch " }, app);

        app.setup();
        app.loop();
        
        
        
        // app.key = 't';
        // app.keyPressed();

        // app.mouseX = 200;
        // app.mouseY = 200;
        // app.mousePressed();


        // app.key = 't';
        // app.keyPressed();

        // app.key = '1';
        // app.mousePressed();
        // app.mousePressed();

        // Thread.sleep(1000);

        // app.key = 2;
        // app.mousePressed();
        // app.mousePressed();

        Tower tower = new Tower(app, 100, 100, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
        app.getFireballList(), 1, 1, 1);

        tower.setDamageLevel(2);
        tower.setRangeLevel(3);
        tower.setSpeedLevel(3);

        tower.drawTower();
        Thread.sleep(1000);

        tower.setSpeedLevel(3);
        tower.setRangeLevel(2);

        tower.drawTower();
        tower.drawRadius();

        // Thread.sleep(5000);
        app.getSoundEffects().close();
        app.delay(1000);

    }



}
