package com.example.parkinson.features.main;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ParkinsonApplication;
import com.example.parkinson.R;
import com.example.parkinson.common.LoadingScreen;
import com.example.parkinson.di.MainComponent;
import com.example.parkinson.features.brodacsts.NotifService;
import com.example.parkinson.features.brodacsts.ReportBroadcast;
import com.example.parkinson.features.brodacsts.ReportService;
import com.example.parkinson.features.notification.NotifServiceForground;
import com.example.parkinson.features.notification.NotificationActivity;
import com.example.parkinson.features.on_boarding.OnBoardingActivity;

import java.util.Calendar;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    public MainComponent mainComponent;

    @Inject
    MainViewModel mainViewModel;
    LoadingScreen loadingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainComponent = ((ParkinsonApplication) getApplicationContext()).appComponent.mainComponent().create();
        mainComponent.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        initObservers();
        initNotifactions();

    }

    private void initNotifactions() {
        Intent intentService = new Intent(this, NotifServiceForground.class);
        startService(intentService);
    }

    private void initUi() {
        loadingScreen = findViewById(R.id.loadingScreen);
    }

    private void initObservers(){
        mainViewModel.openActivityEvent.observe(this, navigationEvent -> {
            switch (navigationEvent){
                case OPEN_ON_BOARDING_ACTIVITY:
                    openOnBoarding();
                    break;
            }
        });
        mainViewModel.isLoading.observe(this, isLoading ->{
            updateLoadingScreen(isLoading);
        });
    }

    /** Show / hide loading animation
     * @param isLoading is true when waiting for data from server
     */
    public void updateLoadingScreen(Boolean isLoading) {
        if(isLoading){
            loadingScreen.show();
        } else {
            loadingScreen.hide();
        }
    }

    private void openOnBoarding(){
        Intent intent = new Intent(this, OnBoardingActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}