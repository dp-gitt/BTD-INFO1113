package WizardTD;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import processing.core.PApplet;
import processing.core.PImage;

import processing.core.PVector;

public class Monster {
    private String type;
    private float currHp;
    private float maxHp;
    private float speed;
    private float armour;
    private float manaGainedOnKill;
    private PVector spawnPoint;
    private float x;
    private float y;
    private PVector wizardSpawnPoint;
    private char[][] map;
    private PApplet app;
    private float speedPixelsPerFrame;
    private ArrayList<Point> monsterPathList = new ArrayList<>();
    private PImage sprite;
    private ManaBar manaBar;
    private ArrayList<Monster> monsterList;
    boolean killMonster = false;
    // private ArrayList<Point> copyMonsterPathList = new ArrayList<>();
    private boolean gameLost = false;
    List<Point> copyMonsterPathList;
    Monster respawnedMonster;
    Monster oldMonster;
    boolean isDying = false;
    int dyingStartFrame;
    PImage gremlinDying1 = App.getGremlinDying1();
    PImage gremlinDying2 = App.getGremlinDying2();
    PImage gremlinDying3 = App.getGremlinDying3();
    PImage gremlinDying4 = App.getGremlinDying4();
    PImage gremlinDying5 = App.getGremlinDying5();
    private boolean gotType = false;
    private String deathType;
    // copy
    // if done real = copy,
    // keep doing real = copy until dead or lost.

    private int k = 0;

    int monsterDead = 0;
    // private boolean isInVisibleArea = false;
    int i = 0;
    private boolean handleRespawn;
    private boolean deathAnimationPlayed = false;
    private boolean finishedAnimation = false;
    private boolean gotDeathFrame = false;
    private int deathStartFrame;
    private float initialSpeed;

    public Monster(PApplet app, String type, float maxHp, float speed, float armour, int manaGainedOnKill,
            PVector spawnPoint, PVector wizardSpawnPoint, char[][] map, PImage sprite, ManaBar manaBar,
            ArrayList<Monster> monsterList, float currHp) {
        this.type = type;
        this.maxHp = maxHp;
        this.speed = speed;
        this.armour = armour;
        this.manaGainedOnKill = manaGainedOnKill;
        this.spawnPoint = spawnPoint;
        this.map = map;
        this.app = app;
        this.wizardSpawnPoint = wizardSpawnPoint;
        // this.speedPixelsPerFrame = speed * (float) app.frameRate / 60.0f; // this can
        // be changed to speed
        this.speedPixelsPerFrame = speed;
        this.y = (spawnPoint.x + 1) * 32 + 16; // Centered vertically on the spawn row
        this.x = (spawnPoint.y) * 32 + 8; // Centered horizontally on the spawn column
        this.sprite = sprite;
        this.manaBar = manaBar;
        this.monsterList = monsterList;
        this.currHp = currHp;
        this.initialSpeed = speed;
        // System.out.println(x);
        // System.out.println(y);

        // Prints the map for debugging purposes
        // System.out.println("Map:");
        // for (int row = 0; row < map.length; row++) {
        // for (int col = 0; col < map[row].length; col++) {
        // System.out.print(map[row][col] + " ");
        // }
        // System.out.println();
        // }

        // Printing vars for debugging
    }

    public ArrayList<Point> determineMonsterPath() {
        // System.out.println("PRINTING SPAWNPOINT LOCATION");
        // System.out.println(spawnPoint.x); // == 0
        // System.out.println(spawnPoint.y);// == 16

        // System.out.println("PRINTING WIZARD LOCATON");
        // System.out.println(wizardSpawnPoint.x); // == 13
        // System.out.println(wizardSpawnPoint.y); // == 6

        Point start = new Point((int) spawnPoint.y, (int) spawnPoint.x, null);
        Point end = new Point((int) wizardSpawnPoint.y, (int) wizardSpawnPoint.x, null);
        List<Point> path = Point.FindPath(this.map, start, end);

        if (path != null) {
            // System.out.println("Path found:");
            for (Point point : path) {
                // System.out.println("made it!");
                // System.out.println(point);
                monsterPathList.add(point);
            }
        } else {
            System.out.println("No path found");
        }
        // System.out.println("PATH FINISHED FINDING");
        // copyMonsterPathList = new ArrayList<>(monsterPathList);
        return monsterPathList;
    }

