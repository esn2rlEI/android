package pl.ujd.cafe;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public final class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double lat, lng;
    private String locationName;

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        this.lat = getIntent().getDoubleExtra("lat", 0);
        this.lng = getIntent().getDoubleExtra("lng", 0);
        this.locationName = getIntent().getStringExtra("location_name");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override public void onMapReady(final GoogleMap googleMap) {
        this.mMap = googleMap;
        final LatLng cafe = new LatLng(this.lat, this.lng);
        this.mMap.addMarker(new MarkerOptions().position(cafe).title(this.locationName));
        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cafe, 15));
    }

}