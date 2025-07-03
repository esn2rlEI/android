package pl.ujd.cafe;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public final class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable final Context context) {
        super(context, "cafe.db", null, 1);
    }

    @Override public void onCreate(final SQLiteDatabase database) {
        database.execSQL("CREATE TABLE drinks (name TEXT PRIMARY KEY, price REAL, description TEXT)");
        database.execSQL("CREATE TABLE snacks (name TEXT PRIMARY KEY, price REAL, description TEXT)");
        database.execSQL("CREATE TABLE locations (name TEXT PRIMARY KEY, address TEXT, hours TEXT, map TEXT)");

        database.insert("drinks", null, this.getProduct("Espresso", 4.0, "Strong and rich"));
        database.insert("drinks", null, this.getProduct("Latte", 4.2, "Creamy and smooth"));
        database.insert("drinks", null, this.getProduct("Tea", 2.0, "Refreshing and light"));

        database.insert("snacks", null, this.getProduct("Sandwich", 5.0, "Fresh and filling"));
        database.insert("snacks", null, this.getProduct("Cookie", 1.2, "Sweet treat"));
        database.insert("snacks", null, this.getProduct("Croissant", 3.0, "Buttery and crisp"));

        database.insert("locations", null, this.getLocation("Warsaw Central Cafe", "Main Street 1", "08:00–22:00", "central_cafe_map"));
        database.insert("locations", null, this.getLocation("Downtown Deli Cracow", "High Street 22", "07:30–23:00", "downtown_deli_map"));
    }

    private ContentValues getProduct(final String name, final double price, final String description) {
        final ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("price", price);
        values.put("description", description);
        return values;
    }

    private ContentValues getLocation(final String name, final String address, final String hours, final String map) {
        final ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", address);
        values.put("hours", hours);
        values.put("map", map);
        return values;
    }

    @Override public void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion) {}

}