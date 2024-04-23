
package de.hft_stuttgart.ip1.server;

import de.hft_stuttgart.ip1.common.Server;
import de.hft_stuttgart.ip1.common.Session;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        System.out.println("Server is starting...");
        Registry registry = LocateRegistry.createRegistry(28725);
        Server server = new ServerImpl();
        UnicastRemoteObject.exportObject(server, 0);
        registry.bind("Sudoku", server);
        System.out.println("Server started");
    }

}

