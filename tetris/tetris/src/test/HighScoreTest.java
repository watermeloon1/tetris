
import main.HighScore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static main.HighScore.record;

public class HighScoreTest {

    @Before
    public void setUp(){

        File file = new File("test.txt");
        HighScore.clearTheFile(file);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert writer != null;
            writer.write(Integer.toString(500));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSave() {
        HighScore.saveScore(1000, "test.txt");

        int highScoreInt = record("test.txt");

        Assert.assertEquals(1000,highScoreInt);
        Assert.assertTrue(HighScore.getHighScore());

    }
}