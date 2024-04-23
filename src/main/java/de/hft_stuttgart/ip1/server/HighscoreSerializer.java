package de.hft_stuttgart.ip1.server;

import de.hft_stuttgart.ip1.common.Highscore;

import java.io.*;
import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class HighscoreSerializer {

    public static void serializeHighscores(List<Highscore> highscoreList, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream("./" + fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(highscoreList);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<HighscoreImpl> deserializeHighscores(String fileName) {
        List<HighscoreImpl> highscoreList = null;
        try {
            FileInputStream fileIn = new FileInputStream("./" + fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            highscoreList = (List<HighscoreImpl>) in.readObject();
            highscoreList = highscoreList.stream().sorted(Comparator.comparing(HighscoreImpl::getScore)).toList();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            //i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return null;
        }
        return highscoreList;
    }
}
