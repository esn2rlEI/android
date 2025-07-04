package pl.ujd.cafe.models;

public final class Location {
    private final String name;
    private final String address;
    private final String hours;
    private final int imageId;

    public Location(final String name, final String address, final String hours, final int imageId) {
        this.name = name;
        this.address = address;
        this.hours = hours;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getHours() {
        return hours;
    }

    public int getImageId() {
        return imageId;
    }

    @Override public String toString() {
        return name + " - " + address;
    }

}