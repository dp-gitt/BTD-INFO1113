package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class MapElements {
    protected int xPos;
    protected int yPos;
    protected PImage sprite;

    /**
     * Constructs a Map Element object with the specified position and sprite.
     *
     * @param xPos   The x-coordinate of the Map Element's position.
     * @param yPos   The y-coordinate of the Map Element's position.
     * @param sprite The image sprite representing the Map Element.
     */
    public MapElements(int xPos, int yPos, PImage sprite) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.sprite = sprite;
    }
    
    /**
     * Draws the MapElement on the screen using the provided PApplet.
     *
     * @param app The PApplet used for rendering.
     */
    public abstract void draw(PApplet app);
} 

