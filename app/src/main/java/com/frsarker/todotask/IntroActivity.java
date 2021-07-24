package com.frsarker.todotask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    ViewPager screenPager;
    IntroViewPageAdapter introViewPageAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0;
    Button btnGetStarted;
    Animation btnAimate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //make activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Check when launch this introduction, if this had opened or not
        if (restorePrefData()){
            Intent mainAct = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainAct);
            finish();
        }


        setContentView(R.layout.activity_intro);

        // Hide the action bar
//        getSupportActionBar().hide();

        //Tab indicator, Init view
        btnNext = (Button)findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_getStarted);
        tabIndicator = findViewById(R.id.tab_indicator);

//        imageView = (ImageView) findViewById(R.id.logo);
//        imageView.setImageResource(R.drawable.thunder);


        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Welcome!","Get started", R.drawable.undraw_completing_6bhr, R.drawable.thunder));
        mList.add(new ScreenItem("Do it!","Let's go started To make your consistency", R.drawable.undraw_happy_birthday_s72n, R.drawable.thunder));
        mList.add(new ScreenItem("Let's done it! ","Be the better version of your", R.drawable.undraw_finish_line_katerina_limpitsouni_xy20, R.drawable.thunder));

        //setup viewpaper
        screenPager = findViewById(R.id.viewpager);
        introViewPageAdapter = new IntroViewPageAdapter(this,mList);
        screenPager.setAdapter(introViewPageAdapter);

        //setup tab indicator w viewpape
        tabIndicator.setupWithViewPager(screenPager);

        //Next button click
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                position = screenPager.getCurrentItem();
                if(position < mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if(position == mList.size()-1){

                    loadLastScreen();
                }

            }
        });

        //tabLayout change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size()-1){
                //Load started button => hide indicator, next button
                    loadLastScreen();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //Get started button listener

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open main activity
                Intent mainAct = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainAct);

                // check first time use app or second time
                savePrefData();
                finish();
            }
        });


    }
    private boolean restorePrefData(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        Boolean isIntroOpened = pref.getBoolean("isOpendIntro",false);
        return isIntroOpened;
    }

    private void savePrefData(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isOpendIntro",true);
        editor.commit();
    }

    private void loadLastScreen(){

        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        //btn animation
        btnGetStarted.setAnimation(btnAimate);

    }
}