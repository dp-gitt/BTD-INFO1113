package WizardTD;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class LevelLoadingTest extends App {

    @Test
    public void testLoadLevelData() {
        // Load a test resource containing level data
        String[] testLevelData = new String[] {
            "W        XSS   ",
            "S   S   XXXX   ",
            "XXXXXXXX XXXXX ",
            // Add more rows as needed
        };

        // Create an instance of your App
        App app = new App();

        // Load the test resource data into your loadLevelData function
        char[][] levelDataMatrix = app.loadLevelData(testLevelData);

        assertNotNull(levelDataMatrix);

        // Add your assertions to test the loaded data
        assertEquals(3, levelDataMatrix.length); // Check the number of rows
        assertEquals(15, levelDataMatrix[0].length); // Check the number of columns in the first row
    }

    @Test
    public void testCreateMapElements() {
        // Create an instance of App
        TestApp app = new TestApp();
        // app.setup();
        // app.loop();
        // app.delay(1000);

        // Sample levelDataMatrix for testing
        char[][] sampleMatrix = {
            { 'X', ' ', 'S', 'W', 'S' },
            { 'X', 'X', 'X', 'X', ' ' },
            { ' ', 'S', 'W', 'X', 'X' },
            { 'X', 'S', 'X', 'X', 'X' },
            { 'X', 'S', 'X', 'X', 'X' }
        };

        // Call createMapElements with the sampleMatrix
        app.createMapElements(sampleMatrix);

        char[][] badMatrix = {
            { ' ', 'X', ' ', ' ', ' ' },
            { ' ', ' ', ' ', 'X', ' ' },
            { ' ', ' ', 'X', ' ', ' ' },
            { ' ', 'X', 'X', ' ', ' ' },
            { ' ', 'B', 'X', ' ', ' ' },
        };

        app.createMapElements(badMatrix);
        assertNotNull(App.getWizardSpawnPoint()); // Check if a wizard spawn point was set
        // assertEquals()

    }

    @Test
    public void testCreateButtons() {
        App app = new App();

        app.createButtons();

        ArrayList<Buttons> buttonsList = app.getButtonsList();

        assertNotNull(buttonsList);
        assertEquals(7, buttonsList.size());
    }

} 