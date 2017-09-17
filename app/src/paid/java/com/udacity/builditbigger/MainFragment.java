package com.udacity.builditbigger;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.udacity.androidlib.JokeDisplayActivity;

/**
 * Created by Viji on 9/10/2017.
 */

public class MainFragment extends Fragment implements View.OnClickListener, JokeListener {


    private ProgressBar mProgressBar;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        root.findViewById(R.id.btn_joke).setOnClickListener(this);

        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_joke:
                fetchJoke();
                break;
        }
    }

    @Override
    public void onJokeFetched(String joke) {
        mProgressBar.setVisibility(View.GONE);
        startActivity(JokeDisplayActivity.getJokeDisplayActivityIntent(getActivity(), joke));
    }

    private void fetchJoke() {
        mProgressBar.setVisibility(View.VISIBLE);
        new JokeAsync(this).execute();
    }
}
