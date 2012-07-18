package com.example.ice;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.WindowManager;

public class StartScreenActivity extends Activity{

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);	
        
        new Handler().postDelayed(new Runnable() {
                public void run() {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                }
        }, 1000);
    }
	
	}
	
	


