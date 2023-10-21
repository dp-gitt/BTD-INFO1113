package WizardTD;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import java.util.Random;

public class Waves {
    private int duration;
    private int preWavePause;
    // private ArrayList<MonsterType> monsterTypeList;
    private List<MonsterType> monsterTypeList;
    private ArrayList<PVector> MonsterSpawnPointsList = new ArrayList<PVector>();
    // private Random random;
    private PImage gremlinSprite;
    private PImage beetleSprite;
    private PImage wormSprite;
    private static PVector wizardSpawnPoint;
    private PApplet app;
    private static ManaBar manaBar;
    private char[][] map;
    Random random = new Random();
    static int counter = 0;
    static int frameCounter;
    private List<Monster> copyList = new ArrayList<>();
    private int numOfMonsters;
    int startTime;

    private ArrayList<Monster> monsterList;
    private boolean waveFinished = false;

    public Waves(PApplet app, int duration, int preWavePause, List<MonsterType> monsterTypeList,
            ArrayList<PVector> MonsterSpawnPointsList, PImage gremlinSprite, PImage beetleSprite, PImage wormSprite,
            ManaBar manaBar, ArrayList<Monster> monsterList) {
        this.duration = duration;
        this.preWavePause = preWavePause;
        // monsterTypeList = new ArrayList<>();
        this.monsterTypeList = monsterTypeList;
        this.MonsterSpawnPointsList = MonsterSpawnPointsList;
        this.gremlinSprite = gremlinSprite;
        this.beetleSprite = beetleSprite;
        this.wormSprite = wormSprite;
        this.app = app;
        this.manaBar = manaBar;
        this.map = map;
        this.monsterList = monsterList;
        copyList.addAll(monsterList);

        for (MonsterType singleMonsterType : monsterTypeList) {
            numOfMonsters += singleMonsterType.getQuantity();
            System.out.println("NUMBER OF MONSTERS" + numOfMonsters);
        }
    }

    public void startWave() {
        // System.out.println(monsterTypeList.size());
        
        int randomMonsterIndex;
        int randomIndex;

        // for (MonsterType monsterType : monsterTypeList)
        if (monsterTypeList.isEmpty()) {
            waveFinished = true;
            // System.out.println("MONSTER TYPELIST IS EMPTY");
        } else {

            if (monsterTypeList.size() != 1) {
                randomMonsterIndex = random.nextInt(monsterTypeList.size());
            } else {
                randomMonsterIndex = 0;

            }

            MonsterType monsterType = monsterTypeList.get(randomMonsterIndex);

            // System.out.println(MonsterSpawnPointsList.size());
            // System.out.println("MAMEER");

            randomIndex = random.nextInt(MonsterSpawnPointsList.size());
            PVector spawnPoint = MonsterSpawnPointsList.get(randomIndex);

            String monsterName = monsterType.getName();
            PImage sprite = wormSprite;
            switch (monsterName) {

                case "gremlin":
                    sprite = gremlinSprite;
                    break;

                case "beetle":
                    sprite = beetleSprite;
                    break;

                case "worm":
                    sprite = wormSprite;
                    break;
            }

            wizardSpawnPoint = App.getWizardSpawnPoint();
            float maxHP = monsterType.getHp();
            float speed = monsterType.getSpeed();
            float armour = monsterType.getArmour();
            int manaGainedOnKill = (int) monsterType.getManaGainedOnKill();

            int quantity = monsterType.getQuantity();
            // System.out.println("QUANITTY TIS" + quantity);

            if (quantity != 0) {
                // int numOfMonsters = 0;
                int spawnTime;

                if (numOfMonsters > 0) {
                    spawnTime = (int) (((float) duration / numOfMonsters) * 60);
                } else {
                    return;
                }

                // can mess up if there is a bug in framecounting
                if (frameCounter >= spawnTime) {
                    map = App.getMapGrid();
                    Monster newMonster = new Monster(app, monsterName, maxHP, speed, armour, manaGainedOnKill,
                            spawnPoint,
                            wizardSpawnPoint, map, sprite, manaBar, monsterList, maxHP);

                    // System.out.println("NEW MONSTER CREATED");
                    monsterList.add(newMonster);
                    newMonster.determineMonsterPath();
                    monsterType.decreaseQuantity(1);
                    frameCounter = 0;
                }
            }

            if (quantity == 0) {
                monsterTypeList.remove(monsterType);
            }

            // System.out.println(frameCounter);

            // addded the monster

            // need the monster list to constantly check if the monster has reached
            // destination in app.java (LAST THING TO DO)
            // if it has then i need to make it go back to its specific spawnpoint

            // monster attribute - local variable

            // app - app
            // type - monsterName
            // maxHP - maxHP
            // speed - speed
            // armour - armour
            // manaGainedOnKill
            // SpawnPoint - firstCoordinate
            // wizardSpawnPoint - wizardSpawnPoint
            // map - map
            // sprite - sprite
            // manabar - manaBar
            // monsterList - monsterList

            // quantity - quantity

            // need to use duration of wave
            // use prewavePause\
            // chooses a monster wihti the monster list randomly based on randonm between
            // index of max size - 1
            // chceks if there anymore remaining
            // and then uses determine path,
            // and then decreases the quanitty of the monstertype.getquanityt - 1
            // have a for loop checking the quantity at the end, and remove it from the
            // luist if theres any left
            // TO WIN WE CHECK THE (!isempty) of the monsterTypeList, and then in the else
            // we do the you win

        }
    }

    public int getDuration() {
        return duration;
    }

    public int getPreWavePause() {
        return preWavePause;
    }

    public ArrayList<Monster> getMonsterList() {
        return monsterList;
    }

    public static void increaseFrameCounter() {
        frameCounter++;
    }

    // public void drawWaveCounter() {

    // }



    // public void drawWaveCounter(int ) {
    //     if (currentWaveIndex < waveList.size() - 1) {
    //         if (millis() > waveStartTime + waveList.get(currentWaveIndex).getDuration() * 1000) {
    //             int remainingTime = nextWaveStartTime - millis();
    //             if (remainingTime > 0) {
    //                 String countdownText = "Wave " + (currentWaveIndex + 2) + " will start in: " + (remainingTime / 1000) + " seconds";
    //                 app.fill(255); // Set text color to white
    //                 app.text(countdownText, 20, 20); // Adjust position as needed
    //             }
    //         }
    //     }
    // }

}
