package com.frsarker.todotask;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserVouchers extends AppCompatActivity {
    String[] descriptionData = {"0", "5", "10", "15", "20"};
    LinearLayout listVoucher;
    List<String> stringList;
    DBHelper mydb;
    int credit;
    Button btnRedeem;
    TextView textView;
    StateProgressBar stateProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_vouchers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });
        stateProgressBar = (StateProgressBar) findViewById(R.id.voucherprogress);
        stateProgressBar.setStateDescriptionData(descriptionData);

        listVoucher=  (LinearLayout) findViewById(R.id.list_voucher);
        btnRedeem = (Button) findViewById(R.id.btnRedeem);
        textView = (TextView) findViewById(R.id.credit2);
        mydb = new DBHelper(this);
        credit = mydb.getCredit();
        if(credit<5){
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
        }
        else if(credit>=5){
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
        }else if(credit>=10){
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
        }else if(credit>=15){
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
        }else if(credit>=20){
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
        }


        fetchVoucher();
    }
    public void fetchVoucher(){

        stringList = new ArrayList<>();
        textView.setText(credit+"");
        if(credit<5){
            btnRedeem.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            btnRedeem.setBackgroundColor(Color.GRAY);
            btnRedeem.setClickable(false);
        }else{
            btnRedeem.getBackground().setColorFilter(null);
            btnRedeem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String voucher = randomStringGenerator.generateString();
                    voucher = voucher.substring(6,voucher.length());
                    mydb.insertVoucher(voucher);
                    Cursor cursor = mydb.getListVoucher();
                    if(cursor!=null)
                        loadDataList(cursor);
                    credit = mydb.getCredit();
                    textView.setText(credit+"");
                    if(credit<5){
                        btnRedeem.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                        btnRedeem.setBackgroundColor(Color.GRAY);
                        btnRedeem.setClickable(false);
                    }
                    if(credit<5){
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                    }
                    else if(credit>=5){
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                    }else if(credit>=10){
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                    }else if(credit>=15){
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                    }else if(credit>=20){
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
                    }
                    Toast.makeText(getApplicationContext(), "added voucher "+voucher, Toast.LENGTH_SHORT).show();
                }
            });
            mydb.updateCredit(-5);
        }
        Cursor cursor = mydb.getListVoucher();
        Log.d("TAGVoucher", "fetchVoucher: "+cursor);
        // Add Buttons
        if(cursor!=null)
         loadDataList(cursor);

    }
    public void loadDataList(Cursor cursor) {
        stringList= new ArrayList<>();
        listVoucher.removeAllViews();
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                String vouch = cursor.getString(0).toString()+""+cursor.getString(1).toString();
                stringList.add(vouch);
                Log.d("vocuher", "loadDataList: "+vouch);
                cursor.moveToNext();
            }
        }
        for (String item:stringList
             ) {
            Button button = new Button(this);
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setTextColor(Color.WHITE);
            button.setText(item);
            listVoucher.addView(button);
        }
    }
}