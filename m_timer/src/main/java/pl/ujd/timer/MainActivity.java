package pl.ujd.timer;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public final class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private TabLayout layout;

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        this.setContentView(R.layout.activity_main);

        this.pager = this.findViewById(R.id.pager);
        this.layout = this.findViewById(R.id.layout);

        final ViewPagerAdapter adapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        adapter.addFragment(new StopwatchFragment(), "Stopwatches");
        adapter.addFragment(new TimerFragment(), "Timers");

        this.pager.setAdapter(adapter);
        this.layout.setupWithViewPager(this.pager);
    }

}