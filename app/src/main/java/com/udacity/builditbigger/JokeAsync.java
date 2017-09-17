package com.udacity.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.gcm.jokebackend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Viji on 9/9/2017.
 * AsyncTask to fetch Joke from Google API
 */

public class JokeAsync extends AsyncTask<Void, Void, String> {


    private static MyApi myApiService;
    private JokeListener mListener;

    public JokeAsync(JokeListener listener) {
        this.mListener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(BuildConfig.SERVER_URL);

            if (BuildConfig.DEBUG) {
                builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
            }

            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        mListener.onJokeFetched(result);
    }
}
