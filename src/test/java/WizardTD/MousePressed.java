// package WizardTD;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import java.util.ArrayList;


// import org.junit.jupiter.api.Test;

// import processing.event.MouseEvent;

// public class MousePressed {
   
//     @Test
//     void testMousePressed() {
//         App app = new App(); // Initialize your game class
//         int brown = app.color(132,115,74);
//         int yellow = app.color(255, 255, 0);
//         // Create some buttons for testing
//         Buttons pauseButton = new Buttons(app, 650, 100, 40, 40, brown, "PAUSE", "P");
//         Buttons buildTowerButton = new Buttons(app, 650, 150, 40, 40, brown, "Build Tower", "T");
//         ArrayList<Buttons> buttonsList = app.getButtonsList();
//         buttonsList.add(pauseButton);
//         buttonsList.add(buildTowerButton);

//         // Initially, the game should not be paused
//         assertFalse(app.isPaused());

//         // Simulate a mouse click on the "P" button
//         app.mouseX = 650;
//         app.mouseY = 100;
//         app.mousePressed = true;
//         app.mousePressed(new MouseEvent(app, System.currentTimeMillis(), 3, 0, app.mouseX, app.mouseY, 0, 1));

//         // Assert that the "P" button's color has changed and its state is toggled
//         // assertEquals(yellow, pauseButton.getFillColour());
//         assertTrue(pauseButton.getIsToggled());

//         // Assert that the game is now paused
//         assertTrue(app.isPaused());

//         // // Simulate a mouse click on the "T" button
//         // app.mouseX = x2;
//         // app.mouseY = y2;
//         // app.mousePressed = true;
//         // app.mousePressed(new MouseEvent(app, 0, 0, 0, 0, 0, 0, 0));

//         // // Assert that the "T" button's color has changed and its state is toggled
//         // assertEquals(yellow, buttonT.getButtonColor());
//         // assertTrue(buttonT.getIsToggled());

//         // // Assert that the `towerMode` is now toggled
//         // assertTrue(app.towerMode);

//         // // Test creating a tower
//         // app.buildTowerButton.setIsToggled(true);
//         // int gridRow = 1; // Adjust this to the appropriate grid row
//         // int gridColumn = 2; // Adjust this to the appropriate grid column
//         // app.mouseX = gridColumn * CELLSIZE;
//         // app.mouseY = gridRow * CELLSIZE + 40;
//         // app.mousePressed = true;
//         // app.mousePressed(new MouseEvent(app, 0, 0, 0, 0, 0, 0, 0));

//         // // Add assertions to verify that a tower has been created
//         // // You can check towerList or any other relevant variable
//         // // Also, verify that the towerGrid has been updated

//         // // Assertions for tower creation
//         // // ...

//         // // Reset any toggled buttons
//         // app.buildTowerButton.setIsToggled(false);
//     }
// }   




// //         private MouseEvent createMockButtonMouseEvent(String buttonLabel) {
// //         MouseEvent mockMouseEvent = new MouseEvent();

// //         // Customize the event based on your requirements
// //         // For example, set the button label to "P"
// //         mockMouseEvent.setButtonLabel(buttonLabel);

// //         return mockMouseEvent;
// //     }

// //     // Mock or stub MouseEvent class
// //     private static class MouseEvent {
// //         private String buttonLabel;

// //         public void setButtonLabel(String label) {
// //             this.buttonLabel = label;
// //         }

// //         public String getButtonLabel() {
// //             return buttonLabel;
// //         }
// //     }
// // }


