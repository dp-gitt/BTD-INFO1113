package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class MapElements {
    protected int xPos;
    protected int yPos;
    protected PImage sprite;

    public MapElements(int xPos, int yPos, PImage sprite) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.sprite = sprite;
    }

    public abstract void draw(PApplet app);
}

