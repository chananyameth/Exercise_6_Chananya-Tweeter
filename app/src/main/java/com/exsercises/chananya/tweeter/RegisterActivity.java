package com.exsercises.chananya.tweeter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText userName = (EditText)findViewById(R.id.UserName);
        final EditText email = (EditText)findViewById(R.id.Email);
        final EditText password = (EditText)findViewById(R.id.Password);
        final EditText password_confirm = (EditText)findViewById(R.id.Password_confirm);
        final Button register = (Button)findViewById(R.id.Register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://37.139.3.222/register";
                JSONObject json = new JSONObject();
                try {
                    json.put("username", userName.getText().toString());
                    json.put("email", email.getText().toString());
                    json.put("password", password.getText().toString());
                    json.put("password_confirmation", password_confirm.getText().toString());

                    JsonAPI.getInstance().post(url, json, new JsonAPI.JsonCallback() {
                        @Override
                        public void onResponse(int statusCode, JSONObject json) {
                            if (statusCode == 200) {
                                String token = null;
                                try {
                                    token = json.getString("token");
                                    JsonAPI.getInstance().addHeader("Token", token);
                                    Intent main = new Intent(RegisterActivity.this, MainActivity.class);
                                    main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(main);
                                } catch (JSONException e) {
                                    e.printStackTrace();
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
