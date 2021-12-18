package com.example.javarnd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText userName = (EditText) findViewById(R.id.editTextTextPersonName2);
        final EditText password = (EditText) findViewById(R.id.editTextTextPassword2);
        Button btnLogin = (Button) findViewById(R.id.button2);
        Button btnRegis = (Button) findViewById(R.id.button3);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = userName.getText().toString();
                String pass = password.getText().toString();
                SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
                String userDetails = preferences.getString(user + pass + "data", "Username and password is Incorrect");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("display", userDetails);
                editor.commit();

                Intent displayScreen = new Intent(MainActivity.this, DisplayScreen.class);
                startActivity(displayScreen);

            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));

            }
        });
    }
}