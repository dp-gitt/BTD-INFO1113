package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.util.*;

public class App extends PApplet {

    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;
    static boolean gameLost = false;
    int preWavePauseTime;
    int currentWaveIndex = 0;
    private SoundEffects soundEffects;
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

    public char[][] towerGrid;
    public static PVector wizardSpawnPoint;
    public Buttons upgradeRangeButton;
    public Buttons buildTowerButton;
    private PImage gremlinSprite;
    private PImage beetleSprite;
    private int initialTowerCost;
    public Random random = new Random();
    public static char[][] mapGrid;

    private PImage wormSprite;
    private ArrayList<MonsterType> monsterTypeList = new ArrayList<MonsterType>();
    private PImage[] towerImageList = new PImage[3];
    // private int m;

    // private static final String levelName = "level2.txt";
    private String levelName;

    public int firstSpawnVecX;
    public int firstSpawnVecY;

    public ManaBar manaBar;
    private boolean isPaused = false;
    private int yellow = color(255, 255, 0);
    private int brown = color(132, 115, 74);
    private boolean towerMode;
    public ArrayList<Fireball> fireballList = new ArrayList<Fireball>();
    private Buttons twoXButton;
    private Buttons pauseButton;
    public Buttons upgradeSpeedButton;
    public Buttons upgradeDamageButton;
    public Buttons manaPoolButton;

    public ArrayList<Monster> monstersToRemoveList = new ArrayList<>();
    public ArrayList<Monster> monstersToRespawnList = new ArrayList<>();

    private JSONObject config;

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
    private int initialTowerRange;
    private int initialTowerDamage;
    private float initialTowerFiringSpeed;
    private int initialMana;
    private int initialManaCap;
    private float initialManaGainedPerSecond;
    public static boolean is2X = false;
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
    private String[] levelLines;

    private long lastSecond = 0;
    private int waveDuration;
    private boolean gotTimeBeforePaused = false;
    private int timeBeforePause;
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
    private boolean gameJustLost;

    public static PVector getWizardSpawnPoint() {
        return wizardSpawnPoint;
    }

    /**
     * Called when the build tower button is toggled
     * displays a hover guider for placing towers.
     * 
     * @param towerList ArrayList of currently existing towers
     */
    public void drawHoverExtension(ArrayList<Tower> towerList) {
        int gridRow = (int) (mouseY / CELLSIZE);
        gridRow -= 1;
        int gridColumn = (int) (mouseX / CELLSIZE);
        // System.out.println(gridRow);
        // System.out.println(gridColumn);
        // createTower(gridRow, gridColumn);

        if (buildTowerButton.getIsToggled()) {

            if (isTileEmpty(gridRow, gridColumn)) {
                // draw green hover over the cell.
                fill(0, 255, 0, 100); // Green with alpha
            } else {
                // draw red hover
                fill(255, 0, 0, 100); // Red with alpha
            }

            if (mouseX < 640 && mouseY > 40) {
                rect(gridColumn * CELLSIZE, gridRow * CELLSIZE + 40, CELLSIZE, CELLSIZE);

            }
        }
    }

    /**
     * Extracts all the individual symbols located inside a
     * level file, and stores them into a 2D matrix such that the symbols
     * can be individually iterated through and the correct image is drawn.
     * 
     * @param levelLines List of strings representing each line in the level file
     * @return 2D character matrix representing level file data
     */

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

