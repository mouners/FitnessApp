package com.mouner.fitnessapp.Calculators;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mouner.fitnessapp.Calculators.Calc.BMIActivity;
import com.mouner.fitnessapp.Calculators.Calc.BMRActivity;
import com.mouner.fitnessapp.Calculators.Calc.FatPercentageActivity;
import com.mouner.fitnessapp.MainActivity;

public class AdapterCalculator extends FragmentPagerAdapter {

    private int numOfTabs;

    public AdapterCalculator(FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new BMRActivity();
            case 1:
                return new BMIActivity();
            case 2:
                return new FatPercentageActivity();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
