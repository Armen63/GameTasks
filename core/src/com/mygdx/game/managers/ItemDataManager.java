package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.game.ItemData;

/**
 * Created by Armen on 11/20/2017.
 */

public class ItemDataManager {
    private static ItemDataManager instance;
    public Array<ItemData> itemData = new Array<>();

    private ItemDataManager() {

    }

    public void initList() {
        Json json = new Json();
        Array<JsonValue> list = json.fromJson(Array.class, (Gdx.files.internal("shop/shop_items.json")));
        for (JsonValue values : list) {
            itemData.add(wrappModel(values));
        }
    }

    private ItemData wrappModel(JsonValue value) {
        return new ItemData(value);
    }

    public static ItemDataManager $() {
        return instance == null ? instance = new ItemDataManager() : instance;
    }
}