package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

public class Paths {

    private int xPos;
    private int yPos;
    private PImage sprite;

    /**
     * Constructs a Paths object with the specified position and sprite.
     
     * @param xPos   The x-coordinate of the Path's position.
     * @param yPos   The y-coordinate of the Path's position.
     * @param sprite The image sprite representing the Path.
     */
    public Paths(int xPos, int yPos, PImage sprite) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.sprite = sprite;
    }

    /**
     * Draws the Path on the screen using the provided PApplet.
     *
     * @param app The PApplet used for rendering.
     */
    public void draw(PApplet app) {
        app.image(sprite, xPos, yPos);
    }
}