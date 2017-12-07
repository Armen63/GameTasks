package com.mygdx.game.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.game.dialog.ShopDialog;
import com.mygdx.game.game.stage.GameStage;
import com.mygdx.game.game.stage.UiStage;
import com.mygdx.game.managers.CameraManager;
import com.mygdx.game.managers.ItemDataManager;
import com.mygdx.game.managers.SoundManager;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;

import static com.badlogic.gdx.Gdx.app;
import static com.mygdx.game.util.Constants.HUD_DELTA_HEIGHT;
import static com.mygdx.game.util.Constants.IMAGE_DARK_GRAY;
import static com.mygdx.game.util.Constants.IMAGE_SHOP_BTN;
import static com.mygdx.game.util.Constants.IMAGE_WORLD;
import static com.mygdx.game.util.Constants.MUSIC_GAME_SCREEN;
import static com.mygdx.game.util.Constants.MUSIC_MENU_SCREEN;

/**
 * Created by Armen on 10/18/2017.
 */

public class GameScreen extends AbstractBaseScreen {
    private static final String LOG_TAG = GameScreen.class.getName();

    private GameStage gsStage;
    public final Image wordBg = new Image((Texture) Assets.$().get(IMAGE_WORLD));
    private Label hudLabelOne;
    private Label hudLabelTwo;
    private Label hudLabelThree;
    private Table hudTable;
    private Table mainTable;
    private Drawable drbBackground;
    private Button shopDialogBtn;
    private ShopDialog shopDialog;

    @Override
    public void show() {
        ItemDataManager.$().initList();
        shopDialog = new ShopDialog();
        SoundManager.$().stopMusic(MUSIC_MENU_SCREEN);
        SoundManager.$().playMusic(MUSIC_GAME_SCREEN, false, 0.8f);

        initBackground();
        initHud();
        setInputProcessors();
    }

    private void setInputProcessors() {
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(
                new InputMultiplexer(
                        UiStage.$(),
                        gsStage,
                        CameraManager.getInstance().getGestureDetector()
                )
        );
    }
    public GameStage getGameStage(){
        return gsStage;
    }
    private void initHud() {
        app.log("ssssss", "gdx" + Gdx.graphics.getWidth());
        drbBackground = Assets.$().defaultSkin.getDrawable(IMAGE_DARK_GRAY);
        mainTable = new Table();
        mainTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        hudTable = new Table();
        hudTable.setSize(Gdx.graphics.getWidth(), HUD_DELTA_HEIGHT);
        hudTable.align(Align.top);


        hudLabelOne();
        hudLabelTwo();
        hudLabelThree();
        initShopDialogButton();
        UiStage.$().addActor(mainTable);
    }

    private void hudLabelOne() {
        hudLabelOne = new Label("100205", new Label.LabelStyle(Assets.$().defaultFont, Color.BLUE));
        hudTable.add(new Container<>(hudLabelOne)
                .background(Assets.$().defaultSkin.getDrawable(Constants.IMAGE_DARK_GRAY)))
                .size(Value.percentWidth(.2f, hudTable), Value.percentHeight(1f, hudTable));
    }

    private void hudLabelTwo() {
        hudLabelTwo = new Label("20000", new Label.LabelStyle(Assets.$().defaultFont, Color.RED));
        hudTable.add(new Container<>(hudLabelTwo)
                .background(Assets.$().defaultSkin.getDrawable(Constants.IMAGE_DARK_GRAY)))
                .size(Value.percentWidth(.2f, hudTable), Value.percentHeight(1f, hudTable))
                .padLeft(Value.percentWidth(0.1f, hudTable));
    }

    private void hudLabelThree() {
        hudLabelThree = new Label("1469503", new Label.LabelStyle(Assets.$().defaultFont, Color.YELLOW));
        hudTable.add(new Container<>(hudLabelThree)
                .background(Assets.$().defaultSkin.getDrawable(Constants.IMAGE_DARK_GRAY)))
                .size(Value.percentWidth(.2f, hudTable), Value.percentHeight(1f, hudTable))
                .padLeft(Value.percentWidth(0.1f, hudTable));
    }

    private void initBackground() {
        gsStage = new GameStage();
        wordBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        wordBg.setPosition(0, 0);
        gsStage.addActor(wordBg);
    }


    private void initShopDialogButton() {
        mainTable.add(hudTable)
                .align(Align.topRight)
                .padTop(Value.percentHeight(.045f, mainTable))
                .expandY()
                .row();

        shopDialogBtn = new Button(Assets.$().defaultSkin.getDrawable(IMAGE_SHOP_BTN));
        shopDialogBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shopDialog.show();
            }
        });
        mainTable.add(shopDialogBtn)
                .width(220)
                .height(200)
                .right()
                .expandX()
                .padBottom(Value.percentHeight(.02f, mainTable));


        UiStage.$().addActor(mainTable);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsStage.getViewport().update(
                (int) Gdx.graphics.getWidth(),
                (int) Gdx.graphics.getHeight(),
                false
        );
        gsStage.act(delta);
        gsStage.draw();

        UiStage.$().getViewport().apply();
        UiStage.$().getViewport().update(
                (int) Gdx.graphics.getWidth(),
                (int) Gdx.graphics.getHeight(),
                false
        );
        UiStage.$().act(delta);
        UiStage.$().draw();

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            CameraManager.getInstance().handleInput();
        }

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
