package taarak.gloify.headlines.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import taarak.gloify.headlines.Adapters.ViewPagerAdapter_Headlinefrag;
import taarak.gloify.headlines.R;

public class HeadlineFragment extends Fragment {

    SharedPreferences sp;
    SharedPreferences.Editor edt;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter_Headlinefrag viewPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_headline, container, false);

        viewPager =root.findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter_Headlinefrag(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout =root.findViewById(R.id.tablay_headlinefrag);
        tabLayout.setupWithViewPager(viewPager);

        sp=getActivity().getSharedPreferences("Key",getActivity().MODE_PRIVATE);
        edt=sp.edit();
        edt.putInt("CURRENT_FRAG",2);
        edt.apply();
        edt.commit();

        return root;
    }
}
