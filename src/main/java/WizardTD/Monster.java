package WizardTD;

import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
import processing.core.PImage;


import processing.core.PVector;

public class Monster {
    private String type;
    private int hp;
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
    // private boolean isInVisibleArea = false;
    int i = 0;

    public Monster(PApplet app, String type, int hp, float speed, float armour, float manaGainedOnKill, PVector spawnPoint, PVector wizardSpawnPoint, char[][] map, PImage sprite) {
        this.type = type;
        this.hp = hp;
        this.speed = speed;
        this.armour = armour;
        this.manaGainedOnKill = manaGainedOnKill;
        this.spawnPoint = spawnPoint;
        this.map = map;
        this.app = app;
        this.wizardSpawnPoint = wizardSpawnPoint;
        this.speedPixelsPerFrame = speed * (float) app.frameRate / 60.0f; // this can be changed to speed
        this.y = (spawnPoint.x + 1) * 32 + 16; // Centered vertically on the spawn row
        this.x = (spawnPoint.y) * 32 + 8 ; // Centered horizontally on the spawn column
        this.sprite = sprite;
        // System.out.println(x);
        // System.out.println(y);

        // Prints the map for debugging purposes
        System.out.println("Map:");
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                System.out.print(map[row][col] + " ");
            }
            System.out.println();
        }

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
                System.out.println(point);
                monsterPathList.add(point);
            }
        } else {
            System.out.println("No path found");
        }
        System.out.println("PATH FINISHED FINDING");
        return monsterPathList;
    }

    public void moveMonster() {
        // System.out.println("Monster is at " + "x" + x + "y"+ y);
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

            // System.out.println(x);
            // System.out.println(y);

            // Check if the monster has reached the target point
            if (PApplet.dist(x, y, targetX, targetY) < speed) {
                monsterPathList.remove(0);
            }
        }
    }

    public void drawMonster() {
        //System.out.println(x);
        //System.out.println(y);
        if (monsterPathList.isEmpty()) {
            return;
        }
        app.image(sprite,x,y);
    }
}

