package game;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {

    private static String[] src = {"Final Project\\src\\audio\\bgm.mp3","Final Project\\src\\audio\\menu.wav","Final Project\\src\\audio\\pFire.wav",
            "Final Project\\src\\audio\\pHit.wav","Final Project\\src\\audio\\eHit.wav","Final Project\\src\\audio\\eDeath.wav","Final Project\\src\\audio\\powerup.wav"};
    protected static Media bgm = new Media(new File(src[0]).toURI().toString());
    protected static AudioClip menu = new AudioClip(new File(src[1]).toURI().toString());
    protected static AudioClip pFire = new AudioClip(new File(src[2]).toURI().toString());
    protected static AudioClip pHit = new AudioClip(new File(src[3]).toURI().toString());
    protected static AudioClip eHit = new AudioClip(new File(src[4]).toURI().toString());
    protected static AudioClip eDead = new AudioClip(new File(src[5]).toURI().toString());
    protected static AudioClip powerup = new AudioClip(new File(src[6]).toURI().toString());
    protected static MediaPlayer bgmp = new MediaPlayer(bgm);
}
