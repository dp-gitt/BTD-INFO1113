package WizardTD;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.*;

public class App extends PApplet {

    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;
    int currentWaveIndex = 0;
    private int k = 0;
    boolean waveChanged = false;
    int waveStartTime;

    
    

    public static int WIDTH = CELLSIZE * BOARD_WIDTH + SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH * CELLSIZE + TOPBAR;

    public static final int FPS = 60;

    public String configPath;
    private PImage fireballSprite;
    public Random random = new Random();
    private boolean x = false;
    public char[][] mapGrid;
    
    public char[][] towerGrid;
    public static PVector wizardSpawnPoint;
    private int numMonstersCreated = 0;
    private int j;
    private int counter = 0;
    private Buttons upgradeRangeButton;
    private Buttons buildTowerButton;
    private PImage gremlinSprite;
    private PImage beetleSprite;
        

    private PImage wormSprite;
    private ArrayList<MonsterType> monsterTypeList = new ArrayList<MonsterType>();
    // public ArrayList<Waves> wavesList;
    // private ArrayList<PImage> towerImageList = new ArrayList<PImage>();
    private PImage[] towerImageList = new PImage[3];
    // private int m;

    private static final String levelName = "level3.txt";
    private static int noOfMonstersNeeded = 5;

    public int firstSpawnVecX;
    public int firstSpawnVecY;
    private int numberOfPossibleSpawns;
    private int maxMana = 1000;
    ManaBar manaBar;
    private int framesBetweenSpawn = 30;
    private int spawnCounter = 0;
    private boolean isPaused = false;
    private int yellow = color(255, 255, 0);
    private int brown = color(132, 115, 74);
    private boolean towerMode;
    private boolean towerHover;
    ArrayList<Fireball> fireballList = new ArrayList<Fireball>();
    Buttons twoXButton;
    Buttons pauseButton;
    Buttons upgradeSpeedButton;
    Buttons upgradeDamageButton;
    Buttons manaPoolButton;

    int currentWaveStartTime; // Keep track of the start time for the current wave
    int delayStartTime;       // Start time for the delay
    boolean isDelaying = true; // Flag to indicate if you are in a delay

    JSONObject config;

    // private int TESTING_VAR = 0;
    // private char[][] levelDataMatrix;
    private ArrayList<MapElements> elementsList = new ArrayList<MapElements>();
    private ArrayList<Paths> pathsList = new ArrayList<Paths>();
    private ArrayList<WizardHouse> wizardHouseList = new ArrayList<WizardHouse>();
    private ArrayList<Buttons> buttonsList = new ArrayList<Buttons>();
    private ArrayList<PVector> MonsterSpawnPointsList = new ArrayList<PVector>();
    private ArrayList<Monster> monsterList = new ArrayList<Monster>();
    private ArrayList<Tower> towerList = new ArrayList<Tower>();
    private ArrayList<Fireball> fireballsToRemoveList = new ArrayList<Fireball>();
    private ArrayList<Waves> waveList = new ArrayList<Waves>(); 

    // public int[][] mapGrid;

    // Feel free to add any additional methods or attributes you want. Please put
    // classes in different files.

    /*
     * This method extracts all the individual symbols located inside a
     * level file, and stores them into a 2D matrix such that the symbols
     * can be individually iterated through and the correct image is drawn.
     */

    public static PVector getWizardSpawnPoint() {
        return wizardSpawnPoint;
    }

    public char[][] getMapGrid() {
        return mapGrid;
    }

