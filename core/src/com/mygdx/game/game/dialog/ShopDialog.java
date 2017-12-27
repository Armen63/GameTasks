package com.mygdx.game.game.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.game.ItemData;
import com.mygdx.game.game.MainController;
import com.mygdx.game.game.stage.UiStage;
import com.mygdx.game.managers.ItemDataManager;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;

/**
 * Created by Armen on 11/17/2017.
 */

public class ShopDialog extends Table {
    private static final String LOG_TAG = ShopDialog.class.getName();

    private static ShopDialog instance;
    private boolean isOpened;
    private Button closeDialogBtn;
    private Label shopCardText;
    private Table itemScrollTable;
    private ShopItem tempItem;

    {
        addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                event.cancel();
                return false;
            }
        });
    }

    public ShopDialog() {
        initUi();
    }


    private void initUi() {
        setTouchable(Touchable.enabled);
        initMainTable();
        initTopOfShop();
        initScrollTable();
    }

    private void initTopOfShop() {
        shopCardText = new Label("Shop Deck", new Label.LabelStyle(Assets.$().defaultFont, Color.RED));
        shopCardText.setAlignment(Align.center);
        add(shopCardText)
                .size(Value.percentWidth(.15f, this), Value.percentHeight(.1f, this));


        closeDialogBtn = new Button(Assets.$().defaultSkin.getDrawable(Constants.IMAGE_CLOSE_SHOP));
        closeDialogBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
        add(closeDialogBtn)
                .size(Value.percentHeight(.15f, this), Value.percentHeight(.15f, this))
                .align(Align.topRight)
                .right()
                .row();
    }

    private void initScrollTable() {

        itemScrollTable = new Table();
        itemScrollTable.align(Align.center);
        itemScrollTable.setPosition(0, 0);

        ScrollPane scroll = new ScrollPane(itemScrollTable);
        scroll.setForceScroll(false, false);

        for (ItemData data : ItemDataManager.$().itemData) {
            final ShopItem shopCard = new ShopItem(data);
            shopCard.setTransform(true);
            itemScrollTable.add(shopCard).size(400, 600);
            shopCard.getItemImage().addListener(new ActorGestureListener() {
                @Override
                public void tap(InputEvent event, float x, float y, int count, int button) {
                    selectShopItem(shopCard);

                }
            });
            shopCard.getUseBtn().addListener(new ActorGestureListener() {
                @Override
                public void tap(InputEvent event, float x, float y, int count, int button) {
                    Array<Cell> cells = itemScrollTable.getCells();
                    Cell currentCell = itemScrollTable.getCell(shopCard);
                    for (int i = cells.indexOf(currentCell, true); i < cells.size - 1; i++) {
                        Gdx.app.log(LOG_TAG, i + "");
                        cells.swap(i, i + 1);

                    }
                    cells.removeValue(currentCell, true);

                    MainController.getGameScreen().getGameStage().addActor(shopCard);
                    hide();
                    shopCard.getUseBtn().setVisible(false);
                    shopCard.setTouchable(Touchable.disabled);

                    shopCard.setPosition((Gdx.graphics.getWidth() - shopCard.getWidth()) * .5f, 0f);
                    shopCard.addAction(Actions.sequence(
                            Actions.moveTo(
                                    (Gdx.graphics.getWidth() - shopCard.getWidth()) * .5f
                                    , (Gdx.graphics.getHeight()) * .5f
                                    , 0.6f, Interpolation.pow3Out
                            ),
                            Actions.moveTo(
                                    (Gdx.graphics.getWidth() - shopCard.getWidth()) * .5f
                                    , (Gdx.graphics.getHeight() - shopCard.getHeight()) * .5f
                                    , 1f, Interpolation.bounceOut
                            ),

                            Actions.scaleTo(0, 0, 1f, Interpolation.exp5Out)

                            , Actions.addAction(new RunnableAction() {
                                @Override
                                public void run() {
                                    shopCard.remove();
                                    MainController.getGameScreen().getGameStage().addSpineBoy((int) shopCard.getX(), (int) shopCard.getY(), data);
                                }
                            })
                    ));
                }
            });
        }
        add(scroll)
                .size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2.1f)
                .colspan(2);
    }

    private void initMainTable() {
        setBackground(Assets.$().defaultSkin.getDrawable((Constants.IMAGE_DARK_GRAY)));
        setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 1.8f);
    }

    private void selectShopItem(ShopItem shopItem) {
        if (tempItem == shopItem && tempItem.getUseBtn().isVisible()) {
            Gdx.app.log(LOG_TAG, "shop " + shopItem.getNameText() + "temp " + tempItem.getNameText());
            tempItem.getUseBtn().setVisible(false);
            return;
        }
        if (tempItem != null) {
            tempItem.getUseBtn().setVisible(false);
        }
        shopItem.getUseBtn().setVisible(true);
        tempItem = shopItem;
    }

    public void show() {
        isOpened = true;
        setPosition(0, -(Gdx.graphics.getHeight() / 1.8f));
        UiStage.$().addActor(this);
        addAction(Actions.moveTo(0, 0, 1f, Interpolation.swing));
    }

    private void hide() {
        if (isOpened) {
            isOpened = false;
            addAction(
                    Actions.sequence(Actions.moveTo(0, -(Gdx.graphics.getHeight() / 1.8f), 0.8f, Interpolation.fade),
                            Actions.addAction(new RunnableAction() {
                                @Override
                                public void run() {
                                    remove();
                                }
                            })));
        }
    }


    private class ShopItem extends Stack {
        private static final int STATE_CARD_VIEW = 1;
        private static final int STATE_INFO_VIEW = 2;

        private Cell currentCell;
        private Table mainTable;
        private int currentState = STATE_CARD_VIEW;
        private Button informationBtn;
        private Image itemImage;
        private Label informationText;
        private Label nameText;
        private TextButton priceBtn;
        private Button useBtn;
        private int id;

        public int getId() {
            return id;
        }

        Label getNameText() {
            return nameText;
        }

        ShopItem(ItemData data) {
            mainTable = new Table();
            mainTable.setBackground(Assets.$().defaultSkin.getDrawable(Constants.IMAGE_CARD_BACKGROUND));
            setTouchable(Touchable.enabled);

            id = data.id;

            informationBtn = new Button(Assets.$().defaultSkin.getDrawable(Constants.IMAGE_SHOP_INFO_BUTTON));
            informationBtn.addListener(new ActorGestureListener() {
                @Override
                public void tap(InputEvent event, float x, float y, int count, int button) {
                    changeState();
                }
            });
            itemImage = new Image(new Texture(Gdx.files.internal("shop/items/" + data.id + ".png")));
            nameText = new Label(data.name, new Label.LabelStyle(new Label.LabelStyle(Assets.$().defaultFont, Color.PINK)));
            nameText.setFontScale(0.65f);
            nameText.setEllipsis(true);

            useBtn = new Button(Assets.$().defaultSkin.getDrawable(Constants.IMAGE_USE_BUTTON));

            informationText = new Label(data.description, new Label.LabelStyle(new Label.LabelStyle(Assets.$().tempFont, Color.GOLD)));
            informationText.setFontScale(0.6f);
            informationText.setAlignment(Align.topLeft);
            informationText.setWrap(true);

            priceBtn = new TextButton(String.valueOf(data.price), new TextButton.TextButtonStyle(Assets.$().defaultSkin.getDrawable(Constants.IMAGE_BUTTON_BG), null, null, Assets.$().defaultFont));
            priceBtn.getLabel().setFontScale(0.7f);


            mainTable.add(new Container<>(informationBtn)
                    .background(Assets.$().defaultSkin.getDrawable(Constants.IMAGE_ELIXIR_BG))
                    .size(Value.percentWidth(0.25f, this), Value.percentHeight(0.18f, this))
                    .padTop(Value.percentHeight(.08f, this))
                    .padLeft(Value.percentWidth(.02f, this))
                    .align(Align.topLeft));

            mainTable.add(nameText)
                    .size(Value.percentWidth(0.58f, this), Value.percentHeight(0.15f, this))
                    .padTop(Value.percentHeight(.05f, this))
                    .padRight(Value.percentHeight(.15f, this))
                    .row();

            nameText.setAlignment(Align.right);
            mainTable.add(itemImage)
                    .size(Value.percentWidth(0.8f, this), Value.percentHeight(0.6f, this))
                    .colspan(2)
                    .row();

            mainTable.add(priceBtn)
                    .size(Value.percentWidth(0.6f, this), Value.percentHeight(0.14f, this))
                    .colspan(2)
                    .align(Align.bottom);

            useBtn.setVisible(false);

            add(mainTable);
            add(new Container<>(useBtn)
                    .size(Value.percentWidth(.6f, mainTable), Value.percentHeight(.22f, mainTable))
                    .padRight(Value.percentWidth(.2f, mainTable))
                    .padLeft(Value.percentWidth(.2f, mainTable))
                    .align(Align.center));

            currentCell = mainTable.getCell(itemImage);
            currentCell.align(Align.center);
        }

        Image getItemImage() {
            return itemImage;
        }

        Button getUseBtn() {
            return useBtn;
        }

        void showInformation() {
            ScrollPane scroll = new ScrollPane(informationText);
            currentCell.setActor(scroll);
        }

        void showCardView() {
            currentCell.setActor(itemImage);
        }

        void changeState() {
            currentState = currentState == STATE_CARD_VIEW ? STATE_INFO_VIEW : STATE_CARD_VIEW;
            switch (currentState) {
                case STATE_CARD_VIEW:
                    showCardView();
                    break;
                case STATE_INFO_VIEW:
                    showInformation();
                    break;
            }
        }
    }

    public static ShopDialog $() {
        return instance == null ? instance = new ShopDialog() : instance;
    }
}
