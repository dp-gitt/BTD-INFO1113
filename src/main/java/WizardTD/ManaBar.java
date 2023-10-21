package WizardTD;

import processing.core.PApplet;

public class ManaBar {
    public static float maxMana;
    public static float currMana;

    public ManaBar(int maxMana) {
        ManaBar.maxMana = maxMana;
        ManaBar.currMana = 1000;
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
        currMana -= amount;
        if (currMana < 0) {
            currMana = 0; // Ensure mana doesn't go negative
        }
    }

    public float getMana() {
        return currMana;
    } 

    public float getMaxMana() {
        return maxMana;
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
