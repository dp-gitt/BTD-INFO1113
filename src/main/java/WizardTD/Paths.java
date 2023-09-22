package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

public class Paths {

    private int xPos;
    private int yPos;
    private PImage sprite;

    public Paths(int xPos, int yPos, PImage sprite) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.sprite = sprite;
    }

    public void draw(PApplet app) {
        app.image(sprite,xPos,yPos);
    }

    public void tick() {
        // logic, and we can turn the images i guess... 
    }
}