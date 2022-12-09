package com.example.java1_2022_kub0679;

import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;

public abstract class Resources {

    public static final Image METEOR_IMAGES[] = new Image[]{
            new Image(Resources.class.getResource("images/meteor.png").toString()),
            new Image(Resources.class.getResource("images/meteor2.png").toString()),
            new Image(Resources.class.getResource("images/meteor3.png").toString()),
    };
    public static final Sound METEORHITSOUND = new Sound("meteorHit.mp3");

    public static final Image COIN_IMAGE = new Image(Resources.class.getResource("images/88_fall.Coin_coinImg.png").toString());

    public static final Sound PICKUPSOUND = new Sound("coinPickup.mp3");
    public static final Image PLAYER_IMAGE = new Image(Resources.class.getResource("images/playerImg.png").toString());

    public static final Sound JUMPSOUND = new Sound("playerJump.mp3");

    public static final Sound USESKILLSOUND = new Sound("skillUseSound.mp3");

    public static final Sound GAMEOVERMUSIC = new Sound("gameOverMusic.mp3", MediaPlayer.INDEFINITE);
    public static final Sound MAINMENUMUSIC = new Sound("mainMenuMusic.mp3", MediaPlayer.INDEFINITE);
    public static final Sound DAYMUSIC = new Sound("BaseGameMusic.mp3", MediaPlayer.INDEFINITE);
    public static final Sound UPGRADEMUSIC = new Sound("upgradeMusic.mp3", MediaPlayer.INDEFINITE);

    public static Sound[] MENUSONGS = new Sound[]{
            GAMEOVERMUSIC,  MAINMENUMUSIC,  DAYMUSIC, UPGRADEMUSIC
    };

}
