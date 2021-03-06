package be.ugent.zeus.hydra.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import be.ugent.zeus.hydra.fragments.ActivitiesFragment;
import be.ugent.zeus.hydra.fragments.ComingSoonFragment;
import be.ugent.zeus.hydra.fragments.HomeFragment;
import be.ugent.zeus.hydra.fragments.InfoFragment;
import be.ugent.zeus.hydra.fragments.NewsFragment;
import be.ugent.zeus.hydra.fragments.RestoFragment;
import be.ugent.zeus.hydra.fragments.SchamperFragment;

/**
 * Created by silox on 17/10/15.
 */
public class SectionPagerAdapter extends FragmentPagerAdapter {

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new SchamperFragment();
            case 2:
                return new RestoFragment();
            case 3:
                return new ActivitiesFragment();
            case 4:
                return new InfoFragment();
            case 5:
                return new NewsFragment();
            default:
                return new ComingSoonFragment();
        }
    }

    @Override
    public int getCount() {
        return 6;
    }


//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return "First Tab";
//            case 1:
//            default:
//                return "Second Tab";
//        }
//    }

}
