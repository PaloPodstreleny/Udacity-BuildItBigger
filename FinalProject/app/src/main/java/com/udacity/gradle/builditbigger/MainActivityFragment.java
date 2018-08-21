package com.udacity.gradle.builditbigger;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.udacity.gradle.builditbigger.idlingResource.SimpleIdlingResource;

import sk.podstreleny.palo.jokeui.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements OnFinishTaskCallback {

    private SimpleIdlingResource mIdlingResource;
    private ProgressBar mProgressBar;
    private Button mJokeButton;
    private String mJoke;
    private TextView mErrorTextView;


    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mErrorTextView = root.findViewById(R.id.error_tv);
        mProgressBar = root.findViewById(R.id.progressBar);
        mJokeButton = root.findViewById(R.id.joke_btn);
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeVisibilityOfJokeButton(View.GONE);
                changeVisibilityOfProgressBar(View.VISIBLE);
                changeVisibilityOfErrorMessage(View.GONE);
                final OnFinishTaskCallback callback = MainActivityFragment.this;
                Pair<OnFinishTaskCallback,SimpleIdlingResource> pair = new Pair<>(callback,mIdlingResource);
                new EndpointsAsyncTask().execute(pair);
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
            changeVisibilityOfErrorMessage(View.GONE);
            if(getContext() !=null) {
                Intent intent = new Intent(getContext(), JokeActivity.class);
                intent.putExtra(JokeActivity.JOKE_EXTRA, joke);
                startActivity(intent);
            }
        }
    }

    private void changeVisibilityOfErrorMessage(int visibility){
        mErrorTextView.setVisibility(visibility);
    }

    @VisibleForTesting
    public String getJoke(){
        return mJoke;
    }

    @VisibleForTesting
    public void setIdlingResource(SimpleIdlingResource resource){
        mIdlingResource = resource;
    }



}
