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
    private int spriteID;
    // private static float cooldownTimer;
    private float towerFiringSpeed;
    private float towerDamage;
    private boolean isHovered;
    private boolean canFire;
    // private ArrayList<PImage> towerImageList = new ArrayList<PImage>();
    PImage[] towerImageList;
    private ArrayList<Fireball> fireballList = new ArrayList<Fireball>();
    Monster targetMonster = null;
    PImage fireballSprite;
    private int cooldownTimer;
    private int rangeLevel;
    private int speedLevel;
    private int damageLevel;
    private float initialTowerDamage;
    // private PImage[] towerImageList;
    private float oSize;
    private float spacing;
    private float yPos;
    private SoundEffects soundEffects = new SoundEffects();

    public float getoSize() {
        return oSize;
    }

    public float getSpacing() {
        return spacing;
    }

    public float getyPos() {
        return yPos;
    }

    public Tower(PApplet app, float towerXPos, float towerYPos, PImage[] towerImageList, int towerCost, int towerRange,
            float towerFiringSpeed, int towerDamage, PImage fireballSprite, ArrayList<Fireball> fireballList,
            int rangeLevel, int speedLevel, int damageLevel) {
        this.app = app;
        this.towerXPos = towerXPos;
        this.towerYPos = towerYPos;
        this.towerImageList = towerImageList;
        this.towerCost = towerCost;
        this.towerRange = towerRange + rangeLevel * 32;
        this.towerFiringSpeed = (float) (towerFiringSpeed + speedLevel * 0.5);
        this.initialTowerDamage = towerDamage;
        this.towerDamage = (float) (towerDamage + damageLevel * 0.5 * initialTowerDamage);
        this.fireballSprite = fireballSprite;
        this.cooldownTimer = 0;
        this.fireballList = fireballList;
        this.rangeLevel = rangeLevel;
        this.speedLevel = speedLevel;
        this.damageLevel = damageLevel;
    }

    public void drawTower() {

        if (rangeLevel >= 2 && speedLevel >= 2 && damageLevel >= 2) {
            app.image(towerImageList[2], towerXPos, towerYPos);
            spriteID = 2;
        } else if (rangeLevel >= 1 && speedLevel >= 1 && damageLevel >= 1) {
            app.image(towerImageList[1], towerXPos, towerYPos);
            spriteID = 1;
        } else {
            app.image(towerImageList[0], towerXPos, towerYPos);
            spriteID = 0;
        }

        // if the tower is purple
        if (spriteID == 0) {
            if (rangeLevel >= 1) {
                // Set the size of each "O"
                 oSize = 4;
                 spacing = 2;
                 yPos = towerYPos + 3;
                app.stroke(255, 0, 255); // Magenta color (R, G, B)
                app.strokeWeight(1);

                // Disable fill for the "O"s
                app.noFill();

                // Draw the "O"s along the top of the tower
                for (int i = 0; i < rangeLevel; i++) {
                    float xPos = towerXPos + i * (oSize + spacing);
                    // Draw an "O" at (xPos, yPos)
                    // You can use the ellipse method to draw outlined circles as "O"s
                    app.ellipse(xPos + 3, yPos, oSize, oSize);
                }
                app.noStroke();
            }

            if (damageLevel >= 1) {
                // Set the size of each "X"
                float xSize = 1; // Adjust size as needed
                float spacing = 5; // Adjust spacing as needed
                float yPos = towerYPos + 32; // Adjust yPos for the bottom edge
                app.fill(255, 0, 255); // Magenta color (R, G, B)
        
                // Draw the "X"s along the bottom edge of the tower
                for (int i = 0; i < damageLevel; i++) {
                    float xPos = towerXPos + i * (xSize + spacing); // Adjust xPos as needed
                    // Draw an "X" at (xPos, yPos)
                    app.textSize(8);
                    app.text("X", xPos, yPos);
                }
            }

            if (speedLevel >= 1) {
                // Set the base radius for the ring
                float baseRadius = 8; // Adjust the base radius as needed
                int strokeWeight = 1;
        
                // Set the number of rings based on speedLevel
                int numRings = speedLevel;

                
        
                // Set the color and thickness for the rings
                app.stroke(0, 0, 255); // Blue color (R, G, B)
                app.strokeWeight(2); // Adjust thickness as needed
        
                // Draw concentric rings inside the tower
                for (int i = 0; i < numRings; i++) {
                    float ringRadius = baseRadius;
                    float centerX = towerXPos + 16;
                    float centerY = towerYPos + 16;
                    app.strokeWeight(strokeWeight);
                    strokeWeight++;
        
                    // Draw a ring using the ellipse function
                    app.noFill(); // No fill for the ring
                    app.ellipse(centerX, centerY, ringRadius * 2, ringRadius * 2);
                }
                app.noStroke(); // Reset stroke settings
            }

        }

        if (spriteID == 1) {
            if (rangeLevel >= 2) {
                int rangeEditor;
                rangeEditor = rangeLevel - 1;
                // Set the size of each "O"
                float oSize = 4;
                float spacing = 2;
                float yPos = towerYPos + 3;
                app.stroke(255, 0, 255); // Magenta color (R, G, B)
                app.strokeWeight(1);

                // Disable fill for the "O"s
                app.noFill();

                // Draw the "O"s along the top of the tower
                for (int i = 0; i < rangeEditor; i++) {
                    float xPos = towerXPos + i * (oSize + spacing);
                    // Draw an "O" at (xPos, yPos)
                    // You can use the ellipse method to draw outlined circles as "O"s
                    app.ellipse(xPos + 3, yPos, oSize, oSize);
                }
                app.noStroke();
            }

            if (damageLevel >= 2) {
                // Set the size of each "X"
                float xSize = 1; // Adjust size as needed
                float spacing = 5; // Adjust spacing as needed
                float yPos = towerYPos + 32; // Adjust yPos for the bottom edge
                app.fill(255, 0, 255); // Magenta color (R, G, B)
        
                // Draw the "X"s along the bottom edge of the tower
                for (int i = 0; i < damageLevel - 1; i++) {
                    float xPos = towerXPos - 1 + i* (xSize + spacing); // Adjust xPos as needed
                    // Draw an "X" at (xPos, yPos)
                    app.textSize(8);
                    app.text("X", xPos, yPos);
                }
            }

            if (speedLevel >= 2) {
                // Set the base radius for the ring
                float baseRadius = 8; // Adjust the base radius as needed
                int strokeWeight = 1;
        
                // Set the number of rings based on speedLevel
                int numRings = speedLevel -1;

                
        
                // Set the color and thickness for the rings
                app.stroke(0, 0, 255); // Blue color (R, G, B)
                app.strokeWeight(2); // Adjust thickness as needed
        
                // Draw concentric rings inside the tower
                for (int i = 0; i < numRings; i++) {
                    float ringRadius = baseRadius;
                    float centerX = towerXPos + 16;
                    float centerY = towerYPos + 16;
                    app.strokeWeight(strokeWeight);
                    strokeWeight++;
        
                    // Draw a ring using the ellipse function
                    app.noFill(); // No fill for the ring
                    app.ellipse(centerX, centerY, ringRadius * 2, ringRadius * 2);
                }
                app.noStroke(); // Reset stroke settings
            }
        }

        if (spriteID == 2) {
            if (rangeLevel >= 2) {

                // THIS CODE WILL JUST OVERLAP THE O's

                // int availableWidth = 32; // Total available width
                // float oSize = 4; // Diameter of each "O"
                // float spacing = 2; // Spacing between "O"s
                // int maxOs = (int) ((availableWidth - oSize) / (oSize + spacing)); // Calculate the maximum number of
                //                                                                   // "O"s

                // if (rangeLevel - 2 > maxOs) {
                //     rangeLevel = maxOs + 2; // Limit the range level to fit within the tower's width
                // }

                // float yPos = towerYPos + 3; // Y position for the "O"s
                // app.stroke(255, 0, 255); // Magenta color (R, G, B)
                // app.strokeWeight(1);

                // // Disable fill for the "O"s
                // app.noFill();

                // // Draw the "O"s along the top of the tower
                // for (int i = 0; i < rangeLevel - 2; i++) {
                //     float xPos = towerXPos + i * (oSize + spacing);
                //     // Draw an "O" at (xPos, yPos)
                //     app.ellipse(xPos + 3, yPos, oSize, oSize);
                // }
                // app.noStroke();

// THIS CODE WILL MAKE IT SO THEY RUN ACROSS BEYOND THE 32 PIXELS OF THE TOWER

                int rangeEditor1;
                rangeEditor1 = rangeLevel - 2;
                // Set the size of each "O"
                float oSize = 4;
                float spacing = 2;
                float yPos = towerYPos + 3;
                app.stroke(255, 0, 255); // Magenta color (R, G, B)
                app.strokeWeight(1);

                // Disable fill for the "O"s
                app.noFill();

                // Draw the "O"s along the top of the tower
                for (int i = 0; i < rangeEditor1; i++) {
                float xPos = towerXPos + i * (oSize + spacing);
                // Draw an "O" at (xPos, yPos)
                // You can use the ellipse method to draw outlined circles as "O"s
                app.ellipse(xPos + 3, yPos, oSize, oSize);
                }
                app.noStroke();
            }

            if (damageLevel >= 2) {
                // Set the size of each "X"
                float xSize = 1; // Adjust size as needed
                float spacing = 5; // Adjust spacing as needed
                float yPos = towerYPos + 32; // Adjust yPos for the bottom edge
                app.fill(255, 0, 255); // Magenta color (R, G, B)
        
                // Draw the "X"s along the bottom edge of the tower
                for (int i = 0; i < damageLevel - 2; i++) {
                    float xPos = towerXPos+ i * (xSize + spacing); // Adjust xPos as needed
                    // Draw an "X" at (xPos, yPos)
                    app.textSize(8);
                    app.text("X", xPos, yPos);
                }
            }

            if (speedLevel >= 2) {
                // Set the base radius for the ring
                float baseRadius = 8; // Adjust the base radius as needed
                int strokeWeight = 1;
        
                // Set the number of rings based on speedLevel
                int numRings = speedLevel - 2;

                
        
                // Set the color and thickness for the rings
                app.stroke(0, 0, 255); // Blue color (R, G, B)
                app.strokeWeight(2); // Adjust thickness as needed
        
                // Draw concentric rings inside the tower
                for (int i = 0; i < numRings; i++) {
                    float ringRadius = baseRadius;
                    float centerX = towerXPos + 16;
                    float centerY = towerYPos + 16;
                    app.strokeWeight(strokeWeight);
                    strokeWeight++;
        
                    // Draw a ring using the ellipse function
                    app.noFill(); // No fill for the ring
                    app.ellipse(centerX, centerY, ringRadius * 2, ringRadius * 2);
                }
                app.noStroke(); // Reset stroke settings
            }

        }

    }

    // let sprite = something and then use that to minus and add the relevant amount
    // of stuffs

    // if (!(rangeLevel >= 1 && speedLevel >= 1 && damageLevel >= 1)) {
    // if (rangeLevel == 0) {

    // }
    // //all the cases before it turns orange
    // } else if (!(rangeLevel >= 2 && speedLevel >= 2 && damageLevel >= 2)) {
    // // orange but not red

    // } else if (!(rangeLevel >= 3 && speedLevel >= 3 && damageLevel >= 3)) {
    // //red and beyond

    // }
    // app.image(sprite, towerXPos, towerYPos);

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

    public int getSpriteID() {
        return spriteID;
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

            soundEffects.playTowerShotSound();


            // System.out.println("New Fireball created");
            fireballList.add(fireball);
            targetMonster = null;
            canFire = false;
            cooldownTimer = 0;
        }

        // if (!fireballList.isEmpty()) {
        // for (Fireball fireball : fireballList) {
        // if (targetMonster != null && fireball != null) {
        // float distanceToMonster = PApplet.dist(fireball.getX(), fireball.getY(),
        // targetMonster.getX(), targetMonster.getY());
        // if (distanceToMonster <= 16) {
        // targetMonster.decreaseHealth(fireball.getDamage());
        // fireballsToRemove.add(fireball);
        // }
        // }
        // }
        // }

        for (Fireball fireball : fireballsToRemove) {
            removeFireball(fireball);
        }
    }

    public void setRangeLevel(int rangeLevel) {
        this.rangeLevel = rangeLevel;
    }

    public void setSpeedLevel(int speedLevel) {
        this.speedLevel = speedLevel;
    }

    public void setDamageLevel(int damageLevel) {
        this.damageLevel = damageLevel;
    }

    public void removeFireball(Fireball fireball) {
        fireballList.remove(fireball);
    }

    public ArrayList<Fireball> getFireballList() {
        return this.fireballList;
    }

    public void upgradeRange() {

        int newLevel = rangeLevel + 1; 
        int upgradeCost = newLevel * 10 + 10;

        if (ManaBar.getMana() - upgradeCost <= 0) {
            return;
        } else {
            rangeLevel += 1;
            towerRange += 32;
            ManaBar.decreaseMana(upgradeCost);
        }
    }

    public int getUpgradeRangeCost() {
        int newLevel = rangeLevel + 1; 
        int upgradeCost = newLevel * 10 + 10;

        return upgradeCost; 
    }

    public void upgradeDamage() {
        int newLevel = damageLevel + 1; 
        int upgradeCost = newLevel * 10 + 10;

        if (ManaBar.getMana() - upgradeCost <= 0) {
            return;
        } else {
            damageLevel += 1;
            towerDamage += (float) (0.5 * initialTowerDamage);
            ManaBar.decreaseMana(upgradeCost);
        }
    }

    public int getUpgradeDamageCost() {
        int newLevel = damageLevel + 1; 
        int upgradeCost = newLevel * 10 + 10;

        return upgradeCost;
    }

    public void upgradeSpeed() {
        int newLevel = speedLevel + 1;
        int upgradeCost = newLevel * 10 + 10;

        if (ManaBar.getMana() - upgradeCost <= 0) {
            return;
        } else {
            speedLevel += 1;
            towerFiringSpeed += 0.5;
            ManaBar.decreaseMana(upgradeCost);
        }
    }

    public int getUpgradeSpeedCost() {
        int newLevel = speedLevel + 1;
        int upgradeCost = newLevel * 10 + 10;

        return upgradeCost;
    }

    // public boolean isMouseOver() {
    // float leftEdge = towerXPos;
    // float rightEdge = towerXPos + 32;
    // float topEdge = towerYPos;
    // float bottomEdge = towerYPos+ 32;
    // return app.mouseX >= leftEdge && app.mouseX <= rightEdge && app.mouseY >=
    // topEdge && app.mouseY <= bottomEdge;
    // }

}