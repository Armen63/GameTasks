package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;

/**
 * Created by Armen on 12/28/2017.
 */

public class Logger {
    public static void LOG(String key, String value){
        Gdx.app.log(key,value);
    }
}
