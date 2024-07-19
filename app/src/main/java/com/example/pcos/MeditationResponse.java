package com.example.pcos;

public class MeditationResponse {
    String audio_name;
    String audio_description;
    String audio_path;
    String duration;
    String error;

    // Getters and setters for each field
    public String getAudioName() {
        return audio_name;
    }

    public void setAudioName(String audio_name) {
        this.audio_name = audio_name;
    }

    public String getAudioDescription() {
        return audio_description;
    }

    public void setAudioDescription(String audio_description) {
        this.audio_description = audio_description;
    }

    public String getAudioPath() {
        return audio_path;
    }

    public void setAudioPath(String audio_path) {
        this.audio_path = audio_path;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
