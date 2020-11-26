package com.example.helloworld;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.helloworld.services.MyJobScheduler;

import static android.content.Context.JOB_SCHEDULER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class FragmentLeft extends Fragment {
    private static final String TAG = "MainActivity";
    private Button btnStart = null;
    private Button btnStop = null;
    private Button btnMgmtMhs = null;
    private Button btnCamera;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnStart = getView().findViewById(R.id.startJob);
        btnStop = getView().findViewById(R.id.stopJob);
        btnMgmtMhs = getView().findViewById(R.id.btnMgmtMhs);
        btnCamera = getView().findViewById(R.id.btnCamera);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleJob(v);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cancelJob(v);
            }
        });
        btnMgmtMhs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                moveToManageMhsActivity(v);
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),CameraActivity.class);
                startActivity(intent);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_left, container, false);
    }

    public void scheduleJob(View v) {
        ComponentName componentName = new ComponentName(getActivity(), MyJobScheduler.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();
        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }
    public void cancelJob(View v) {
        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "Job cancelled");
    }
    public void moveToManageMhsActivity(View v){
        Intent intent = new Intent(v.getContext(),ManageMhsActivity.class);
        startActivity(intent);
    }
}
