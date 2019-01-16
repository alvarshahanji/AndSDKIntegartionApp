package com.example.varshahanji.andsdkintegartionapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SyncStatusObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.sdk.AppLovinSdk;

public class InterstitialActivity extends Activity {
    public static final String INTERSTITIALZONE1 = "efad6cabb7735e7c";
    public static final String INTERSTITIALZONE2 = "e8922f0b81c9701a";

    private RadioGroup radioGroup;
    private RadioButton zone1,zone2;
    private int selectedId;
    private AppLovinAd                   currentAd;
    private AppLovinInterstitialAdDialog interstitialAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        zone1 = (RadioButton) findViewById(R.id.zone1);
        zone2 = (RadioButton) findViewById(R.id.zone2);

        interstitialAd = AppLovinInterstitialAd.create( AppLovinSdk.getInstance( getApplicationContext() ), getApplicationContext() );

        final Button loadButton = (Button) findViewById( R.id.loadButton );
        final Button showButton = (Button) findViewById( R.id.showButton );

        loadButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectedId = radioGroup.getCheckedRadioButtonId();

                System.out.println( "Interstitial loading..." );
                showButton.setEnabled( false );
                String zoneId = "";
                if(selectedId == zone1.getId())
                    zoneId = INTERSTITIALZONE1;
                else if (selectedId == zone2.getId())
                    zoneId = INTERSTITIALZONE2;

                AppLovinSdk.getInstance( getApplicationContext() ).getAdService().loadNextAdForZoneId( zoneId, new AppLovinAdLoadListener()
                {
                    @Override
                    public void adReceived(AppLovinAd ad)
                    {
                        System.out.println( "Interstitial Loaded    : " + ad.getZoneId() );
                        openDialog("Interstitial Loaded    : " + ad.getZoneId());
                        currentAd = ad;

                        runOnUiThread( new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                showButton.setEnabled( true );
                            }
                        } );
                    }

                    @Override
                    public void failedToReceiveAd(int errorCode)
                    {
                        System.out.println( "Interstitial failed to load with error code :" + errorCode );
                        openDialog("Interstitial failed to load with error code :" + errorCode);
                    }
                } );
            }
        } );

        showButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ( currentAd != null )
                {
                    interstitialAd.showAndRender( currentAd );
                }
            }
        } );


        interstitialAd.setAdDisplayListener( new AppLovinAdDisplayListener()
        {
            @Override
            public void adDisplayed(AppLovinAd appLovinAd)
            {
                System.out.println( "Interstitial Displayed" );
            }

            @Override
            public void adHidden(AppLovinAd appLovinAd)
            {
                System.out.println( "Interstitial Hidden" );
            }
        } );

        interstitialAd.setAdClickListener( new AppLovinAdClickListener()
        {
            @Override
            public void adClicked(AppLovinAd appLovinAd)
            {
                System.out.println( "Interstitial Clicked" );
            }
        } );

        // This will only ever be used if you have video ads enabled.
        interstitialAd.setAdVideoPlaybackListener( new AppLovinAdVideoPlaybackListener()
        {
            @Override
            public void videoPlaybackBegan(AppLovinAd appLovinAd)
            {
                System.out.println( "Video Started" );
            }

            @Override
            public void videoPlaybackEnded(AppLovinAd appLovinAd, double percentViewed, boolean wasFullyViewed)
            {
                System.out.println("Video Ended" );
            }
        } );

    }

    public void openDialog(String dialogText) {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Set Custom Title
        TextView title = new TextView(this);
        // Title Properties
        title.setText("Event");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(15);
        alertDialog.setCustomTitle(title);

        // Set Message
        TextView msg = new TextView(this);
        // Message Properties
        msg.setText(dialogText);
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        alertDialog.setView(msg);

        // Set Button
        // you can more buttons
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
            }
        });

        new Dialog(getApplicationContext());
        alertDialog.show();

        // Set Properties for OK Button
        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        okBT.setPadding(400, 10, 10, 10);   // Set Position
        okBT.setTextColor(Color.BLUE);
        okBT.setTextSize(20);
        okBT.setLayoutParams(neutralBtnLP);


    }


}
