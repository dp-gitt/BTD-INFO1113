package WizardTD;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class LevelLoadingTest extends App{

    // @BeforeEach
    // public void setUp() {
    //     System.setProperty("java.awt.headless", "true");
    // }




    @Test
    public void testLoadLevelData() {
        // Load a test resource containing level data
        String[] testLevelData = new String[] {
            "W        SSS   ",
            "S        X X   ",
            // Add more rows as needed
        };

        // Create an instance of your App
        App app = new App();

        // Load the test resource data into your loadLevelData function
        char[][] levelDataMatrix = app.loadLevelData(testLevelData);

        assertNotNull(levelDataMatrix);

        // Add your assertions to test the loaded data
        assertEquals(2, levelDataMatrix.length); // Check the number of rows
        assertEquals(15, levelDataMatrix[0].length); // Check the number of columns in the first row
    }

    @Test
    public void testCreateMapElements() {
        // Create an instance of App (your main class)
        TestApp app = new TestApp();
        // app.setup();
        // app.loop();
        // app.delay(1000);

        // Sample levelDataMatrix for testing
        char[][] sampleMatrix = {
            { 'X', ' ', 'S', 'W', 'S' },
            { 'X', 'X', 'X', 'X', ' ' },
            { ' ', 'S', 'W', 'X', ' ' },
        };

        // Call createMapElements with the sampleMatrix
        app.createMapElements(sampleMatrix);

        // Add your assertions to check if the map elements were created as expected
        // For example:
        // assertEquals(15, app.getElementsList().size()); // Check the number of elements created
        assertNotNull(App.getWizardSpawnPoint()); // Check if a wizard spawn point was set
        // assertEquals()
        
        // Add more assertions as needed
    }

    @Test
    public void testCreateButtons() {
        App app = new App();
        // app.setup();
        // app.loop();
        // app.delay(1000);

        app.createButtons();

        ArrayList<Buttons> buttonsList = app.getButtonsList();

        assertNotNull(buttonsList);
        assertEquals(7, buttonsList.size());
    }

    // @Test
    // public void testCreateTower() {
    //     App app = new App();
    //     PApplet.runSketch(new String[] { "Sketch " }, app);
    //     app.setup();
    //     app.loop();
    //     app.delay(1000);

    //     // Simulate the game state or dependencies as needed for the test
    //     // Set up buildTowerButton, upgradeRangeButton, upgradeSpeedButton, and ManaBar
    //     // to simulate the conditions you want to test
    //     buildTowerButton = new Buttons(this, 650, 150, 40, 40, color(132, 115, 74), "Build Tower", "T");
    //     upgradeRangeButton = new Buttons(this, 650, 200, 40, 40, color(132, 115, 74), "Upgrade Range", "U1");
    //     upgradeSpeedButton = new Buttons(this, 650, 250, 40, 40, color(132, 115, 74), "Upgrade Speed", "U2");
    //     upgradeDamageButton = new Buttons(this, 650, 300, 40, 40, color(132, 115, 74), "Upgrade Damage", "U3");
    //     // For example:
    //     // app.getBuildTowerButton().setIsToggled(true);
    //     // app.getUpgradeRangeButton().setIsToggled(true);
    //     // app.getUpgradeSpeedButton().setIsToggled(true);
    //     // app.getUpgradeDamageButton().setIsToggled(true);

    //     // Set up initial mana
    //     ManaBar.setMana(100);

    //     int gridRow = 1;
    //     int gridColumn = 1;

    //     // Perform the action
    //     app.createTower(gridRow, gridColumn);

    //     // Assert the results
    //     // You can check the towerList to ensure a new tower was created
    //     assertTrue(app.getTowerList().size() > 0);

    //     // You can also check other game state variables to ensure they are modified as expected
    //     // For example, check if ManaBar.getMana() has been decreased correctly

    //     // Additional assertions for tower levels, cost, and other conditions as needed
    //     // For example, check the values of rangeLevel, speedLevel, damageLevel, and towerCost

    //     // Assert.assertEquals(expected, actual);

    //     // More assertions if needed
    // }

} 