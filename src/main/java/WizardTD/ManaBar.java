package WizardTD;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class ManaBar {
    public static float maxMana;
    public static float currMana;
    public static float manaGainedPerSecond;
    public static int manaPoolSpellCost;
    public static int manaPoolSpellCostIncreasePerUse;
    public static float manaPoolSpellCapMultiplier;
    public static float manaPoolSpellManaGainedMultiplier;
    private static App app;
    private static ArrayList<Waves> waveList;
    private static List<MonsterType> monsterTypeList;

    /**
     * Constructs a ManaBar object with the specified parameters.
     *
     * @param app                               The PApplet used for rendering and
     *                                          interaction.
     * @param initialMana                       The initial mana available in the
     *                                          ManaBar.
     * @param maxMana                           The maximum mana capacity of the
     *                                          ManaBar.
     * @param initialManaGainedPerSecond        The initial rate at which mana is
     *                                          gained per second.
     * @param manaPoolSpellInitialCost          The initial cost of the mana pool
     *                                          spell.
     * @param manaPoolSpellCostIncreasePerUse   The amount by which the mana pool
     *                                          spell cost increases with each use.
     * @param manaPoolSpellCapMultiplier        The multiplier that affects the mana
     *                                          pool spell's capacity.
     * @param manaPoolSpellManaGainedMultiplier The multiplier that affects the mana
     *                                          gained with the mana pool spell.
     */
    public ManaBar(App app, int initialMana, int maxMana, float initialManaGainedPerSecond,
            int manaPoolSpellInitialCost, int manaPoolSpellCostIncreasePerUse, float manaPoolSpellCapMultiplier,
            float manaPoolSpellManaGainedMultiplier) {
        ManaBar.maxMana = maxMana;
        ManaBar.currMana = initialMana;
        ManaBar.manaGainedPerSecond = initialManaGainedPerSecond;
        ManaBar.manaPoolSpellCost = manaPoolSpellInitialCost;
        ManaBar.manaPoolSpellCostIncreasePerUse = manaPoolSpellCostIncreasePerUse;
        ManaBar.manaPoolSpellCapMultiplier = manaPoolSpellCapMultiplier;
        ManaBar.manaPoolSpellManaGainedMultiplier = manaPoolSpellManaGainedMultiplier;
        ManaBar.app = app;
    }

    /**
     * Retrieves the cost of the mana pool spell.
     *
     * @return The cost of the mana pool spell.
     */
    public static int getManaPoolSpellCost() {
        return manaPoolSpellCost;
    }

    /**
     * Increases the current mana by the specified amount, capping it at the maximum
     * mana capacity.
     *
     * @param amount The amount of mana to increase.
     */
    public static void increaseMana(int amount) {
        int copyCurrMana = (int) currMana;
        copyCurrMana += amount;
        if ((copyCurrMana += amount) > maxMana) {
            currMana = maxMana; // Cap mana at max
        } else {
            currMana += amount;
        }
    }

    /**
     * Decreases the current mana by the specified amount, disallowing decreases
     * that would result in a current mana value less than 0.
     * 
     * @param amount The amount of mana to decrease
     */
    public static void decreaseMana(float amount) {
        if (currMana - amount < 0) {
            return;
        } else {
            currMana -= amount;
        }
    }

    /**
     * Adds mana to the current mana pool at a specified rate per second, up to the
     * maximum mana capacity.
     */
    public static void addTrickle() {
        if (currMana + manaGainedPerSecond > maxMana) {
            return;
        } else {
            currMana += manaGainedPerSecond;
        }

    }

    /**
     * Executes the mana pool spell, spending mana and applying various effects.
     */
    public static void manaSpell() {
        if (currMana - manaPoolSpellCost <= 0) {
            return;
        } else {

            currMana -= manaPoolSpellCost;
            manaPoolSpellCost += manaPoolSpellCostIncreasePerUse;

            maxMana *= manaPoolSpellCapMultiplier;
            manaPoolSpellCapMultiplier += 0.1;
            // System.out.println(manaGainedPerSecond);
            manaGainedPerSecond *= manaPoolSpellManaGainedMultiplier;
            // System.out.println(manaGainedPerSecond);
            waveList = app.getWaveList();

            for (Waves wave : waveList) {
                monsterTypeList = wave.getMonsterList();
                for (MonsterType monsterType : monsterTypeList) {
                    monsterType.multiplyManaGainedOnKill(manaPoolSpellManaGainedMultiplier);
                }
            }
            // from every wave in waveList go to the monsterTYpeList, and then from that,
            // search through the list, and increment the value of managainedonkill

            manaPoolSpellManaGainedMultiplier += 0.1;
        }
    }

    /**
     * Draws the ManaBar UI on the screen at the specified position and dimensions.
     *
     * @param app    The PApplet used for rendering.
     * @param x      The X-coordinate of the top-left corner of the UI.
     * @param y      The Y-coordinate of the top-left corner of the UI.
     * @param width  The width of the UI.
     * @param height The height of the UI.
     */

    public void draw(PApplet app, float x, float y, float width, float height) {
        app.textSize(18);
        app.stroke(0);
        app.strokeWeight(2);
        app.fill(255);
        app.rect(x, y, width, height);

        float barWidth = (currMana / maxMana) * width;
        app.fill(0, 214, 214);
        app.rect(x, y, barWidth, height);

        app.fill(0);
        float manaNumberWidth = app.textWidth(currMana + "/" + maxMana);
        app.text((int) currMana + "/" + (int) maxMana, x + (width - manaNumberWidth) / 2,
                y + height / 2 + app.textAscent() / 2 - 2);
        app.noStroke();
        float manaTextWidth = app.textWidth("MANA");
        app.text("MANA: ", x - manaTextWidth - 10, y + height / 2 + app.textAscent() / 2 - 2);
        app.textSize(12);
    }

    /**
     * Retrieves the current mana
     * 
     * @return The current mana
     */
    public static float getMana() {
        return currMana;
    }

    /**
     * Retrieves the maximum mana capacity of the mana pool.
     *
     * @return The maximum mana capacity.
     */
    public float getMaxMana() {
        return maxMana;
    }

    /**
     * Retrieves the cost of the mana pool spell.
     *
     * @return The cost of the mana pool spell.
     */
    public float getManaPoolCost() {
        return manaPoolSpellCost;
    }

    /**
     * Sets the current mana value as specified
     * 
     * @param i The amount to set the current mana
     */
    public static void setMana(int i) {
        currMana = i;
    }

}
