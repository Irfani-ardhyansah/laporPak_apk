package com.example.project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private Button btnLogin;
    private TextView btnDaftar;
    private EditText nama,password;
    private String URL_LOGIN = "http://192.168.1.6/project_android/public/api_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        nama = findViewById(R.id.nama);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnDaftar = findViewById(R.id.btnDaftar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nama.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (!name.isEmpty() || !pass.isEmpty()){
                    Login(name, pass);
                } else {
                    nama.setError("Tolong Masukkan Nama");
                    password.setError("Tolong Masukkan Password");
                }
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDaftar = new Intent(getApplicationContext(),Daftar.class);
                startActivity(iDaftar);
            }
        });
    }

    private void Login(final String nama, final String password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String nama = object.getString("nama").trim();

                                    Toast.makeText(MainActivity.this,
                                            "Berhasil Login. \n Selamat Datang "
                                                    +nama, Toast.LENGTH_SHORT)
                                            .show();

                                    Intent intent = new Intent(MainActivity.this, Home.class);
                                    startActivity(intent);
                                }

                            } else {
                                Toast.makeText(MainActivity.this, "Nama dan Password Salah!!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Password atau Nama Salah", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error" +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
