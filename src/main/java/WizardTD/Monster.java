package WizardTD;

import java.util.ArrayList;
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
    
    int monsterDead = 0;
    // private boolean isInVisibleArea = false;
    int i = 0;

    public Monster(PApplet app, String type, float maxHp, float speed, float armour, int manaGainedOnKill, 
                    PVector spawnPoint, PVector wizardSpawnPoint, char[][] map, PImage sprite, ManaBar manaBar, ArrayList<Monster> monsterList) {
        this.type = type;
        this.maxHp = maxHp;
        this.speed = speed;
        this.armour = armour;
        this.manaGainedOnKill = manaGainedOnKill;
        this.spawnPoint = spawnPoint;
        this.map = map;
        this.app = app;
        this.wizardSpawnPoint = wizardSpawnPoint;
        //this.speedPixelsPerFrame = speed * (float) app.frameRate / 60.0f; // this can be changed to speed
        this.speedPixelsPerFrame = speed;
        this.y = (spawnPoint.x + 1) * 32 + 16; // Centered vertically on the spawn row
        this.x = (spawnPoint.y) * 32 + 8 ; // Centered horizontally on the spawn column
        this.sprite = sprite;
        this.manaBar = manaBar;
        this.monsterList = monsterList;
        this.currHp = maxHp;
        // System.out.println(x);
        // System.out.println(y);

        // Prints the map for debugging purposes
        // System.out.println("Map:");
        // for (int row = 0; row < map.length; row++) {
        //     for (int col = 0; col < map[row].length; col++) {
        //         System.out.print(map[row][col] + " ");
        //     }
        //     System.out.println();
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
            System.out.println("Path found:");
            for (Point point : path) {
                // System.out.println("made it!");
                // System.out.println(point);
                monsterPathList.add(point);
            }
        } else {
            System.out.println("No path found");
        }
        // System.out.println("PATH FINISHED FINDING");
        return monsterPathList;
    }

    public void moveMonster() {
        // System.out.println("Monster is at " + "x" + x + "y"+ y);
        // System.out.println("OLD X AND Y" + x + " and " + y);
        if (!monsterPathList.isEmpty()) {
            // System.out.println(x);
            Point target = monsterPathList.get(0);
            //System.out.println(target.x);
            //System.out.println(target.y);
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
            float speedX = (dx / distance) * speed;
            float speedY = (dy / distance) * speed;
    
            // Update the monster's position based on the calculated speed values
            x += speedX;
            y += speedY;
            // System.out.println("NEW X AND Y" + x + " and " + y);

            // System.out.println(x);
            // System.out.println(y);

            // Check if the monster has reached the target point
            if (PApplet.dist(x, y, targetX, targetY) < speed) {
                monsterPathList.remove(0);
            }
            if (monsterPathList.size() == 0 && monsterDead == 0) {
                manaBar.decreaseMana(currHp);
                monsterDead = 1;
            }
        }
    }

    public void drawMonster() {
        //System.out.println(x);
        //System.out.println(y);
        if (monsterPathList.isEmpty()) {
            System.out.println("HI");
            return;
        }
        app.image(sprite,x,y);
        // System.out.println("drawn monster at");
        // System.out.println(x);
        // System.out.println(y);

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

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void decreaseHealth(int damage) {
        currHp -= damage;
        if (currHp <= 0) {
            removeMonster();
        } 
    }

    public void removeMonster() {
        monsterList.remove(this);
        ManaBar.increaseMana((int) manaGainedOnKill);
    }

}