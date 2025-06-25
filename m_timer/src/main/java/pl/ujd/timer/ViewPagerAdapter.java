package pl.ujd.timer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public final class ViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragments = new ArrayList<>();
    private final ArrayList<String> titles = new ArrayList<>();

    public ViewPagerAdapter(@NonNull final FragmentManager fm) {
        super(fm);
    }

    @NonNull @Override public Fragment getItem(final int position) {
        return fragments.get(position);
    }

    public void addFragment(final Fragment fragment, final String title) {
        this.fragments.add(fragment);
        this.titles.add(title);
    }

    @Override public int getCount() {
        return fragments.size();
    }

    @Nullable @Override public CharSequence getPageTitle(final int position) {
        return titles.get(position);
    }

}