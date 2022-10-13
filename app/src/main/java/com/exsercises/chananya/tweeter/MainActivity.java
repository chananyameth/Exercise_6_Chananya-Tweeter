package com.exsercises.chananya.tweeter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ListAdapter listAdapter;
    private ArrayList<Post> posts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.ListView);
        listAdapter = new ListAdapter(this, posts);
        listView.setAdapter(listAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newPost:
                Intent pos = new Intent(MainActivity.this, PostActivity.class);
                startActivity(pos);
                break;
            case R.id.logout:
                JsonAPI.getInstance().removeHeader("Token");
                Intent log = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(log);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        String url = "http://37.139.3.222/posts";
        JsonAPI.getInstance().get(url, new JsonAPI.JsonCallback() {
            @Override
            public void onResponse(int statusCode, JSONObject json) {
                if (statusCode == 200) {
                    try {
                        posts.clear();
                        JSONArray Jposts = json.getJSONArray("posts");
                        for (int i=0; i< Jposts.length() ; i++) {
                            JSONObject jo = Jposts.getJSONObject(i);
                            Post p = new Post(jo.getString("title"), jo.getString("body"));
                            posts.add(p);
                        }
                        listAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Login not successful", Toast.LENGTH_LONG).show();
                    }
                } else if (statusCode == 401) {
                    Intent log = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(log);
                }
            }
        });
    }
}
