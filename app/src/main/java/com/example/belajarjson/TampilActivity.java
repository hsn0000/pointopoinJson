package com.example.belajarjson;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.belajarjson.helpers.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TampilActivity extends AppCompatActivity {

    ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);
        listView = findViewById(R.id.listview);
        getJSON();

    }
    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {

            JSONArray result = new JSONArray(JSON_STRING);
            for (int i = 0; i<result.length(); i++){
                JSONObject jo =result.getJSONObject(i);
                String id = jo.getString("id");
                String buku = jo.getString("buku");
                String author = jo.getString("author");

                HashMap<String, String > books = new HashMap<>();
                books.put("id",id);
                books.put("buku",buku);
                books.put("author",author);
                list.add(books);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                TampilActivity.this, list,
                android.R.layout.simple_list_item_2,
                new String[]{"buku", "author"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        listView.setAdapter(adapter);
    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog Loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Loading = ProgressDialog.show(TampilActivity.this,
                        "Mengambil data", "Mohon tunggu.");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Loading.dismiss();
                JSON_STRING=s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(RequestHandler.URL_ADD);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
