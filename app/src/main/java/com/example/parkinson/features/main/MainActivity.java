package com.example.parkinson.features.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.ParkinsonApplication;
import com.example.parkinson.R;
import com.example.parkinson.di.MainComponent;
import com.example.parkinson.di.OnBoardingComponent;
import com.example.parkinson.features.on_boarding.OnBoardingActivity;
import com.example.parkinson.features.on_boarding.OnBoardingViewModel;
import com.example.parkinson.model.enums.EClinics;
import com.example.parkinson.model.enums.EQuestionType;
import com.example.parkinson.model.question_models.MultipleChoiceQuestion;
import com.example.parkinson.model.question_models.OpenQuestion;
import com.example.parkinson.model.question_models.Question;
import com.example.parkinson.model.question_models.Questionnaire;
import com.example.parkinson.model.user_models.Patient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    public MainComponent mainComponent;

    @Inject
    MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainComponent = ((ParkinsonApplication) getApplicationContext()).appComponent.mainComponent().create();
        mainComponent.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObservers();
    }

    private void initObservers(){
        mainViewModel.navigationEvent.observe(this, new Observer<MainViewModel.NavigationEvent>() {
            @Override
            public void onChanged(MainViewModel.NavigationEvent navigationEvent) {
                switch (navigationEvent){
                    case OPEN_ON_BOARDING_ACTIVITY:
                        openOnBoarding();
                        break;
                }
            }
        });
    }

    private void openOnBoarding(){
        Intent intent = new Intent(this, OnBoardingActivity.class);
        startActivity(intent);
        finish();
    }
}