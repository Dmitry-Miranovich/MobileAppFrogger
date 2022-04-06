package com.example.lab1.db.view;

import androidx.annotation.NonNull;

public class Player {
    private String nickname;
    private String score;
    private String max_score;
    private String time;

    public Player(String nickname, String score){
        this.nickname = nickname;
        this.score = score;
    }
    public Player(String nickname, String score, String time, String max_score){
        this.nickname = nickname;
        this.score = score;
        this.time = time;
        this.max_score = max_score;
    }

    public String getMax_score() {
        return max_score;
    }

    public void setMax_score(String max_score) {
        this.max_score = max_score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @NonNull
    @Override
    public String toString() {
        return "Player: " + nickname + ", " + "score: " + score +", " + "time: " + time + ", " + "best score: " + max_score;
    }
}
