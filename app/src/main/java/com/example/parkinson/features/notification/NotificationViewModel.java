package com.example.parkinson.features.notification;

import com.example.parkinson.data.DataRepository;
import com.example.parkinson.data.UserRepository;
import com.example.parkinson.model.enums.EStatus;
import com.example.parkinson.model.general_models.Report;
import java.util.Calendar;

import javax.inject.Inject;

public class NotificationViewModel {
    private final UserRepository userRepository;
    private final DataRepository dataRepository;

    @Inject
    public NotificationViewModel(UserRepository userRepository, DataRepository dataRepository) {
        this.userRepository = userRepository;
        this.dataRepository = dataRepository;
    }

    public void updateReport(EStatus status,boolean isDyskinesia, boolean isHallucinations, boolean isFalls) {
        Report report = new Report(Calendar.getInstance().getTime().getTime(), status,isDyskinesia, isHallucinations, isFalls);
        userRepository.postReport(report);
    }
}
