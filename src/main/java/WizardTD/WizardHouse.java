package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

public class WizardHouse extends MapElements {
    /**
     * Constructs a WizardHouse object with the specified position and sprite.
     *
     * @param xPos   The x-coordinate of the WizardHouse's position.
     * @param yPos   The y-coordinate of the WizardHouse's position.
     * @param sprite The image sprite representing the WizardHouse.
     */
    public WizardHouse(int xPos, int yPos, PImage sprite) {
        super(xPos, yPos, sprite);
    }

    /**
     * Draws the WizardHouse on the screen using the provided PApplet.
     *
     * @param app The PApplet used for rendering.
     */
    public void draw(PApplet app) {
        app.image(sprite, xPos, yPos);
    }

    /**
     * Gets the x coordinates of the WizardHouse
     * 
     * @return x coordinates of WizardHouse
     */
    public int getXPos() {
        return this.xPos;
    }

    /**
     * Gets the y coordinates of the WizardHouse
     * 
     * @return y coordinates of WizardHouse
     */
    public int getYpos() {
        return this.yPos;
    }

}