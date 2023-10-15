package WizardTD;

import processing.core.PApplet;

public class Buttons {
    
    
    // private int colour;
    private int x,y;

    private float width, height;
    private PApplet app;
    private int strokeColour = 0;
    private int fillColour;
    private String text;
    private String label;
    private boolean isToggled = false;
    
    
    /// to initalise fillColour you use method color(r,g,b);
    public Buttons(PApplet app, int x, int y, int width, int height, int fillColour, String text, String label) {
        this.app = app;
        // this.colour = app.color(r,g,b);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fillColour = fillColour;
        this.text = text;
        this.label = label;

    }

    public String getLabel () {
        return this.label;
    }

    public int getFillColour() {
        return this.fillColour;
    }

    public boolean getIsToggled() {
        return this.isToggled;
    }

    public void setIsToggled(boolean isToggled) {
        this.isToggled = isToggled;
    }

    public void drawButton() {
        app.stroke(strokeColour);
        app.strokeWeight(3);
        app.fill(fillColour);
        app.rect(x,y,width,height);
        app.noStroke();
    }

    public void changeButtonColour(int newColour) {
        this.fillColour = newColour;
        app.fill(newColour);
    }

    public void drawText() {
        app.fill(0);
        app.text(text,x + width + 5,y + height/2 - 15,60,40);
    }

    public void drawLabel() {
        app.fill(0);
        app.textSize(24);
        // float fontHeight = app.textAscent() + app.textDescent();
        if (label.length() == 2) {
            app.text(label, x + width/2 - app.textWidth(label)/2 , y +height/2 + app.textAscent()/2);
            
        } else {
            app.textAlign(0);
            app.text(label,x + 5, y +height/2 + app.textAscent()/2);
            
        }
        app.textSize(12);
    }

    public boolean isMouseOver() {
        float leftEdge = x;
        float rightEdge = x + width;
        float topEdge = y;
        float bottomEdge = y + height;
        return app.mouseX >= leftEdge && app.mouseX <= rightEdge && app.mouseY >= topEdge && app.mouseY <= bottomEdge;
    }

}
