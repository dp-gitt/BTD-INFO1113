package WizardTD;

import processing.core.PImage;
import processing.core.PApplet;

public class Grass extends MapElements {
    /**
     * Constructs a Grass object with the specified position and sprite.
     *
     * @param xPos   The x-coordinate of the Grass's position.
     * @param yPos   The y-coordinate of the Grass's position.
     * @param sprite The image sprite representing the Grass.
     */
    public Grass(int xPos, int yPos, PImage sprite) {
        super(xPos, yPos, sprite);
    }

    /**
     * Draws the Grass on the screen using the provided PApplet.
     *
     * @param app The PApplet used for rendering.
     */
    public void draw(PApplet app) {
        app.image(sprite, xPos, yPos);
    }
}
