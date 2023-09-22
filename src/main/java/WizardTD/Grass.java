package WizardTD;

import processing.core.PImage;
import processing.core.PApplet;

public class Grass extends MapElements {
        public Grass(int xPos, int yPos, PImage sprite) {
            super(xPos,yPos,sprite);
        }

        public void draw(PApplet app) {
            app.image(sprite,xPos,yPos);
        }
}

// public class Grass {
    
//     private int x;
//     private int y;
//     private PImage sprite;


//     public Grass(int x, int y, PImage sprite) {
//         this.x = x;
//         this.y = y;
//         this.sprite = sprite; 
//     }

//     public void tick() {
//         //logic for drawing grass 
//     }

//     public void draw (PApplet appGrass) {
//         //drawing grass
//         appGrass.image(this.sprite,this.x,this.y);   
//     } 
// }
