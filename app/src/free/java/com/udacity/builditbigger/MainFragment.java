package com.udacity.builditbigger;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.udacity.androidlib.JokeDisplayActivity;

/**
 * Created by Viji on 9/10/2017.
 */

public class MainFragment extends Fragment implements View.OnClickListener, JokeListener {
    private static final String TAG = MainFragment.class.getSimpleName();
    private InterstitialAd mInterstitialAd;
    private AdRequest mAdRequest;
    private ProgressBar mProgressBar;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        root.findViewById(R.id.btn_joke).setOnClickListener(this);

        MobileAds.initialize(getActivity(), getString(R.string.banner_ad_unit_id));
        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));

        showNewInterstitial();

        AdView adView = (AdView) root.findViewById(R.id.adView);
        adView.loadAd(mAdRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                showNewInterstitial();
                fetchJoke();
            }
        });
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_joke:
                showAd();
                break;
        }
    }

    @Override
    public void onJokeFetched(String joke) {
        if (getActivity() != null) {
            mProgressBar.setVisibility(View.GONE);
            startActivity(JokeDisplayActivity.getJokeDisplayActivityIntent(getActivity(), joke));
        }
    }

    private void showNewInterstitial() {
        mAdRequest = new AdRequest.Builder()
                .addTestDevice("33BE2250B43518CCDA7DE426D04EE232")
                .build();
        Log.d(TAG, "showNewInterstitial: isTestDevice " + mAdRequest.isTestDevice(getActivity()));
        mInterstitialAd.loadAd(mAdRequest);
    }

    private void showAd() {
        mInterstitialAd.show();
    }

    private void fetchJoke() {
        mProgressBar.setVisibility(View.VISIBLE);
        new JokeAsync(this).execute();
    }
}
