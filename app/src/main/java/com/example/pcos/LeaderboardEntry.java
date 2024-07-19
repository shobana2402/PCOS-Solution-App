package com.example.pcos;

public class LeaderboardEntry {
    private String name;
    private int totalScore;
    private String profileImage;

    public LeaderboardEntry(String name, int totalScore, String profileImage) {
        this.name = name;
        this.totalScore = totalScore;
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
