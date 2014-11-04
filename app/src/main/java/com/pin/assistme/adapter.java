package com.pin.assistme;

/**
 * Created by tomer on 27/05/14.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
public class adapter extends FragmentPagerAdapter {

    public adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new page1();
            case 1:
                // Games fragment activity
                return new page2();
            case 2:
                // Movies fragment activity
                return new page3();
            case 3:
                return new page4();

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }

}