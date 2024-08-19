package com.mouner.fitnessapp.Calculators;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.mouner.fitnessapp.R;

public class CalculatorsActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate( R.layout.activity_calculators, container, false );

        TabLayout tabLayout = v.findViewById(R.id.tabBar);
        ViewPager viewPager = v.findViewById(R.id.viewPager);

        AdapterCalculator adapterCalculator = new AdapterCalculator(getParentFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapterCalculator);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return v;
    }

}