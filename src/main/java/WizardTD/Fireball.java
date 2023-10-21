package WizardTD;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Fireball {
    private float x, y;
    private float targetX, targetY;
    private float speed;
    private float damage;
    private PApplet app;
    private Monster targetMonster;
    private PImage sprite;
    private ArrayList<Fireball> fireballList = new ArrayList<Fireball>();
    private boolean hasRemovedHP = false;
    // private PImage sprite = app.image(app.loadImage("src/main/resources/WizardTD/fireball.png"));

    Fireball(PApplet app, Monster targetMonster, float x, float y, float targetX, float targetY, float speed, float damage, PImage sprite, ArrayList<Fireball> fireballList) {
        this.x = x;
        this.y = y;
        this.targetX = targetX;
        this.targetY = targetY;
        this.speed = speed;
        this.damage = damage;
        this.app = app;
        this.targetMonster = targetMonster;
        this.sprite = sprite;
        this.fireballList = fireballList;
    }

    public void updateTargetPosition() {
        // Continuously update the target position based on the monster's movement
        float changeX = targetMonster.getX() - x;
        float changeY = targetMonster.getY() - y;
        float distance = PApplet.dist(x, y, targetMonster.getX() + 16, targetMonster.getY() +16);
        if (distance > 0) {
            float speedX = (changeX / distance) * speed;
            float speedY = (changeY / distance) * speed;
            targetX = targetMonster.getX() + 16 + speedX;
            targetY = targetMonster.getY() + 16 + speedY;
        }
    }

    public void draw() {
        app.image(sprite, x, y);
    }

    public void moveFireball() {
        float changeX = targetX - x;
        float changeY = targetY - y;
        float distance = PApplet.dist(x, y, targetX, targetY);
        if (distance > 0) {
            float speedX = (changeX/distance) * speed;
            float speedY = (changeY/distance) * speed;
            x += speedX;
            y += speedY;
        }
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getDamage() {
        return this.damage;
    }

    public boolean hasHitTarget() {
        // Check if the fireball has reached its target
        return PApplet.dist(x, y, targetX, targetY) <= 16;
    }

    public void reduceHP() {
        System.out.println("called HPREDUCTION");
        System.out.println((int) ((float)targetMonster.getArmour() * damage));
        // System.out.println(targetMonster.getArmour());
        targetMonster.decreaseHealth( (int) ((float)targetMonster.getArmour() * damage));
    }

    public void removeFireball() {
        fireballList.remove(this);
    }

    public boolean getHasRemovedHP() {
        return this.hasRemovedHP;
    }

    public void setHasRemovedHP(boolean hasRemovedHP) {
        this.hasRemovedHP = hasRemovedHP;
    }
}

