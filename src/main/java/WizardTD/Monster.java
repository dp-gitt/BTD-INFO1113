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
    private ArrayList<Point> monsterPathList = new ArrayList<>();
    private PImage sprite;
    private ManaBar manaBar;
    private ArrayList<Monster> monsterList;
    boolean killMonster = false;
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
    int monsterDead = 0;
    int i = 0;
    private boolean handleRespawn;
    private boolean deathAnimationPlayed = false;
    private boolean finishedAnimation = false;
    private boolean gotDeathFrame = false;
    private int deathStartFrame;

    /**
     * Constructs a new Monster object.
     * 
     * @param app              The PApplet instance to which this button belongs.
     * @param type             The type of the monster
     * @param maxHp            The maximum health points of the monster
     * @param speed            The speed of the monster, in pixels per frame
     * @param armour           The armour of the monster
     * @param manaGainedOnKill The mana increased in the ManaBar for killing the
     *                         monster
     * @param spawnPoint       The spawn point of the monster
     * @param wizardSpawnPoint The spawnpoint of the wizard house (destination)
     * @param map              A 2D Map grid representing the map in tiles of 32x32
     *                         pixels each.
     * @param sprite           The PImage represneting the sprite of the monster
     * @param manaBar          The manaBar instance this monster will affect when
     *                         killed
     * @param monsterList      A list of all currently spawned Monsters
     * @param currHp           The current/initial health points of the monster
     */
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
        this.y = (spawnPoint.x + 1) * 32 + 16; // Centered vertically on the spawn row
        this.x = (spawnPoint.y) * 32 + 8; // Centered horizontally on the spawn column
        this.sprite = sprite;
        this.manaBar = manaBar;
        this.monsterList = monsterList;
        this.currHp = currHp;
    }

    /**
     * Utilises the monster's current x and y position to determine the path to the
     * Wizard House as a list of points
     * relative to the map specified.
     * 
     * @return Returns an ArrayList of the points (in order) in the specified map
     *         grid that the monster must follow
     *         in order to reach the Wizard House from its current point
     */

    public ArrayList<Point> determineMonsterPath() {
        Point start = new Point((int) spawnPoint.y, (int) spawnPoint.x, null);
        Point end = new Point((int) wizardSpawnPoint.y, (int) wizardSpawnPoint.x, null);
        List<Point> path = Point.FindPath(this.map, start, end);

        if (path != null) {
            for (Point point : path) {
                monsterPathList.add(point);
            }
        } else {
            System.out.println("No path found");
        }
        return monsterPathList;
    }

    /**
     * Utilises the monsterPathList genereated by the determineMonsterPath() to move
     * the monster
     * to its next position at the speed specified in its attribute. If the monster
     * has reached its
     * destination, then it will automatically be banished and respawned, with mana
     * deducted from
     * the specified manaBar. If there is not enough mana, then it will trigger
     * App.gameLost().
     */
    public void moveMonster() {

        float calcSpeed = speed;

        if (App.getIs2X()) {
            calcSpeed *= 2 * speed;
        }
        if (!monsterPathList.isEmpty()) {

            Point target = monsterPathList.get(0);

            float targetX = (target.x) * 32 + 8; // Calculate the target x-coordinate
            float targetY = (target.y + 1) * 32 + 16; // Calculate the target y-coordinate

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

    /**
     * Draws the monster's sprite onto the screen, at the specified x and y values.
     * It will also
     * display the health bar above the monster, relative to its current position,
     * as a portion of its
     * current health, and initial health. If the monster is dead (no health
     * points), then a death
     * animation will be played for the monster.
     */
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
            int deathAnimationFrames = 20; // Adjust the duration based on your preference

            // Display different dying images based on the frame difference

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
                } else {
                    app.image(gremlinDying5, x, y);
                }
            } else {
                // Dying animation is finished
                deathAnimationPlayed = true;
                isDying = false;
                killMonster = true;

            }
            return;
        }

        app.image(sprite, x, y);

        if (currHp > 0) { // Only draw health bars if current HP is greater than 0
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

    /**
     * Gets the X-coordinate of the monster's position.
     *
     * @return The X-coordinate of the monster's position.
     */
    public float getX() {
        return this.x;
    }

    /**
     * Gets the Y-coordinate of the monster's position.
     *
     * @return The Y-coordinate of the monster's position.
     */
    public float getY() {
        return this.y;
    }

    /**
     * Decreases the monster's health by the specified amount.
     *
     * @param damage The amount of damage to decrease the health.
     */
    public void decreaseHealth(int damage) {
        currHp -= damage;
        if (currHp <= 0 && !isDying) {
            isDying = true;
            killMonster = false;
            dyingStartFrame = app.frameCount; // Record the frame when dying starts
        }
    }

    /**
     * Creates a respawned monster based on the original monster's attributes.
     * 
     */
    public void createRespawnMonster() {
        respawnedMonster = new Monster(app, type, maxHp, speed, armour, (int) manaGainedOnKill, spawnPoint,
                wizardSpawnPoint, map, sprite, manaBar, monsterList, currHp);
        respawnedMonster.determineMonsterPath();

        handleRespawn = true;
    }

    /**
     * Indicates whether the respawned monster should be handled.
     *
     * @return true if the respawned monster should be handled, false otherwise.
     */
    public boolean getHandleRespawn() {
        return handleRespawn;
    }

    /**
     * Gets the respawned monster if it exists.
     *
     * @return The respawned monster or null if not applicable.
     */
    public Monster getRespawnMonster() {
        return respawnedMonster;
    }

    /**
     * Checks if the monster should be killed.
     *
     * @return true if the monster should be killed, false otherwise.
     */
    public boolean getKillMonster() {
        return killMonster;
    }

    /**
     * Gets the armor value of the monster.
     *
     * @return The armor value of the monster.
     */
    public float getArmour() {
        return armour;
    }

    /**
     * Gets the mana gained by killing this monster.
     *
     * @return The mana gained on killing the monster.
     */
    public float getManaGainedOnKill() {
        return manaGainedOnKill;
    }

    /**
     * Checks if the death animation has been played.
     *
     * @return true if the death animation has been played, false otherwise.
     */
    public boolean getDeathAnimationPlayed() {
        return deathAnimationPlayed;
    }

    /**
     * Gets the type of the monster.
     *
     * @return The type of the monster.
     */
    public String getType() {
        return type;
    }

    /**
     * Indicates whether the death animation has finished playing.
     *
     * @return true if the death animation has finished playing, false otherwise.
     */
    public boolean getFinishedAnimation() {
        return finishedAnimation;
    }

    /**
     * Checks if the monster has reached the death frame.
     *
     * @return true if the monster has reached the death frame, false otherwise.
     */
    public boolean gotDeathFrame() {
        return gotDeathFrame;
    }

    /**
     * Sets the frame when the monster's death animation starts.
     *
     * @param frameCount The frame count when the death animation starts.
     */
    public void setDeathStartFrame(int frameCount) {
        deathStartFrame = frameCount;
    }

    /**
     * Indicates whether the monster has reached the death frame.
     *
     * @param b true if the monster has reached the death frame, false otherwise.
     */
    public void setGotDeathFrame(boolean b) {
        gotDeathFrame = true;
    }

    /**
     * Sets whether the death animation has been played.
     *
     * @param p true if the death animation has been played, false otherwise.
     */
    public void setDeathAnimationPlayed(boolean p) {
        deathAnimationPlayed = p;
    }

    /**
     * Gets the frame when the monster's death animation starts.
     *
     * @return The frame count when the death animation starts.
     */
    public int getDeathStartFrame() {
        return deathStartFrame;
    }

    /**
     * Multiplies the mana gained on killing the monster by the specified
     * multiplier.
     *
     * @param manaPoolSpellManaGainedMultiplier The multiplier to apply to mana
     *                                          gained on kill.
     */
    public void multiplyManaGainedOnKill(float manaPoolSpellManaGainedMultiplier) {
        manaGainedOnKill *= manaPoolSpellManaGainedMultiplier;
    }

}