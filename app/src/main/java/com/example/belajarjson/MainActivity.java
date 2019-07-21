package com.example.belajarjson;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.belajarjson.helpers.RequestHandler;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText buku, author;
    Button button, btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buku = findViewById(R.id.buku);
        author = findViewById(R.id.author);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployee();
            }
        });
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TampilActivity.class));
            }
        });
    }
    private void addEmployee (){
        final String sbuku = buku.getText().toString().trim();
        final String sauthor = author.getText().toString().trim();

        class AddEmployee extends AsyncTask<Void, Void, String>{

            ProgressDialog loading;

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,
                        "Menanmbahkan..",
                        "Tunggu ya");
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put("buku", sbuku);
                params.put("author",sauthor);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(RequestHandler.URL_ADD,params);
                return res;
            }
        }
        AddEmployee ae = new AddEmployee();
        ae.execute();
    }
}
