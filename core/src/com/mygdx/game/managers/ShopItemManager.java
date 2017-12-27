package com.mygdx.game.managers;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.game.ItemData;

/**
 * Created by Armen on 12/27/2017.
 */

public class ShopItemManager  {
    private static ShopItemManager instance;
    public Array<ItemData> itemData = new Array<>();

    private ShopItemManager() {
        initList();
    }

    public void initList() {
        for (ItemData data : ItemDataManager.$().itemData) {
            itemData.add(data);
        }
    }

    public static ShopItemManager $() {
        return instance == null ? instance = new ShopItemManager() : instance;
    }

}
