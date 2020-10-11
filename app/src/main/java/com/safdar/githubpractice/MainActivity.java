package com.safdar.githubpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.safdar.githubpractice.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView mQueryResult, mJsonResult;
    private EditText mQuery;
    private Button mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuery = findViewById(R.id.et_search);
        mQueryResult = findViewById(R.id.tv_result);
        mSearch = findViewById(R.id.bt_search);
        mJsonResult = findViewById(R.id.tv_json_result);



    }

    public void search_button(View view) throws IOException {
        String query = mQuery.getText().toString();
        URL searchUrl = NetworkUtils.buildUrl(query);
        String githubJson = NetworkUtils.getResponseFromHttpUrl(searchUrl);
        mQueryResult.setText(searchUrl.toString());
        mJsonResult.setText(githubJson);
        new GithubQueryTask().execute(searchUrl);

    }
    public class GithubQueryTask extends AsyncTask<URL,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String githubSearchResults = null;
            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return githubSearchResults;
        }

        @Override
        protected void onPostExecute(String githubSearchResults) {
            if (githubSearchResults != null && !githubSearchResults.equals("")) {

                mJsonResult.setText(githubSearchResults);
            }

        }
    }
}