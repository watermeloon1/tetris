package main;

import javax.swing.*;

public class Window {

    public static final int WIDTH = 450;
    public static final int HEIGHT = 638;

    private final Board board;
    private final Title title;
    private final JFrame window;
    private final Settings settings;
    private final Help help;

    public Window() {

        window = new JFrame("Tetris");
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);window.setResizable(false);


        title = new Title(this);
        board = new Board(this);
        settings = new Settings(this);
        help = new Help(this);

        window.addMouseMotionListener(title);
        window.addMouseListener(title);

        window.add(title);
        window.setVisible(true);
    }

    public void openSettings() {
        window.remove(title);
        try {
            Thread.sleep(120);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.addMouseMotionListener(settings);
        window.addMouseListener(settings);
        window.add(settings);
        window.setVisible(true);
    }

    public void exitSettings() {
        window.remove(settings);
        try {
            Thread.sleep(120);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.add(title);
    }

    public void openHelp()  {

        window.remove(title);
        try {
            Thread.sleep(120);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.addMouseMotionListener(help);
        window.addMouseListener(help);
        window.add(help);
        window.setVisible(true);
    }

    public void exitHelp() {
        window.remove(help);
        try {
            Thread.sleep(120);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.add(title);
    }

    public void openTetris(){
        window.remove(title);
        try {
            Thread.sleep(120);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.addMouseMotionListener(board);
        window.addMouseListener(board);
        window.addKeyListener(board);
        window.add(board);
        board.startGame();
        window.revalidate();
    }

    public void exitTetris(){
        board.resetGame(Board.board);
        window.removeMouseListener(board);
        window.removeMouseMotionListener(board);
        window.removeKeyListener(board);
        window.remove(board);
        try {
            Thread.sleep(120);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.add(title);
    }

    public static void main(String[] args) {
        new Window();
    }
}