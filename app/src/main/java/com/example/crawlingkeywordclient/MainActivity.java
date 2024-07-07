package com.example.crawlingkeywordclient;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(MyWorker.class, 1, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(this).enqueue(request);
    }
}