package WizardTD;

import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;

import processing.core.PVector;

public class Monster {
    private String type;
    private int hp;
    private float speed;
    private float armour;
    private float manaGainedOnKill;
    private PVector spawnPoint;
    private PVector wizardSpawnPoint;
    private char[][] map;
    private PApplet app;
    private ArrayList<Point> monsterPathList = new ArrayList<>();

    public Monster(PApplet app, String type, int hp, float speed, float armour, float manaGainedOnKill, PVector spawnPoint, PVector wizardSpawnPoint, char[][] map) {
        this.type = type;
        this.hp = hp;
        this.speed = speed;
        this.armour = armour;
        this.manaGainedOnKill = manaGainedOnKill;
        this.spawnPoint = spawnPoint;
        this.map = map;
        this.app = app;
        this.wizardSpawnPoint = wizardSpawnPoint;

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
        // System.out.println(spawnPoint.y);//  == 16

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
}
