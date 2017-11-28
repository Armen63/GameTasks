package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

;

/**
 * Created by Armen on 10/18/2017.
 */

public class Assets extends AssetManager {
    private static Assets instance;

    private Assets() {
        super();
        loadImages();
    }

    public void loadImages() {
        load(Constants.IMAGE_WORLD, Texture.class);
        load(Constants.IMAGE_HUD_BG, Texture.class);
        load(Constants.IMAGE_MENU_BG, Texture.class);
        load(Constants.ICON_GAME_BACK, Texture.class);
        load(Constants.IMAGE_CONFIRM_DIALOG_BG, Texture.class);
        load(Constants.IMAGE_BUTTON_BG, Texture.class);
        load(Constants.IMAGE_SHOP_BTN, Texture.class);
        load(Constants.IMAGE_CLOSE_SHOP, Texture.class);
        load(Constants.IMAGE_CARD_BACKGROUND, Texture.class);
        load(Constants.IMAGE_SHOP_LABEL_TEXT, Texture.class);
        load(Constants.IMAGE_SHOP_INFO_BUTTON, Texture.class);
    }

    public FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    public BitmapFont defaultFont = defaultFont();
    public Skin uiSkin;

    private BitmapFont defaultFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("default.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 80;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 2;
        parameter.borderWidth = 2;
        parameter.borderColor = new Color(Color.PINK);
        parameter.shadowColor = new Color(Color.PINK);
        BitmapFont currentFont = generator.generateFont(parameter);
        generator.dispose();


        uiSkin = new Skin();
        uiSkin.add("defaultFont", currentFont);
        uiSkin.addRegions(new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        uiSkin.load(Gdx.files.internal("uiskin.json"));
        return currentFont;
    }

    public static Assets $() {
        return (instance == null ? instance = new Assets() : instance);
    }

}
