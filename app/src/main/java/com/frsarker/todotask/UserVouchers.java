package com.frsarker.todotask;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserVouchers extends AppCompatActivity {
    String[] descriptionData = {"10", "20", "30", "40"};
    LinearLayout listVoucher;
    List<String> stringList;
    DBHelper mydb;
    int credit;
    Button btnRedeem;
    TextView textView;
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
        StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.voucherprogress);
        stateProgressBar.setStateDescriptionData(descriptionData);

        listVoucher=  (LinearLayout) findViewById(R.id.list_voucher);
        btnRedeem = (Button) findViewById(R.id.btnRedeem);
        textView = (TextView) findViewById(R.id.credit2);
        mydb = new DBHelper(this);
        credit = mydb.getCredit();

        fetchVoucher();
    }
    public void fetchVoucher(){
        stringList = new ArrayList<>();

        textView.setText(credit+"");
        if(credit<5){
            btnRedeem.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            btnRedeem.setClickable(false);
        }else{
            btnRedeem.getBackground().setColorFilter(null);
            btnRedeem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String voucher = randomStringGenerator.generateString();
                    mydb.insertVoucher(voucher);
                    Cursor cursor = mydb.getListVoucher();
                    if(cursor!=null)
                        loadDataList(cursor);
                    credit = mydb.getCredit();
                    textView.setText(credit+"");
                }
            });
            mydb.updateCredit(-5);
        }
        Cursor cursor = mydb.getListVoucher();
        // Add Buttons
        if(cursor!=null)
         loadDataList(cursor);

    }
    public void loadDataList(Cursor cursor) {
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                String vouch = cursor.getString(0).toString();
                stringList.add(vouch);
                cursor.moveToNext();
            }
        }
        for (String item:stringList
             ) {
            Button button = new Button(this);
            button.setText(item);
            listVoucher.addView(button);
        }
    }
}