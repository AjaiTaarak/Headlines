package taarak.gloify.headlines.Adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import taarak.gloify.headlines.Headlineui.BusinessFragment;
import taarak.gloify.headlines.Headlineui.EntertainmentFragment;
import taarak.gloify.headlines.Headlineui.HealthFragment;
import taarak.gloify.headlines.Headlineui.LatestFragment;
import taarak.gloify.headlines.Headlineui.SportsFragment;
import taarak.gloify.headlines.Headlineui.TechnologyFragment;

public class ViewPagerAdapter_Headlinefrag extends FragmentPagerAdapter {

    public ViewPagerAdapter_Headlinefrag(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new LatestFragment();
        }
        else if (position == 1)
        {
            fragment = new BusinessFragment();
        }
        else if (position == 2)
        {
            fragment = new TechnologyFragment();
        }
        else if (position == 3)
        {
            fragment = new EntertainmentFragment();
        }
        else if (position == 4)
        {
            fragment = new SportsFragment();
        }
        else if (position == 5)
        {
            fragment = new HealthFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title="Latest";
        }
        else if (position == 1)
        {
            title="Business";
        }
        else if (position == 2)
        {
            title="Technology";
        }
        else if (position == 3)
        {
            title="Entertainment";
        }
        else if (position == 4)
        {
            title="Sports";
        }
        else if (position == 5)
        {
            title="Health";
        }
        return title;
    }
}
