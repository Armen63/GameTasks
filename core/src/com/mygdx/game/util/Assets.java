package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


/**
 * Created by Armen on 10/18/2017.
 */

public class Assets extends AssetManager {
    private static Assets instance;
    public Skin defaultSkin;

    private Assets() {
        super();
        loadImages();
        defaultSkin = new Skin(
                Gdx.files.internal("skin/uiskin.json"),
                new TextureAtlas(Gdx.files.internal("skin/pack.atlas"))
        );

    }


    private void loadImages() {
        load(Constants.IMAGE_WORLD, Texture.class);
        load(Constants.IMAGE_MENU_BG, Texture.class);
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    public BitmapFont defaultFont = defaultFont();
    public BitmapFont tempFont = tempFont();
    public Skin uiSkin;

    private BitmapFont defaultFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("default.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 80;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 2;
        parameter.borderWidth = 2;
        parameter.borderColor = new Color(Color.RED);
        parameter.shadowColor = new Color(Color.RED);
        BitmapFont currentFont = generator.generateFont(parameter);
        generator.dispose();

        return currentFont;
    }

    private BitmapFont tempFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("default.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;
        BitmapFont currentFont = generator.generateFont(parameter);
        generator.dispose();

        return currentFont;
    }

    public static Assets $() {
        return (instance == null ? instance = new Assets() : instance);
    }

}
