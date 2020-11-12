package com.example.helloworld;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.example.helloworld.services.MyJobScheduler;
import com.example.helloworld.utils.SessionManagement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.helloworld.ui.main.SectionsPagerAdapter;

import static com.example.helloworld.LoginActivity.CHANNEL_ID_1;

public class HomeActivity extends AppCompatActivity {
    private WifiManager wifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }

    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            switch(wifiStateExtra){
                case WifiManager.WIFI_STATE_ENABLED:
                    sendOnChannel1("Wifi Terdeteksi Nyala","Wifinya sudah nyala lur, selamat berselancar!");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    sendOnChannel1("Wifi Terdeteksi Mati","Lurr! Wifinya mati, sekarang kamu berselancar pakai paket data. Hati-hati!");
                    break;
            }
        }
    };
    private void sendOnChannel1(String title, String message) {
        //Persiapkan Channel
        NotificationChannel channel1 = new NotificationChannel(CHANNEL_ID_1,"Channel 1", NotificationManager.IMPORTANCE_DEFAULT);
        //Persiapkan Notif Manager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Buat channelnya
        notificationManager.createNotificationChannel(channel1);
        //Persiapkan Notifikasinya
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .build();
        notificationManager.notify(0, notification);
    }

    public void logout(View v) {
        //Delete session
        SessionManagement sessionManagement = new SessionManagement(HomeActivity.this);
        sessionManagement.removeSession();

        //go to loginactivity
        Intent intent = new Intent(v.getContext(),LoginActivity.class);
        startActivity(intent);
    }
}