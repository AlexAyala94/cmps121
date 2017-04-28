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

public class MainActivity extends AppCompatActivity {

    static final public String MYPREFS = "myprefs";
    static final public String MYPREFS2 = "myprefs2";
    static final public String MYPREFS3 = "myprefs3";
    static final public String PREF_STRING_1 = "string_1";
    static final public String PREF_STRING_2 = "string_2";
    static final public String PREF_STRING_3 = "string_3";

    AppInfo appInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appInfo = AppInfo.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // set string 1 as EditText
        SharedPreferences settings = getSharedPreferences(MainActivity.MYPREFS, 0);
        String myText = settings.getString(MainActivity.PREF_STRING_1, "");
        EditText edv = (EditText) findViewById(R.id.page1editText);
        edv.setText(myText);

        // set string 2 as TextView
        settings = getSharedPreferences(MainActivity.MYPREFS2, 0);
        myText = settings.getString(MainActivity.PREF_STRING_2, "");
        TextView tv = (TextView) findViewById(R.id.page1textView2);
        tv.setText(myText);

        // set string 3 as TextView
        settings = getSharedPreferences(MainActivity.MYPREFS3, 0);
        myText = settings.getString(MainActivity.PREF_STRING_3, "");
        tv = (TextView) findViewById(R.id.page1textView3);
        tv.setText(myText);
    }

    public void save1(View V) {
        // edit string 1
        EditText edv = (EditText) findViewById(R.id.page1editText);
        String text = edv.getText().toString();
        SharedPreferences settings = getSharedPreferences(MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_STRING_1, text);
        editor.commit();
        appInfo.setColor(text);
    }

    public void goToActivity2(View V) {
        Intent intent = new Intent(this, SecondActivity.class);
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
        // do nothing
    }
}
