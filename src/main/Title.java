package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;


import static main.Settings.loadImage;

public class Title extends JPanel implements MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 11L;
    private int mouseX, mouseY;
    private final Rectangle playBounds;
    private final Rectangle settingsBounds;
    private final Rectangle helpBounds;
    private final Rectangle exitBounds;
    private boolean leftClick = false;
    private final BufferedImage play, settings,help, background, exit;
    private final Window window;
    private final BufferedImage[] playButton = new BufferedImage[2];
    private final BufferedImage[] settingsButton = new BufferedImage[2];
    private final BufferedImage[] helpButton = new BufferedImage[2];
    private final BufferedImage[] exitButton = new BufferedImage[2];


    public Title(Window window) {

        background = loadImage("../textures/background.png");
        play = loadImage("../textures/play.png");
        settings = loadImage("../textures/settings.png");
        help = loadImage("../textures/help.png");
        exit = loadImage("../textures/exit.png");

        Timer timer = new Timer(1000 / 60, e -> repaint());
        timer.start();
        mouseX = 0;
        mouseY = 0;

        //Slicing
        playButton[0] = play.getSubimage(0, 0, 150, 60);
        playButton[1] = play.getSubimage(150, 0, 150, 60);

        settingsButton[0] = settings.getSubimage(0, 0, 150, 60);
        settingsButton[1] = settings.getSubimage(150, 0, 150, 60);

        helpButton[0] = help.getSubimage(0, 0, 150, 60);
        helpButton[1] = help.getSubimage(150, 0, 150, 60);

        exitButton[0] = exit.getSubimage(0, 0, 150, 60);
        exitButton[1] = exit.getSubimage(150, 0, 150, 60);

        //Declaring bounds
        playBounds = new Rectangle(Window.WIDTH / 2 - 70, Window.HEIGHT / 2 - 130, 150, 60);
        settingsBounds = new Rectangle(Window.WIDTH / 2 - 70, Window.HEIGHT / 2 +10 , 150, 60);
        helpBounds = new Rectangle(Window.WIDTH / 2 - 70, Window.HEIGHT / 2 + 90 , 150, 60);
        exitBounds = new Rectangle(Window.WIDTH / 2 - 70, Window.HEIGHT / 2 + 170 , 150, 60);

        this.window = window;


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, null);

        String tetrisString = "TETRIS";
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 45));
        g.drawString(tetrisString,Window.WIDTH / 2  - 78 , 80);

        if (leftClick && playBounds.contains(mouseX, mouseY)) {
                window.openTetris();
        }

        if (leftClick && settingsBounds.contains(mouseX, mouseY)) {
                window.openSettings();
        }

        if (leftClick && helpBounds.contains(mouseX, mouseY)) {
                window.openHelp();
        }


        if (leftClick && exitBounds.contains(mouseX, mouseY))
            System.exit(0);


        if (playBounds.contains(mouseX, mouseY)) {
            g.drawImage(playButton[0], Window.WIDTH / 2 - 75, Window.HEIGHT / 2 - 160, null);

        } else {
            g.drawImage(playButton[1], Window.WIDTH / 2 - 75, Window.HEIGHT / 2 - 160, null);
        }

        if (settingsBounds.contains(mouseX, mouseY)) {
            g.drawImage(settingsButton[0], Window.WIDTH / 2 - 75, Window.HEIGHT / 2 - 20, null);
        } else {
            g.drawImage(settingsButton[1], Window.WIDTH / 2 - 75, Window.HEIGHT / 2 - 20, null);
        }

        if (helpBounds.contains(mouseX, mouseY)) {
            g.drawImage(helpButton[0], Window.WIDTH / 2 - 75, Window.HEIGHT / 2 + 60 , null);
        } else {
            g.drawImage(helpButton[1], Window.WIDTH / 2 - 75, Window.HEIGHT / 2 + 60, null);
        }

        if (exitBounds.contains(mouseX, mouseY)) {
            g.drawImage(exitButton[0], Window.WIDTH / 2 - 75, Window.HEIGHT / 2 + 140 , null);
        } else {
            g.drawImage(exitButton[1], Window.WIDTH / 2 - 75 , Window.HEIGHT / 2 + 140, null);
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
        if (e.getButton() == MouseEvent.BUTTON1)
            leftClick = false;
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
}
