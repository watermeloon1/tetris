package main;

import java.io.*;
import java.util.Scanner;

public class HighScore {

    private static boolean highScore = false;

    public static void saveScore(int score, String path){

        File file = new File(path);
        try {
            int highScoreInt = record(path);
            if(highScoreInt < score){
                highScore = true;
                clearTheFile(file);
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(Integer.toString(score));

                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearTheFile(File file) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert fw != null;
        PrintWriter pwOb = new PrintWriter(fw, false);
        pwOb.flush();
        pwOb.close();
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int record(String path) {
        File file = new File(path);
        int highScoreInt = 0;
        if (file.length() == 0) {
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                assert writer != null;
                writer.write(Integer.toString(0));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        try {
            Scanner reader = new Scanner(file);
            String highScoreString = reader.nextLine();
            highScoreInt = Integer.parseInt(highScoreString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        return highScoreInt;

    }

    public static boolean getHighScore(){return highScore;}
    public static void setHighScore(boolean statement){highScore = statement;}
}


