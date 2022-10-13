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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = (EditText)findViewById(R.id.Email);
        final EditText password = (EditText)findViewById(R.id.Password);
        final Button login = (Button)findViewById(R.id.Login);
        final Button register = (Button)findViewById(R.id.Register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(reg);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://37.139.3.222/login";
                JSONObject json = new JSONObject();
                try {
                    json.put("email", email.getText().toString());
                    json.put("password", password.getText().toString());

                    JsonAPI.getInstance().post(url, json, new JsonAPI.JsonCallback() {
                        @Override
                        public void onResponse(int statusCode, JSONObject json) {
                            if (statusCode == 200) {
                                String token = null;
                                try {
                                    token = json.getString("token");
                                    JsonAPI.getInstance().addHeader("Token", token);
                                    LoginActivity.this.finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(LoginActivity.this, "Login not successful", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