    public void createMonsters() {

        int randomIndex = random.nextInt(MonsterSpawnPointsList.size());
        PVector firstCoordinate = MonsterSpawnPointsList.get(randomIndex); // gets a random spawnPoint
        // PVector firstCoordinate = MonsterSpawnPointsList.get(1); // Get the first
        // coordinate
        // int firstSpawnVecX = (int) firstCoordinate.x;
        // int firstSpawnVecY = (int) firstCoordinate.y;

        // int randomIndex = random.nextInt(myList.size());

        // MonsterSpawnPointsList =
        monsterList.add(new Monster(this, "gremlin", 100, 1, 150, 100, firstCoordinate, wizardSpawnPoint, mapGrid,
                gremlinSprite, manaBar, monsterList));
        // System.out.println("Monster has been added ot the list");
        Monster newMonster = monsterList.get(monsterList.size() - 1); // Get the last added monster
        newMonster.determineMonsterPath(); // Calculate the path for this specific monster
        // System.out.println(monsterList.size());

        // for (PVector coordinate : MonsterSpawnPointsList) {
        // monsterList.add(new Monster(this,"gremlin",100,2,150,100,coordinate,
        // wizardSpawnPoint, mapGrid));
        // }

    }

    public char[][] loadLevelData(String levelName) {
        String[] levelLines = loadStrings(levelName);
        int numRows = levelLines.length;
        int numColumns = levelLines[0].length();
        char[][] levelDataMatrix = new char[numRows][numColumns];

        for (int currRow = 0; currRow < numRows; currRow++) {
            for (int currCol = 0; currCol < numColumns; currCol++) {
                char currentChar = ' ';
                if (currCol < levelLines[currRow].length()) {
                    currentChar = levelLines[currRow].charAt(currCol);
                }
                levelDataMatrix[currRow][currCol] = currentChar;
            }
        }
        return levelDataMatrix;
    }

    public void createMapElements(char[][] levelDataMatrix) {
        for (int row = 0; row < levelDataMatrix.length; row++) {
            for (int column = 0; column < levelDataMatrix[0].length; column++) {
                char mapSymbol = levelDataMatrix[row][column];
                int xPos = column * CELLSIZE;
                int yPos = row * CELLSIZE + 40;
                // System.out.println(xPos);

                if (mapSymbol == ' ') {
                    elementsList.add(new Grass(xPos, yPos, loadImage("src/main/resources/WizardTD/grass.png")));
                    // new instance of grass
                    // added to list of elements to draw
                } else if (mapSymbol == 'S') {
                    elementsList.add(new Shrub(xPos, yPos, loadImage("src/main/resources/WizardTD/shrub.png")));
                    // new instance of shrub
                } else if (mapSymbol == 'W') {
                    wizardSpawnPoint = new PVector(row, column);
                    xPos += (CELLSIZE - 48) / 2; // Center horizontally
                    yPos += (CELLSIZE - 48) / 2; // Center vertically
                    wizardHouseList.add(
                            new WizardHouse(xPos, yPos, loadImage("src/main/resources/WizardTD/wizard_house.png")));
                    // new instance of Wizard House
                } else if (mapSymbol == 'X') {
                    createMapPaths(levelDataMatrix, row, column);
                    if (row == 0 || row == 19) {
                        System.out.println("Theres a path on the top or row");
                        MonsterSpawnPointsList.add(new PVector(row, column));
                        numberOfPossibleSpawns += 1;
                    } else if (column == 0 || column == 19) {
                        MonsterSpawnPointsList.add(new PVector(row, column));
                        System.out.println("theres a path on the first or last column");
                        numberOfPossibleSpawns += 1;
                    }
                }
            }

        }
    }

