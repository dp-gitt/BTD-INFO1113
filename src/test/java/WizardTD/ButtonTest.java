package WizardTD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import processing.core.PApplet;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ButtonTest {
    
    // @AfterAll
    // public void cleanUp() {
    //     App app = new App();
    //     app.resetGame();
    // }

    @Test
    @Order(1)
    public void testKeyPressed() {
        App app = new App();
        PApplet.runSketch(new String[] { "App " }, app);
        app.setup();
        app.loop();
        app.delay(3000);

        if (App.isGameLost()) {
            app.resetGame();
        }

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

        app.key = 'd';
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
    @Order(2)
    public void testMousePressed() {
        App app = new App();
        PApplet.runSketch(new String[] { "App " }, app);
        app.setup();
        app.loop();
        app.delay(3000);

        ManaBar.setMana(1000);

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
        // app.mousePressed();
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
        boolean towerMode = app.isTowerMode();
        assertFalse(towerMode);
        app.mousePressed();
        // assertFalse(buildTowerButton.getIsToggled());
        ManaBar.setMana(1000);
        App.setIs2X(false);
        app.delay(2000);

    }

    @Test
    @Order(3)
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
    @Order(4)
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

    @Test 
    @Order(5)
    public void testdrawTooltip() {
        App app = new App();
        PApplet.runSketch(new String[] { "App " }, app);
        app.setup();
        app.loop();
        app.delay(3000);
        Tower tower = new Tower(app, 100, 100, app.getTowerImageList(), 100, 100, 1, 20, app.getFireballSprite(),
                app.getFireballList(), 0, 0, 0);
        ToolTip tooltip = app.getToolTip();

        tooltip.drawToolTip(app, true, true, true, tower);
        tooltip.drawToolTip(app, false, false, false, tower);
        tooltip.toolTipCostCheck(true, true, true, tower);
        app.delay(1000);
    }

    // @Test
    // @
    // public void toggleTest() {
    //     if 
    // }

    @Test
    @Order(7)
    public void testResetGame() {
        App app = new App();
        PApplet.runSketch(new String[] { "App " }, app);
        app.setup();
        app.loop();
        app.delay(3000);

        app.isTileEmpty(0, 0);

        app.resetGame();
        app.setPaused(true);
        app.drawButtonCost("A");

        SoundEffects sf = new SoundEffects();
        sf.close();
        app.delay(1000);
    }
}
