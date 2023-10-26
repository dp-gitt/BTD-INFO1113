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

    /**
     * Constructs a Waves object with the specified parameters.
     *
     * @param app                    The main PApplet.
     * @param duration               The duration of the wave.
     * @param preWavePause           The pause before the wave starts.
     * @param monsterTypeList        The list of monster types for the wave.
     * @param MonsterSpawnPointsList The list of monster spawn points.
     * @param gremlinSprite          The sprite for the Gremlin monster.
     * @param beetleSprite           The sprite for the Beetle monster.
     * @param wormSprite             The sprite for the Worm monster.
     * @param manaBar                The mana bar.
     * @param monsterList            The list of monsters in the game.
     */
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
        this.monsterList = monsterList;
        copyList.addAll(monsterList);
        

        for (MonsterType singleMonsterType : monsterTypeList) {

            numOfMonsters += singleMonsterType.getQuantity();
            
        }
    
    }

    /**
     * Gets a copy of the original monster list.
     *
     * @return A copy of the original monster list.
     */
    public List<Monster> getCopyList() {
        return copyList;
    }

    /**
     * Starts a wave, spawning monsters based on configured parameters.
     */
    public void startWave() {
        int randomMonsterIndex;
        int randomIndex;

        if (monsterTypeList.isEmpty()) {
            waveFinished = true;
        } else {

            if (monsterTypeList.size() != 1) {
                randomMonsterIndex = random.nextInt(monsterTypeList.size());
            } else {
                randomMonsterIndex = 0;
            }

            MonsterType monsterType = monsterTypeList.get(randomMonsterIndex);
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

            if (quantity != 0) {
                int spawnTime = 0;

                if (numOfMonsters > 0) {
                    if (numOfMonsters == 1) {
                        spawnTime = 0;
                    } else {
                        spawnTime = (int) (((float) duration / numOfMonsters) * 60);
                    }

                } else {
                    return;
                }

                if (frameCounter >= spawnTime) {
                    map = App.getMapGrid();
                    Monster newMonster = new Monster(app, monsterName, maxHP, speed, armour, manaGainedOnKill,
                            spawnPoint,
                            wizardSpawnPoint, map, sprite, manaBar, monsterList, maxHP);

                    monsterList.add(newMonster);
                    newMonster.determineMonsterPath();
                    monsterType.decreaseQuantity(1);
                    frameCounter = 0;
                }
            }

            if (quantity == 0) {
                monsterTypeList.remove(monsterType);
            }
        }
    }

    /**
     * Gets the duration of the wave.
     *
     * @return The duration of the wave.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Gets the pause duration before the wave starts.
     *
     * @return The pause duration before the wave starts.
     */
    public int getPreWavePause() {
        return preWavePause;
    }

    /**
     * Gets the list of monster types for the wave.
     *
     * @return The list of monster types for the wave.
     */
    public List<MonsterType> getMonsterList() {
        return monsterTypeList;
    }

    /**
     * Increases the frame counter for wave spawning.
     */

    public static void increaseFrameCounter() {
        frameCounter++;
    }

    /**
     * Gets a copy of the original monster list.
     *
     * @return A copy of the original monster list.
     */
    public List<Monster> getMonsterTypeList() {
        return copyList;
    }
}
