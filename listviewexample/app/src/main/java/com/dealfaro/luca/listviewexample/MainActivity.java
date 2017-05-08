package com.dealfaro.luca.listviewexample;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RequestQueue queue;

    private static final String LOG_TAG = "lv-ex";

    private class ListElement {
        ListElement(String tl, String ul) {
            textLabel = tl;
            urlLabel = ul;
        }

        public String textLabel;
        public String urlLabel;
    }

    private ArrayList<ListElement> aList;

    private class MyAdapter extends ArrayAdapter<ListElement> {
        int resource;
        Context context;

        public MyAdapter(Context _context, int _resource, List<ListElement> items) {
            super(_context, _resource, items);
            resource = _resource;
            context = _context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout newView;
            ListElement w = getItem(position);

            // Inflate a new view if necessary.
            if (convertView == null) {
                newView = new LinearLayout(getContext());
                LayoutInflater vi = (LayoutInflater)
                        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi.inflate(resource,  newView, true);
            } else {
                newView = (LinearLayout) convertView;
            }

            // Fills in the view.
            TextView tv = (TextView) newView.findViewById(R.id.itemText);
            tv.setText(w.textLabel);

            // sets url to tag of the view
            newView.setTag(w.urlLabel);
            newView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = v.getTag().toString(); // gets url from view's tag
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, s, duration);
                    toast.show();
                    webView(s);
                }
            });

            return newView;
        }
    }

    private MyAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aList = new ArrayList<ListElement>();
        aa = new MyAdapter(this, R.layout.list_element, aList);
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(aa);
        aa.notifyDataSetChanged();
        queue = Volley.newRequestQueue(this);
        clickRefresh(null); // provides a list on app startup
    }

    public void clickRefresh (View v) {
        Log.i(LOG_TAG, "Requested a refresh of the list");
        String url = "https://luca-ucsc-teaching-backend.appspot.com/hw4/get_news_sites";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jArray = response.getJSONArray("news_sites");
                            aList.clear();
                            // goes through JSONArray for news sites
                            Log.wtf(LOG_TAG, "length: " + jArray.length());
                            if (jArray.length() == 0) {
                                Toast toast = Toast.makeText(MainActivity.this, "No news sites!", Toast.LENGTH_SHORT);
                                toast.show();
                            } else {
                                for (int i = 0; i < jArray.length(); i++) {
                                    String url = jArray.getJSONObject(i).getString("url");
                                    String subtitle = jArray.getJSONObject(i).getString("subtitle");
                                    String title = jArray.getJSONObject(i).getString("title");
                                    Log.wtf(LOG_TAG, "url: " + url);
                                    Log.wtf(LOG_TAG, "title: " + title);
                                    Log.wtf(LOG_TAG, "subtitle: " + subtitle);
                                    if (!url.equals("null") && !title.equals("null")) {
                                        if (subtitle.equals("null")) {
                                            aList.add(new ListElement(
                                                    title + "\n" + "URL: " + url, url
                                            ));
                                        } else {
                                            aList.add(new ListElement(
                                                    title + "\n" + subtitle, url
                                            ));
                                        }

                                    }
                                }
                                aa.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            Log.wtf(LOG_TAG, "broken");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.wtf(LOG_TAG, error.toString());
                    }
                });

        queue.add(jsObjRequest);
    }

    public void webView(String s) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", s); // the next activity uses this to know what url to go to
        startActivity(intent);
    }
}
