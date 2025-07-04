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

    public float getItemsWorth() {
        float worth = 0;
        for (int i = 0; i < this.items.size(); i++) {
            worth += this.items.get(i).getPrice() * this.items.get(i).getQuantity();
        }
        return worth;
    }

    public void addItem(final Item item) {
        boolean occurrence = false;
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).getName().equals(item.getName())) {
                this.items.get(i).addQuantity(); occurrence = true;
            }
        }
        if (!occurrence) this.items.add(item);
    }

    public void removeItem(final String name) {
        this.items.removeIf(item -> item.getName().equals(name));
    }

    public void clear() {
        this.items.clear();
    }

}