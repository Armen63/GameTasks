package com.mygdx.game.game;

import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by Armen on 11/20/2017.
 */

public class ItemData {
    public String name;
    public String description;
    public int price;
    public int id;

    public ItemData() {
    }


    public ItemData(JsonValue value) {
        this.id = value.getInt("id", 0);
        this.name = value.getString("name", "");
        this.description = value.getString("description", "");
        this.price = value.getInt("price", 0);
    }

}