    public void moveMonster() {

        float calcSpeed = speed;

        if (App.getIs2X()) {
            calcSpeed *= 2 * speed;
        } 



        // System.out.println("Monster is at " + "x" + x + "y"+ y);
        // System.out.println("OLD X AND Y" + x + " and " + y);
        if (!monsterPathList.isEmpty()) {
            // System.out.println(x);
            Point target = monsterPathList.get(0);
            // System.out.println(target.x);
            // System.out.println(target.y);
            float targetX = (target.x) * 32 + 8; // Calculate the target x-coordinate
            float targetY = (target.y + 1) * 32 + 16; // Calculate the target y-coordinate

            // System.out.println("GOING TO THE TARGET:" + monsterPathList.get(0));

            // Calculate the direction and distance to the next point
            float dx = targetX - x;
            float dy = targetY - y;

            // float moveX = dx/speed;
            // float moveY = dy/speed;

            float distance = PApplet.dist(x, y, targetX, targetY);

            // Normalize the direction vector
            float speedX = (dx / distance) * calcSpeed;
            float speedY = (dy / distance) * calcSpeed;

            // Update the monster's position based on the calculated speed values

            x += speedX;
            y += speedY;
  


            // System.out.println("NEW X AND Y" + x + " and " + y);

            // System.out.println(x);
            // System.out.println(y);

            // Check if the monster has reached the target point
            if (PApplet.dist(x, y, targetX, targetY) < calcSpeed) {
                monsterPathList.remove(0);
            }
            if (monsterPathList.size() == 0 && monsterDead == 0) {
                float currMana = ManaBar.getMana();
                if ((currMana - currHp <= 0)) {

                    System.out.println("Game lost");
                    App.gameLost();
                    return;
                } else {
                    ManaBar.decreaseMana(currHp);
                    // for (Point p : copyMonsterPathList) {
                    // System.out.println(p);
                    // }
                    // System.out.println("monster path copy size" + copyMonsterPathList.size());
                    // monsterDead = 1;
                    System.out.println("respawning Monster");
                    createRespawnMonster();
                }

                // if the wizard can banish it, set mosnterpathlist = coptmonsterpathlist and
                // decrease mana.
                // else wizard cant banish so you lose.
                // monster needs to get removed from the wizard house instantly after it
                // reaches.

            }
        }
    }

