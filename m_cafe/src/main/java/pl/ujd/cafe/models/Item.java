package pl.ujd.cafe.models;

public class Item {

    private final String name;
    private final float price;
    private final String description;

    private final int imageId;

    private int quantity = 1;

    public Item(final String name, final float price, final String description, final int imageId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getImageId() {
        return imageId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity() {
        this.quantity++;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        return name.equals(((Item) o).name);
    }

    @Override public int hashCode() {
        return name.hashCode();
    }

    @Override public String toString() {
        return name;
    }

}