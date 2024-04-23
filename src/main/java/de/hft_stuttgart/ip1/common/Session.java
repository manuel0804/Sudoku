package de.hft_stuttgart.ip1.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Session extends Remote {

    Character[] getBoard() throws RemoteException;

    Boolean compare(Character given, Integer position) throws RemoteException;

    Boolean createBoard(Integer size) throws RemoteException;

    Boolean[] getChangeableBoard() throws RemoteException;

    Character[] getBoardLsg() throws RemoteException;

    Player getActivePlayer() throws RemoteException;

    Boolean addPlayer(Player player) throws RemoteException;

    Boolean removePlayer(Player player) throws RemoteException;

    Boolean startGame() throws RemoteException;

    Boolean getGameStarted() throws RemoteException;

    Player getPlayer(String Username) throws RemoteException;
    String getSessionName() throws RemoteException;

    List<String> getPlayerList() throws RemoteException;

    Session getSession() throws RemoteException;

    Boolean isBoardSolved() throws RemoteException;

    String getPlayerScore() throws RemoteException;

    List<Highscore> returnHighscore() throws RemoteException;

    String getGuesses() throws RemoteException;

    Boolean isFinished() throws RemoteException;
}
