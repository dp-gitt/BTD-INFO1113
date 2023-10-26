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

    /**
     * Constructs a Fireball Object
     * 
     * @param app           The PApplet Object associated with the application
     * @param targetMonster The target monster the fireball is aimed at
     * @param x             initial x coordinate of the fireball
     * @param y             initial y coordiate of the fireball
     * @param targetX       the x coordinate of the target destination
     * @param targetY       the y coordinate of the target destination
     * @param speed         speed at which the fireball moves (pixels per frame)
     * @param damage        raw damage fireball does to a monster
     * @param sprite        The PImage representing the Fireball's visual
     *                      appearance.
     * @param fireballList  The list of Fireballs currently in the game
     * 
     */
    Fireball(PApplet app, Monster targetMonster, float x, float y, float targetX, float targetY, float speed,
            float damage, PImage sprite, ArrayList<Fireball> fireballList) {
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

    /**
     * Updates the position of the target monster relative to the fireball
     * as the monster may have moved.
     */
    public void updateTargetPosition() {
        // Continuously update the target position based on the monster's movement
        float changeX = targetMonster.getX() - x;
        float changeY = targetMonster.getY() - y;
        float distance = PApplet.dist(x, y, targetMonster.getX() + 16, targetMonster.getY() + 16);
        if (distance > 0) {
            float speedX = (changeX / distance) * speed;
            float speedY = (changeY / distance) * speed;
            targetX = targetMonster.getX() + 16 + speedX;
            targetY = targetMonster.getY() + 16 + speedY;
        }
    }

    /**
     * Draws the fireball object to the screen.
     */
    public void draw() {
        app.image(sprite, x, y);
    }

    public void moveFireball() {
        float changeX = targetX - x;
        float changeY = targetY - y;
        float distance = PApplet.dist(x, y, targetX, targetY);
        if (distance > 0) {
            float speedX = (changeX / distance) * speed;
            float speedY = (changeY / distance) * speed;
            x += speedX;
            y += speedY;
        }
    }

    /**
     * Gets the x-coordinate of the Fireball's current position.
     *
     * @return The x-coordinate of the Fireball.
     */
    public float getX() {
        return this.x;
    }

    /**
     * Gets the y-coordinate of the Fireball's current position.
     *
     * @return The y-coordinate of the Fireball.
     */
    public float getY() {
        return this.y;
    }

    /**
     * Gets the amount of damage the Fireball inflicts.
     *
     * @return The damage value of the Fireball.
     */
    public float getDamage() {
        return this.damage;
    }

    /**
     * Checks if the Fireball has reached its target destination.
     *
     * @return True if the Fireball has hit its target, false otherwise.
     */
    public boolean hasHitTarget() {
        // Check if the fireball has reached its target
        return PApplet.dist(x, y, targetX, targetY) <= 16;
    }

    /**
     * Reduces the health points (HP) of the target Monster based on the Fireball's
     * damage.
     * The damage inflicted is influenced by the target Monster's armor value.
     */
    public void reduceHP() {
        // System.out.println("called HPREDUCTION");
        System.out.println((int) ((float) targetMonster.getArmour() * damage));
        // System.out.println(targetMonster.getArmour());
        targetMonster.decreaseHealth((int) ((float) targetMonster.getArmour() * damage));
    }

    /**
     * Removes the Fireball from the list of active Fireballs.
     */
    public void removeFireball() {
        fireballList.remove(this);
    }

    /**
     * Checks if the Fireball has already removed HP from its target Monster.
     *
     * @return True if HP has been removed, false otherwise.
     */
    public boolean getHasRemovedHP() {
        return this.hasRemovedHP;
    }

    /**
     * Sets whether the Fireball has removed HP from its target Monster.
     *
     * @param hasRemovedHP True if HP has been removed, false otherwise.
     */
    public void setHasRemovedHP(boolean hasRemovedHP) {
        this.hasRemovedHP = hasRemovedHP;
    }
}
