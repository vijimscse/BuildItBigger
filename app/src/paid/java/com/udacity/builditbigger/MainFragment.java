package com.udacity.builditbigger;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.udacity.androidlib.JokeDisplayActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Viji on 9/10/2017.
 * Shows UI for the MainActivity
 */

public class MainFragment extends Fragment implements JokeListener {


    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, root);

        return root;
    }

    @OnClick(R.id.btn_joke)
    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_joke:
                if (NetworkUtility.isInternetConnected(getActivity())) {
                    fetchJoke();
                } else {
                    Snackbar.make(view, R.string.no_internet, Snackbar.LENGTH_SHORT).show();
                }
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
