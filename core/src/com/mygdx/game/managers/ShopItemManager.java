package com.mygdx.game.managers;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.util.data.ItemData;

import java.util.Comparator;

/**
 * Created by Armen on 12/27/2017.
 */

public class ShopItemManager {
    private static ShopItemManager instance;
    public Array<ItemData> itemDataList = new Array<>();

    private ShopItemManager() {
        initList();
    }

    private void initList() {
        for (ItemData data : ItemDataManager.$().itemData) {
            itemDataList.add(data);
        }
    }

    public static ShopItemManager $() {
        return instance == null ? instance = new ShopItemManager() : instance;
    }


    public void addItemWithRefresh(ItemData data) {
        itemDataList.add(data);
        itemDataList.sort(new Comparator<ItemData>() {
            @Override
            public int compare(ItemData t1, ItemData t2) {
                return t1.orderBy > t2.orderBy ? 1 : -1;
            }
        });
    }

}
