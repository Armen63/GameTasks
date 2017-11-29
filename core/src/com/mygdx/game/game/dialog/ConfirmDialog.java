package com.mygdx.game.game.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.game.stage.MenuStage;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;

import static com.badlogic.gdx.Gdx.app;

/**
 * Created by Armen on 11/9/2017.
 */

public class ConfirmDialog extends BaseDialog {

    protected Table mainTable;
    private Label lbl_question;
    private TextButton btnYes;
    private TextButton btnNo;

    public static final int BTN_FONT_SIZE = 6;
    public static final int LABEL_FONT_SIZE = 8;

    public ConfirmDialog(Stage stage) {
        super(stage);
        initUi();
    }


    public void initUi() {
        mainTable = new Table(Assets.$().uiSkin);
        group.addActor(mainTable);
        mainTable.setBackground(new TextureRegionDrawable(new TextureRegion((Texture) Assets.$().get(Constants.IMAGE_DARK_GRAY))));
        mainTable.setSize(
                MenuStage.$().getWidth() / 2,
                MenuStage.$().getHeight() / 1.5f
        );
        mainTable.setPosition((Gdx.graphics.getWidth() - mainTable.getWidth()) * .5f, (Gdx.graphics.getHeight() - mainTable.getHeight()) * .5f);

    }

    public ConfirmDialog setDescription(String description) {
        lbl_question = new Label("Are you sure ?", new Label.LabelStyle(Assets.$().defaultFont, Color.BLUE));
        mainTable.add(lbl_question)
                .align(Align.center)
                .width(Value.percentWidth(.68f, mainTable))
                .height(Value.percentHeight(.1f, mainTable))
                .expandY()
                .colspan(2)
                .row();

        app.log("sd", "" + lbl_question.getX());
        return this;
    }


    public ConfirmDialog setPositiveButton(String text, ActorGestureListener listener) {
        btnYes = new TextButton(text, new TextButton.TextButtonStyle(new TextureRegionDrawable(new TextureRegion((Texture) Assets.$().get(Constants.IMAGE_BUTTON_BG))), null, null, Assets.$().uiSkin.getFont("myFont")));

        mainTable.add(btnYes)
                .align(Align.bottom)
                .width(Value.percentWidth(.3f, mainTable))
                .height(Value.percentHeight(0.2f, mainTable))
                .left()
                .padLeft(Value.percentWidth(.18f, mainTable));
        btnYes.addListener(listener);
        return this;
    }

    public ConfirmDialog setNegativeButton(String text, ActorGestureListener listener) {
        btnNo = new TextButton(text, new TextButton.TextButtonStyle(new TextureRegionDrawable(new TextureRegion((Texture) Assets.$().get(Constants.IMAGE_BUTTON_BG))), null, null, Assets.$().uiSkin.getFont("myFont")));
        mainTable.add(btnNo)
                .align(Align.bottom)
                .width(Value.percentWidth(.3f, mainTable))
                .height(Value.percentHeight(0.2f, mainTable))
                .right()
                .padLeft(Value.percentWidth(.04f, mainTable))
                .padRight(Value.percentWidth(.18f, mainTable));
        btnNo.addListener(listener);
        return this;
    }

}
