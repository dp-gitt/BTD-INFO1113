package WizardTD;

import processing.core.PApplet;

public class Fireball {
    float x, y;
    float targetX, targetY;
    float speed;
    int damage;
    PApplet app;

    Fireball(PApplet app, float x, float y, float targetX, float targetY, float speed, int damage) {
        this.x = x;
        this.y = y;
        this.targetX = targetX;
        this.targetY = targetY;
        this.speed = speed;
        this.damage = damage;
        this.app = app;
    }

    public void move() {
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
}

