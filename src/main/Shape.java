package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Shape {
    private int xStart, yStart;
    private int deltaX;

    private long time, lastTime;
    private int delay;

    private BufferedImage block;
    private int blockColor;
    private int[][] coordinates;
    private Board board;
    private boolean collision = false;

    private final static int easySpeed = 800;
    private final static int normalSpeed = 600;
    private final static int hardSpeed = 400;

    public static int speed = normalSpeed;

    public Shape(int[][] coordinates, BufferedImage block, Board board, int blockColor) {
        this.coordinates = coordinates;
        this.block = block;
        this.blockColor = blockColor;
        this.board = board;

        yStart = 0;
        xStart = 4;
        deltaX = 0;

        delay = speed;
        time = 0;
        lastTime = System.currentTimeMillis();
    }

    public Shape(int[][] coordinates){
        this.coordinates = coordinates;
    }

    public void render(Graphics g) {


        for (int row = 0; row < coordinates.length; row++) {
            for (int col = 0; col < coordinates[0].length; col++) {
                if (coordinates[row][col] != 0) {
                    g.drawImage(block, col * 30 + xStart * 30, row * 30 + yStart * 30, null);
                }
            }
        }
    }

    public void update(int[][] boardM) {
        boolean move = true;

        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (collision) {
            for (int row = 0; row < coordinates.length; row++) {
                for (int col = 0; col < coordinates[0].length; col++) {
                    if (coordinates[row][col] != 0)
                        boardM[yStart + row][xStart + col] = blockColor;
                }
            }
            checkRow(boardM);
            board.setCurrentShape();
        }

        if ((xStart + deltaX + coordinates[0].length <= 10) && (xStart + deltaX >= 0)) {

            for (int row = 0; row < coordinates.length; row++) {
                for (int col = 0; col < coordinates[row].length; col++) {
                    if (coordinates[row][col] != 0) {
                        if (boardM[yStart + row][xStart + deltaX + col] != 0) {
                            move = false;
                        }
                    }
                }
            }

            if (move)
                xStart = deltaX + xStart;
        }

        if ((yStart + 1 + coordinates.length <= 20)) {

            for (int row = 0; row < coordinates.length; row++) {
                for (int col = 0; col < coordinates[row].length; col++) {
                    if (coordinates[row][col] != 0) {
                        if (boardM[yStart + 1 + row][xStart + col] != 0) {
                            collision = true;
                        }
                    }
                }
            }
            if (time > delay) {
                yStart = yStart + 1;
                time = 0;
            }
        } else {
            collision = true;
        }

        deltaX = 0;
    }

    public void checkRow(int[][] matrix) {
        int size = matrix.length - 1;
        int combo = 0;

        for (int i = matrix.length - 1; i > 0; i--) {

            int count = 0;

            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0)
                    count++;

                matrix[size][j] = matrix[i][j];

                if (count == matrix[0].length) {
                    Board.addScore();
                    combo += 1;

                    switch (combo) {
                        case (2), (4), (3) -> Board.addScore();
                    }
                    if(combo == 4)
                        Board.addScore();
                }
            }
            if (count < matrix[0].length)
                size--;
        }
    }

    public void rotateShapeCCW(int[][] board) {

        int[][] rotatedShape;
        int[][] temp = transposeMatrix(coordinates);
        mirrorMatrix(temp);
        rotatedShape = temp;

        if ((xStart + rotatedShape[0].length > board[0].length) || (yStart + rotatedShape.length > board.length )) {
            return;
        }

        for (int row = 0; row < rotatedShape.length; row++) {
            for (int col = 0; col < rotatedShape[row].length; col++) {
                if (rotatedShape[row][col] != 0) {
                    if (board[yStart + row][xStart + col] != 0) {
                        return;
                    }
                }
            }
        }
        coordinates = rotatedShape;
    }

    public void rotateShapeCW(int[][] board) {

        int[][] rotatedShape;
        int[][] temp = transposeMatrix(coordinates);
        mirrorMatrixH(temp);
        rotatedShape = temp;

        if ((xStart + rotatedShape[0].length > board[0].length) || (yStart + rotatedShape.length > board.length )) {
            return;
        }

        for (int row = 0; row < rotatedShape.length; row++) {
            for (int col = 0; col < rotatedShape[row].length; col++) {
                if (rotatedShape[row][col] != 0) {
                    if (board[yStart + row][xStart + col] != 0) {
                        return;
                    }
                }
            }
        }
        coordinates = rotatedShape;
    }

    public void mirrorMatrix(int[][] matrix) {

        int middle = matrix.length / 2;

        for (int i = 0; i < middle; i++) {
            int[] temp = matrix[i];

            matrix[i] = matrix[matrix.length - i - 1];
            matrix[matrix.length - i - 1] = temp;
        }

    }

    public void mirrorMatrixH(int[][] matrix) {

        int width = matrix[0].length;

        for (int i = 0; i < matrix.length; i++) {
            for (int p1 = 0; p1 < width / 2; p1++) {
                int p2 = width - p1 - 1;
                int temp = matrix[i][p1];
                matrix[i][p1] = matrix[i][p2];
                matrix[i][p2] = temp;
            }
        }
    }

    public int[][] transposeMatrix(int[][] matrix) {
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                temp[j][i] = matrix[i][j];
        return temp;
    }

    public int[][] getCoordinates() {

        return coordinates;
    }

    public BufferedImage getBlock() {

        return block;
    }

    public int getColor() {

        return blockColor;
    }

    public int getxStart() {

        return xStart;
    }

    public int getyStart() {

        return yStart;
    }

    public static int getSpeed(){

        return speed;
    }

    public static int getEasySpeed(){

        return easySpeed;
    }

    public static int getNormalSpeed(){

        return normalSpeed;

    }

    public static int getHardSpeed(){

        return hardSpeed;

    }

    public void speedUp() {

        delay = 60;
    }

    public void slowDown() {

        delay = speed;
    }

    public void setDeltaX(int deltaX) {

        this.deltaX = deltaX;
    }

    public static void setSpeed(int speed){

        Shape.speed = speed;
    }
}