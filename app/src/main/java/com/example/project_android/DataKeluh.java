package com.example.project_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DataKeluh extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datakeluh);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.btn_parse);
        Button btnBack = findViewById(R.id.btnBack);

        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iBack = new Intent(DataKeluh.this,Home.class);
                startActivity(iBack);
            }
        });
    }

    private void jsonParse() {
        String url = "http://192.168.1.6/project_android/public/api_keluhan.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("keluhan");

                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject keluh = jsonArray.getJSONObject(i);

                                String judul = keluh.getString("judul");
                                String isi = keluh.getString("isi");
                                String tempat = keluh.getString("tempat");

                                mTextViewResult.append(i + ". Judul \t: " + judul + "\n" + "\tLaporan \t: " + isi  +"\n"+ "\tTempat \t: " + tempat +"\n\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
