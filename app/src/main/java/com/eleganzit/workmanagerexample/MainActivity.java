package com.eleganzit.workmanagerexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private boolean mIsPeriodicWorkScheduled = false;
    private Button mPeriodicWorkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPeriodicWorkButton = findViewById(R.id.button_schedule_periodic_work);
        // One time work
        findViewById(R.id.button_schedule_one_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneTimeWorkRequest compressionWork =
                        new OneTimeWorkRequest.Builder(MyWorker.class)
                                .build();
                WorkManager.getInstance().enqueue(compressionWork);

            }
        });

        // Periodic work
        mPeriodicWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PeriodicWorkRequest.Builder builder = new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.MINUTES);
                builder.setConstraints(Constraints.NONE);
                PeriodicWorkRequest workRequest = builder.build();

                if (mIsPeriodicWorkScheduled) {

                    UUID workId = workRequest.getId();

                    WorkManager.getInstance().cancelWorkById(workId);

                    mPeriodicWorkButton.setText("Schedule Periodic Work");

                    mIsPeriodicWorkScheduled = false;

                } else {

                    WorkManager.getInstance().enqueue(workRequest);

                    mIsPeriodicWorkScheduled = true;

                    mPeriodicWorkButton.setText("Cancel Periodic Work");
                }
            }
        });
    }
}