    /**
     * Iterates through a 2D matrix's rows and columns to determine the position
     * of different symbols according to their rows and columns with
     * (' ', 'S', 'W', and 'X') representing a grass tile, shrub, wizard house,
     * and path respectively. Consequently, each tile will result in a respective
     * instantiation of its class.
     * 
     * Calls createMapPaths() method
     * 
     * @param levelDataMatrix 2D character matrix representing individual characters
     *                        in the level file
     */

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
                        MonsterSpawnPointsList.add(new PVector(row, column));
                    } else if (column == 0 || column == 19) {
                        MonsterSpawnPointsList.add(new PVector(row, column));
                    }
                }
            }

        }
    }

    /**
     * Accesses the character in the specified row and column and analyses its
     * surrounding characters
     * to determine the correct path sprite, and its relevant rotation.
     * 
     * @param levelDataMatrix 2D character matrix representing individual characters
     *                        in the level file
     * @param row             the row to access in levelDataMatrix
     * @param column          the column to access in levelDataMatrix
     * 
     */
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

    /**
     * Calls the Buttons constructor and instantiates the 7 buttons
     * with their relative attributes, such as x and y values,
     * and their colours so that they can be drawn correctly.
     */

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

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setTowerMode(boolean towerMode) {
        this.towerMode = towerMode;
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

        soundEffects = new SoundEffects();

        textFont(createFont("Arial", 12));
        noStroke();
        textAlign(LEFT);
        createButtons();
        manaBar = new ManaBar(this, initialMana, initialManaCap, initialManaGainedPerSecond, manaPoolSpellInitialCost,
                manaPoolSpellCostIncreasePerUse, manaPoolSpellCapMultiplier, manaPoolSpellManaGainedMultiplier);
        toolTip = new ToolTip(manaBar);
    }

    public boolean isTowerMode() {
        return towerMode;
    }

    /**
     * Loads all the required sprites for the game. Also loads and appends the
     * different
     * tower sprites in the correct order into a list, which is used for drawing
     * the towers created according to their level.
     */

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

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed() {
        // to upgrade a tower, need:
        // not in tower toggle mode
        // be in tower upgrade Toggled
        // need to press on the tower. check towerList

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

    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased() {

    }

    /**
     * Recieves mouse movement signals.
     */

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

    /**
     * Recieves mouse press signals
     */
    @Override
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
                    // System.out.println(isPaused);
                }

                if (button.getLabel() == "T") {
                    // System.out.println("Button T pressed");
                    towerMode = !towerMode;
                    // System.out.println(towerMode);
                }

            }
        }

        if (buildTowerButton.getIsToggled()) {
            int gridRow = (int) (mouseY / CELLSIZE);
            gridRow -= 1;
            int gridColumn = (int) (mouseX / CELLSIZE);

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
                            soundEffects.playTowerUpgradeSound();
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
                            soundEffects.playTowerUpgradeSound();
                        }

                    } else if (rangeToggle && !speedToggle && damageToggle) {

                        if (toolTip.toolTipCostCheck(rangeToggle, speedToggle, damageToggle, tower)) {
                            tower.upgradeRange();
                            tower.upgradeDamage();
                            soundEffects.playTowerUpgradeSound();
                        }

                    } else if (!rangeToggle && speedToggle && damageToggle) {
                        if (toolTip.toolTipCostCheck(rangeToggle, speedToggle, damageToggle, tower)) {
                            tower.upgradeSpeed();
                            tower.upgradeDamage();
                            soundEffects.playTowerUpgradeSound();
                        }

                    } else if (rangeToggle && !speedToggle && !damageToggle) {
                        if (toolTip.toolTipCostCheck(rangeToggle, speedToggle, damageToggle, tower)) {
                            tower.upgradeRange();
                            soundEffects.playTowerUpgradeSound();
                        }
                    } else if (!rangeToggle && speedToggle && !damageToggle) {
                        if (toolTip.toolTipCostCheck(rangeToggle, speedToggle, damageToggle, tower)) {
                            tower.upgradeSpeed();
                            soundEffects.playTowerUpgradeSound();
                        }
                    } else if (!rangeToggle && !speedToggle && damageToggle) {
                        if (toolTip.toolTipCostCheck(rangeToggle, speedToggle, damageToggle, tower)) {
                            tower.upgradeDamage();
                            soundEffects.playTowerUpgradeSound();
                        }
                    }
                }

            }
        }
    }

    public static void setIs2X(boolean is2x) {
        is2X = is2x;
    }

    /**
     * Recieves mouse release signals
     */
    @Override
    public void mouseReleased() {
    }

    public boolean isTileEmpty(int row, int col) {
        if (row >= 0 && row < towerGrid.length && col >= 0 && col < towerGrid[0].length) {
            return towerGrid[row][col] != 'S' && towerGrid[row][col] != 'T' && towerGrid[row][col] != 'X';
        }
        return false; // invalid coordinates. e.g. outside map
    }

    /**
     * Accesses the character specified by the given row and column within
     * the 2D map matrix, to determine if a tower can be placed at this location.
     * If the tower can be placed, a new tower is instantiated, and the relevant
     * cost
     * is calculated and deducted from the player's mana. Plays a feedback sound if
     * tower is successfully placed.
     * 
     * @param gridRow    specified row within towerGrid to place tower
     * @param gridColumn specified column within towerGrid to place tower
     */
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
                Tower newTower = new Tower(this, towerXPos, towerYPos,
                        towerImageList, towerCost, initialTowerRange, initialTowerFiringSpeed, initialTowerDamage,
                        fireballSprite, fireballList, rangeLevel, speedLevel, damageLevel);

                soundEffects.playTowerPlaceSound();

                ManaBar.decreaseMana((float) towerCost);
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
     * Parses through the specified config file, by accessing each JSONArray, and
     * its
     * objects. It will store the read values into the relevant attributes.
     * Its logic follows by parsing through each wave, and its attributes
     * such as pre_wave_pause_time and monsterTypes, and will call the MonsterType
     * constructor
     * to initialise any monsterTypes and their attributes for that specific wave.
     * This is followed by
     * the instantiation of the waves themselves, as specified in the config.
     * 
     * @param config The config file; initialised as a JSONObject.
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

            List<MonsterType> copyList = new ArrayList<>();
            copyList.addAll(monsterTypeList);
            if (this != null) {
                Waves wave = new Waves(this, duration, preWavePause, copyList, MonsterSpawnPointsList, gremlinSprite,
                        beetleSprite, wormSprite, manaBar, monsterList);
                waveList.add(wave);
            }

            monsterTypeList.clear();

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

    public ArrayList<MapElements> getElementsList() {
        return elementsList;
    }

    public static char[][] getMapGrid() {
        return mapGrid;
    }

    public int getInitialTowerCost() {
        return initialTowerCost;
    }

    public String getLevelName() {
        return levelName;
    }

    public ArrayList<Buttons> getButtonsList() {
        return buttonsList;
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

    public char[][] getTowerGrid() {
        return towerGrid;
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

    public boolean isPaused() {
        return isPaused;
    }

    public PImage[] getTowerImageList() {
        return towerImageList;
    }

    public static boolean getIs2X() {
        return is2X;
    }

    public Buttons getTwoXButton() {
        return twoXButton;
    }

    public PImage getFireballSprite() {
        return fireballSprite;
    }

    public ArrayList<Tower> getTowerList() {
        return towerList;
    }

    public SoundEffects getSoundEffects() {
        return soundEffects;
    }

    public static boolean isGameLost() {
        return gameLost;
    }

    public static void setGameLost(boolean gameLost) {
        App.gameLost = gameLost;
    }

    public ArrayList<Fireball> getFireballList() {
        return fireballList;
    }

    public static void main(String[] args) {
        PApplet.main("WizardTD.App");
    }

    /**
     * Draw all elements in the game by current frame.
     */

    @Override
    public void draw() {
        System.out.println(mouseX);
        System.out.println(mouseY);

        background(255);
        for (Paths pathsToDraw : pathsList) {
            if (this != null) {
                pathsToDraw.draw(this);
            }
        }

        for (MapElements elementToDraw : elementsList) {
            if (this != null) {
                elementToDraw.draw(this);
            }

        }

        for (WizardHouse wizardHouse : wizardHouseList) {
            int wizardXPos = wizardHouse.getXPos();
            int wizardYPos = wizardHouse.getYpos();
            MapElements grassUnderWizard = new Grass(wizardXPos, wizardYPos,
                    loadImage("src/main/resources/WizardTD/grass.png"));
            grassUnderWizard.draw(this);
            wizardHouse.draw(this);
        }

        for (Tower tower : towerList) {
            if (tower.getIsHovered() == true && !gameLost) {
                tower.drawRadius();
            }
        }

        rect(640, 40, 120, 640);
        fill(132, 115, 74);

        // side and top backgrounds
        rect(640, 40, 120, 640);
        fill(132, 115, 74);
        rect(0, 0, 760, 40);
        fill(132, 115, 74);
        if (this != null) {
            manaBar.draw(this, 375, 9, 345, 22);
        }

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
            }

            if (!gameLost) {
                if (!isPaused) {

                    if (gameJustLost) {
                        k = 0;
                        waveChanged = false;
                        gotWaveFinishedTime = false;
                        gameJustLost = false;
                    }

                    Waves wave = waveList.get(k);
                    wave.startWave();

                    if (monsterList.isEmpty() && k == waveList.size() - 1) {
                        System.out.println("YOU WON");
                        gameWon = true;
                    }

                    Waves.increaseFrameCounter();

                    if (!waveChanged) {
                        waveStartTime = millis(); // Store the start time of the current wave
                        waveChanged = true;
                    }

                    waveDuration = wave.getDuration();

                    if (millis() - waveStartTime >= waveDuration * 1000 && k < waveList.size() - 1 && waveChanged) {

                        String countdownText = "Wave " + (k + 2) + " starts in "
                                + ((preWavePauseTime - (millis() - waveFinishedAt)) / 1000) + " seconds";
                        fill(0); // Set text color to white
                        textSize(20);
                        text(countdownText, 10, 25); // Adjust position as needed

                        if (!gotWaveFinishedTime) {
                            waveFinishedAt = millis();
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

            drawHoverExtension((towerList));

            for (Tower tower : towerList) {
                tower.drawTower();
                checkUpgradeStatus(tower);
            }
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

        if (gameLost) {
            // Display "Game Over" message
            background(255);
            fill(0, 20, 30); // Set text color to white
            textSize(32);
            textAlign(CENTER, CENTER);
            text("Game Over", width / 2, height / 2);
            text("Press 'R' to restart", width / 2, height / 2 + 40);
            if (key == 'r') {
                resetGame();
            }
            textAlign(LEFT);

        }
    }

    /**
     * Handles the game restarting and resets any altered game logic since setup.
     * Clears all lists, storing monster data and tower data, and resets wave logic.
     */
    public void resetGame() {

        for (Waves wave : waveList) {
            List<Monster> copyList = wave.getCopyList();
            List<Monster> localMonsterTypeList = wave.getMonsterTypeList();
            // wave.frameCounter = 0;

            copyList.clear();
            localMonsterTypeList.clear();
        }

        gameJustLost = true;

        monsterList.clear();
        monstersToRemoveList.clear();
        monstersKilledList.clear();
        monstersToRespawnList.clear();
        monsterTypeList.clear();
        towerList.clear();
        waveList.clear();
        parseConfig(config);
        towerGrid = loadLevelData(levelLines);
        mapGrid = loadLevelData(levelLines);
        gameLost = !gameLost;
        is2X = false;
        isPaused = false;
        twoXButton.setIsToggled(false);
        pauseButton.setIsToggled(false);
        buildTowerButton.setIsToggled(false);
        manaBar = new ManaBar(this, initialMana, initialManaCap, initialManaGainedPerSecond, manaPoolSpellInitialCost,
                manaPoolSpellCostIncreasePerUse, manaPoolSpellCapMultiplier, manaPoolSpellManaGainedMultiplier);
        toolTip = new ToolTip(manaBar);
    }

    /**
     * Draws the cost of the button's action beside it in text
     * that is overlayed on top of a small box.
     * 
     * @param label label attribute of the relevant button
     */

    public void drawButtonCost(String label) {
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

    /**
     * If the user's mouse is over the specified tower,
     * and at least one upgrade button is toggled, a tooltip will be drawn
     * to display the cost of applying the toggled upgrade(s) onto the specified
     * tower.
     * 
     * @param tower Tower object to check upgrade status
     */

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

    public Buttons getPauseButton() {
        return pauseButton;
    }

    public Buttons getManaPoolButton() {
        return manaPoolButton;
    }
}
