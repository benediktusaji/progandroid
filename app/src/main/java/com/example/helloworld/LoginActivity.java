package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    //Ini contoh komentar
    private static final String TAG = "Aktivitasku";
    public static final String CHANNEL_ID_1 = "Channel1";
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d("Aktivitasku","Check");
        setContentView(R.layout.activity_login);

        final Button button = findViewById(R.id.btnLogin);
        final EditText txtUsername = findViewById(R.id.editTextUsername);
        final EditText txtPassword = findViewById(R.id.editTextPassword);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if(txtUsername.getText().toString().equals("admin")&&txtPassword.getText().toString().equals("admin")){
                    // Masuk ke activity baru
                    Intent intent = new Intent(v.getContext(), HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Username atau Password Anda tidak benar!",Toast.LENGTH_LONG).show();
                }
            }
        });
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
                    sendOnChannel1("Wifi Terdeteksi Mati","Lurr! Wifinya mati, kamu sekarang berselancar pakai paket data. Hati-hati!");
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
}