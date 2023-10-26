package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

public class Shrub extends MapElements {
    /**
     * Constructs a Shrub object with the specified position and sprite.
     *
     * @param xPos   The x-coordinate of the Shrub's position.
     * @param yPos   The y-coordinate of the Shrub's position.
     * @param sprite The image sprite representing the Shrub.
     */
    public Shrub(int xPos, int yPos, PImage sprite) {
        super(xPos, yPos, sprite);
    }

    /**
     * Draws the Shrub on the screen using the provided PApplet.
     *
     * @param app The PApplet used for rendering.
     */
    public void draw(PApplet app) {
        app.image(sprite, xPos, yPos);
    }
}