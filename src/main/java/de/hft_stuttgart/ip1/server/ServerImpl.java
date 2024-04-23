package de.hft_stuttgart.ip1.server;

import de.hft_stuttgart.ip1.client.gui.SudokuPanel;
import de.hft_stuttgart.ip1.common.Highscore;
import de.hft_stuttgart.ip1.common.Player;
import de.hft_stuttgart.ip1.common.Server;
import de.hft_stuttgart.ip1.common.Session;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerImpl implements Server {

    private List<Player> players;
    private List<Session> sessions;
    private List<Highscore> highscores;

    public ServerImpl() {
        this.players = new ArrayList<>(){
            @Override
            public boolean contains(Object o) {
                if (o instanceof String) {
                    for (Player player : this) {
                        try {
                            if (player.getUsername().equals(o)) {
                                return true;
                            }
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                return false;
            }
            @Override
            public boolean remove(Object o) {
                if (o instanceof String) {
                    for (Player player : this) {
                        try {
                            if (player.getUsername().equals(o)) {
                                return super.remove(player);
                            }
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                return false;
            }
        };
        this.sessions = new ArrayList<>();
        highscores = new ArrayList<>();
        try{
            var scores = HighscoreSerializer.deserializeHighscores("highscores.ser");
            highscores.addAll(scores);
        }
        catch (Exception e) {
            System.out.println("No highscores found");
        }


    }

    @Override
    public Player login(String username) throws RemoteException {
        if (!players.contains(username)) {
            players.add(new PlayerImpl(username));
            return players.get(players.size()-1);
        }
        else {
            return null;
        }
    }

    @Override
    public Boolean logout(String username) throws RemoteException {
        if(players.contains(username)) {
            players.remove(username);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Session newSession(String name, Integer boardSize, Integer playerCount) throws RemoteException {
        for (Session session : sessions) {
            if (Objects.equals(session.getSessionName(), name)) {
                return null;
            }
        }
            var session = new SessionImpl(boardSize, playerCount, name, this);
            sessions.add(session);
            return session;
    }

    @Override
    public Session joinSession(String name, Player player) throws RemoteException {
        for (Session session : sessions) {
            if (Objects.equals(session.getSessionName(), name)) {
                session.addPlayer(player);
                return session;
            }
        }
        return null;
    }

    @Override
    public List<String> getPlayerList() throws RemoteException {
        List<String> playerList = new ArrayList<>();
        for (Player player : players) {
            {
                playerList.add(player.getUsername());
            }

        }
        return playerList;
    }

    @Override
    public List<String> getSessionList() throws RemoteException {
        List<String> sessionList = new ArrayList<>();
        for (Session session : sessions) {
            sessionList.add(session.getSessionName());
        }
        return sessionList;
    }

    @Override
    public Session updateSession(Session session) throws RemoteException {
        for (int i = 0; i < sessions.size(); i++) {
            if (Objects.equals(sessions.get(i).getSessionName(), session.getSessionName())) {
                sessions.set(i, session);
                return session;
            }
        }
        return null;
    }

    @Override
    public List<String> getHighScore(Integer gameSize, Integer playerCount) throws RemoteException {
        List<String> highscoreList = new ArrayList<>();
        for (Highscore score : highscores) {
            if(gameSize != -1 && playerCount != -1){
                if (Objects.equals(score.getGameSize(), gameSize) && Objects.equals(score.getPlayerCount(), playerCount)) {
                    highscoreList.add(score.getUsername() + " " + score.getScore());
                }
            }
            else{
                highscoreList.add(score.getUsername() + " " + score.getScore());
            }
        }
        return highscoreList;
    }

    public void addHighscore(Session session) throws RemoteException {
        highscores.addAll(session.returnHighscore());
        sessions.remove(session);
        HighscoreSerializer.serializeHighscores(highscores, "highscores.ser");
    }



}
