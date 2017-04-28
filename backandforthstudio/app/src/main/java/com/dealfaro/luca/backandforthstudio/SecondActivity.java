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
import static com.dealfaro.luca.backandforthstudio.MainActivity.MYPREFS2;
import static com.dealfaro.luca.backandforthstudio.MainActivity.PREF_STRING_1;
import static com.dealfaro.luca.backandforthstudio.MainActivity.PREF_STRING_2;

public class SecondActivity extends AppCompatActivity {

    AppInfo appInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        appInfo = AppInfo.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // set string 1 as TextView
        SharedPreferences settings = getSharedPreferences(MYPREFS, 0);
        String myText = settings.getString(PREF_STRING_1, "");
        TextView tv = (TextView) findViewById(R.id.page2textView1);
        tv.setText(myText);

        // set string 2 as EditView
        settings = getSharedPreferences(MainActivity.MYPREFS2, 0);
        myText = settings.getString(MainActivity.PREF_STRING_2, "");
        EditText edv = (EditText) findViewById(R.id.page2editText);
        edv.setText(myText);

        // set string 3 as TextView
        settings = getSharedPreferences(MainActivity.MYPREFS3, 0);
        myText = settings.getString(MainActivity.PREF_STRING_3, "");
        tv = (TextView) findViewById(R.id.page2textView3);
        tv.setText(myText);
    }

    public void save2(View V) {
        // edit string 2
        EditText edv = (EditText) findViewById(R.id.page2editText);
        String text = edv.getText().toString();
        SharedPreferences settings = getSharedPreferences(MYPREFS2, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_STRING_2, text);
        editor.commit();
        appInfo.setColor2(text);
    }

    public void goToActivity1(View V) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goToActivity3(View V) {
        Intent intent = new Intent(this, ThirdActivity.class);
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
