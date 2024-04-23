package de.hft_stuttgart.ip1.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Server extends Remote {

    Player login(String username) throws RemoteException;

    Boolean logout(String username) throws RemoteException;

    Session newSession(String name, Integer boardSize, Integer playerCount) throws RemoteException;

    Session joinSession(String name, Player player) throws RemoteException;

    List<String> getPlayerList() throws RemoteException;

    List<String> getSessionList() throws RemoteException;
    Session updateSession(Session session) throws RemoteException;
    List<String> getHighScore(Integer gameSize, Integer playerCount) throws RemoteException;

    void addHighscore(Session session) throws RemoteException;

}
