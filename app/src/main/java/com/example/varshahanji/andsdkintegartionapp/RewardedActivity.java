package com.example.varshahanji.andsdkintegartionapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.applovin.adview.AppLovinIncentivizedInterstitial;
import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdRewardListener;
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.sdk.AppLovinErrorCodes;
import com.applovin.sdk.AppLovinSdk;

import java.util.Map;

public class RewardedActivity extends Activity
{
    public static final String REWARDEDZONE1 = "4fa79820dcf45b64";
    public static final String REWARDEDZONE2 = "6679232d300fd637";

    private AppLovinIncentivizedInterstitial incentivizedInterstitial;

    private RadioGroup radioGroup;
    private RadioButton zone1,zone2;
    private int selectedId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        zone1 = (RadioButton) findViewById(R.id.zone1);
        zone2 = (RadioButton) findViewById(R.id.zone2);

        final Button loadButton = (Button) findViewById( R.id.loadButton );
        final Button showButton = (Button) findViewById( R.id.showButton );

        loadButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println( "Rewarded video loading..." );
                showButton.setEnabled( false );

                selectedId = radioGroup.getCheckedRadioButtonId();

                String zoneId = "";
                if(selectedId == zone1.getId())
                    zoneId = REWARDEDZONE1;
                else if (selectedId == zone2.getId())
                    zoneId = REWARDEDZONE2;

                incentivizedInterstitial = AppLovinIncentivizedInterstitial.create( zoneId, AppLovinSdk.getInstance( getApplicationContext() ) );
                incentivizedInterstitial.setUserIdentifier( "DEMO_USER_IDENTIFIER" ); // Set an optional user identifier used for S2S callbacks

                incentivizedInterstitial.preload( new AppLovinAdLoadListener()
                {
                    @Override
                    public void adReceived(AppLovinAd appLovinAd)
                    {
                        System.out.println( "Rewarded video loaded. ZoneId : "+ appLovinAd.getZoneId() );
                        openDialog("Rewarded video loaded.ZoneId : "+ appLovinAd.getZoneId() );
                        showButton.setEnabled( true );
                    }

                    @Override
                    public void failedToReceiveAd(int errorCode)
                    {
                        System.out.println( "Rewarded video failed to load with error code : " + errorCode );
                        openDialog("Rewarded video failed to load with error code : " + errorCode);
                    }
                } );
            }
        } );

        showButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showButton.setEnabled( false );

                //
                // OPTIONAL: Create listeners
                //

                // Reward Listener
                AppLovinAdRewardListener adRewardListener = new AppLovinAdRewardListener()
                {
                    @Override
                    public void userRewardVerified(AppLovinAd appLovinAd, Map map)
                    {

                        String currencyName = (String) map.get( "currency" );

                        String amountGivenString = (String) map.get( "amount" );

                        System.out.println( "Rewarded " + amountGivenString + " " + currencyName );

                    }

                    @Override
                    public void userOverQuota(AppLovinAd appLovinAd, Map map)
                    {
                        System.out.println( "Reward validation request exceeded quota with response: " + map );
                    }

                    @Override
                    public void userRewardRejected(AppLovinAd appLovinAd, Map map)
                    {
                        System.out.println( "Reward validation request was rejected with response: " + map );
                    }

                    @Override
                    public void validationRequestFailed(AppLovinAd appLovinAd, int responseCode)
                    {
                        System.out.println( "Reward validation request failed with error code: " + responseCode );
                    }

                    @Override
                    public void userDeclinedToViewAd(AppLovinAd appLovinAd)
                    {
                        System.out.println( "User declined to view ad" );
                    }
                };

                // Video Playback Listener
                AppLovinAdVideoPlaybackListener adVideoPlaybackListener = new AppLovinAdVideoPlaybackListener()
                {
                    @Override
                    public void videoPlaybackBegan(AppLovinAd appLovinAd)
                    {
                        System.out.println( "Video Started" );
                    }

                    @Override
                    public void videoPlaybackEnded(AppLovinAd appLovinAd, double percentViewed, boolean fullyWatched)
                    {
                        System.out.println( "Video Ended" );
                    }
                };

                // Ad Dispaly Listener
                AppLovinAdDisplayListener adDisplayListener = new AppLovinAdDisplayListener()
                {
                    @Override
                    public void adDisplayed(AppLovinAd appLovinAd)
                    {
                        System.out.println( "Ad Displayed" );
                    }

                    @Override
                    public void adHidden(AppLovinAd appLovinAd)
                    {
                        System.out.println( "Ad Dismissed" );
                    }
                };

                // Ad Click Listener
                AppLovinAdClickListener adClickListener = new AppLovinAdClickListener()
                {
                    @Override
                    public void adClicked(AppLovinAd appLovinAd)
                    {
                        System.out.println( "Ad Click" );
                    }
                };

                incentivizedInterstitial.show( getApplicationContext(), adRewardListener, adVideoPlaybackListener, adDisplayListener, adClickListener );
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
