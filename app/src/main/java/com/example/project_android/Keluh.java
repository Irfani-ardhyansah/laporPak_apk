package com.example.project_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Keluh extends AppCompatActivity {

    private Button btnKeluh, btnBack;
    private EditText judul, isi, tempat;
    private static String URL_REGIST = "http://192.168.1.6/project_android/public/api_inputKeluh.php";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keluhan);
        btnKeluh = findViewById(R.id.btnKeluh);
        btnBack = findViewById(R.id.btnBack);
        judul = findViewById(R.id.judul);
        isi = findViewById(R.id.isi);
        tempat = findViewById(R.id.tempat);

        btnKeluh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Keluh();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iBack = new Intent(getApplicationContext(),Home.class);
                startActivity(iBack);
            }
        });
    }

    private void Keluh(){
        final String judul = this.judul.getText().toString().trim();
        final String isi = this.isi.getText().toString().trim();
        final String tempat = this.tempat.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(Keluh.this, "Terima Kasih Atas Keluhannya\n Akan Segera Kami Tindak Lanjuti!!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Keluh.this, Home.class);
                                startActivity(intent);
                            }
                        }  catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Keluh.this, "Keluhan Terjadi Error!"  + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Keluh.this, "Keluhan Terjadi Error!"  + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("judul", judul);
                params.put("isi", isi);
                params.put("tempat", tempat);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
