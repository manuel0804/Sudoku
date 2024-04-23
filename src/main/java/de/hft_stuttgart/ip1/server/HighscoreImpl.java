package de.hft_stuttgart.ip1.server;

import de.hft_stuttgart.ip1.common.Highscore;
import java.io.Serializable;
import java.rmi.RemoteException;

public class HighscoreImpl implements Highscore, Serializable {
    private static final long serialVersionUID = 1L;
    String username;
    Integer gameSize;
    Integer playerCount;
    Integer score;

    public HighscoreImpl(String username, Integer gameSize, Integer playerCount, Integer score) {
        this.username = username;
        this.gameSize = gameSize;
        this.playerCount = playerCount;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }
    public Integer getGameSize() {
        return gameSize;
    }
    public Integer getPlayerCount() {
        return playerCount;
    }
    public Integer getScore() {
        return score;
    }
}
