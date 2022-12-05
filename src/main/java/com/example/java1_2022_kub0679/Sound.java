package com.example.java1_2022_kub0679;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URISyntaxException;

public class Sound {

    private MediaPlayer player;

    public Sound() {
    }
    public Sound(String fileName) {
        Media media = null;
        try {
            media = new Media(getClass().getResource("sounds/"+fileName).toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        player = new MediaPlayer(media);
        player.setCycleCount(1);
    }

    public void playSound(String fileName) {
        try{
            Media media = new Media(getClass().getResource("sounds/"+fileName).toURI().toString());
            player = new MediaPlayer(media);
            player.setCycleCount(MediaPlayer.INDEFINITE);
            player.play();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Sound(String fileName, int cycleCount) {
        Media media = null;
        try {
            media = new Media(getClass().getResource("sounds/"+fileName).toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        player = new MediaPlayer(media);
        player.setCycleCount(cycleCount);
    }

    public boolean isPlaying(){
        return player.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public void stopSound(){
        player.stop();
    }
    public void playSound(){
        player.seek(new Duration(0));
        player.play();
    }
    public void setVolume(double volume){
        player.setVolume(volume);
    }

}
