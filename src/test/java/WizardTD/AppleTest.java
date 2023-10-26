package WizardTD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import processing.core.PApplet;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppleTest {

    // All tests were completed with the values in config.json changed to match RequiredConfig.json. Change before running test : )

    @Test
    @Order(2)
    public void towerDrawing() throws InterruptedException {
        App app = new App();
        PApplet.runSketch(new String[] { "App " }, app);
        app.setup();
        app.loop();
        app.delay(3000);
        App.setIs2X(false);
        // toggles the towerBuild button
        // app.resetGame();

        if (App.isGameLost()) {
            app.resetGame();
        }

        app.key = 't';
        app.keyPressed();

        ManaBar.setMana(1000);

        app.mouseX = 200;
        app.mouseY = 200;
        app.mousePressed();

        ArrayList<Tower> towerList = app.getTowerList();

        assertEquals(1, towerList.size());

        // now displaying tooltip check
        Buttons buildTowerButton = app.getBuildTowerButton();
        buildTowerButton.setIsToggled(false);

        Buttons upgradeRangeButton = app.getUpgradeRangeButton();
        Buttons upgradeSpeedButton = app.getUpgradeSpeedButton();
        Buttons upgradeDamageButton = app.getUpgradeDamageButton();

        // case 1: range, speed, damage
        upgradeRangeButton.setIsToggled(true);
        upgradeSpeedButton.setIsToggled(true);
        upgradeDamageButton.setIsToggled(true);

        app.mouseX = 200;
        app.mouseY = 200;
        app.mousePressed();
        app.mouseMoved();

        for (Tower tower : towerList) {
            assertEquals(1, tower.getRangeLevel());
            assertEquals(1, tower.getSpeedLevel());
            assertEquals(1, tower.getDamageLevel());
        }

        upgradeRangeButton.setIsToggled(true);
        upgradeSpeedButton.setIsToggled(true);
        upgradeDamageButton.setIsToggled(false);

        app.mouseX = 200;
        app.mouseY = 200;
        app.mousePressed();
        app.mouseMoved();

        for (Tower tower : towerList) {
            assertEquals(2, tower.getRangeLevel());
            assertEquals(2, tower.getSpeedLevel());
            assertEquals(1, tower.getDamageLevel());
        }

        upgradeRangeButton.setIsToggled(true);
        upgradeSpeedButton.setIsToggled(false);
        upgradeDamageButton.setIsToggled(true);

        app.mouseX = 200;
        app.mouseY = 200;
        app.mousePressed();
        app.mouseMoved();

        for (Tower tower : towerList) {
            assertEquals(3, tower.getRangeLevel());
            assertEquals(2, tower.getSpeedLevel());
            assertEquals(2, tower.getDamageLevel());
        }

        upgradeRangeButton.setIsToggled(false);
        upgradeSpeedButton.setIsToggled(true);
        upgradeDamageButton.setIsToggled(true);

        app.mouseX = 200;
        app.mouseY = 200;
        app.mousePressed();
        app.mouseMoved();

        for (Tower tower : towerList) {
            assertEquals(3, tower.getRangeLevel());
            assertEquals(3, tower.getSpeedLevel());
            assertEquals(3, tower.getDamageLevel());
        }

        upgradeRangeButton.setIsToggled(true);
        upgradeSpeedButton.setIsToggled(false);
        upgradeDamageButton.setIsToggled(false);

        app.mouseX = 200;
        app.mouseY = 200;
        app.mousePressed();
        app.mouseMoved();

        for (Tower tower : towerList) {
            assertEquals(4, tower.getRangeLevel());
            assertEquals(3, tower.getSpeedLevel());
            assertEquals(3, tower.getDamageLevel());
        }

        upgradeRangeButton.setIsToggled(false);
        upgradeSpeedButton.setIsToggled(true);
        upgradeDamageButton.setIsToggled(false);

        app.mouseX = 200;
        app.mouseY = 200;
        app.mousePressed();
        app.mouseMoved();

        for (Tower tower : towerList) {
            assertEquals(4, tower.getRangeLevel());
            assertEquals(4, tower.getSpeedLevel());
            assertEquals(3, tower.getDamageLevel());
        }

        upgradeRangeButton.setIsToggled(false);
        upgradeSpeedButton.setIsToggled(false);
        upgradeDamageButton.setIsToggled(true);

        app.mouseX = 200;
        app.mouseY = 200;
        app.mousePressed();
        app.mouseMoved();

        for (Tower tower : towerList) {
            assertEquals(4, tower.getRangeLevel());
            assertEquals(4, tower.getSpeedLevel());
            assertEquals(4, tower.getDamageLevel());
        }

        app.mouseX = 275;
        app.mouseY = 80;
        app.mouseMoved();


        boolean emptyTowerList = towerList.isEmpty();
        // app.mousePressed();

        assertFalse(emptyTowerList);
        

        app.delay(3000);
        Thread.sleep(10000);
    }

    @Test
    @Order(1)
    public void testCreateTower() {
        App app = new App();
        PApplet.runSketch(new String[] { "App " }, app);
        app.delay(3000);
        app.setup();
        app.loop();
        // app.delay(3000);
        App.setIs2X(false);
        ManaBar.setMana(1000);

        Buttons upgradeRangeButton = app.getUpgradeRangeButton();
        Buttons upgradeDamageButton = app.getUpgradeDamageButton();
        Buttons upgradeSpeedButton = app.getUpgradeSpeedButton();

        upgradeDamageButton.setIsToggled(true);
        upgradeRangeButton.setIsToggled(true);
        upgradeSpeedButton.setIsToggled(true);

        app.createTower(0, 4);
        ArrayList<Tower> towerList = app.getTowerList();

        for (Tower tower : towerList) {
            tower.drawTower();
            tower.drawRadius();
        }

        app.delay(2000);

    }

    @Test
    @Order(3)
    public void testCreateTower1() {
        App app = new App();
        PApplet.runSketch(new String[] { "App " }, app);
        app.setup();
        app.loop();
        app.delay(3000);
        ManaBar.setMana(1000);

        Buttons upgradeRangeButton = app.getUpgradeRangeButton();
        Buttons upgradeDamageButton = app.getUpgradeDamageButton();
        Buttons upgradeSpeedButton = app.getUpgradeSpeedButton();
        Buttons buildTowerButton = app.getBuildTowerButton();

        buildTowerButton.setIsToggled(true);
        upgradeDamageButton.setIsToggled(false);
        upgradeRangeButton.setIsToggled(true);
        upgradeSpeedButton.setIsToggled(true);

        app.createTower(0, 4);
        ArrayList<Tower> towerList = app.getTowerList();

        for (Tower tower : towerList) {
            tower.drawTower();
            tower.drawRadius();
        }

        app.delay(2000);
    }

    @Test
    @Order(4)
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
        Tower tower6 = new Tower(app, 150, 150, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
                app.getFireballList(),2, 2, 2);
        Tower tower7 = new Tower(app, 150, 150, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
                app.getFireballList(),1, 2, 2);



        ManaBar.setMana(1000);

        tower.drawTower();
        tower1.drawTower();
        tower2.drawTower();
        tower3.drawTower();
        tower4.drawTower();
        tower5.drawTower();
        tower6.drawTower();
        tower7.drawTower();

        tower.setIsHovered(true);
        tower.drawRadius();
        tower1.drawRadius();
        tower2.drawRadius();
        tower3.drawRadius();
        tower4.drawRadius();
        tower5.drawRadius();
        tower6.drawRadius();
        tower7.drawRadius();

        app.delay(5000);
        

        assertEquals(-1, -1);

    }

}
