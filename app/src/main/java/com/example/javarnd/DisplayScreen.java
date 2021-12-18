package com.example.javarnd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javarnd.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DisplayScreen extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<String> userList;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;
    ArrayAdapter<String> listAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());



        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        String display = preferences.getString("display","");

        TextView displayInfo = (TextView) findViewById(R.id.textView);
        displayInfo.setText(display);

        

        initialiZe();
        
        new fetchData().start();;


    }

    private void initialiZe() {
        userList = new ArrayList<>();
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,userList);


    }

    class fetchData extends Thread{
        String data = "";


        @Override
        public void run() {

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(DisplayScreen.this);
                    progressDialog.setMessage("Fetching Data");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });
            try {
                URL url = new URL("https://reqres.in/api/users?page=2");
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream)));
                String line;

                while((line = bufferedReader.readLine())!=null){
                    data = data + line;
                }
                if(!data.isEmpty()){
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray user = jsonObject.getJSONArray("data");
                    userList.clear();
                    for(int i = 0; i<user.length();i++){
                        JSONObject names = user.getJSONObject(i);
                        String name = names.getString("name");
                        userList.add(name);

                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                   if(progressDialog.isShowing()){
                       progressDialog.dismiss();
                   }
                   listAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
