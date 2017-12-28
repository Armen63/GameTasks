package com.mygdx.game.managers;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import static com.mygdx.game.util.Constants.MUSIC_DIALOG_BLT;
import static com.mygdx.game.util.Constants.MUSIC_GAME_SCREEN;
import static com.mygdx.game.util.Constants.MUSIC_MENU_SCREEN;
import static com.mygdx.game.util.Constants.SOUND_PATH;


/**
 * Created by Armen on 11/14/2017.
 */

public class SoundManager {
    private static SoundManager instance = null;


    boolean isPlaying;
    private Array<String> preLoadAudio = new Array<String>();
    private ObjectMap<String, Music> musicMap = new ObjectMap<String, Music>();
    private ObjectMap<String, Sound> soundMap = new ObjectMap<String, Sound>();

    private SoundManager() {
        preLoadAudio.add(MUSIC_MENU_SCREEN);
        preLoadAudio.add(MUSIC_GAME_SCREEN);
        preLoadAudio.add(MUSIC_DIALOG_BLT);
        loadMusics();
    }

    public static SoundManager $() {
        if (instance == null)
            instance = new SoundManager();
        return instance;
    }

    public void playSound(Sound sound) {
        if (isPlaying) {
            sound.play();
        }
    }

    public void playSound(String soundName, boolean loop) {
        if (isPlaying) {
            String fileName = getAudioFilePath(soundName);
            Sound sound = soundMap.get(fileName);
            if (sound == null) {
                try {
                    sound = Gdx.audio.newSound(Gdx.files.getFileHandle(fileName, Files.FileType.Internal));
                    soundMap.put(fileName, sound);
                } catch (Exception ex) {
                    fileName = getAudioFilePath(soundName);
                    sound = soundMap.get(fileName);
                    if (sound == null) {
                        try {
                            sound = Gdx.audio.newSound(Gdx.files.getFileHandle(fileName, Files.FileType.Internal));
                            soundMap.put(fileName, sound);
                        } catch (Exception e) {
                        }
                    }
                }
            }
            if (sound != null) {


                sound.play();
            }
        }
    }


/////////////////////////////////////////////////////
    //  Music////

    public void playMusic(Music music, boolean isLooping) {
        if (isLooping) {
            music.play();
            music.setLooping(isLooping);
        }
    }

    public void playMusic(String musicName, boolean loop, float volume) {
        if (!isPlaying) {
            String fileName = getAudioFilePath(musicName);
            Music music = musicMap.get(fileName);
            music.setVolume(volume);
            music.setLooping(loop);
            music.play();
        }
    }

    public static void pause(Music sound) {
        if (sound != null) {
            sound.pause();
        }
    }

    public static void stopMusic(Music music) {
        if (music != null) {
            music.stop();
        }
    }

    public void stopMusic(String musicName) {
        String fileName = getAudioFilePath(musicName);
        Music music = musicMap.get(fileName);
        music.stop();
    }

    public void loadMusics() {
        for (int i = 0; i < preLoadAudio.size; i++) {
            String filePath = getAudioFilePath(preLoadAudio.get(i));
            //music ? sound
            Music music = Gdx.audio.newMusic(Gdx.files.getFileHandle(filePath, Files.FileType.Internal));
            musicMap.put(filePath, music);
        }
    }


    private String getAudioFilePath(String audioName) {
        return SOUND_PATH + audioName + ".mp3";
    }
}
