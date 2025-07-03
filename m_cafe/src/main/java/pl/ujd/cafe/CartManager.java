package pl.ujd.cafe;

import java.util.ArrayList;
import java.util.List;

import pl.ujd.cafe.models.Item;

public final class CartManager {

    private static CartManager instance;

    private final List<Item> items = new ArrayList<>();

    public static CartManager getInstance() {
        if (instance == null) instance = new CartManager();
        return instance;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(final Item item) {
        this.items.add(item);
    }

    public void clear() {
        this.items.clear();
    }

}