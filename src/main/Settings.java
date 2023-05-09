package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;

public class Settings extends JPanel implements MouseListener, MouseMotionListener {


    private static final long serialVersionUID = 11L;
    private int mouseX, mouseY;
    private boolean leftClick = false;
    private final BufferedImage background, grid, back, easy, normal, hard;
    private final Window window;
    private final BufferedImage[] backButton = new BufferedImage[2];
    private final BufferedImage[] gridButton = new BufferedImage[4];
    private final BufferedImage[] easyButton = new BufferedImage[2];
    private final BufferedImage[] normalButton = new BufferedImage[2];
    private final BufferedImage[] hardButton = new BufferedImage[2];
    private final Rectangle backBounds, gridBounds, easyBounds, normalBounds, hardBounds;
    private static boolean showGrid = false;


    public static BufferedImage loadImage(String path) {
        try {
            File file = new File(path);
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Settings(Window window) {

        Timer timer = new Timer(1000 / 60, e -> repaint());
        timer.start();
        mouseX = 0;
        mouseY = 0;

        background = loadImage("../textures/background.png");
        back = loadImage("../textures/back.png");
        grid = loadImage("../textures/grid.png");
        easy = loadImage("../textures/easy.png");
        normal = loadImage("../textures/normal.png");
        hard = loadImage("../textures/hard.png");

        //Slicing
        assert back != null;
        backButton[0] = back.getSubimage(0, 0, 100, 40);
        backButton[1] = back.getSubimage(100, 0, 100, 40);

        assert grid != null;
        gridButton[0] = grid.getSubimage(0,0,100,40);
        gridButton[1] = grid.getSubimage(100,0,100,40);
        gridButton[2] = grid.getSubimage(200,0,100,40);
        gridButton[3] = grid.getSubimage(300,0,100,40);

        assert easy != null;
        easyButton[0] = easy.getSubimage(0,0,75,30);
        easyButton[1] = easy.getSubimage(75,0,75,30);

        assert normal != null;
        normalButton[0] = normal.getSubimage(0,0,75,30);
        normalButton[1] = normal.getSubimage(75,0,75,30);

        assert hard != null;
        hardButton[0] = hard.getSubimage(0,0,75,30);
        hardButton[1] = hard.getSubimage(75,0,75,30);

        //Declaring bounds
        backBounds = new Rectangle(Window.WIDTH / 2 - 43, Window.HEIGHT / 2 + 130, 100, 40);
        gridBounds = new Rectangle(Window.WIDTH / 2 + 82, Window.HEIGHT / 2 - 15, 100,40);
        easyBounds = new Rectangle(Window.WIDTH / 2 - 140, Window.HEIGHT / 2  - 80, 75, 30);
        normalBounds = new Rectangle(Window.WIDTH /  2 - 30, Window.HEIGHT / 2 - 80, 75, 30);
        hardBounds = new Rectangle(Window.WIDTH /  2 + 77, Window.HEIGHT / 2 - 80 , 75, 30);

        this.window = window;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);


        String settingsString = "SETTINGS";
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 45));
        g.drawString(settingsString,Window.WIDTH / 2  - 110 , 80);

        if (leftClick && backBounds.contains(mouseX, mouseY)) {
                window.exitSettings();
        }

        if (backBounds.contains(mouseX, mouseY)) {
            g.drawImage(backButton[0], Window.WIDTH / 2 - 50, Window.HEIGHT / 2 + 100, null);
        } else {
            g.drawImage(backButton[1], Window.WIDTH / 2 - 50, Window.HEIGHT / 2 + 100, null);
        }

        String gridString = "Grid assist and reference: ";
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        g.drawString(gridString,35, Window.HEIGHT/2 - 20);

        if (leftClick && gridBounds.contains(mouseX, mouseY)) {
            setShowGrid();
            try {
                Thread.sleep(140);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (gridBounds.contains(mouseX, mouseY)) {
            if (Settings.getShowGrid()) {
                g.drawImage(gridButton[1], Window.WIDTH /2 + 75 , Window.HEIGHT / 2 - 45 , null);
            } else
                g.drawImage(gridButton[0], Window.WIDTH /2 + 75, Window.HEIGHT / 2 - 45, null);
        }else {
            if (Settings.getShowGrid()) {
                g.drawImage(gridButton[3], Window.WIDTH / 2 + 75, Window.HEIGHT / 2 - 45, null);
            }else {
                g.drawImage(gridButton[2], Window.WIDTH / 2 + 75, Window.HEIGHT / 2 - 45, null);
            }

        }

        String speedString = "Difficulty";
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
        g.drawString(speedString,Window.WIDTH/2 - 58, Window.HEIGHT/2 - 150);

        if (easyBounds.contains(mouseX, mouseY )|| Shape.getSpeed() == Shape.getEasySpeed()) {
            g.drawImage(easyButton[0], Window.WIDTH / 2 - 147, Window.HEIGHT / 2 - 110, null);
        } else {
            g.drawImage(easyButton[1], Window.WIDTH / 2 - 147, Window.HEIGHT / 2 - 110, null);
        }

        if (normalBounds.contains(mouseX, mouseY )|| Shape.getSpeed() == Shape.getNormalSpeed()) {
            g.drawImage(normalButton[0], Window.WIDTH / 2 - 37, Window.HEIGHT / 2 - 110, null);
        } else {
            g.drawImage(normalButton[1], Window.WIDTH / 2 - 37, Window.HEIGHT / 2 - 110 , null);
        }

        if (hardBounds.contains(mouseX, mouseY) || Shape.getSpeed() == Shape.getHardSpeed() ) {
            g.drawImage(hardButton[0], Window.WIDTH / 2 + 70, Window.HEIGHT / 2 - 110, null);
        } else {
            g.drawImage(hardButton[1], Window.WIDTH / 2 + 70, Window.HEIGHT / 2 - 110, null);
        }

        if (leftClick && easyBounds.contains(mouseX, mouseY)) {
            Shape.setSpeed(Shape.getEasySpeed());
        }

        if (leftClick && normalBounds.contains(mouseX, mouseY)) {
            Shape.setSpeed(Shape.getNormalSpeed());
        }

        if (leftClick && hardBounds.contains(mouseX, mouseY)) {
            Shape.setSpeed(Shape.getHardSpeed());
        }

    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            leftClick = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public static boolean getShowGrid() {
        return !showGrid;
    }

    public void setShowGrid() {
        Settings.showGrid = !Settings.showGrid;
    }
}
