package com.exsercises.chananya.tweeter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        final EditText title = (EditText)findViewById(R.id.title);
        final EditText body = (EditText)findViewById(R.id.body);
        final Button ok = (Button)findViewById(R.id.post);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://37.139.3.222/posts";
                JSONObject json = new JSONObject();
                try {
                    json.put("title", title.getText().toString());
                    json.put("body", body.getText().toString());

                    JsonAPI.getInstance().post(url, json, new JsonAPI.JsonCallback() {
                        @Override
                        public void onResponse(int statusCode, JSONObject json) {
                            if (statusCode == 200)
                                PostActivity.this.finish();
                            else
                                Toast.makeText(PostActivity.this, "Post not successful", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
