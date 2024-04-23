package de.hft_stuttgart.ip1.server;

import de.hft_stuttgart.ip1.common.Player;

public class PlayerImpl implements Player {
    private final String username;
    private Integer score;

    public PlayerImpl(String name) {
        this.username = name;
        this.score = 0;
    }

    public String getUsername() {
        return username;
    }

    public Integer getScore() {
        return score;
    }

    public void addScore(Integer score) {
        this.score += score;
    }
}
