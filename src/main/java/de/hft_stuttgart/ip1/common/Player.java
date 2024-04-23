package de.hft_stuttgart.ip1.common;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Player extends Remote, Serializable {

     String getUsername() throws RemoteException;

     Integer getScore() throws RemoteException;

     void addScore(Integer score) throws RemoteException;
}
