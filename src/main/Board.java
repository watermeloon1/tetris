package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JPanel;
import javax.swing.Timer;

import static main.Settings.loadImage;

public class Board extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 11L;

    public static int score = 0;

    private  BufferedImage blocks, background, pause, refresh, menu, scoreP, best;
    private static int boardHeight = 20;
    private static int boardWidth = 10;
    private final int blockSize = 30;
    public static int[][] board = new int[boardHeight][boardWidth];

    private final ArrayList<Shape> shapes = new ArrayList<>();
    public static Shape currentShape;
    public static Shape nextShape;

    private  Timer looper;
    private int mouseX, mouseY;
    private boolean leftClick = false;

    private  Rectangle pauseBounds, refreshBounds, menuBounds;
    private final BufferedImage[] pauseButton = new BufferedImage[4];
    private final BufferedImage[] refreshButton = new BufferedImage[2];
    private final BufferedImage[] menuButton = new BufferedImage[2];

    private boolean gamePaused = false;
    private boolean gameOver = false;

    private int index;

    private Window window;



    public Board(Window window) {

        blocks = loadImage("../textures/tiles.png");
        background = loadImage("../textures/background.png");
        pause = loadImage("../textures/pausebutton.png");
        refresh = loadImage("../textures/refreshbutton.png");
        menu = loadImage("../textures/menubutton.png");
        scoreP = loadImage("../textures/score.png");
        best = loadImage("../textures/top.png");

        mouseX = 0;
        mouseY = 0;

        assert pause != null;
        pauseButton[0] = pause.getSubimage(0,0, 50,50);
        pauseButton[1] = pause.getSubimage(50,0,50,50);
        pauseButton[2] = pause.getSubimage(100,0,50,50);
        pauseButton[3] = pause.getSubimage(150,0,50,50);

        refreshButton[0] = refresh.getSubimage(0,0, 50,50);
        refreshButton[1] = refresh.getSubimage(50,0,50,50);

        menuButton[0] = menu.getSubimage(0,0, 50,50);
        menuButton[1] = menu.getSubimage(50,0,50,50);

        pauseBounds = new Rectangle(349, 410, 50, 50);

        refreshBounds = new Rectangle(349, 470, 50, 50);

        menuBounds = new Rectangle(349, 530, 50, 50);

        int FPS = 60;
        int delay = 1000 / FPS;
        looper = new Timer(delay, new GameLooper());

        assert blocks != null;
        shapes.add(new Shape(new int[][]{
                {1,1,1,1},
        }, blocks.getSubimage(0, 0, blockSize, blockSize), this, 1));

        shapes.add(new Shape(new int[][]{
                {1, 1, 1},
                {0, 1, 0},
        }, blocks.getSubimage(blockSize, 0, blockSize, blockSize), this, 2));

        shapes.add(new Shape(new int[][]{
                {1, 1, 1},
                {1, 0, 0},
        }, blocks.getSubimage(blockSize * 2, 0, blockSize, blockSize), this, 3));

        shapes.add(new Shape(new int[][]{
                {1, 1, 1},
                {0, 0, 1},
        }, blocks.getSubimage(blockSize * 3, 0, blockSize, blockSize), this, 4));

        shapes.add(new Shape(new int[][]{
                {0, 1, 1},
                {1, 1, 0},
        }, blocks.getSubimage(blockSize * 4, 0, blockSize, blockSize), this, 5));

        shapes.add(new Shape(new int[][]{
                {1, 1, 0},
                {0, 1, 1},
        }, blocks.getSubimage(blockSize * 5, 0, blockSize, blockSize), this, 6));

        shapes.add(new Shape(new int[][]{
                {1, 1},
                {1, 1},
        }, blocks.getSubimage(blockSize * 6, 0, blockSize, blockSize), this, 7));


        this.window = window;
    }

    public Board(int[][] board){
        Board.board = board;
        boardHeight = board[0].length;
        boardWidth = board.length;
    }

    public Board(Shape[] shapes, int[][] board, Timer looper){
        Collections.addAll(this.shapes, shapes);
        Board.board = board;
        this.looper = looper;
    }

    public void update()  {
        if (pauseBounds.contains(mouseX, mouseY) && leftClick && !gameOver) {
            setGamePaused();
        }

        if (refreshBounds.contains(mouseX, mouseY) && leftClick) {
            startGame();
        }

        if (menuBounds.contains(mouseX, mouseY) && leftClick) {
                window.exitTetris();
        }

        if (gamePaused || gameOver) {
            return;
        }

        currentShape.update(getBoard());
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, null);

        g.drawImage(scoreP, main.Window.WIDTH - 132, main.Window.HEIGHT / 2 - 60, null);
        g.drawImage(best, main.Window.WIDTH - 132, main.Window.HEIGHT / 2 - 160, null);


        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != 0) {
                    g.drawImage(blocks.getSubimage((board[row][col] - 1) * blockSize,
                            0, blockSize, blockSize), col * blockSize, row * blockSize, null);
                }

            }
        }
        if (Settings.getShowGrid()) {
            for (int row = 0; row < nextShape.getCoordinates().length; row++) {
                for (int col = 0; col < nextShape.getCoordinates()[0].length; col++) {
                    if (nextShape.getCoordinates()[row][col] != 0) {
                        if(index == 0)
                            g.drawImage(nextShape.getBlock(), col * 30 + 308, row * 30 + 60, null);

                        if(index == 6)
                            g.drawImage(nextShape.getBlock(), col * 30 + 337, row * 30 + 60, null);

                        if(index != 0 && index != 6 )
                            g.drawImage(nextShape.getBlock(), col * 30 + 322, row * 30 + 60, null);

                    }
                }
            }
        }

        currentShape.render(g);

        if (pauseBounds.contains(mouseX, mouseY)) {
            if (gamePaused) {
                g.drawImage(pauseButton[3], 342, 380, null);
            } else
                g.drawImage(pauseButton[2], 342, 380, null);
        }else {
            if (gamePaused) {
                g.drawImage(pauseButton[1], 342, 380, null);
            }else {
                g.drawImage(pauseButton[0], 342, 380, null);
            }

        }

        if (refreshBounds.contains(mouseX, mouseY))
            g.drawImage(refreshButton[1], 342, 440, null);
        else
            g.drawImage(refreshButton[0], 342, 440, null);

        if (menuBounds.contains(mouseX, mouseY))
            g.drawImage(menuButton[1], 342, 500, null);
        else
            g.drawImage(menuButton[0], 342, 500, null);


        if (gamePaused) {
            String gamePausedString = "GAME PAUSED";
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
            g.drawString(gamePausedString, 35, main.Window.HEIGHT / 2);
        }
        if (gameOver) {
            String gameOverString = "GAME OVER";
            String newHighScore = "New High Score";
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
            g.drawString(gameOverString, 60, main.Window.HEIGHT / 2 - 10);
        if(HighScore.getHighScore()){
                g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
                g.drawString(newHighScore, 78, main.Window.HEIGHT / 2 + 10);
            }
        }
        g.setColor(Color.WHITE);

        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));

        g.drawString(score + "", main.Window.WIDTH - 115, main.Window.HEIGHT / 2 - 8);
        g.drawString(HighScore.record("highscore.txt") + "", main.Window.WIDTH - 115, main.Window.HEIGHT / 2 - 108);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(new Color(0, 0, 0, 100));

        if (Settings.getShowGrid()) {
            for (int i = 0; i <= boardHeight; i++) {
                g2d.drawLine(0, i * blockSize, boardWidth * blockSize, i * blockSize);
            }
            for (int j = 0; j <= boardWidth; j++) {
                g2d.drawLine(j * blockSize, 0, j * blockSize, boardHeight * 30);
            }
        }
    }

    public void setIndex(){
        index = (int) (Math.random() * shapes.size());
    }

    public void setNextShape() {
        setIndex();
        nextShape = new Shape(shapes.get(index).getCoordinates(), shapes.get(index).getBlock(), this, shapes.get(index).getColor());
    }

    public void setCurrentShape() {
        currentShape = nextShape;
        setNextShape();

        for (int row = 0; row < currentShape.getCoordinates().length; row++) {
            for (int col = 0; col < currentShape.getCoordinates()[0].length; col++) {
                if (currentShape.getCoordinates()[row][col] != 0) {
                    if (board[currentShape.getyStart() + row][currentShape.getxStart() + col] != 0) {
                        HighScore.saveScore(score, "highscore.txt");
                        gameOver = true;
                    }
                }
            }
        }

    }

    class GameLooper implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            update();
            if(!gameOver && !gamePaused) {
                currentShape.update(getBoard());
                repaint();
            }

            repaint();
        }

    }

    public void startGame() {
        resetGame(board);
        setNextShape();
        setCurrentShape();
        gameOver = false;
        looper.start();

    }

    public void resetGame(int[][] board) {
        HighScore.setHighScore(false);
        score = 0;
        gamePaused = false;

        for (int[] ints : board) Arrays.fill(ints, 0);
        looper.stop();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!gamePaused && !gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_UP)
                currentShape.rotateShapeCCW(board);
            if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                currentShape.setDeltaX(1);
            if (e.getKeyCode() == KeyEvent.VK_LEFT)
                currentShape.setDeltaX(-1);
            if (e.getKeyCode() == KeyEvent.VK_DOWN)
                currentShape.rotateShapeCW(board);
            if (e.getKeyCode() == KeyEvent.VK_SPACE)
                currentShape.speedUp();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            currentShape.slowDown();
    }

    @Override
    public void keyTyped(KeyEvent e) {

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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            leftClick = true;
        try {
            Thread.sleep(140);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
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

    public static void addScore() {

        score = score + 100;
    }

    public int[][] getBoard() {

        return board;
    }


    public boolean getGamePaused(){

        return gamePaused;
    }

    public boolean getGameOver(){

        return gameOver;
    }

    private void setGamePaused(){

        gamePaused = !gamePaused;
    }


}
