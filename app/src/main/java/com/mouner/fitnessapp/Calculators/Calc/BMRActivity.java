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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mouner.fitnessapp.R;


public class BMRActivity extends Fragment implements AdapterView.OnItemSelectedListener{

    RadioGroup radioGroup;
    EditText age, weight, height;
    RadioButton gendermale, genderfemale;
    TextView txtCal, BMRResult, TEEResult, Bar;
    Button Calbut, clearbut;

    float bmr , bee, tee;

    float weightValue = 0;
    float heightValue = 0;
    float ageValue = 0;


    String S1, S2, S3;

    LinearLayout Linear;

    String teeActi;
    private String calc;
    private String calc1;

    @SuppressLint("SimpleDateFormat")
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate( R.layout.activity_bmr, container, false );

        Spinner dropdown = v.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.planets_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setOnItemSelectedListener( this);
        dropdown.setAdapter(adapter);

        radioGroup = v.findViewById( R.id.radioGroup );
        Linear = v.findViewById( R.id.LinearLayout);
        age = v.findViewById( R.id.agetxtbar );
        weight = v.findViewById( R.id.weighttxtbar );
        height = v.findViewById( R.id.heighttxtbar );
        gendermale = v.findViewById( R.id.maletxtbar );
        genderfemale = v.findViewById( R.id.femaletxtbar );
        BMRResult = v.findViewById( R.id.BMR_Result );
        TEEResult = v.findViewById( R.id.TEE_Result );
        Calbut = v.findViewById( R.id.Calbut );
        clearbut = v.findViewById( R.id.clearbut );
        txtCal = v.findViewById( R.id.Txt );
        Bar = v.findViewById(R.id.bar);

        Calbut.setOnClickListener( new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "DefaultLocale", "SetTextI18n"})
            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                S1 = weight.getText().toString();
                S2 = height.getText().toString();
                S3 = age.getText().toString();

                try {
                    weightValue = Float.parseFloat( S1 );
                    heightValue = Float.parseFloat( S2 );
                    ageValue = Float.parseFloat( S3 );
                    BMRCalc();
                } catch (Exception e) {
                    BMRResult.setText( "" );
                    TEEResult.setText( "" );
                    Toast(getContext());

                }

                if(bmr > 0){
                    calc = String.format( "%.2f", bmr );
                    BMRResult.setText(" " + calc + " Cal" );
                    if(Linear.isShown()){

                    }
                    else {
                        animate(Linear);
                    }
                }

                if(tee > 0){
                    TEEResult.setTextSize(22);
                    calc1 = String.format( "%.2f", tee );
                    TEEResult.setText(" " + calc1 + " Cal" );
                }else if(bmr > 0){
                    TEEResult.setText("if you want to calculate your TEE,\n you have to choose your activity up!" );
                    TEEResult.setTextSize(16);
                }

            }
        } );

        clearbut.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reset();

                dropdown.setAdapter(adapter);
            }
        } );

        Bar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                dropdown.setAdapter(adapter);
            }
        } );

        txtCal.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Bmr & Tee?");
                alertDialog.setMessage( "Bmr?\nBasal metabolic rate (BMR) is the number of calories your body needs to accomplish its most basic (basal) life-sustaining functions.\n\nTee?\nTEE = \"Total Energy expenditure\" is the amount of calories burned by the human body in one day adjusted to the amount of activity (sedentary, moderate or strenuous)." );
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

    private float BMRCalc() {
        if (gendermale.isChecked() && weightValue > 0 && heightValue > 0 && ageValue > 0) {
            bmr = (float) (66 + (weightValue * 13.7) + (heightValue * 5) - (ageValue * 6.8));
        } else if(genderfemale.isChecked() && weightValue > 0 && heightValue > 0 && ageValue > 0)
        {
            bmr = (float) ((weightValue * 9.6) + (heightValue * 1.8) - (ageValue * 4.7) + 655);
        }else {
            Toast.makeText(getActivity(),"Make Sure you are filling a right numbers", Toast.LENGTH_LONG).show();
        }
        return bmr;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        try{
            S1 = weight.getText().toString();
            S2 = height.getText().toString();
            S3 = age.getText().toString();

            weightValue = Float.parseFloat( S1 );
            heightValue = Float.parseFloat( S2 );
            ageValue = Float.parseFloat( S3 );


            if (gendermale.isChecked() && weightValue > 0 && heightValue > 0 && ageValue > 0) {
                bee = (float) (66 + (weightValue * 13.7) + (heightValue * 5) - (ageValue * 6.8));
            } else if(genderfemale.isChecked() && weightValue > 0 && heightValue > 0 && ageValue > 0)
            {
                bee = (float) ((weightValue * 9.6) + (heightValue * 1.8) - (ageValue * 4.7) + 655);
            }
        }catch (Exception e){

        }

        if(position == 0 ){
            TEEResult.setText("if you want to calculate your TEE,\n you have to choose your activity up! " );
            TEEResult.setTextSize(16);
        }
        if(position == 1){
            tee = (float) (bee* 1.2);
            teeActi = "Little";
        }if(position == 2){
            tee = (float) (bee * 1.375);
            teeActi = "Light";
        }if(position == 3){
            tee = (float) (bee * 1.55);
            teeActi = "Moderate";
        }if(position == 4){
            tee = (float) (bee * 1.725);
            teeActi = "Heavy";
        }if(position == 5){
            tee = (float) (bee * 1.9);
            teeActi = "Very";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void reset(){
        if(!Linear.isShown()){

        }else {
            animate_fideOut(Linear);
        }

        age.setText( "" );
        weight.setText( "" );
        height.setText( "" );
        BMRResult.setText( "" );
        TEEResult.setText( "" );
        weightValue = 0;
        ageValue = 0;
        heightValue = 0;
        tee = 0;
        bmr = 0;

        radioGroup.clearCheck();

    }

    public void animate(View view){

        Animation animation   =    AnimationUtils.loadAnimation(getActivity(), R.anim.transition_fade_in );
        animation.setDuration(1000);
        Linear.setAnimation(animation);
        Linear.animate();
        Linear.setVisibility( VISIBLE);
        animation.start();
    }

    public void animate_fideOut(View view){

        Animation animation   =    AnimationUtils.loadAnimation(getActivity(), R.anim.transition_fade_out );
        animation.setDuration(1000);
        Linear.setAnimation(animation);
        Linear.animate();
        Linear.setVisibility( INVISIBLE);
        animation.start();
    }

    private void Toast(Context context){

        Toast.makeText(context,"Make Sure you are filling \n Height, Weight and Age!", Toast.LENGTH_LONG).show();
    }
}