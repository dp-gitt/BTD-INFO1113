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
    static boolean gameLost = false;
    int preWavePauseTime;
    int currentWaveIndex = 0;
    private int k = 0;
    boolean waveChanged = false;
    boolean gotWaveFinishedTime = false;
    private boolean gameWon = false;
    // int waveStartTime;

    int waveStartTime = 0; // Initialize to 0
    int waveFinishedAt = 0;
    // int waveFinishedTime = 0; // Initialize to 0
    // boolean waveChanged = false;

    public static int WIDTH = CELLSIZE * BOARD_WIDTH + SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH * CELLSIZE + TOPBAR;

    public static final int FPS = 60;

    public String configPath;
    private PImage fireballSprite;
    
    public PImage getFireballSprite() {
        return fireballSprite;
    }

    public Random random = new Random();
    private boolean x = false;
    public static char[][] mapGrid;

    public ArrayList<Tower> getTowerList() {
        return towerList;
    }

    public char[][] towerGrid;
    public static PVector wizardSpawnPoint;
    private int numMonstersCreated = 0;
    private int j;
    private int counter = 0;
    public Buttons upgradeRangeButton;
    public Buttons buildTowerButton;
    private PImage gremlinSprite;
    private PImage beetleSprite;
    int initialTowerCost;

    private PImage wormSprite;
    private ArrayList<MonsterType> monsterTypeList = new ArrayList<MonsterType>();
    // public ArrayList<Waves> wavesList;
    // private ArrayList<PImage> towerImageList = new ArrayList<PImage>();
    private PImage[] towerImageList = new PImage[3];
    // private int m;

    // private static final String levelName = "level2.txt";
    private String levelName;
    private static int noOfMonstersNeeded = 5;

    public int firstSpawnVecX;
    public int firstSpawnVecY;
    private int numberOfPossibleSpawns;

    ManaBar manaBar;
    // private int framesBetweenSpawn = 30;
    // private int spawnCounter = 0;
    private boolean isPaused = false;
    private int yellow = color(255, 255, 0);
    private int brown = color(132, 115, 74);
    private boolean towerMode;
    private boolean towerHover;
    ArrayList<Fireball> fireballList = new ArrayList<Fireball>();
    private Buttons twoXButton;
    private Buttons pauseButton;
    public Buttons upgradeSpeedButton;
    public Buttons upgradeDamageButton;
    public Buttons manaPoolButton;

    public ArrayList<Monster> monstersToRemoveList = new ArrayList<>();
    public ArrayList<Monster> monstersToRespawnList = new ArrayList<>();

    int currentWaveStartTime; // Keep track of the start time for the current wave
    int delayStartTime; // Start time for the delay
    boolean isDelaying = true; // Flag to indicate if you are in a delay

    JSONObject config;

    // private int TESTING_VAR = 0;
    // private char[][] levelDataMatrix;
    private ArrayList<MapElements> elementsList = new ArrayList<MapElements>();
    private ArrayList<Paths> pathsList = new ArrayList<Paths>();
    private ArrayList<WizardHouse> wizardHouseList = new ArrayList<WizardHouse>();
    private ArrayList<Buttons> buttonsList = new ArrayList<Buttons>();
    private ArrayList<PVector> MonsterSpawnPointsList = new ArrayList<PVector>();
    private static ArrayList<Monster> monsterList = new ArrayList<Monster>();
    private ArrayList<Tower> towerList = new ArrayList<Tower>();
    private ArrayList<Fireball> fireballsToRemoveList = new ArrayList<Fireball>();
    private ArrayList<Waves> waveList = new ArrayList<Waves>();
    private ArrayList<Monster> monstersKilledList = new ArrayList<Monster>();
    private boolean removeMonsters;
    private int initialTowerRange;
    private int initialTowerDamage;
    private float initialTowerFiringSpeed;
    private int initialMana;
    private int initialManaCap;
    private float initialManaGainedPerSecond;
    public static boolean is2X = false;
    // private static String levelName = "level2.txt";
    private static PImage gremlinDying1;
    private static PImage gremlinDying2;
    private static PImage gremlinDying3;
    private static PImage gremlinDying4;
    private static PImage gremlinDying5;

    int manaPoolSpellInitialCost;
    int manaPoolSpellCostIncreasePerUse;
    float manaPoolSpellCapMultiplier;
    float manaPoolSpellManaGainedMultiplier;
    private ToolTip toolTip;
    private PImage grassImage;
    private PImage shrubImage;
    private PImage wizardHouseImage;
    private String[] testLevelLines;
    private String[] LevelLines;
    private String[] levelLines;

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

    // public void createMonsters() {

    // int randomIndex = random.nextInt(MonsterSpawnPointsList.size());
    // PVector firstCoordinate = MonsterSpawnPointsList.get(randomIndex); // gets a
    // random spawnPoint
    // // PVector firstCoordinate = MonsterSpawnPointsList.get(1); // Get the first
    // // coordinate
    // // int firstSpawnVecX = (int) firstCoordinate.x;
    // // int firstSpawnVecY = (int) firstCoordinate.y;

    // // int randomIndex = random.nextInt(myList.size());

    // // MonsterSpawnPointsList =
    // monsterList.add(new Monster(this, "gremlin", 100, 1, 150, 100,
    // firstCoordinate, wizardSpawnPoint, mapGrid,
    // gremlinSprite, manaBar, monsterList));
    // // System.out.println("Monster has been added ot the list");
    // Monster newMonster = monsterList.get(monsterList.size() - 1); // Get the last
    // added monster
    // newMonster.determineMonsterPath(); // Calculate the path for this specific
    // monster
    // // System.out.println(monsterList.size());

    // // for (PVector coordinate : MonsterSpawnPointsList) {
    // // monsterList.add(new Monster(this,"gremlin",100,2,150,100,coordinate,
    // // wizardSpawnPoint, mapGrid));
    // // }

    // }

    // public char[][] loadLevelData(String levelName) {
    //     String[] levelLines = loadStrings(levelName);
    //     int numRows = levelLines.length;
    //     int numColumns = levelLines[0].length();
    //     char[][] levelDataMatrix = new char[numRows][numColumns];
    //     for (int currRow = 0; currRow < numRows; currRow++) {
    //         for (int currCol = 0; currCol < numColumns; currCol++) {
    //             char currentChar = ' ';
    //             if (currCol < levelLines[currRow].length()) {
    //                 currentChar = levelLines[currRow].charAt(currCol);
    //             }
    //             levelDataMatrix[currRow][currCol] = currentChar;
    //         }
    //     }
    //     return levelDataMatrix;
    // }

    public char[][] loadLevelData(String[] levelLines) {
        // String[] levelLines = loadStrings(levelName);
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
                    elementsList.add(new Grass(xPos, yPos, grassImage));
                    // new instance of grass
                    // added to list of elements to draw
                } else if (mapSymbol == 'S') {
                    elementsList.add(new Shrub(xPos, yPos, shrubImage));
                    // new instance of shrub
                } else if (mapSymbol == 'W') {
                    wizardSpawnPoint = new PVector(row, column);
                    xPos += (CELLSIZE - 48) / 2; // Center horizontally
                    yPos += (CELLSIZE - 48) / 2; // Center vertically
                    wizardHouseList.add(
                            new WizardHouse(xPos, yPos, wizardHouseImage));
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
    }

    public Buttons getUpgradeRangeButton() {
        return upgradeRangeButton;
    }

    public Buttons getBuildTowerButton() {
        return buildTowerButton;
    }

    public Buttons getUpgradeSpeedButton() {
        return upgradeSpeedButton;
    }

    public Buttons getUpgradeDamageButton() {
        return upgradeDamageButton;
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

    public ArrayList<Buttons> getButtonsList() {
        return buttonsList;
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
        
        loadImages();
        loadWizShrubPathImages();
        loadPathImages();

        config = loadJSONObject("config.json");
        parseConfig(config);

        levelLines = loadStrings(levelName);


        towerGrid = loadLevelData(levelLines);

        mapGrid = loadLevelData(levelLines);
        createMapElements(mapGrid);
        



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
        manaBar = new ManaBar(this, initialMana, initialManaCap, initialManaGainedPerSecond, manaPoolSpellInitialCost,
                manaPoolSpellCostIncreasePerUse, manaPoolSpellCapMultiplier, manaPoolSpellManaGainedMultiplier);
        toolTip = new ToolTip(manaBar);
        // Load images during setup
        // Eg:
        // loadImage("src/main/resources/WizardTD/tower0.png");
        // loadImage("src/main/resources/WizardTD/tower1.png");
        // loadImage("src/main/resources/WizardTD/tower2.png");
    }

    public void loadImages() {
        
        fireballSprite = loadImage("src/main/resources/WizardTD/fireball.png");
        PImage tower0 = loadImage("src/main/resources/WizardTD/tower0.png");
        towerImageList[0] = tower0;
        PImage tower1 = loadImage("src/main/resources/WizardTD/tower1.png");
        towerImageList[1] = tower1;
        PImage tower2 = loadImage("src/main/resources/WizardTD/tower2.png");
        towerImageList[2] = tower2;

        gremlinSprite = loadImage("src/main/resources/WizardTD/gremlin.png");
        beetleSprite = loadImage("src/main/resources/WizardTD/beetle.png");
        wormSprite = loadImage("src/main/resources/WizardTD/worm.png");

        gremlinDying1 = loadImage("src/main/resources/WizardTD/gremlin1.png");
        gremlinDying2 = loadImage("src/main/resources/WizardTD/gremlin2.png");
        gremlinDying3 = loadImage("src/main/resources/WizardTD/gremlin3.png");
        gremlinDying4 = loadImage("src/main/resources/WizardTD/gremlin4.png");
        gremlinDying5 = loadImage("src/main/resources/WizardTD/gremlin5.png");
    }

    public char[][] getTowerGrid() {
        return towerGrid;
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
                is2X = !is2X;
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
    // public void mousePressed(MouseEvent e) {
    public void mousePressed() {
        for (Buttons button : buttonsList) {
            if (button.isMouseOver()) {
                if (button.getIsToggled() == false)
                    // if (button.getLabel() == "M") {

                    // }
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

                        if (toolTip.toolTipCostCheck(rangeToggle, speedToggle, damageToggle, tower)) {
                            tower.upgradeRange();
                            tower.upgradeSpeed();
                            tower.upgradeDamage();
                        }

                    } else if (rangeToggle && speedToggle && !damageToggle) {

                        // tooltipCostCheck(true, true, false);
                        /// with arguments (range, speed, damage)
                        // it then reads the levels and then checks the cost. If there is insufficient,
                        // then it doesnt allow the lines below.
                        // tooltip.drawCost()

                        if (toolTip.toolTipCostCheck(rangeToggle, speedToggle, damageToggle, tower)) {
                            tower.upgradeRange();
                            tower.upgradeSpeed();
                        }

                    } else if (rangeToggle && !speedToggle && damageToggle) {

                        if (toolTip.toolTipCostCheck(rangeToggle, speedToggle, damageToggle, tower)) {
                            tower.upgradeRange();
                            tower.upgradeDamage();
                        }

                    } else if (!rangeToggle && speedToggle && damageToggle) {
                        if (toolTip.toolTipCostCheck(rangeToggle, speedToggle, damageToggle, tower)) {
                            tower.upgradeSpeed();
                            tower.upgradeDamage();
                        }

                    } else if (rangeToggle && !speedToggle && !damageToggle) {
                        if (toolTip.toolTipCostCheck(rangeToggle, speedToggle, damageToggle, tower)) {
                            tower.upgradeRange();
                        }
                    } else if (!rangeToggle && speedToggle && !damageToggle) {
                        if (toolTip.toolTipCostCheck(rangeToggle, speedToggle, damageToggle, tower)) {
                            tower.upgradeSpeed();
                        }
                    } else if (!rangeToggle && !speedToggle && damageToggle) {
                        if (toolTip.toolTipCostCheck(rangeToggle, speedToggle, damageToggle, tower)) {
                            tower.upgradeDamage();
                        }
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

            // not the right maths but in this case we can only initialise a max of level 1
            // tower so it works.
            int towerCost = initialTowerCost + 20 * (rangeLevel) + 20 * (speedLevel) + 20 * (damageLevel);

            if (ManaBar.getMana() > towerCost) {
                System.out.println("Tower built has levels:");
                System.out.println("range " + rangeLevel);
                System.out.println("speed " + speedLevel);
                System.out.println("damage " + damageLevel);
                System.out.println("Cost " + towerCost);

                Tower newTower = new Tower(this, towerXPos, towerYPos,
                        towerImageList, towerCost, initialTowerRange, initialTowerFiringSpeed, initialTowerDamage,
                        fireballSprite, fireballList, rangeLevel, speedLevel, damageLevel);

                ManaBar.decreaseMana((float) towerCost);
                towerList.add(newTower);
                towerGrid[gridRow][gridColumn] = 'T';
            }
        }
    }

    public ArrayList<MapElements> getElementsList() {
        return elementsList;
    }

    public static char[][] getMapGrid() {
        return mapGrid;
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

    public void parseConfig(JSONObject config) {

        levelName = config.getString("layout");
        initialTowerRange = config.getInt("initial_tower_range");
        initialTowerFiringSpeed = config.getFloat("initial_tower_firing_speed");
        initialTowerDamage = config.getInt("initial_tower_damage");
        initialMana = config.getInt("initial_mana");
        initialManaCap = config.getInt("initial_mana_cap");
        initialManaGainedPerSecond = config.getFloat("initial_mana_gained_per_second");
        initialTowerCost = config.getInt("tower_cost");
        manaPoolSpellInitialCost = config.getInt("mana_pool_spell_initial_cost");
        manaPoolSpellCostIncreasePerUse = config.getInt("mana_pool_spell_cost_increase_per_use");
        manaPoolSpellCapMultiplier = config.getFloat("mana_pool_spell_cap_multiplier");
        manaPoolSpellManaGainedMultiplier = config.getFloat("mana_pool_spell_mana_gained_multiplier");

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

                MonsterType newMonsterType = new MonsterType(type, hp, speed, armour, manaGainedOnKill, quantity);

                monsterTypeList.add(newMonsterType);

            }

            // System.out.println(monsterTypeList.size());
            // for (Monster monster : mons)

            // List<MonsterType> copyList = new ArrayList<>(monsterTypeList);
            // for (MonsterType monsterType : copyList) {
            // System.out.println(monsterType.getName());
            // }

            List<MonsterType> copyList = new ArrayList<>();
            copyList.addAll(monsterTypeList);
            if (this != null) {
                        Waves wave = new Waves(this, duration, preWavePause, copyList, MonsterSpawnPointsList, gremlinSprite,
                beetleSprite, wormSprite, manaBar, monsterList);
                waveList.add(wave);
            }

            
            // System.out.println(monsterTypeList.size());
            monsterTypeList.clear();

            // each wave has a monsterTypesList, and the wave class will have
            // a startWave method which will iterate through the monstertypes
            // list and based on the quantity, and the individual waves timings
            // spawn monsters randomly from the options it has.
            // so in app.java i can iterate through the waveList, so i keep track
            // of the number of waves and as i iterate through them i call
            // startWave method.

            // i have a waveList of waves. Each wave has its own Monstertype List.
            // I can iterate through each wave to start the wave, and then
            // for each wave, i can iterate through the monstertypeslist
            // and then create instances of monster class using getter methods from it
            // eg. for MonsterType monstertype : monsterTypeList {
            // Monster newMonster = new Monster(monstertype.getHP())
            // }
            //

        }
    }

    public static PImage getGremlinDying1() {
        return gremlinDying1;
    }

    public static PImage getGremlinDying2() {
        return gremlinDying2;
    }

    public static PImage getGremlinDying3() {
        return gremlinDying3;
    }

    public static PImage getGremlinDying4() {
        return gremlinDying4;
    }

    public static PImage getGremlinDying5() {
        return gremlinDying5;
    }

    public static void gameLost() {
        gameLost = true;
    }

    public void removeMonsters(Monster monsterToRemove, Monster monsterToSpawn) {
        System.out.println("this method called");
        removeMonsters = true;
    }

    public int getInitialTowerCost() {
        return initialTowerCost;
    }

    public String getLevelName() {
        return levelName;
    }

    public int getInitialTowerRange() {
        return initialTowerRange;
    }

    public int getInitialTowerDamage() {
        return initialTowerDamage;
    }

    public float getInitialTowerFiringSpeed() {
        return initialTowerFiringSpeed;
    }

    public int getInitialMana() {
        return initialMana;
    }

    public int getInitialManaCap() {
        return initialManaCap;
    }

    public float getInitialManaGainedPerSecond() {
        return initialManaGainedPerSecond;
    }

    public int getManaPoolSpellInitialCost() {
        return manaPoolSpellInitialCost;
    }

    public int getManaPoolSpellCostIncreasePerUse() {
        return manaPoolSpellCostIncreasePerUse;
    }

    public float getManaPoolSpellCapMultiplier() {
        return manaPoolSpellCapMultiplier;
    }

    public float getManaPoolSpellManaGainedMultiplier() {
        return manaPoolSpellManaGainedMultiplier;
    }

    public ArrayList<Waves> getWaveList() {
        return waveList;
    }

    private boolean gotDeathFrame = false;
    private int deathStartFrame;
    private long lastSecond = 0;
    private int waveDuration;
    private int currentTime;
    private boolean gotTimeBeforePaused = false;
    private int timeBeforePause;
    private boolean wasPaused = false;
    private int timePausedFor;
    private PImage horizontalPath;
    private PImage verticalPath;
    private PImage downT;
    private PImage leftT;
    private PImage upT;
    private PImage rightT;
    private PImage intersection;
    private PImage rightDown;
    private PImage downLeft;
    private PImage leftUp;
    private PImage upRight;

    // public void drawAnimation(String monsterType, float x, float y) {

    // if (monsterType.compareTo("gremlin") == 0) {
    // if (!gotDeathFrame) {
    // deathStartFrame = frameCount;
    // gotDeathFrame = true;
    // }

    // int frameDifference = frameCount - deathStartFrame;
    // int deathAnimationFrames = 20;

    // if (frameDifference < deathAnimationFrames) {
    // if (frameDifference < 4) {
    // System.out.println("playing 1");
    // image(gremlinDying1, x, y);
    // } else if (frameDifference < 8) {
    // System.out.println("playing 2");
    // image(gremlinDying2, x, y);
    // } else if (frameDifference < 12) {
    // System.out.println("playing 3");
    // image(gremlinDying3, x, y);
    // } else if (frameDifference < 16) {
    // System.out.println("playing 4");
    // image(gremlinDying4, x, y);
    // } else {
    // System.out.println("playing 5");
    // image(gremlinDying5, x, y);
    // }
    // } else {
    // gotDeathFrame = false;
    // return;

    // }
    // }

    // public void drawAnimation(Monster monster, float x, float y) {
    //     int deathAnimationFrames = 20; // Adjust the duration based on your preference

    //     if (!monster.gotDeathFrame()) {
    //         monster.setDeathStartFrame(frameCount);
    //         monster.setGotDeathFrame(true);
    //     }

    //     int frameDifference = frameCount - monster.getDeathStartFrame();

    //     if (frameDifference < deathAnimationFrames) {
    //         int frameIndex = floor(map(frameDifference, 0, deathAnimationFrames, 0, 5));
    //         PImage deathFrame = null;

    //         if (monster.getType().equals("gremlin")) {
    //             // Set the appropriate death frame based on the frame index
    //             switch (frameIndex) {
    //                 case 0:
    //                     deathFrame = gremlinDying1;
    //                     break;
    //                 case 1:
    //                     deathFrame = gremlinDying2;
    //                     break;
    //                 case 2:
    //                     deathFrame = gremlinDying3;
    //                     break;
    //                 case 3:
    //                     deathFrame = gremlinDying4;
    //                     break;
    //                 case 4:
    //                     deathFrame = gremlinDying5;
    //                     monster.setDeathAnimationPlayed(true);
    //                     break;
    //             }
    //         }

    //         if (deathFrame != null) {
    //             image(deathFrame, x, y);
    //         }
    //     } else {
    //         monster.setDeathAnimationPlayed(true);
    //         gotDeathFrame = false;
    //     }
    // }

    // if (monsterType.equals("gremlin")) {
    // // Load gremlin death animation frames as PImage variables
    // PImage[] deathFrames = new PImage[5];
    // deathFrames[0] = getGremlinDying1();
    // deathFrames[1] = getGremlinDying2();
    // deathFrames[2] = getGremlinDying3();
    // deathFrames[3] = getGremlinDying4();
    // deathFrames[4] = getGremlinDying5();

    // // Calculate the frame index based on elapsed time and custom frame rate (4
    // frames per second)
    // int customFrameRate = 4; // 4 frames per second
    // int frameIndex = (int)((millis() - waveFinishedAt) / (1000.0 /
    // customFrameRate));

    // // Display the current death frame
    // if (frameIndex >= 0 && frameIndex < deathFrames.length) {
    // System.out.println("animation played");
    // image(deathFrames[frameIndex], x, y);
    // } else {
    // // Animation is complete, do additional logic if needed
    // }
    // }

    // }

    public boolean isPaused() {
        return isPaused;
    }

    public PImage[] getTowerImageList() {
        return towerImageList;
    }

    public static boolean getIs2X() {
        return is2X;
    }

    @Override
    public void draw() {
        background(255);
        // System.out.println("1");
        for (Paths pathsToDraw : pathsList) {
            if (this != null) {
                pathsToDraw.draw(this);
            }
        }
        // System.out.println("2");
        for (MapElements elementToDraw : elementsList) {
            if ( this != null) {
                elementToDraw.draw(this);
            }
            
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
            if (tower.getIsHovered() == true && !gameLost) {
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
        if (this!= null) {
            manaBar.draw(this, 375, 9, 345, 22);
        }
        
        // manaBar.increaseMana(1);
        // System.out.println("6");
        if (buttonsList != null) {

        
        for (Buttons button : buttonsList) {
            if (button.getLabel() == "M") {
                button.changeText("Mana Pool cost: " + (int) manaBar.getManaPoolCost());
            }
            if (button != null) {
                button.drawText();
                button.drawButton();
                button.drawLabel();
            }
        }
    }

        if (!gameLost) {

            if (isPaused & !gotTimeBeforePaused) {

                timeBeforePause = millis();
                gotTimeBeforePaused = true;
                wasPaused = true;
            }

            if (!gameLost) {
                if (!isPaused) {
                    Waves wave = waveList.get(k);
                    wave.startWave();

                    if (monsterList.isEmpty() && k == waveList.size() - 1) {
                        System.out.println("YOU WON");
                        gameWon = true;
                    }

                    Waves.increaseFrameCounter();
                    // int totalPreviousDurations = wave.getDuration();

                    if (!waveChanged) {
                        waveStartTime = millis(); // Store the start time of the current wave
                        // System.out.println("wave" + (k+1) + "Start time " + waveStartTime);
                        // System.out.println("wave start time" + waveStartTime);
                        waveChanged = true;
                    }


                    waveDuration = wave.getDuration();

                    if (millis() - waveStartTime >= waveDuration * 1000 && k < waveList.size() - 1 && waveChanged) {

                        String countdownText = "Wave " + (k + 1) + " starts in "
                                + ((preWavePauseTime - (millis() - waveFinishedAt)) / 1000) + " seconds";
                        fill(0); // Set text color to white
                        textSize(20);
                        text(countdownText, 10, 25); // Adjust position as needed

                        if (!gotWaveFinishedTime) {
                            waveFinishedAt = millis();
                            // System.out.println(waveFinishedAt);
                            gotWaveFinishedTime = true;
                        }

                        Waves nextWave = waveList.get(k + 1);
                        preWavePauseTime = (int) (nextWave.getPreWavePause() * 1000);

                        if (millis() >= waveFinishedAt + preWavePauseTime) {
                            // We've waited for the pre-wave pause, so we can now move to the next wave.
                            System.out.println(millis());
                            System.out.println("NEW WAVE");
                            k++;
                            waveChanged = false;
                            gotWaveFinishedTime = false;
                        }
                    }
                }

                for (Monster monster : monsterList) {
                    monster.drawMonster();
                    // System.out.println("made it past here");
                    if (!isPaused) {
                        monster.moveMonster();

                        if (monster.getKillMonster()) {
                            monstersKilledList.add(monster);
                        }

                        if (monster.getHandleRespawn()) {
                            monstersToRespawnList.add(monster.getRespawnMonster());
                            monstersToRemoveList.add(monster);
                        }
                    }
                }
            }

            if (!isPaused) {

                for (Monster monster : monstersKilledList) {
                    System.out.println("12");
                    ManaBar.increaseMana((int) monster.getManaGainedOnKill());
                    monsterList.remove(monster);

                }

                monstersKilledList.clear();

                for (Monster monster : monstersToRespawnList) {
                    monsterList.add(monster);
                }

                monstersToRespawnList.clear();

                for (Monster monster : monstersToRemoveList) {

                    monsterList.remove(monster);
                }
            }

            for (Tower tower : towerList) {
                tower.drawTower();
                checkUpgradeStatus(tower);
            }
            // System.out.println("9");
            for (Buttons button : buttonsList) {
                // if it is NOT toggled, and the mouse is over it, turn it gray

                if (button.getIsToggled() && button.getLabel() == "M") {
                    ManaBar.manaSpell();
                    button.setIsToggled(false);
                } else if (!button.getIsToggled() && button.isMouseOver()) {
                    button.changeButtonColour(color(200));

                } else if (button.getIsToggled()) {
                    button.changeButtonColour(yellow);

                } else {
                    button.changeButtonColour(brown);
                }

                if (button.isMouseOver() && (button.getLabel() == "T" || button.getLabel() == "M")) {
                    drawButtonCost(button.getLabel());

                }
            }

            if (!isPaused) {

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

                for (Fireball fireball : fireballsToRemoveList) {
                    fireball.removeFireball();
                }
                // System.out.println("13");
                fireballsToRemoveList.clear();

            }
        }

        long currentTime = millis();

        // Check if a second has passed since the last update
        if (currentTime - lastSecond >= 1000) {
            // Add a trickle to ManaBar
            ManaBar.addTrickle();

            // Update the last timestamp to the current time
            lastSecond = currentTime;
        }
    }
    public ArrayList<Fireball> getFireballList() {
        return fireballList;
    }

    private void drawButtonCost(String label) {
        if (label == "T") {
            fill(255);

            rect(580, 160, 55, 15);
            strokeWeight(1);
            fill(0);
            textSize(12);

            text("Cost: " + initialTowerCost, 582, 170);

            // change fill to white
            // draw rectangle next to button
            // draw text on top of button
        } else if (label == "M") {
            fill(255);

            rect(580, 360, 55, 15);
            strokeWeight(1);
            fill(0);
            textSize(12);

            text("Cost: " + ManaBar.getManaPoolSpellCost(), 582, 370);

        }

    }

    private void checkUpgradeStatus(Tower tower) {

        if (tower.isMouseOver()) {
            boolean rangeToggle = upgradeRangeButton.getIsToggled();
            boolean speedToggle = upgradeSpeedButton.getIsToggled();
            boolean damageToggle = upgradeDamageButton.getIsToggled();

            if (!buildTowerButton.getIsToggled() && (rangeToggle || speedToggle || damageToggle)) {
                toolTip.drawToolTip(this, rangeToggle, speedToggle, damageToggle, tower);
                // could use checkCostToolTip to extend the tooltip table. e.g. I can use that
            }
        } else {
            return;
        }
    }

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

    public static ArrayList<Monster> getMonsterList() {
        return monsterList;
    }

    public void loadWizShrubPathImages() {
        grassImage = loadImage("src/main/resources/WizardTD/grass.png");
        shrubImage = loadImage("src/main/resources/WizardTD/shrub.png");
        wizardHouseImage = loadImage("src/main/resources/WizardTD/wizard_house.png");
    }

    public void loadPathImages() {
        horizontalPath = loadImage("src/main/resources/WizardTD/path0.png");
        verticalPath = rotateImageByDegrees(horizontalPath, 90);
        downT = loadImage("src/main/resources/WizardTD/path2.png");
        leftT = rotateImageByDegrees(downT, 90);
        upT = rotateImageByDegrees(downT, 180);
        rightT = rotateImageByDegrees(downT, 270);
        intersection = loadImage("src/main/resources/WizardTD/path3.png");
        rightDown = loadImage("src/main/resources/WizardTD/path1.png");
        downLeft = rotateImageByDegrees(rightDown, 90);
        leftUp = rotateImageByDegrees(rightDown, 180);
        upRight = rotateImageByDegrees(rightDown, 270);
    }

}
