package com.example.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    class weather extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... Address) {

            try {
                URL url=new URL(Address[0]);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream is= connection.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);

                int data = isr.read();
                String content="";
                char ch;
                while (data !=-1){
                    ch=(char) data;
                    content=content+ch;
                    data=isr.read();

                }

                return content;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String content;
        weather weather=new weather();
        try {
            content=weather.execute("https://openweathermap.org/data/2.5/weather?q=Landon&appid=b6907d289e10d714a6e88b30761fae22").get();
            Log.i("content",content);
            JSONObject jsonObject=new JSONObject(content);
            String Weatherdata=jsonObject.getString("weather");

            JSONArray array=new JSONArray(Weatherdata);

            String Main="";
            String Discription="";

            for(int i=0;i<array.length();i++){
                JSONObject weatherpart= array.getJSONObject(i);
                Main=weatherpart.getString("main");
                Discription=weatherpart.getString("description");

            }

            Log.i("main",Main);
            Log.i("description",Discription);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
