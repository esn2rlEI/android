package pl.ujd.cafe.models;

public class Item {

    private final String name;
    private final float price;
    private final String description;

    private final int imageId;

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

    @Override public String toString() {
        return name;
    }

}