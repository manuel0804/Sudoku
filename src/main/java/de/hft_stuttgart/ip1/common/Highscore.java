package de.hft_stuttgart.ip1.common;

import java.rmi.RemoteException;

public interface Highscore {

    String getUsername() throws RemoteException;
    Integer getGameSize() throws RemoteException;
    Integer getPlayerCount() throws RemoteException;
    Integer getScore() throws RemoteException;

}
