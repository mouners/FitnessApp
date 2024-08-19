package com.mouner.fitnessapp.Calculators.Calc;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import android.widget.TextView;
import android.widget.Toast;
import com.mouner.fitnessapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BMIActivity extends Fragment {

    View Body;
    String calc;
    EditText weight, height;
    TextView Result, TxtDic, BmiBar, TEXTT;
    Button CalcBtn, ClearBtn;

    float weightValue1 = 0;
    float heightValue1 = 0;
    float BMI = 0;

    LinearLayout Linear;

    String bmiAbo;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate( R.layout.activity_bmi, container, false );

        weight = v.findViewById( R.id.weightbar);
        height = v.findViewById( R.id.heightbar);

        CalcBtn = v.findViewById( R.id.CalcBtn);
        ClearBtn = v.findViewById( R.id.ClearBtn);

        Result = v.findViewById( R.id.result);
        TxtDic = v.findViewById( R.id.txtdic);
        TEXTT = v.findViewById( R.id.TEXTT);

        Linear = v.findViewById( R.id.Linear );
        BmiBar = v.findViewById( R.id.bmi_bar);
        Body = v.findViewById( R.id.body);

        BmiBar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        CalcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                String A1 = weight.getText().toString();
                String A2 = height.getText().toString();

                try {
                    weightValue1 = Float.parseFloat(A1);
                    heightValue1 = Float.parseFloat(A2);
                    CalcBMI();
                    CheckSecResu();
                    if(weightValue1 <= 0 || heightValue1 <= 0) {

                    }else if(BMI > 40 || BMI <= 0){

                    }else {
                        if(Linear.isShown()){

                        }
                        else {
                            animate(Linear);
                        }
                    }

                } catch (Exception e) {
                    Result.setText( "" );
                    Toast(getContext());
                    Linear.setVisibility( INVISIBLE);
                }
            }
        });

        ClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();

            }
        });

        v.findViewById( R.id.bmitxt).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Bmi?");
                alertDialog.setMessage( "Bmi?\nThe body mass index (BMI)\" is a measure that uses your height and weight to work out if your weight is healthy." );
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

    private void CalcBMI(){
        BMI = weightValue1 / ((heightValue1 / 100) * (heightValue1 / 100));

        if(weightValue1 <= 0 || heightValue1 <= 0) {
            ToastBmi_0( getContext() );
        } else if(BMI <= 0) {
            ToastBmi_0(getContext());
        }else if(BMI > 40) {
            ToastBmi_50(getContext());
        }else {
            calc = String.format( "%.2f", BMI );
            Result.setText( calc );
        }
    }

    private void CheckSecResu(){

        if(BMI < 18.5){
            if(BMI < 7){
                Body.setTranslationX( -50 );
            }
            else if(BMI >= 7 && BMI < 13){
                Body.setTranslationX( 10 );
            }
            else if(BMI >= 13 && BMI < 18){
                Body.setTranslationX( 60 );
            }

            BmiBar.setBackgroundResource( R.drawable.layout_bmiblue_bar );
            Linear.setBackgroundResource( R.drawable.layout_bmiblue );

            TEXTT.setText("Under weight:");
            TxtDic.setText("A BMI of less than 18.5 indicates that you are underweight, so you may need to put on some weight. You are recommended to ask your doctor or a dietitian for advice.");
            bmiAbo = "Under Weight";
        }
        else if (BMI >= 18 && BMI < 24.9){
            if(BMI < 20){
                Body.setTranslationX( 160 );
            }
            else if(BMI >= 20 && BMI < 22){
                Body.setTranslationX( 230 );
            }
            else if(BMI >= 22 && BMI < 25){
                Body.setTranslationX( 300 );
            }

            BmiBar.setBackgroundResource( R.drawable.layout_bmigreen_bar );
            Linear.setBackgroundResource( R.drawable.layout_bmigreen );

            TEXTT.setText("Healthy weight:");
            TxtDic.setText("A BMI of 18.5–24.9 indicates that you are at a healthy weight for your height. By maintaining a healthy weight, you lower your risk of developing serious health problems.");
            bmiAbo = "Healthy weight";

        }
        else if (BMI > 25.0 && BMI < 30){
            if(BMI < 26){
                Body.setTranslationX( 370 );
            }
            else if(BMI >= 26 && BMI < 29){
                Body.setTranslationX( 450 );
            }
            else if(BMI >= 29 && BMI < 30){
                Body.setTranslationX( 530 );
            }

            BmiBar.setBackgroundResource( R.drawable.layout_bmiorange_bar );
            Linear.setBackgroundResource( R.drawable.layout_bmiorange );

            TEXTT.setText("Over weight:");
            TxtDic.setText("A BMI of 25–29.9 indicates that you are slightly overweight. You may be advised to lose some weight for health reasons. You are recommended to talk to your doctor or a dietitian for advice.");
            bmiAbo = "Over Weight";
        }
        else if (BMI >= 30 && BMI < 40){
            if(BMI > 30 && BMI < 33){
                Body.setTranslationX( 590 );
            }
            else if(BMI >= 33 && BMI < 37){
                Body.setTranslationX( 670 );
            }
            else if(BMI >= 37 ){
                Body.setTranslationX( 750 );
            }

            BmiBar.setBackgroundResource( R.drawable.layout_bmired_bar );
            Linear.setBackgroundResource( R.drawable.layout_bmired );

            TEXTT.setText("Obese:");
            TxtDic.setText("A BMI of over 30 indicates that you are heavily overweight. Your health may be at risk if you do not lose weight. You are recommended to talk to your doctor or a dietitian for advice.");
            bmiAbo = "Obese";
        }
    }

    public void reset(){
        if(!Linear.isShown()){

        }else {
            animate_fideOut(Linear);
        }

        weight.setText("");
        height.setText("");
        Result.setText("");
    }

    public void animate(View view){

        Animation animation   =    AnimationUtils.loadAnimation(getActivity(), R.anim.transition_fade_in );
        animation.setDuration(500);
        Linear.setAnimation(animation);
        Linear.animate();
        Linear.setVisibility( VISIBLE);
        animation.start();
    }

    public void animate_fideOut(View view){

        Animation animation   =    AnimationUtils.loadAnimation(getActivity(), R.anim.transition_fade_out );
        animation.setDuration(500);
        Linear.setAnimation(animation);
        Linear.animate();
        Linear.setVisibility( INVISIBLE);
        animation.start();
    }

    private void Toast(Context context){

        Toast.makeText(context,"Make Sure you are filling \n Height and Weight!", Toast.LENGTH_LONG).show();
    }

    private void ToastBmi_50(Context context){

        Toast.makeText(context,"Your BMI is higher than 40. It can't be calculated.", Toast.LENGTH_LONG).show();
    }

    private void ToastBmi_0(Context context){

        Toast.makeText(getActivity(),"Make Sure you are filling a right numbers", Toast.LENGTH_LONG).show();
    }
}