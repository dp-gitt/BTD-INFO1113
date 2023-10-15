package WizardTD;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Tower {

    private PApplet app;
    private float towerXPos;
    private float towerYPos;
    private PImage sprite;

    private int towerCost;
    private int towerRange;
    // private static float cooldownTimer;
    private float towerFiringSpeed;
    private int towerDamage;
    private boolean isHovered;
    private boolean canFire;
    private ArrayList<Fireball> fireballList = new ArrayList<Fireball>();
    Monster targetMonster = null;
    PImage fireballSprite;
    private int cooldownTimer;

    public Tower(PApplet app, float towerXPos, float towerYPos, PImage sprite, int towerCost, int towerRange,
            float towerFiringSpeed, int towerDamage, PImage fireballSprite, ArrayList<Fireball> fireballList) {
        this.app = app;
        this.towerXPos = towerXPos;
        this.towerYPos = towerYPos;
        this.sprite = sprite;
        this.towerCost = towerCost;
        this.towerRange = towerRange;
        this.towerFiringSpeed = towerFiringSpeed;
        this.towerDamage = towerDamage;
        this.fireballSprite = fireballSprite;
        this.cooldownTimer = 0;
        this.fireballList = fireballList;
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
        app.stroke(255, 0, 205);
        app.strokeWeight(2);
        app.noFill();

        float centreX = towerXPos + 16;
        float centreY = towerYPos + 16;
        app.ellipse(centreX, centreY, towerRange * 2, towerRange * 2);
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

    public void updateTimer() {
        this.cooldownTimer += 1;
    }

    public void survey(ArrayList<Monster> monsterList) {
        ArrayList<Fireball> fireballsToRemove = new ArrayList<>();
        for (Monster monster : monsterList) {
            float distance = PApplet.dist(towerXPos + 16, towerYPos + 16, monster.getX(), monster.getY());
            if (distance <= this.towerRange && targetMonster == null) {
                targetMonster = monster;
            }
        }

        if (cooldownTimer >= app.frameRate / towerFiringSpeed) {
            canFire = true;
        }

        if (targetMonster != null && canFire) {
            float targetX = targetMonster.getX() + 16;
            float targetY = targetMonster.getY() + 16;

            Fireball fireball = new Fireball(app, targetMonster, towerXPos, towerYPos, targetX, targetY, 5, towerDamage,
                    fireballSprite, fireballList);
            System.out.println("New Fireball created");
            fireballList.add(fireball);
            targetMonster = null;
            canFire = false;
            cooldownTimer = 0;
        }

        // if (!fireballList.isEmpty()) {
        //     for (Fireball fireball : fireballList) {
        //         if (targetMonster != null && fireball != null) {
        //             float distanceToMonster = PApplet.dist(fireball.getX(), fireball.getY(), targetMonster.getX(), targetMonster.getY());
        //             if (distanceToMonster <= 16) {
        //                 targetMonster.decreaseHealth(fireball.getDamage());
        //                 fireballsToRemove.add(fireball);
        //             }
        //         }
        //     }
        // }

        for (Fireball fireball : fireballsToRemove) {
            removeFireball(fireball);
        } 
    }

    public void removeFireball(Fireball fireball) {
        fireballList.remove(fireball);
    }

    public ArrayList<Fireball> getFireballList() {
        return this.fireballList;
    }

}