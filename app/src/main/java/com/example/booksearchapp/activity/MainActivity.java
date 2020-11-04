package com.example.booksearchapp.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.booksearchapp.R;
import com.example.booksearchapp.adapter.RecyclerViewAdapter;
import com.example.booksearchapp.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Book> mBooks;
    private RecyclerViewAdapter mAdapter;
    private RequestQueue mRequestQueue;

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    private EditText search_edit_text;
    private Button search_button;
    private TextView error_message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_edit_text = findViewById(R.id.search_box);
        search_button = findViewById(R.id.search_buttton);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBooks = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);


        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBooks.clear();
                search();
            }
        });


    }

    private void parseJson(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String title = "";
                        String author = "";
                        String publishedDate = "NoT Available";
                        String description = "No Description";
                        Integer pageCount = 0;
                        try {
                            JSONArray items = response.getJSONArray("items");
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                                title = volumeInfo.getString("title");
                                JSONArray authors = volumeInfo.getJSONArray("authors");
                                if (authors.length() ==0) {
                                    author = "";
                                } else {
                                for (int j = 0; j < authors.length(); j++) {
                                    author += authors.getString(j) + "\n";
                                }}
                                publishedDate = volumeInfo.getString("publishedDate");
                                pageCount = volumeInfo.getInt("pageCount");
                                description = volumeInfo.getString("description");
                                String thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
                                String previewLink = volumeInfo.getString("previewLink");
                                mBooks.add(new Book(title, author, publishedDate, description, thumbnail, previewLink, pageCount));
                                mAdapter = new RecyclerViewAdapter(com.example.booksearchapp.activity.MainActivity.this, mBooks);
                                mRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
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
        mRequestQueue.add(request);
    }


    private void search() {
        String search_query = search_edit_text.getText().toString();

        if (search_query.equals("")) {
            Toast.makeText(this, "Please enter your query", Toast.LENGTH_SHORT).show();
            return;
        }
        String final_query = search_query.replace(" ", "+");
        Uri uri = Uri.parse(BASE_URL + final_query);
        Uri.Builder builder = uri.buildUpon();
        parseJson(builder.toString());
    }
}
