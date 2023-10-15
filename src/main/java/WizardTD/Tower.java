package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

public class Tower {

    private PApplet app;
    private float towerXPos;
    private float towerYPos;
    private PImage sprite; 

    private int towerCost;
    private int towerRange;
    private int towerFiringSpeed; 
    private int towerDamage;
    private boolean isHovered;

    public Tower(PApplet app, float towerXPos, float towerYPos, PImage sprite, int towerCost, int towerRange, int towerFiringSpeed, int towerDamage) {
        this.app = app;
        this.towerXPos = towerXPos;
        this.towerYPos = towerYPos;
        this.sprite = sprite;
        this.towerCost = towerCost;
        this.towerRange = towerRange;
        this.towerFiringSpeed = towerFiringSpeed;
        this.towerDamage = towerDamage;
    }

    public void drawTower() {
        app.image(sprite, towerXPos, towerYPos);
    }

    public boolean isMouseOver() {
        float leftEdge = towerXPos;
        float rightEdge = towerXPos + 32;
        float topEdge = towerYPos;
        float bottomEdge = towerYPos + 32;
        return app.mouseX >= leftEdge && app.mouseX <= rightEdge && app.mouseY >= topEdge && app.mouseY <= bottomEdge;
    }

    public void drawRadius() {
        app.stroke(255,0,205);
        app.strokeWeight(2);
        app.noFill();

        float centreX = towerXPos + 16;
        float centreY = towerYPos + 16;
        app.ellipse(centreX, centreY, towerRange *2, towerRange * 2);
        app.noStroke();
        // towerXPos + 16;
        // towerYPos + 16;
    }

    public boolean getIsHovered() {
        return this.isHovered;
    }

    public void setIsHovered(boolean isHovered) {
        this.isHovered = isHovered;
    }

    public void setTowerRange(int towerRange) {
        this.towerRange = towerRange;
    }


}