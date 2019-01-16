package com.example.varshahanji.andsdkintegartionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.applovin.sdk.AppLovinSdk;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ListView listView;
    String[] listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppLovinSdk.initializeSdk(getApplicationContext());

        listView=(ListView)findViewById(R.id.listView);
        textView=(TextView)findViewById(R.id.textView);
        listItem = getResources().getStringArray(R.array.array_technology);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                String value=adapter.getItem(position);
//                Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
                if (value.equals("Banner")) {
                    Intent mass = new Intent(getApplicationContext(), BannerActivity.class);
                    startActivity(mass);
                }
                else if (value.equals("Interstitial")){
                    Intent mass = new Intent(getApplicationContext(), InterstitialActivity.class);
                    startActivity(mass);
                }
                else if (value.equals("Rewarded Ad")){
                    Intent mass = new Intent(getApplicationContext(), RewardedActivity.class);
                    startActivity(mass);
                }
            }
        });
    }
}
