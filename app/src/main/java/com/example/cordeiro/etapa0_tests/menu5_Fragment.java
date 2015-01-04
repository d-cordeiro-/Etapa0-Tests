package com.example.cordeiro.etapa0_tests;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * interface.
 */
public class menu5_Fragment extends Fragment {

    private static final String TAG_HOST = "Host";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu5_fragment, container, false);

        new GetJSONTask().execute("http://headers.jsontest.com/");

        return view;
    }

    private class GetJSONTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            DefaultHttpClient http_client = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httppost = new HttpPost("http://headers.jsontest.com/");

            // Depends on your web service
            httppost.setHeader("Content-type", "application/json");

            InputStream inputStream = null;
            String result = "{\"Host\": \"nop\"}";
            try {
                HttpResponse response = http_client.execute(httppost);
                result = EntityUtils.toString(response.getEntity());
            } catch (Exception e) {
                Log.e("CENAS", e.toString());
            } finally {
                try {
                    if (inputStream != null)
                        inputStream.close();
                } catch (Exception squish) {
                }
            }

            return result;
        }

        protected void onPostExecute(String result) {
            JSONObject json_obj = null;
            try {
                json_obj = new JSONObject(result);
                String host = json_obj.getString(TAG_HOST);

                TextView text = (TextView) getView().findViewById(R.id.textView);
                text.setText(host);

            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
        }
    }
}
