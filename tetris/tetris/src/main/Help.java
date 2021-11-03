package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import static main.Settings.loadImage;

public class Help extends JPanel implements MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 11L;
    private int mouseX, mouseY;
    private boolean leftClick = false;
    private final Window window;
    private final BufferedImage background, back;
    private final BufferedImage[] backButton = new BufferedImage[2];
    private final Rectangle backBounds;

    public Help(Window window){

        Timer timer = new Timer(1000 / 60, e -> repaint());
        timer.start();
        mouseX = 0;
        mouseY = 0;

        background = loadImage("/background.png");
        back = loadImage("/back.png");

        assert back != null;
        backButton[0] = back.getSubimage(0, 0, 100, 40);
        backButton[1] = back.getSubimage(100, 0, 100, 40);

        backBounds = new Rectangle(Window.WIDTH / 2 - 43, Window.HEIGHT / 2 + 130, 100, 40);

        this.window = window;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);

        String helpString = "HELP";
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 45));
        g.drawString(helpString,Window.WIDTH / 2  - 60 , 80);


        String[] text = {"The Tetris playing field is a 10x20 grid." ,
                "Your goal is to continually stack tetrominoes and clear",
                "lines as long as you can. To do that, you need to fill an",
                "entire line horizontally with blocks, causing those lines",
                "to disappear and giving you more space to stack.",
                "If you run out of space and can no longer place",
                "any more tetrominoes,you top out and the game is over.",
                "Good luck, have fun!"};

        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));

        int j = 180;
        for(int i = 0; i < 8;i++){
            g.drawString(text[i],25, j);
            if (i == 4 || i == 6){
                j = j + 40;
            }else{
                j = j + 20;
            }
        }

        if (leftClick && backBounds.contains(mouseX, mouseY)) {
                window.exitHelp();
        }

        if (backBounds.contains(mouseX, mouseY)) {
            g.drawImage(backButton[0], Window.WIDTH / 2 - 50, Window.HEIGHT / 2 + 100, null);
        } else {
            g.drawImage(backButton[1], Window.WIDTH / 2 - 50, Window.HEIGHT / 2 + 100, null);
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
