/*
    Ryan Shee (rshee)
    Homework 3
*/
package com.dealfaro.luca.androidhomephone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "ryanlog";

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getPressed(View V) {
        getMessages("abracadabra");
    }

    public void postPressed(View V) {
        sendMsg("abracadabra");
    }

    private void sendMsg(final String msg) {
        StringRequest sr = new StringRequest(Request.Method.POST,
                "https://luca-ucsc-teaching-backend.appspot.com/hw3/request_via_post",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.wtf(LOG_TAG, "Got:" + response);
                String text = "";

                // convert String to JSONObject, then parse JSONObject for String
                try {
                    JSONObject json = new JSONObject(response);
                    text = json.getString("result");
                } catch (JSONException e) {
                    Log.wtf(LOG_TAG, "broken");
                }

                // set text
                TextView tv = (TextView)findViewById(R.id.my_text);
                tv.setText(text);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("token", msg);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);

    }

    private void getMessages(final String recipient) {

        // Instantiate the RequestQueue.
        String url = "https://luca-ucsc-teaching-backend.appspot.com/hw3/request_via_get";

        String my_url = url + "?token=" + URLEncoder.encode(recipient);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, my_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.wtf(LOG_TAG, "Received: " + response.toString());
                        String text = "";

                        // parse JSONObject for String
                        try {
                            text = response.getString("result");
                        } catch (JSONException e) {
                            Log.wtf(LOG_TAG, "broken");
                        }

                        // set text
                        TextView tv = (TextView)findViewById(R.id.my_text);
                        tv.setText(text);
                        // Ok, let's disassemble a bit the json object.
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.wtf(LOG_TAG, error.toString());
                    }
                });

        // In some cases, we don't want to cache the request.
        // jsObjRequest.setShouldCache(false);

        queue.add(jsObjRequest);
    }
}
