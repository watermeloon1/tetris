
import main.Board;
import main.Shape;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShapeTest {
    Shape shape;
    Board board;
    int[][] matrix;
    int[][] matrixT;
    int[][] matrixR;
    int[][] transposed;
    int[][] mirrored;
    int[][] mirroredH;
    int[][] rotatedCCW;
    int[][] rotatedCW;

    int a;

    @Before
    public void setUp(){
        a = 2;

        matrixR = new int[][]{
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
                {0,0,0,1,1,0,0,0,0,1},
                {1,0,0,1,1,1,0,0,1,1},
                {1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1},
        };

        matrix = new int[][]{
                {4, 5, 7},
                {2, 3, 9},
                {6, 1, 8},

        };

        transposed = new int[][]{
                {4, 2, 6},
                {5, 3, 1},
                {7, 9, 8},
        };

        mirrored = new int[][]{
                {6, 1, 8},
                {2, 3, 9},
                {4, 5, 7},
        };

        mirroredH = new int[][]{

                {7, 5, 4},
                {9, 3, 2},
                {8, 1, 6},
        };

        rotatedCCW = new int[][]{
                {7, 9, 8},
                {5, 3, 1},
                {4, 2, 6},
        };

        rotatedCW = new int[][]{
                {6, 2, 4},
                {1, 3, 5},
                {8, 9, 7},
        };

        shape = new Shape(matrix, null, board, a);
        board = new Board(matrixR);
        matrixT = shape.transposeMatrix(matrix);

    }

    @Test
    public void testTranspose(){

        int[] first = new int[]{transposed[0][0], transposed[0][1],transposed[0][2]};
        int[] firstT = new int[]{matrixT[0][0], matrixT[0][1], matrixT[0][2]};

        int[] second = new int[]{transposed[1][0], transposed[1][1], transposed[1][2]};
        int[] secondT = new int[]{matrixT[1][0], matrixT[1][1], matrixT[1][2]};

        int[] third = new int[]{transposed[2][0], transposed[2][1], transposed[2][2]};
        int[] thirdT = new int[]{matrixT[2][0], matrixT[2][1], matrixT[2][2]};

        Assert.assertArrayEquals(first, firstT);
        Assert.assertArrayEquals(second,secondT);
        Assert.assertArrayEquals(third, thirdT);
    }

    @Test
    public void testMirror() {
        shape.mirrorMatrix(matrix);

        int[] first = new int[]{mirrored[0][0], mirrored[0][1],mirrored[0][2]};
        int[] firstM = new int[]{matrix[0][0], matrix[0][1], matrix[0][2]};

        int[] second = new int[]{mirrored[1][0], mirrored[1][1], mirrored[1][2]};
        int[] secondM = new int[]{matrix[1][0], matrix[1][1], matrix[1][2]};

        int[] third = new int[]{mirrored[2][0], mirrored[2][1], mirrored[2][2]};
        int[] thirdM = new int[]{matrix[2][0], matrix[2][1], matrix[2][2]};

        matrix = new int[][]{
                {4, 5, 7},
                {2, 3, 9},
                {6, 1, 8},

        };

        Assert.assertArrayEquals(first, firstM);
        Assert.assertArrayEquals(second,secondM);
        Assert.assertArrayEquals(third, thirdM);
    }

    @Test
    public void testMirrorH() {
        shape.mirrorMatrixH(matrix);

        int[] first = new int[]{mirroredH[0][0], mirroredH[0][1],mirroredH[0][2]};
        int[] firstH = new int[]{matrix[0][0], matrix[0][1], matrix[0][2]};

        int[] second = new int[]{mirroredH[1][0], mirroredH[1][1], mirroredH[1][2]};
        int[] secondH = new int[]{matrix[1][0], matrix[1][1], matrix[1][2]};

        int[] third = new int[]{mirroredH[2][0], mirroredH[2][1], mirroredH[2][2]};
        int[] thirdH = new int[]{matrix[2][0], matrix[2][1], matrix[2][2]};

        matrix = new int[][]{
                {4, 5, 7},
                {2, 3, 9},
                {6, 1, 8},

        };

        Assert.assertArrayEquals(first, firstH);
        Assert.assertArrayEquals(second,secondH);
        Assert.assertArrayEquals(third, thirdH);
    }

    @Test
    public void testRotateCCW(){
        shape.rotateShapeCCW(matrixR);

        int[] first = new int[]{rotatedCCW[0][0], rotatedCCW[0][1],rotatedCCW[0][2]};
        int[] firstR = new int[]{shape.getCoordinates()[0][0], shape.getCoordinates()[0][1], shape.getCoordinates()[0][2]};

        int[] second = new int[]{rotatedCCW[1][0], rotatedCCW[1][1], rotatedCCW[1][2]};
        int[] secondR = new int[]{shape.getCoordinates()[1][0], shape.getCoordinates()[1][1], shape.getCoordinates()[1][2]};

        int[] third = new int[]{rotatedCCW[2][0], rotatedCCW[2][1], rotatedCCW[2][2]};
        int[] thirdR = new int[]{shape.getCoordinates()[2][0], shape.getCoordinates()[2][1], shape.getCoordinates()[2][2]};

        Assert.assertArrayEquals(first, firstR);
        Assert.assertArrayEquals(second,secondR);
        Assert.assertArrayEquals(third, thirdR);
    }

    @Test
    public void testRotateCW(){
        shape.rotateShapeCW(matrixR);

        int[] first = new int[]{rotatedCW[0][0], rotatedCW[0][1],rotatedCW[0][2]};
        int[] firstR = new int[]{shape.getCoordinates()[0][0], shape.getCoordinates()[0][1], shape.getCoordinates()[0][2]};

        int[] second = new int[]{rotatedCW[1][0], rotatedCW[1][1], rotatedCW[1][2]};
        int[] secondR = new int[]{shape.getCoordinates()[1][0], shape.getCoordinates()[1][1], shape.getCoordinates()[1][2]};

        int[] third = new int[]{rotatedCW[2][0], rotatedCW[2][1], rotatedCW[2][2]};
        int[] thirdR = new int[]{shape.getCoordinates()[2][0], shape.getCoordinates()[2][1], shape.getCoordinates()[2][2]};

        Assert.assertArrayEquals(first, firstR);
        Assert.assertArrayEquals(second,secondR);
        Assert.assertArrayEquals(third, thirdR);
    }

    @Test
    public void testCheckRow(){
        shape.checkRow(matrixR);
        Assert.assertEquals(500, Board.score);
    }
}