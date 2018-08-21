package com.udacity.gradle.builditbigger;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SimpleFragmentActivityTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().add(R.id.fragment,fragment).commit();
    }

}
