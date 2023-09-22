package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.*;

import javafx.scene.shape.Path;

public class App extends PApplet {

    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;

    public static int WIDTH = CELLSIZE * BOARD_WIDTH + SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH * CELLSIZE + TOPBAR;

    public static final int FPS = 60;

    public String configPath;

    public Random random = new Random();

    private static String levelName = "level4.txt";
    // private int TESTING_VAR = 0;
    // private char[][] levelDataMatrix;
    private ArrayList<MapElements> elementsList = new ArrayList<MapElements>();
    private ArrayList<Paths> pathsList = new ArrayList<Paths>();

    // Feel free to add any additional methods or attributes you want. Please put
    // classes in different files.

    /*
     * This method extracts all the individual symbols located inside a
     * level file, and stores them into a 2D matrix such that the symbols
     * can be individually iterated through and the correct image is drawn.
     */
    public char[][] loadLevelData(String levelName) {
        String[] levelLines = loadStrings(levelName);
        int numRows = levelLines.length;
        int numColumns = levelLines[0].length();
        char[][] levelDataMatrix = new char[numRows][numColumns];

        for (int currRow = 0; currRow < numRows; currRow++) {
            for (int currCol = 0; currCol < numColumns; currCol++) {
                levelDataMatrix[currRow][currCol] = levelLines[currRow].charAt(currCol);
            }
        }
        return levelDataMatrix;
        // for (int currRow = 0; currRow < numRows; currRow++) {
        // for (int currCol = 0; currCol < numColumns; currCol++) {
        // System.out.print(levelDataMatrix[currRow][currCol] + " ");
        // }
        // System.out.println(); // Move to the next row
        // }
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
                    elementsList.add(
                            new WizardHouse(xPos, yPos, loadImage("src/main/resources/WizardTD/wizard_house.png")));
                    // new instance of Wizard House
                } else if (mapSymbol == 'X') {
                    createMapPaths(levelDataMatrix, row, column);
                    // System.out.println("X found");
                }
                // else if (mapSymbol == 'X') {
                // elementsList.add(new
                // Paths(xPos,yPos,loadImage("src/main/resources/WizardTD/path0.png")));
                // // for paths i might not need 3rd input because there is different types of
                // paths
                // // new instance of Paths
                // }

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
            //System.out.println("VERTICAL DRAWN");
        }  else if (isToRight && isToLeft &&!isAbove && !isBelow) {
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
        createMapElements(loadLevelData(levelName));

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

    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased() {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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

    @Override
    public void draw() {
        for (MapElements elementToDraw : elementsList) {
            elementToDraw.draw(this);
        }

        for (Paths pathsToDraw : pathsList) {
            pathsToDraw.draw(this);
        }

        // if (TESTING_VAR == 0) {
        // loadLevelData(levelName);
        // TESTING_VAR++;
        // }
        // this.grass.draw(this);

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
}

