/*
    Ryan Shee (rshee)
    Homework 2
*/
package com.dealfaro.luca.backandforthstudio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.dealfaro.luca.backandforthstudio.MainActivity.MYPREFS;
import static com.dealfaro.luca.backandforthstudio.MainActivity.MYPREFS3;
import static com.dealfaro.luca.backandforthstudio.MainActivity.PREF_STRING_1;
import static com.dealfaro.luca.backandforthstudio.MainActivity.PREF_STRING_3;

public class ThirdActivity extends AppCompatActivity {

    AppInfo appInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("In activity 3");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        appInfo = AppInfo.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // set string 1 as TextView
        SharedPreferences settings = getSharedPreferences(MYPREFS, 0);
        String myText = settings.getString(PREF_STRING_1, "");
        TextView tv = (TextView) findViewById(R.id.page3textView1);
        tv.setText(myText);

        // set string 2 as TextView
        settings = getSharedPreferences(MainActivity.MYPREFS2, 0);
        myText = settings.getString(MainActivity.PREF_STRING_2, "");
        tv = (TextView) findViewById(R.id.page3textView2);
        tv.setText(myText);

        // set string 3 as EditText
        settings = getSharedPreferences(MainActivity.MYPREFS3, 0);
        myText = settings.getString(MainActivity.PREF_STRING_3, "");
        EditText edv = (EditText) findViewById(R.id.page3editText);
        edv.setText(myText);
    }

    public void save3(View V) {
        // edit string 3
        EditText edv = (EditText) findViewById(R.id.page3editText);
        String text = edv.getText().toString();
        SharedPreferences settings = getSharedPreferences(MYPREFS3, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_STRING_3, text);
        editor.commit();
        appInfo.setColor3(text);
    }

    public void goToActivity1(View V) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goToActivity2(View V) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
