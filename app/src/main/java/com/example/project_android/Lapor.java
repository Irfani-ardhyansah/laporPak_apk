package com.example.project_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Lapor extends AppCompatActivity implements View.OnClickListener {

    private ImageButton chooseBn;
    private Button btnLapor, btnBack;
    private EditText judul, isi, tempat, harga;
    private final int IMG_REQUEST = 1;
    private ImageView imgView;
    private Bitmap bitmap;
    private static String URL_REGIST = "http://192.168.1.6/project_android/public/api_inputLapor.php";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lapor);
        Button btnLapor = findViewById(R.id.btnLapor);
        Button btnBack = findViewById(R.id.btnBack);
        ImageButton chooseBn = findViewById(R.id.chooseBn);
         judul = findViewById(R.id.judul);
         isi = findViewById(R.id.isi);
         tempat = findViewById(R.id.tempat);
         harga = findViewById(R.id.harga);
        imgView = (ImageView)findViewById(R.id.imageView);
         chooseBn.setOnClickListener(this);
        btnLapor.setOnClickListener(this);
        btnBack.setOnClickListener(this);

//        btnLapor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Lapor();
//            }
//        });
//
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent iBack = new Intent(getApplicationContext(),Home.class);
//                startActivity(iBack);
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLapor:
                Lapor();
                break;

            case R.id.btnBack:
                Back();
                break;

            case R.id.chooseBn:
                selectImage();
                break;
        }

    }


    private void Back(){
        Intent iBack = new Intent(getApplicationContext(),Home.class);
        startActivity(iBack);
    }


    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST && resultCode==RESULT_OK && data!=null)
        {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imgView.setImageBitmap(bitmap);
                imgView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(Lapor.this, "Terjadi error" + e.toString(), Toast.LENGTH_LONG).show();
            }

        }
    }


    private void Lapor(){
        final String judul = this.judul.getText().toString().trim();
        final String isi = this.isi.getText().toString().trim();
        final String tempat = this.tempat.getText().toString().trim();
        final String harga = this.harga.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(Lapor.this, "Terima Kasih Atas Laporan\n Akan Segera Kami Tindak Lanjuti!!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Lapor.this, Home.class);
                                startActivity(intent);
                            }
                        }  catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Lapor.this, "Laporan Terjadi Error!"  + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Lapor.this, "Laporan Terjadi Error!"  + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("judul", judul);
                params.put("isi", isi);
                params.put("tempat", tempat);
                params.put("harga", harga);
                params.put("foto",imageToString(bitmap));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);

    }
}