    public void drawMonster() {







        if (isDying) {
                // deathType = type;
            if (!gotType) {
                System.out.println(deathType);
                gotType = true;
                dyingStartFrame = app.frameCount;
                handleRespawn = false;
            }
    
            // Calculate the frame difference since dying started
            int frameDifference = app.frameCount - dyingStartFrame;
            int deathAnimationFrames = 20;  // Adjust the duration based on your preference
    
            // Display different dying images based on the frame difference

            // System.out.println(deathType);
            // System.out.println(frameDifference);
            // System.out.println(deathAnimationFrames);
        if (frameDifference < deathAnimationFrames) {
            System.out.println(deathType);
            if (frameDifference < 4) {
                app.image(gremlinDying1, x, y);              
            } else if (frameDifference < 8) {
                app.image(gremlinDying2, x, y);
            } else if (frameDifference < 12) {
                app.image(gremlinDying3, x, y);
            } else if (frameDifference < 16) {
                app.image(gremlinDying4, x, y);
                // System.out.println("HIJQBWDIQBWIUJDBQIWDBIQBWUIDBIQUWBDUIQWBD");
            } else {
                app.image(gremlinDying5, x, y);
            }
            } else {
                // Dying animation is finished
                // System.out.println("death type " + deathType);
                deathAnimationPlayed = true;
                isDying = false;
                killMonster = true;
                
            }
            return;
        }



        // if (isDying) {

        //     if (!gotType) {
        //         deathType = type;
        //         System.out.println(deathType);
        //         gotType = true;
        //         deathAnimationPlayed = false;
        //     }

        //     // Calculate the frame difference since dying started
        //     int frameDifference = app.frameCount - dyingStartFrame;

        //     // Display different dying images based on the frame difference
        //     if (frameDifference < 4 ) {
        //         app.image(gremlinDying1, x, y);
        //         System.out.println(type);
        //     } else if (frameDifference < 8 ) {
        //         app.image(gremlinDying2, x, y);
        //     } else if (frameDifference < 12 ) {
        //         app.image(gremlinDying3, x, y);
        //     } else if (frameDifference < 16 ) {
        //         app.image(gremlinDying4, x, y);
        //     } else if (frameDifference < 20 ) {
        //         app.image(gremlinDying5, x, y);
        //     } else {
        //         // Dying animation is finished
        //         deathAnimationPlayed = true;
        //         killMonster = true;
        //         isDying = false;
        //     }
        //     return;
        // }
// -------------------------------------------
        // System.out.println(x);
        // System.out.println(y);

        // public void drawMonster() {
        //System.out.println(x);
        //System.out.println(y);
        // if (isDying) {
        // // Calculate the frame difference since dying started
        // int frameDifference = app.frameCount - dyingStartFrame;

        // // Display different dying images based on the frame difference
        // if (frameDifference < 4) {
        // app.image(gremlinDying1, x, y);
        // } else if (frameDifference < 8) {
        // app.image(gremlinDying2, x, y);
        // } else if (frameDifference < 12) {
        // app.image(gremlinDying3, x, y);
        // } else if (frameDifference < 16) {
        // app.image(gremlinDying4, x, y);
        // } else if (frameDifference < 20 ) {
        // app.image(gremlinDying5, x, y);
        // } else {
        // // Dying animation is finished
        // killMonster = true;
        // isDying = false;
        // }
        // return;
        // } else if (isDying) {
        // killMonster = true;
        // isDying = false;
        // return;
        // }

        // if (monsterPathList.isEmpty()) {
        //     // System.out.println("monster list is empty");
        //     return;
        // }

        
        app.image(sprite, x, y);
        // System.out.println("drawn monster at");
        // System.out.println(x);
        // System.out.println(y);

        if (currHp > 0) {  // Only draw health bars if current HP is greater than 0
            float barWidth = 30; // Adjust this to fit your needs
            float barHeight = 4; // Adjust this to fit your needs
        
            // Calculate the position of the health bars above the monster
            float barX = x - barWidth / 2 + 10;
            float barY = y - 7; // Adjust this to position the bars correctly
        
            // Draw the red background bar
            app.fill(255, 0, 0); // Red color
            app.rect(barX, barY, barWidth, barHeight);
        
            // Calculate the width of the green HP bar based on current HP
            float hpBarWidth = PApplet.map(currHp, 0, maxHp, 0, barWidth);
        
            // Draw the green HP bar
            app.fill(0, 255, 0); // Green color
            app.rect(barX, barY, hpBarWidth, barHeight);
        }
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void decreaseHealth(int damage) {
        currHp -= damage;
        if (currHp <= 0 && !isDying) {
            isDying = true;
            killMonster = false;
            dyingStartFrame = app.frameCount; // Record the frame when dying starts
        }
    }

    // void removeMonster() {
    //     monsterList.remove(this);
    //     ManaBar.increaseMana((int) manaGainedOnKill);
    // }

    // public void removeMonster() {
    // if (type == "gremlin") {

    // }
    // monsterList.remove(this);
    // ManaBar.increaseMana((int) manaGainedOnKill);
    // }

    public void createRespawnMonster() {
        respawnedMonster = new Monster(app, type, maxHp, speed, armour, (int) manaGainedOnKill, spawnPoint,
                wizardSpawnPoint, map, sprite, manaBar, monsterList, currHp);
        respawnedMonster.determineMonsterPath();

        handleRespawn = true;
        // System.out.println("made it here");
        // monsterList.add(respawnedMonster);
        // System.out.println("made it past here");
    }

    public boolean getHandleRespawn() {
        return handleRespawn;
    }

    public Monster getRespawnMonster() {
        return respawnedMonster;
    }

    public boolean getKillMonster() {
        return killMonster;
    }

    public float getArmour() {
        return armour;
    }

    public float getManaGainedOnKill() {
        return manaGainedOnKill;
    }

    public boolean getDeathAnimationPlayed() {
        return deathAnimationPlayed;
    }

    public String getType() {
        return type;
    }

    public boolean getFinishedAnimation() {
        return finishedAnimation;
    }

    public boolean gotDeathFrame() {
        return gotDeathFrame;
    }

    public void setDeathStartFrame(int frameCount) {
        deathStartFrame = frameCount;
    }

    public void setGotDeathFrame(boolean b) {
        gotDeathFrame = true;
    }

    public void setDeathAnimationPlayed(boolean p) {
        deathAnimationPlayed = p;
    }

    public int getDeathStartFrame() {
        return deathStartFrame;
    }

    public void multiplyManaGainedOnKill(float manaPoolSpellManaGainedMultiplier) {
        manaGainedOnKill *= manaPoolSpellManaGainedMultiplier;
    }

}