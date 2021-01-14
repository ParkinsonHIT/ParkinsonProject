package com.example.parkinson.features.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.parkinson.data.DataRepository;
import com.example.parkinson.data.UserRepository;
import com.example.parkinson.di.MainScope;
import com.example.parkinson.model.enums.EQuestionType;
import com.example.parkinson.model.question_models.MultipleChoiceQuestion;
import com.example.parkinson.model.question_models.OpenQuestion;
import com.example.parkinson.model.question_models.Question;
import com.example.parkinson.model.question_models.Questionnaire;
import com.example.parkinson.model.user_models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


@MainScope
public class MainViewModel {
    private final UserRepository userRepository;
    private final DataRepository dataRepository;
    MutableLiveData<Patient> patientEvent;
    MutableLiveData<NavigationEvent> navigationEvent;

    public enum NavigationEvent {
        OPEN_ON_BOARDING_ACTIVITY
    }


    // @Inject tells Dagger how to create instances of MainViewModel
    @Inject
    public MainViewModel(UserRepository userRepository, DataRepository dataRepository) {
        this.userRepository = userRepository;
        this.dataRepository = dataRepository;
        patientEvent = new MutableLiveData<>();
        navigationEvent = new MutableLiveData<>();
    }

    public void initData() {
        userRepository.getPatientDetails(setPatientDataListener());
    }

    private ValueEventListener setPatientDataListener(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Patient patient_Info = dataSnapshot.getValue(Patient.class);
                    patientEvent.postValue(patient_Info);

                    //for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                       // Patient patient_Info = snapshot.getValue(Patient.class);
                        //patientEvent.postValue(patient_Info);
                    //}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    public void logOut() {
        userRepository.logout();
        navigationEvent.postValue(NavigationEvent.OPEN_ON_BOARDING_ACTIVITY);
    }
}
