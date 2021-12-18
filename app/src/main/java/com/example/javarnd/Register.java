package com.example.javarnd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final EditText userName = (EditText) findViewById(R.id.editTextTextPersonName);
        final EditText password = (EditText) findViewById(R.id.editTextTextPassword);
        final EditText email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        Button btnRegister = (Button) findViewById(R.id.button4);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
                String newUser = userName.getText().toString();
                String newPassword = password.getText().toString();
                String newEmail = email.getText().toString();

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString(newUser + newPassword + "data", newUser + "/n" + newEmail);
                editor.commit();

                Intent loginScreen = new Intent(Register.this, MainActivity.class);
                startActivity(loginScreen);

            }
        });

    }
}
