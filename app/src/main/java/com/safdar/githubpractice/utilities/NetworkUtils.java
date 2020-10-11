package com.safdar.githubpractice.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final private static String GITHUB_BASE_URL = "https://api.github.com/search/repositories";
    final private static String PARAM_QUERY = "q";
    final private static String PARAM_SORT = "sort";
    final private static String sortBy = "stars";

    public static URL buildUrl(String githubSearchQuery) {
        Uri builtUrl = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, githubSearchQuery)
                .appendQueryParameter(PARAM_SORT, sortBy).build();

        URL url =null;
        try {
            url=new URL(builtUrl.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
