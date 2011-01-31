package com.apps.jerdog.tinytoggle;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.admob.android.ads.AdView;

public class MainActivity extends Activity {
		private AudioManager mAudioManager;					// class-level variable, making it available to the whole Activity
		private boolean mPhoneIsSilent;						// class-level boolean to keep track of ringer state
		
//		private static final int STOPSPLASH = 0;
//		//time in milliseconds
//		private static final long SPLASHTIME = 3000;
//		
//		private ImageView splash;
//		
//		//handler for splash screen
//		private Handler splashHandler = new Handler() {
//			/* (non-Javadoc)
//			 * @see android.os.Handler#handleMessage(android.os.Message)
//			 */
//			@Override
//			public void handleMessage(Message msg) {
//				switch (msg.what) {
//				case STOPSPLASH:
//					//remove SplashScreen from view
//					splash.setVisibility(View.GONE);
//					break;
//				}
//				super.handleMessage(msg);
//			}
//		};
		

    @Override
    // onCreate() is the entry point to the application - all important initialization needs to happen here
    public void onCreate(Bundle savedInstanceState) {		// bundle is a key value that maps between string keys and various
    														// parcelable types.
    	
        super.onCreate(savedInstanceState);					// this is required for the application to run - calling to the 
        													// base Activity class to perform setup work for the MainActivity
        													// class.  This should always be included to the onCreate() method
        
        setContentView(R.layout.main);						// to get the UI to show up on the screen, you have to set the 
        													// content view for the activity
        
/*		splash = (ImageView) findViewById(R.id.splashscreen);
		Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);
*/
		
		//        AdManager.setTestDevices( new String[] { 
//        		AdManager.TEST_EMULATOR, "emulator-5554," } );
        
        AdView adView = (AdView)findViewById(R.id.ad);
        adView.requestFreshAd();
        
        mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);			// initializing the mAudioManager variable by getting
        																		// the service from the base Activity getSystemService
        																		// method call
        
        checkIfPhoneIsSilent();								// calls to initialize the mPhoneIsSilent boolean, default of false
        
        setButtonClickListener();							// a private void method containing the button code

    }
    
    
    private void setButtonClickListener() {
    	Button toggleButton = (Button)findViewById(R.id.toggleButton);			// uses the findViewById) method, available to all
        																		// activities in Android - allowing you to find any
        																		// view inside the activity's layout and do stuff
        																		// always returns a View class that you must cast
        																		// to the appropriate type before working with it
        
        		toggleButton.setOnClickListener(new View.OnClickListener() {	//event handling code
					
					@Override
					public void onClick(View v) {
						if (mPhoneIsSilent) {
							// Change back to normal mode
							mAudioManager
								.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
							mPhoneIsSilent = false;
						} else {
							// Change to silent mode
							mAudioManager
								.setRingerMode(AudioManager.RINGER_MODE_SILENT);
							mPhoneIsSilent = true;
						}

						//Now toggle the UI again
						toggleUi();
					}
				});
    }
    
    /**
     * Checks to see if the phone is currently in silent mode.
     */
    	private void checkIfPhoneIsSilent() {
    		int ringerMode = mAudioManager.getRingerMode();
    		if (ringerMode == AudioManager.RINGER_MODE_SILENT) {
    			mPhoneIsSilent = true;
    		} else {
    			mPhoneIsSilent = false;
    		}
    	}
     
    		/**
    		 * Toggles the UI images from silent to normal and vice versa.
    		 */
    	private void toggleUi() {
    		ImageView imageView = (ImageView) findViewById(R.id.phone_icon);
    		Drawable newPhoneImage;
    		
    		if (mPhoneIsSilent) {
    			newPhoneImage = 
    				getResources().getDrawable(R.drawable.phone_silent);
    		} else {
    			newPhoneImage = 
    				getResources().getDrawable(R.drawable.phone_on);
    		}
    		
    		imageView.setImageDrawable(newPhoneImage);
    	}
    
    @Override
    protected void onResume() {
    	super.onResume();
    	checkIfPhoneIsSilent();
    	toggleUi();
    }
}

	