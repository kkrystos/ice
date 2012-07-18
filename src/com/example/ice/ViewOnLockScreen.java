package com.example.ice;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class ViewOnLockScreen extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.view_on_lock_screen);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		
		ImageButton lockScreenBtn = (ImageButton) findViewById(R.id.LockScreenBtn);
		Button exitTolockScreenBtn = (Button) findViewById(R.id.ExitToLockBtn);
		
		lockScreenBtn.setOnClickListener(this);
		exitTolockScreenBtn.setOnClickListener(this);
	}

	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.LockScreenBtn:
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			finish();
			break;
			
		case R.id.ExitToLockBtn:
			finish();
			break;
			
		default:
				
			finish();
			break;
		
		}
		
	}

}
