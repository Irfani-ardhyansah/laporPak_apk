package com.example.project_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class Daftar extends AppCompatActivity {

    private Button btnDaftar;
    private EditText nama, no_hp, gender, alamat, password;
    private static String URL_REGIST = "http://192.168.1.6/project_android/public/api_register.php";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        btnDaftar = findViewById(R.id.btnDaftar);
        nama = findViewById(R.id.nama);
        no_hp = findViewById(R.id.no_hp);
        gender = findViewById(R.id.gender);
        alamat = findViewById(R.id.alamat);
        password = findViewById(R.id.password);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regist();
            }
        });
    }

    private void Regist(){
        final String nama = this.nama.getText().toString().trim();
        final String no_hp = this.no_hp.getText().toString().trim();
        final String gender = this.gender.getText().toString().trim();
        final String alamat = this.alamat.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(Daftar.this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();

                                Intent iDaftar = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(iDaftar);
                            }
                        }  catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Daftar.this, "Pendaftaran Error!"  + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Daftar.this, "Pendaftaran Error!"  + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("no_hp", no_hp);
                params.put("alamat", alamat);
                params.put("gender", gender);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
