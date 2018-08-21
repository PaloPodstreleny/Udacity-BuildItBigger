package com.udacity.gradle.builditbigger.free;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.OnFinishTaskCallback;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.idlingResource.SimpleIdlingResource;

import sk.podstreleny.palo.jokeui.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements OnFinishTaskCallback {

    private static final int JOKE_SIZE_MINIMUM = 2;
    @Nullable private SimpleIdlingResource mIdlingResource;
    private ProgressBar mProgressBar;
    private Button mJokeButton;
    private InterstitialAd mInterstitialAdd;
    private String mJoke;
    private TextView mErrorTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mErrorTextView = root.findViewById(R.id.error_tv);
        mProgressBar = root.findViewById(R.id.progressBar);
        mJokeButton = root.findViewById(R.id.joke_btn);

        AdView mAdView = root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeVisibilityOfProgressBar(View.VISIBLE);
                changeVisibilityOfJokeButton(View.GONE);
                changeVisibilityOfErrorMessage(View.GONE);
                final OnFinishTaskCallback callback = MainActivityFragment.this;
                Pair<OnFinishTaskCallback,SimpleIdlingResource> pair = new Pair<>(callback,mIdlingResource);
                new EndpointsAsyncTask().execute(pair);
            }
        });

        mInterstitialAdd = new InterstitialAd(getContext());

        mInterstitialAdd.setAdUnitId(getString(R.string.banner_ad_unit_id_2));
        AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mInterstitialAdd.loadAd(request);

        mInterstitialAdd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                moveToAnotherActivity(mJoke);
            }

        });

    }


    private void changeVisibilityOfProgressBar(int visibility){
        mProgressBar.setVisibility(visibility);
    }

    private void changeVisibilityOfJokeButton(int visibility){
        mJokeButton.setVisibility(visibility);
    }

    @Override
    public void onFinished(String joke) {
        mJoke = joke;
        changeVisibilityOfProgressBar(View.GONE);
        changeVisibilityOfJokeButton(View.VISIBLE);
        if(joke == null){
            changeVisibilityOfErrorMessage(View.VISIBLE);
        }else {
            changeVisibilityOfJokeButton(View.GONE);
            if(mInterstitialAdd.isLoaded()){
                mInterstitialAdd.show();
            }else {
                moveToAnotherActivity(joke);
            }
        }

    }

    private void moveToAnotherActivity(String joke){
        changeVisibilityOfProgressBar(View.GONE);
        if(getContext() !=null && joke.length() > JOKE_SIZE_MINIMUM) {
            Intent intent = new Intent(getContext(), JokeActivity.class);
            intent.putExtra(JokeActivity.JOKE_EXTRA, joke);
            startActivity(intent);
        }
    }

    private void changeVisibilityOfErrorMessage(int visibility){
        mErrorTextView.setVisibility(visibility);
    }

}
