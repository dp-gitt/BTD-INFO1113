package WizardTD;

import processing.core.PApplet;

public class Buttons {

    // private int colour;
    private int x, y;

    private float width, height;
    private PApplet app;
    private int strokeColour = 0;
    private int fillColour;
    private String text;
    private String label;
    private boolean isToggled = false;

    /**
     * Constructs a new Buttons object.
     *
     * @param app        The PApplet instance to which this button belongs.
     * @param x          The x-coordinate of the button's top-left corner.
     * @param y          The y-coordinate of the button's top-left corner.
     * @param width      The width of the button.
     * @param height     The height of the button.
     * @param fillColour The fill color of the button, represented as an integer.
     * @param text       The text displayed on the button.
     * @param label      A label associated with the button for identification.
     */
    public Buttons(PApplet app, int x, int y, int width, int height, int fillColour, String text, String label) {
        this.app = app;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fillColour = fillColour;
        this.text = text;
        this.label = label;

    }

    /**
     * Gets the label associated with this button.
     *
     * @return A string representing the label of the button.
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Gets the fill color of the button.
     *
     * @return An integer representing the fill color of the button.
     */
    public int getFillColour() {
        return this.fillColour;
    }

    /**
     * Checks if the button is in a toggled state.
     *
     * @return true if the button is toggled; false otherwise.
     */
    public boolean getIsToggled() {
        return this.isToggled;
    }

    /**
     * Sets the toggled state of the button.
     *
     * @param isToggled true to toggle the button, false to untoggle it.
     */
    public void setIsToggled(boolean isToggled) {
        this.isToggled = isToggled;
    }

    /**
     * Draws the button on the screen using the specified properties.
     */
    public void drawButton() {
        app.stroke(strokeColour);
        app.strokeWeight(3);
        app.fill(fillColour);
        app.rect(x, y, width, height);

        app.noStroke();
    }

    /**
     * Changes the fill color of the button to the specified color.
     * If the new color is 0, a default color is set.
     *
     * @param newColour An integer representing the new fill color.
     */

    public void changeButtonColour(int newColour) {
        this.fillColour = newColour;
        app.fill(newColour);
        // Perform color-changing operations
        this.fillColour = newColour;
        app.fill(newColour);

    }

    /**
     * Draws the text associated with the button.
     */
    public void drawText() {
        app.fill(0);
        app.text(text, x + width + 5, y + height / 2 - 15, 60, 40);
    }

    /**
     * Draws the label associated with the button.
     */
    public void drawLabel() {
        app.fill(0);
        app.textSize(24);
        // float fontHeight = app.textAscent() + app.textDescent();
        if (label.length() == 2) {
            app.text(label, x + width / 2 - app.textWidth(label) / 2, y + height / 2 + app.textAscent() / 2);
        } else {
            app.textAlign(0);
            app.text(label, x + 5, y + height / 2 + app.textAscent() / 2);
        }
        app.textSize(12);
    }

    /**
     * Checks if the mouse pointer is currently over the button.
     *
     * @return true if the mouse is over the button; false otherwise.
     */
    public boolean isMouseOver() {
        float leftEdge = x;
        float rightEdge = x + width;
        float topEdge = y;
        float bottomEdge = y + height;
        return app.mouseX >= leftEdge && app.mouseX <= rightEdge && app.mouseY >= topEdge && app.mouseY <= bottomEdge;
    }

    /**
     * Changes the text associated with the button.
     *
     * @param text A string representing the new text for the button.
     */ 
    public void changeText(String text) {
        this.text = text;
    }

}
