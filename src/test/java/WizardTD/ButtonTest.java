package WizardTD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;

public class ButtonTest {
    

    @Test
    public void testKeyPressed() {
        App app = new App();
        PApplet.runSketch(new String[] { "App " }, app);
        app.setup();
        app.loop();
        app.delay(3000);

        Buttons twoXButton = app.getTwoXButton();
        Buttons pauseButton = app.getPauseButton();
        Buttons buildTowerButton = app.getBuildTowerButton();
        Buttons upgradeRangeButton = app.getUpgradeRangeButton();
        Buttons upgradeSpeedButton = app.getUpgradeSpeedButton();
        Buttons upgradeDamageButton = app.getUpgradeDamageButton();
        Buttons manaPoolButton = app.getManaPoolButton();

        twoXButton.setIsToggled(false);
        pauseButton.setIsToggled(false);
        buildTowerButton.setIsToggled(false);
        upgradeRangeButton.setIsToggled(false);
        upgradeSpeedButton.setIsToggled(false);
        upgradeDamageButton.setIsToggled(false);
        manaPoolButton.setIsToggled(false);

        app.key = 'f';
        app.keyPressed();
        assertEquals(true, twoXButton.getIsToggled());
        app.key = 'f';
        app.keyPressed();
        


        app.key = 'p';
        app.keyPressed();
        assertEquals(true, pauseButton.getIsToggled());
        app.keyPressed();

        app.key = 't';
        app.keyPressed();
        assertEquals(true, buildTowerButton.getIsToggled());
        app.keyPressed();

        app.key = '1';
        app.keyPressed();
        assertEquals(true, upgradeRangeButton.getIsToggled());
        app.keyPressed();

        app.key = '2';
        app.keyPressed();
        assertEquals(true, upgradeSpeedButton.getIsToggled());
        app.keyPressed();

        app.key = '3';
        app.keyPressed();
        assertEquals(true, upgradeDamageButton.getIsToggled());
        app.keyPressed();

        app.key = 'm';
        app.keyPressed();
        assertEquals(true, manaPoolButton.getIsToggled());
        app.keyPressed();
        ManaBar.setMana(1000);
        App.setIs2X(false);
        app.delay(2000);

    }

    @Test 
    public void testMousePressed() {
        App app = new App();
        PApplet.runSketch(new String[] { "App " }, app);
        app.setup();
        app.loop();
        app.delay(3000);

        Buttons twoXButton = app.getTwoXButton();
        Buttons pauseButton = app.getPauseButton();
        Buttons buildTowerButton = app.getBuildTowerButton();
        Buttons upgradeRangeButton = app.getUpgradeRangeButton();
        Buttons upgradeSpeedButton = app.getUpgradeSpeedButton();
        Buttons upgradeDamageButton = app.getUpgradeDamageButton();
        Buttons manaPoolButton = app.getManaPoolButton();

        twoXButton.setIsToggled(false);
        pauseButton.setIsToggled(false);
        buildTowerButton.setIsToggled(false);
        upgradeRangeButton.setIsToggled(false);
        upgradeSpeedButton.setIsToggled(false);
        upgradeDamageButton.setIsToggled(false);
        manaPoolButton.setIsToggled(false);

        // pressing pause button 
        app.setPaused(false);
        app.mouseX = 672;
        app.mouseY = 123;
        app.mousePressed();
        app.mouseReleased();
        // assertTrue(pauseButton.getIsToggled());
        app.mousePressed();
        app.mouseReleased();
        // assertFalse(app.isPaused());

        // pressing build tower button
        app.setTowerMode(false);
        app.mouseX = 668;
        app.mouseY = 169;
        app.mousePressed();
        // boolean towerMode = app.isTowerMode();
        // assertTrue(towerMode);
        app.mousePressed();
        // assertFalse(buildTowerButton.getIsToggled());
        ManaBar.setMana(1000);
        App.setIs2X(false);
        app.delay(2000);

    }

    @Test
    public void manaSpell() {
        App app = new App();
        PApplet.runSketch(new String[] { "App " }, app);
        app.setup();
        app.loop();
        app.delay(3000);

        ManaBar.setMana(1000);
        ManaBar.manaSpell();
        ManaBar.setMana(1000);

        app.delay(1000);
    }

    @Test 
    public void drawButtonCost() {
        App app = new App();
        PApplet.runSketch(new String[] { "App " }, app);
        app.setup();
        app.loop();
        app.delay(3000);

        ManaBar.setMana(1000);

        app.drawButtonCost("T");
        app.drawButtonCost("M");

        app.delay(1000);
    }
}