    /* It loads in all the different paths and rotates them accordingly */
    public void createMapPaths(char[][] levelDataMatrix, int row, int column) {
        // put these in setup.
        PImage horizontalPath = loadImage("src/main/resources/WizardTD/path0.png");
        PImage verticalPath = rotateImageByDegrees(horizontalPath, 90);
        PImage downT = loadImage("src/main/resources/WizardTD/path2.png");
        PImage leftT = rotateImageByDegrees(downT, 90);
        PImage upT = rotateImageByDegrees(downT, 180);
        PImage rightT = rotateImageByDegrees(downT, 270);
        PImage intersection = loadImage("src/main/resources/WizardTD/path3.png");
        PImage rightDown = loadImage("src/main/resources/WizardTD/path1.png");
        PImage downLeft = rotateImageByDegrees(rightDown, 90);
        PImage leftUp = rotateImageByDegrees(rightDown, 180);
        PImage upRight = rotateImageByDegrees(rightDown, 270);

        // Edges Or Corners
        int xPos = column * CELLSIZE;
        int yPos = row * CELLSIZE + 40;

        boolean isToRight = false;
        boolean isToLeft = false;
        boolean isAbove = false;
        boolean isBelow = false;

        if (column < levelDataMatrix[0].length - 1 && levelDataMatrix[row][column + 1] == 'X') {
            isToRight = true;
        }

        if (column > 0 && levelDataMatrix[row][column - 1] == 'X') {
            isToLeft = true;
        }

        if (row > 0 && levelDataMatrix[row - 1][column] == 'X') {
            isAbove = true;
        }

        if (row < levelDataMatrix.length - 1 && levelDataMatrix[row + 1][column] == 'X') {
            isBelow = true;
        }

        // at the very top -> Vertical Path
        if (yPos == 40) {
            pathsList.add(new Paths(xPos, yPos, verticalPath));

        } else if (isAbove && isBelow && !isToLeft && !isToRight) {
            pathsList.add(new Paths(xPos, yPos, verticalPath));
            // System.out.println("VERTICAL DRAWN");
        } else if (isToRight && isToLeft && !isAbove && !isBelow) {
            pathsList.add(new Paths(xPos, yPos, horizontalPath));
            // System.out.println("HORIZONTAL DRAWN");
        } else if (isAbove && isBelow && isToRight && !isToLeft) {
            pathsList.add(new Paths(xPos, yPos, rightT));
            // RIGHT T DRAWN
        } else if (!isAbove && isBelow && isToRight && isToLeft) {
            pathsList.add(new Paths(xPos, yPos, downT));
        } else if (isAbove && isBelow && !isToRight && isToLeft) {
            pathsList.add(new Paths(xPos, yPos, leftT));
        } else if (isAbove && !isBelow && isToRight && isToLeft) {
            pathsList.add(new Paths(xPos, yPos, upT));
        } else if (isAbove && isBelow && isToRight && isToLeft) {
            pathsList.add(new Paths(xPos, yPos, intersection));
        } else if (!isAbove && isBelow && !isToRight && isToLeft) {
            pathsList.add(new Paths(xPos, yPos, rightDown));
        } else if (isAbove && !isBelow && !isToRight && isToLeft) {
            pathsList.add(new Paths(xPos, yPos, downLeft));
        } else if (isAbove && !isBelow && isToRight && !isToLeft) {
            pathsList.add(new Paths(xPos, yPos, leftUp));
        } else if (!isAbove && isBelow && isToRight && !isToLeft) {
            pathsList.add(new Paths(xPos, yPos, upRight));
        } else if (isAbove && !isBelow && !isToRight && !isToLeft) {
            pathsList.add(new Paths(xPos, yPos, verticalPath));
        } else if (!isAbove && isBelow && !isToRight && !isToLeft) {
            pathsList.add(new Paths(xPos, yPos, verticalPath));
        } else if (!isAbove && !isBelow && isToRight && !isToLeft) {
            pathsList.add(new Paths(xPos, yPos, horizontalPath));
        } else if (!isAbove && !isBelow && !isToRight && isToLeft) {
            pathsList.add(new Paths(xPos, yPos, horizontalPath));
        }

        else {
            System.out.println("right: " + isToRight);
            System.out.println("lef: " + isToLeft);
            System.out.println("up: " + isAbove);
            System.out.println("down: " + isBelow);
            System.out.println(row);
            System.out.println(column);
            System.out.println("not drawn");
        }
    }

