package pl.ujd.timer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public final class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        final ViewPager pager = this.findViewById(R.id.pager);
        final TabLayout layout = this.findViewById(R.id.layout);

        final ViewPagerAdapter adapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        adapter.addFragment(new StopwatchFragment(), "Stopwatches");
        adapter.addFragment(new TimerFragment(), "Timers");

        pager.setAdapter(adapter);
        layout.setupWithViewPager(pager);
    }

}