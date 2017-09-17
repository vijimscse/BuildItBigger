package com.udacity.builditbigger;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Viji on 9/10/2017.
 * Shows UI for the MainActivity
 */

public class MainFragment extends Fragment implements JokeListener {
    private static final String TEST_DEVICE_ID = "33BE2250B43518CCDA7DE426D04EE232";
    private InterstitialAd mInterstitialAd;
    private AdRequest mAdRequest;

    @BindView(R.id.progressBar)
    public ProgressBar mProgressBar;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);

        MobileAds.initialize(getActivity(), getString(R.string.banner_ad_unit_id));

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));

        showNewInterstitial();

        AdView adView = root.findViewById(R.id.adView);
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

    @OnClick(R.id.btn_joke)
    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_joke:
                if (NetworkUtility.isInternetConnected(getActivity())) {
                    showAd();
                } else {
                    Snackbar.make(view, R.string.no_internet, Snackbar.LENGTH_SHORT).show();
                }
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
                .addTestDevice(TEST_DEVICE_ID)
                .build();
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
