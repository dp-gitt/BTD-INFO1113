package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

public class WizardHouse extends MapElements {
        public WizardHouse(int xPos, int yPos, PImage sprite) {
            super(xPos,yPos,sprite);
        }

        public void draw(PApplet app) {
            app.image(sprite,xPos,yPos);
        }

        public void tick() {

        }
}