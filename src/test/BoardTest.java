
import main.Board;
import main.Shape;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

public class BoardTest {

    Board board;
    int[][] boardM;
    Shape[] shapes = new Shape[2];
    Timer looper;
    Shape comp;

    @Before
    public void setUp(){

        boardM = new int[][]{
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,1,0,0,0,0},
                {0,0,1,1,0,1,0,0,0,0},
                {0,0,1,1,0,1,0,0,0,0},
                {0,0,1,1,0,1,0,0,0,0},
        };

        shapes[0] = new Shape(new int[][]{
                {1, 1, 1, 1}
        });

        shapes[1] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 1, 0},
        });

        looper = new Timer(1000,null);

        board = new Board(shapes, boardM, looper);
    }

    @Test
    public void testStopGame(){
        board.resetGame(boardM);
        Assert.assertEquals(0,Board.score);
        Assert.assertFalse(board.getGamePaused());
        for(int i = 0; i < boardM[0].length;i++){
            for (int[] ints : boardM) {
                Assert.assertEquals(0, ints[i]);
            }
        }
    }

    @Test
    public void testStartGame(){
        board.startGame();
        Assert.assertNotNull(Board.currentShape);
        Assert.assertFalse(board.getGameOver());
    }

    @Test
    public void testNextShape(){
        board.setNextShape();
        comp = Board.nextShape;
        Assert.assertNotNull(comp);
    }
}