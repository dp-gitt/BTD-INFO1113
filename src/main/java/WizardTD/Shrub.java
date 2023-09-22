package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

public class Shrub extends MapElements {
        public Shrub(int xPos, int yPos, PImage sprite) {
            super(xPos,yPos,sprite);
        }

        public void draw(PApplet app) {
            app.image(sprite,xPos,yPos);
        }
}