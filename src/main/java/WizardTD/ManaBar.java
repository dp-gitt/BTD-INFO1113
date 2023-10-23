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
    private static ArrayList<Monster> monsterList;
    private static App app;
    private static ArrayList<Waves> waveList;
    private static List<MonsterType> monsterTypeList;

    public ManaBar(App app,int initialMana ,int maxMana, float initialManaGainedPerSecond, int manaPoolSpellInitialCost, int manaPoolSpellCostIncreasePerUse, float manaPoolSpellCapMultiplier, float manaPoolSpellManaGainedMultiplier) {
        ManaBar.maxMana = maxMana;
        ManaBar.currMana = initialMana;
        ManaBar.manaGainedPerSecond = initialManaGainedPerSecond;
        ManaBar.manaPoolSpellCost = manaPoolSpellInitialCost;
        ManaBar.manaPoolSpellCostIncreasePerUse = manaPoolSpellCostIncreasePerUse;
        ManaBar.manaPoolSpellCapMultiplier = manaPoolSpellCapMultiplier;
        ManaBar.manaPoolSpellManaGainedMultiplier = manaPoolSpellManaGainedMultiplier;
        ManaBar.app = app;
    }


    public static int getManaPoolSpellCost() {
        return manaPoolSpellCost;
    }


    public static void increaseMana(int amount) {
        int copyCurrMana = (int) currMana;
        copyCurrMana += amount;
        if ((copyCurrMana += amount) > maxMana) {
            currMana = maxMana; // Cap mana at max
        } else {
            currMana += amount;
        }
    }

    public static void decreaseMana(float amount) {
        if (currMana - amount < 0) {
            return;
        } else {
            currMana -= amount;
        }
    }

    public static float getMana() {
        return currMana;
    } 

    public float getMaxMana() {
        return maxMana;
    }

    public static void addTrickle() {
        if (currMana + manaGainedPerSecond > maxMana) {
            return;
        } else {
            currMana += manaGainedPerSecond;
        }
        
    }

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
                    System.out.println("before " + monsterType.getManaGainedOnKill());
                    monsterType.multiplyManaGainedOnKill(manaPoolSpellManaGainedMultiplier);
                    System.out.println("after" + monsterType.getManaGainedOnKill());
                    // System.out.println("it is before " + monster.getManaGainedOnKill());
                    // monster.multiplyManaGainedOnKill(manaPoolSpellManaGainedMultiplier);
                    // System.out.println("it is after " + monster.getManaGainedOnKill());
                }
            }
            // from every wave in waveList go to the monsterTYpeList, and then from that, search through the list, and increment the value of managainedonkill
            
            // monsterList = App.getMonsterList();
            // for (Monster monster : monsterList) {
            //     monster.multiplyManaGainedOnKill(manaPoolSpellManaGainedMultiplier);
            // }
            manaPoolSpellManaGainedMultiplier += 0.1;
        }
    }

    public float getManaPoolCost() {
        return manaPoolSpellCost;
    }

    public void draw(PApplet app, float x, float y, float width, float height) {
        app.textSize(18);
        app.stroke(0);
        app.strokeWeight(2);
        app.fill(255);
        app.rect(x,y,width,height);

        float barWidth = (currMana / maxMana) * width;
        // System.out.println(barWidth);
        //float floatBarWidth = (float) barWidth;
        // System.out.println(currMana);
        // System.out.println(maxMana);
        // System.out.println(width);
        // System.out.println(200/1000);
        app.fill(0, 214, 214);
        app.rect(x,y,barWidth,height);
        
        app.fill(0);
        float manaNumberWidth = app.textWidth(currMana + "/" + maxMana);
        app.text((int) currMana+ "/" + (int) maxMana, x + (width - manaNumberWidth)/2,y + height/2 + app.textAscent()/2 - 2);
        app.noStroke();
        float manaTextWidth = app.textWidth("MANA");
        app.text("MANA: ",x - manaTextWidth - 10,y + height/2 + app.textAscent()/2 - 2);
        app.textSize(12);
    }


    public static void setMana(int i) {
        currMana = i;
    }

    // public void draw(PApplet app, float x, float y, float width, float height) {
    //     // Draw white background rectangle
    //     app.fill(255);
    //     app.rect(x, y, width, height);

    //     // Calculate the width of the blue bar based on current mana
    //     float barWidth = PApplet.map(mana, 0, maxMana, 0, width);

    //     // Draw the blue bar
    //     app.fill(0, 0, 255); // Blue color
    //     app.rect(x, y, barWidth, height);

    //     // Display mana amount text
    //     app.fill(0); // Black color for text
    //     app.text("Mana: " + mana, x + 10, y + height / 2 + 5);
    // }
    

    // public void draw(PApplet app, float x, float y, float width, float height) {
    // // Draw white background rectangle
    // app.fill(255);
    // app.rect(x, y, width, height);

    // // Calculate the width of the blue bar based on current mana
    // float barWidth = PApplet.map(mana, 0, maxMana, 0, width);

    // // Draw the blue bar
    // app.fill(0, 0, 255); // Blue color
    // app.rect(x, y, barWidth, height);

    // // Display mana amount text
    // app.fill(0); // Black color for text
    // app.text("Mana: " + mana, x + 10, y + height / 2 + 5);

}
