package com.mouner.fitnessapp.Calculators.Calc;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.mouner.fitnessapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FatPercentageActivity extends Fragment {

    EditText HeightEditText, WeightEditText, AgeEditText;
    RadioGroup GenderRadioGroup;
    RadioButton MaleRadio, FemaleRadio;
    Button CalcBtn, ClearBtn;

    LinearLayout ResultLinear;
    TextView Bar;
    TextView ResultText, MoreInfoText;

    String fatPer;


    @SuppressLint("SimpleDateFormat")
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fat_percentage, container, false);

        HeightEditText = v.findViewById(R.id.heighttxtbar);
        WeightEditText = v.findViewById(R.id.weighttxtbar);
        AgeEditText = v.findViewById(R.id.agetxtbar);
        GenderRadioGroup = v.findViewById(R.id.radioGroup);
        MaleRadio = v.findViewById(R.id.maletxtbar);
        FemaleRadio = v.findViewById(R.id.femaletxtbar);

        CalcBtn = v.findViewById(R.id.Calbut);
        ClearBtn = v.findViewById(R.id.clearbut);

        ResultLinear = v.findViewById(R.id.LinearLayout);
        Bar = v.findViewById(R.id.bar);
        ResultText = v.findViewById(R.id.Fat_Result);
        MoreInfoText = v.findViewById(R.id.Txt);

        CalcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!HeightEditText.getText().toString().equals("") &&
                        !WeightEditText.getText().toString().equals("") &&
                        !AgeEditText.getText().toString().equals("") &&
                        (MaleRadio.isChecked() || FemaleRadio.isChecked()))    {

                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    float HeightValue = Float.parseFloat(HeightEditText.getText().toString());
                    float WeightValue = Float.parseFloat(WeightEditText.getText().toString());
                    float AgeValue = Float.parseFloat(AgeEditText.getText().toString());

                    fatPerCalc(HeightValue, WeightValue, AgeValue);

                    ResultText.setText(" "+fatPer+ "%");
                    if(ResultLinear.isShown()){

                    }
                    else {
                        animate(ResultLinear);
                    }

                }
            }
        });
        ClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
        Bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        MoreInfoText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View view = inflater.inflate(R.layout.fat_table, null);

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setView(view);
                alertDialog.setCancelable(false);

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
                        "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        } );
                alertDialog.show();

            }
        });

        return v;
    }

    @SuppressLint("DefaultLocale")
    private String fatPerCalc(float height, float weight, float age) {

        float BMI = weight / ((height / 100) * (height / 100));
        float result = 0;

        if(MaleRadio.isChecked()){
            result = (float) (5.4 - 10.8 - (age * 0.23) + (BMI * 1.20));
        }else if(FemaleRadio.isChecked()){
            result = (float) (5.4 - (age * 0.23) + (BMI * 1.20));
        }
        fatPer = String.format("%.0f", result);

        return fatPer;
    }

    public void reset(){
        if(!ResultLinear.isShown()){

        }else {
            animate_fideOut(ResultLinear);
        }

        AgeEditText.setText( "" );
        WeightEditText.setText( "" );
        HeightEditText.setText( "" );
        ResultText.setText( "" );
        fatPer = "";
        GenderRadioGroup.clearCheck();

    }

    public void animate(View view){

        Animation animation   =    AnimationUtils.loadAnimation(getActivity(), R.anim.transition_fade_in );
        animation.setDuration(1000);
        ResultLinear.setAnimation(animation);
        ResultLinear.animate();
        ResultLinear.setVisibility( VISIBLE);
        animation.start();
    }

    public void animate_fideOut(View view){

        Animation animation   =    AnimationUtils.loadAnimation(getActivity(), R.anim.transition_fade_out );
        animation.setDuration(1000);
        ResultLinear.setAnimation(animation);
        ResultLinear.animate();
        ResultLinear.setVisibility( INVISIBLE);
        animation.start();
    }
}