    public void createButtons() {

        twoXButton = new Buttons(this, 650, 50, 40, 40, color(132, 115, 74), "2x Speed", "FF");
        pauseButton = new Buttons(this, 650, 100, 40, 40, color(132, 115, 74), "PAUSE", "P");
        buildTowerButton = new Buttons(this, 650, 150, 40, 40, color(132, 115, 74), "Build Tower", "T");
        upgradeRangeButton = new Buttons(this, 650, 200, 40, 40, color(132, 115, 74), "Upgrade Range", "U1");
        upgradeSpeedButton = new Buttons(this, 650, 250, 40, 40, color(132, 115, 74), "Upgrade Speed", "U2");
        upgradeDamageButton = new Buttons(this, 650, 300, 40, 40, color(132, 115, 74), "Upgrade Damage", "U3");
        manaPoolButton = new Buttons(this, 650, 350, 40, 40, color(132, 115, 74), "Mana Pool cost: 100", "M");
        buttonsList.add(twoXButton);
        buttonsList.add(pauseButton);
        buttonsList.add(buildTowerButton);
        buttonsList.add(upgradeRangeButton);
        buttonsList.add(upgradeSpeedButton);
        buttonsList.add(upgradeDamageButton);
        buttonsList.add(manaPoolButton);
    }

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the
     * player, enemies and map elements.
     */
    @Override
    public void setup() {
        frameRate(FPS);
        mapGrid = loadLevelData(levelName);
        towerGrid = loadLevelData(levelName);
        createMapElements(mapGrid);
        fireballSprite = loadImage("src/main/resources/WizardTD/fireball.png");
        PImage tower0 = loadImage("src/main/resources/WizardTD/tower0.png");
        towerImageList[0] = tower0;
        PImage tower1 = loadImage("src/main/resources/WizardTD/tower1.png");
        towerImageList[1] = tower1;
        PImage tower2 = loadImage("src/main/resources/WizardTD/tower2.png");
        
        gremlinSprite = loadImage("src/main/resources/WizardTD/gremlin.png");
        beetleSprite = loadImage("src/main/resources/WizardTD/beetle.png");
        wormSprite = loadImage("src/main/resources/WizardTD/worm.png");






        towerImageList[2] = tower2;

        config = loadJSONObject("config.json");
        parseConfig(config);

        // while (numMonstersCreated < noOfMonstersNeeded) {
        // createMonsters();
        // System.out.println("MONSTER CREATED");
        // numMonstersCreated++;
        // }

        // for (Monster monster : monsterList) {
        // monster.determineMonsterPath();
        // }
        textFont(createFont("Arial", 12));
        noStroke();
        textAlign(LEFT);
        createButtons();
        manaBar = new ManaBar(maxMana);

        // Load images during setup
        // Eg:
        // loadImage("src/main/resources/WizardTD/tower0.png");
        // loadImage("src/main/resources/WizardTD/tower1.png");
        // loadImage("src/main/resources/WizardTD/tower2.png");
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed() {
        // to upgrade a tower, need:
        // not in tower toggle mode
        // be in tower upgrade Toggled
        // need to press on the tower. check towerList

        // if (key == '1') {
        // upgradeRangeButton.setIsToggled(!upgradeRangeButton.getIsToggled());
        // } else if (key == 'p') {
        // pauseButton.setIsToggled(!pauseButton.getIsToggled());
        // }

        switch (key) {
            case 'f':
                twoXButton.setIsToggled(!twoXButton.getIsToggled());
                break;

            case 'p':
                pauseButton.setIsToggled(!pauseButton.getIsToggled());
                isPaused = !isPaused;
                break;

            case 't':
                buildTowerButton.setIsToggled(!buildTowerButton.getIsToggled());
                break;

            case '1':
                upgradeRangeButton.setIsToggled(!upgradeRangeButton.getIsToggled());
                break;

            case '2':
                upgradeSpeedButton.setIsToggled(!upgradeSpeedButton.getIsToggled());
                break;

            case '3':
                upgradeDamageButton.setIsToggled(!upgradeDamageButton.getIsToggled());
                break;

            case 'm':
                manaPoolButton.setIsToggled(!manaPoolButton.getIsToggled());
                break;

        }

        // if (!buildTowerButton.getIsToggled()) {
        // if (key == '1') {
        // upgradeRangeButton.setIsToggled(!upgradeRangeButton.getIsToggled());
        // System.out.println("Button1 Toggled");
        // System.out.println(upgradeRangeButton.getIsToggled());
        // }
        // }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased() {

    }

    @Override
    public void mouseMoved() {

        // for (Buttons button : buttonsList) {
        // if (!button.getIsToggled() && button.isMouseOver()) {
        // button.changeButtonColour(color(200));
        // }
        // }

        // for (Buttons button : buttonsList) {
        // if (!button.getIsToggled() && button.isMouseOver()) {
        // button.changeButtonColour(color(200));
        // } else if (button.getIsToggled()) {
        // button.changeButtonColour(yellow);
        // } else {
        // button.changeButtonColour(brown);
        // }
        // }

        // for (Buttons button : buttonsList) {
        // if (button.isMouseOver() ) {
        // if (button.getIsToggled()) {
        // button.changeButtonColour(yellow);
        // } else {
        // button.changeButtonColour(color(200)); // Change to grey
        // }
        // } else {
        // if (!button.getIsToggled()) {
        // button.changeButtonColour(brown); // Reset to the original color
        // }

        // }
        // }

        if (!towerList.isEmpty()) {
            for (Tower tower : towerList) {
                if (tower.isMouseOver()) {
                    tower.setIsHovered(true);
                } else {
                    tower.setIsHovered(false);
                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (Buttons button : buttonsList) {
            if (button.isMouseOver()) {
                if (button.getIsToggled() == false)
                    button.changeButtonColour(yellow);
                else {
                    button.changeButtonColour(brown);
                }

                button.setIsToggled(!button.getIsToggled());

                if (button.getLabel() == "P") {

                    isPaused = !isPaused;
                    System.out.println(isPaused);
                }

                if (button.getLabel() == "T") {
                    System.out.println("Button T pressed");
                    towerMode = !towerMode;
                    System.out.println(towerMode);
                }
            }
        }

        if (buildTowerButton.getIsToggled()) {
            int gridRow = (int) (mouseY / CELLSIZE);
            gridRow -= 1;
            int gridColumn = (int) (mouseX / CELLSIZE);
            // System.out.println(gridRow);
            // System.out.println(gridColumn);
            createTower(gridRow, gridColumn);
        }

        for (Tower tower : towerList) {
            if (!buildTowerButton.getIsToggled()) {

                boolean rangeToggle = upgradeRangeButton.getIsToggled();
                boolean speedToggle = upgradeSpeedButton.getIsToggled();
                boolean damageToggle = upgradeDamageButton.getIsToggled();
                if (tower.isMouseOver()) {

                    if (rangeToggle && speedToggle && damageToggle) {
                        tower.upgradeRange();
                        tower.upgradeSpeed();
                        tower.upgradeDamage();

                    } else if (rangeToggle && speedToggle && !damageToggle) {
                        tower.upgradeRange();
                        tower.upgradeSpeed();
                    } else if (rangeToggle && !speedToggle && damageToggle) {
                        tower.upgradeRange();
                        tower.upgradeDamage();
                    } else if (!rangeToggle && speedToggle && damageToggle) {
                        tower.upgradeSpeed();
                        tower.upgradeDamage();

                    } else if (rangeToggle && !speedToggle && !damageToggle) {
                        tower.upgradeRange();
                    } else if (!rangeToggle && speedToggle && !damageToggle) {
                        tower.upgradeSpeed();
                    } else if (!rangeToggle && !speedToggle && damageToggle) {
                        tower.upgradeDamage();
                    }
                }

                // else if (rangeToggle && !speedToggle && !damageToggle) {
                // tower.upgradeRange();
                // } else if (rangeToggle && speedToggle && !damageToggle) {
                // tower.upgradeRange();
                // tower.upgradeSpeed();
                // } else if (!rangeToggle && speedToggle && damageToggle) {
                // tower.upgradeSpeed();
                // tower.upgradeDamage();
                // } else if (!rangeToggle && speedToggle && !damageToggle) {
                // tower.upgradeSpeed();
                // } else if (rangeToggle && speedToggle && !damageToggle) {
                // tower.upgradeSpeed();
                // }

                // System.out.println("here");
                // if (tower.isMouseOver()) {
                // tower.upgradeRange();
                // System.out.println("range upgrade!!");
                // }
            }
        }

        // if (!buildTowerButton.getIsToggled() && upgradeSpeedButton.getIsToggled()) {
        // for (Tower tower : towerList) {
        // if (tower.isMouseOver()) {
        // tower.upgradeRange();
        // System.out.println("RANGE UPGRADED");
        // }
        // }
        // }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    public boolean isTileEmpty(int row, int col) {
        if (row >= 0 && row < towerGrid.length && col >= 0 && col < towerGrid[0].length) {
            return towerGrid[row][col] != 'S' && towerGrid[row][col] != 'T' && towerGrid[row][col] != 'X';
        }

        return false; // invalid coordinates. e.g. outside map
    }

    public void createTower(int gridRow, int gridColumn) {
        int rangeLevel = 0;
        int speedLevel = 0;
        int damageLevel = 0;
        if (isTileEmpty(gridRow, gridColumn)) {

            if (buildTowerButton.getIsToggled()) {
                if (upgradeRangeButton.getIsToggled()) {
                    rangeLevel = 1;
                }

                if (upgradeSpeedButton.getIsToggled()) {
                    speedLevel = 1;
                }

                if (upgradeDamageButton.getIsToggled()) {
                    damageLevel = 1;
                }

            }

            int towerXPos = gridColumn * CELLSIZE;
            int towerYPos = gridRow * CELLSIZE + 40;
            int initialTowerCost = 100;
            // not the right maths but in this case we can only initialise a max of level 1
            // tower so it works.
            int towerCost = initialTowerCost + 20 * (rangeLevel) + 20 * (speedLevel) + 20 * (damageLevel);

            if (manaBar.getMana() > towerCost) {
                System.out.println("Tower built has levels:");
                System.out.println("range " + rangeLevel);
                System.out.println("speed " + speedLevel);
                System.out.println("damage " + damageLevel);
                System.out.println("Cost " + towerCost);

                Tower newTower = new Tower(this, towerXPos, towerYPos,
                        towerImageList, towerCost, 100, (float) 1, 20,
                        fireballSprite, fireballList, rangeLevel, speedLevel, damageLevel);

                manaBar.decreaseMana((float) towerCost);
                towerList.add(newTower);
                towerGrid[gridRow][gridColumn] = 'T';
            }
        }
    }

    /*
     * @Override
     * public void mouseDragged(MouseEvent e) {
     * 
     * }
     */

    /**
     * Draw all elements in the game by current frame.
     */
    

    private void parseConfig(JSONObject config) {
        String layout = config.getString("layout");
        int initialTowerRange = config.getInt("initial_tower_range");
        float initialTowerFiringSpeed = config.getFloat("initial_tower_firing_speed");
        int initialTowerDamage = config.getInt("initial_tower_damage");
        int initialMana = config.getInt("initial_mana");
        int initialManaCap = config.getInt("initial_mana_cap");
        float initialManaGainedPerSecond = config.getFloat("initial_mana_gained_per_second");
        int towerCost = config.getInt("tower_cost");
        int manaPoolSpellInitialCost = config.getInt("mana_pool_spell_initial_cost");
        int manaPoolSpellCostIncreasePerUse = config.getInt("mana_pool_spell_cost_increase_per_use");
        float manaPoolSpellCapMultiplier = config.getFloat("mana_pool_spell_cap_multiplier");
        float manaPoolSpellManaGainedMultiplier = config.getFloat("mana_pool_spell_mana_gained_multiplier");

        JSONArray waves = config.getJSONArray("waves");
        for (int i = 0; i < waves.size(); i++) {
            JSONObject waveNumber = waves.getJSONObject(i);
            int duration = waveNumber.getInt("duration");
            int preWavePause = waveNumber.getInt("pre_wave_pause");
            JSONArray monsters = waveNumber.getJSONArray("monsters");
            
            for (int j = 0; j < monsters.size(); j++) {
                JSONObject monster = monsters.getJSONObject(j);
                String type = monster.getString("type");
                float hp = monster.getFloat("hp");
                float speed = monster.getFloat("speed");
                float armour = monster.getFloat("armour");
                float manaGainedOnKill = monster.getFloat("mana_gained_on_kill");
                int quantity = monster.getInt("quantity");
                

                // each wave has a monsterTypesList, and the wave class will have
                // a startWave method which will iterate through the monstertypes
                // list and based on the quantity, and the individual waves timings
                // spawn monsters randomly from the options it has.
                // so in app.java i can iterate through the waveList, so i keep track
                // of the number of waves and as i iterate through them i call 
                // startWave method.

                // i have a waveList of waves. Each wave has its own Monstertype List.
                //I can iterate through each wave to start the wave, and then 
                // for each wave, i can iterate through the monstertypeslist
                // and then create instances of monster class using getter methods from it
                // eg. for MonsterType monstertype : monsterTypeList {
                    //Monster newMonster = new Monster(monstertype.getHP())
                //} 
                //

                MonsterType newMonsterType = new MonsterType(type, hp,speed,armour,manaGainedOnKill,quantity);
                
                
                monsterTypeList.add(newMonsterType);
                
                
                    
            }
            
            // System.out.println(monsterTypeList.size());
            // for (Monster monster : mons)

            // List<MonsterType> copyList = new ArrayList<>(monsterTypeList);
            // for (MonsterType monsterType : copyList) {
            //     System.out.println(monsterType.getName());
            // }

            List<MonsterType> copyList = new ArrayList<>();
            copyList.addAll(monsterTypeList);
            Waves wave = new Waves(this, duration, preWavePause, copyList, MonsterSpawnPointsList, gremlinSprite, beetleSprite, wormSprite, manaBar, mapGrid, monsterList);
            waveList.add(wave);
            // System.out.println(monsterTypeList.size());
            monsterTypeList.clear();
        }
    }


    @Override
    public void draw() {
        background(255);
        // System.out.println("1");
        for (Paths pathsToDraw : pathsList) {
            pathsToDraw.draw(this);
        }
        // System.out.println("2");
        for (MapElements elementToDraw : elementsList) {
            elementToDraw.draw(this);
        }
        // System.out.println("3");
        for (WizardHouse wizardHouse : wizardHouseList) {
            int wizardXPos = wizardHouse.getXPos();
            int wizardYPos = wizardHouse.getYpos();
            MapElements grassUnderWizard = new Grass(wizardXPos, wizardYPos,
                    loadImage("src/main/resources/WizardTD/grass.png"));
            grassUnderWizard.draw(this);
            wizardHouse.draw(this);
        }
        // System.out.println("4");
        for (Tower tower : towerList) {
            if (tower.getIsHovered() == true) {
                tower.drawRadius();
            }
        }
        // System.out.println("5");
        rect(640, 40, 120, 640);
        fill(132, 115, 74);

        // side and top backgrounds
        rect(640, 40, 120, 640);
        fill(132, 115, 74);
        rect(0, 0, 760, 40);
        fill(132, 115, 74);

        manaBar.draw(this, 375, 9, 345, 22);
        // manaBar.increaseMana(1);
        // System.out.println("6");
        for (Buttons button : buttonsList) {
            button.drawText();
            button.drawButton();
            button.drawLabel();
        }

        Waves wave = waveList.get(k);
        wave.startWave();
        
        Waves.increaseFrameCounter();
        int totalPreviousDurations = wave.getDuration();
        int currentWaveIndex = k;
        
        

        while (currentWaveIndex > 0) {
            currentWaveIndex--; // Move to the previous wave
            Waves previousWave = waveList.get(currentWaveIndex);
            totalPreviousDurations += previousWave.getDuration();
        } 

        if (!waveChanged) {
            waveStartTime = millis(); // Store the start time of the current wave
            waveChanged = true;
        }

        if (millis() - totalPreviousDurations >= wave.getDuration() * 1000 && k < waveList.size() - 1 ) {
            System.out.println("NEW WAVE");
            k++;
            waveChanged = false;
        }
       
        for (Monster monster : monsterList) {
            monster.drawMonster();
            monster.moveMonster();
        }

        System.out.println(k);
        

        

        


        // if (millis() - waveList.get(k-1).getDuration >= currentWave.getDuration() * 1000) {
        //     // Current wave's duration has been reached
        //     currentWaveIndex++; // Move to the next wave
        //     if (currentWaveIndex < waveList.size()) {
        //         waveList.get(currentWaveIndex).startWave(); // Start the new wave
        //     }
        // }
        






        // System.out.println(millis());


        // int k = 0;
        // while (k < waveList.size()) {

        //     boolean waveIsPaused = false;


        //     Waves currentWave = waveList.get(k);
        //     int duration = currentWave.getDuration();
        //     int preWavePause = currentWave.getPreWavePause();

        //     int elapsedTime = millis();

        //     if (elapsedTime == 



        //     k++;
        // }
        
        





        // Waves.increaseFrameCounter();

        // System.out.println("7");
        // if (spawnCounter >= framesBetweenSpawn && numMonstersCreated < noOfMonstersNeeded) {
        //     createMonsters(); // Spawn a new monster
        //     spawnCounter = 0; // Reset the counter
        //     numMonstersCreated++;
        //     System.out.println("MONSTER CREATED");
        // } else {
        //     spawnCounter++; // Increment the counter on each frame
        // }

        // for (Waves wave : waveList) {
        //     System.out.println(wave.getDuration());
        // } 


        //////DELETED THIS PART 

        // for (Monster monster : monsterList) {
        //     // System.out.println("MADE IN HERE");
        //     monster.drawMonster();
        //     if (!isPaused) {
        //         monster.moveMonster();
        //     }
        // }



        // System.out.println("8");
        for (Tower tower : towerList) {
            tower.drawTower();
        }
        // System.out.println("9");
        for (Buttons button : buttonsList) {
            if (!button.getIsToggled() && button.isMouseOver()) {
                button.changeButtonColour(color(200));
            } else if (button.getIsToggled()) {
                button.changeButtonColour(yellow);
            } else {
                button.changeButtonColour(brown);
            }
        }
        // System.out.println("10");
        // fireballList.clear();

        for (Tower tower : towerList) {
            tower.survey(monsterList);
            fireballList.addAll(tower.getFireballList());
            tower.updateTimer();
        }
        // System.out.println("11");
        for (Fireball fireball : fireballList) {
            fireball.updateTargetPosition();
            fireball.moveFireball();
            fireball.draw();
            if (fireball.hasHitTarget()) {
                fireballsToRemoveList.add(fireball);
                if (!fireball.getHasRemovedHP()) {
                    fireball.reduceHP();
                    fireball.setHasRemovedHP(true);
                }
            }
        }

        // System.out.println("12");
        // System.out.println(fireballList.size());
        // System.out.println(fireballsToRemoveList.size());
        for (Fireball fireball : fireballsToRemoveList) {
            fireball.removeFireball();
        }
        // System.out.println("13");
        fireballsToRemoveList.clear();

        
    }

    // if (spawnCounter >= framesBetweenSpawn && numMonstersCreated <
    // noOfMonstersNeeded) {
    // createMonster(); // Spawn a new monster
    // spawnCounter = 0; // Reset the counter
    // numMonstersCreated++; // Increment the number of monsters created
    // } else {
    // spawnCounter++; // Increment the counter on each frame
    // }

    public static void main(String[] args) {
        PApplet.main("WizardTD.App");
    }

    /**
     * Source:
     * https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
     * 
     * @param pimg  The image to be rotated
     * @param angle between 0 and 360 degrees
     * @return the new rotated image
     */
    public PImage rotateImageByDegrees(PImage pimg, double angle) {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.createImage(newWidth, newHeight, ARGB);
        // BufferedImage rotated = new BufferedImage(newWidth, newHeight,
        // BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotated = (BufferedImage) result.getNative();
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i, j, rotated.getRGB(i, j));
            }
        }

        return result;
    }
}
