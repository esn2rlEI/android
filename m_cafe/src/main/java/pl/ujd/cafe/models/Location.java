package pl.ujd.cafe.models;

public final class Location {
    private String name;
    private String address;
    private double latitude;
    private double longitude;

    public Location(String name, String address, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {return name; }
    public String getAddress() { return address; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    @Override
    public String toString() {
        return name + " - " + address;
    }

}
