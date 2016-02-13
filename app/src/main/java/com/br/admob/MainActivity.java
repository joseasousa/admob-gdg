package com.br.admob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {
    private AdView adView;

    private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button fab = (Button) findViewById(R.id.banner);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }else{
                   newTela();
               }
            }
        });

        adView = (AdView) findViewById(R.id.adView);
        //producao
        // AdRequest adRequest = new AdRequest.Builder().build();

        //teste
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        //todos dos emuladores
                .addTestDevice("67D2B619EB094CC318FA159750B8BDFB")  // Exemplo de id de um dispositivo
        .build();

        adView.loadAd(adRequest);



        interstitialAd = new InterstitialAd(getBaseContext());
        interstitialAd.setAdUnitId(getString(R.string.intertial_ad_id));

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                requestNewInterstitial();
                newTela();            }
        });
    }

    private void requestNewInterstitial() {
        //producao
        // AdRequest adRequest = new AdRequest.Builder().build();

        //teste
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        //todos dos emuladores
                .addTestDevice("67D2B619EB094CC318FA159750B8BDFB")  // Exemplo de id de um dispositivo
                .build();

       interstitialAd.loadAd(adRequest);
    }

    private void newTela(){
        Intent intent = new Intent(getBaseContext(),Tela2Activity.class);
        startActivity(intent);

    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
        if (!interstitialAd.isLoaded()) {
            requestNewInterstitial();
        }
    }


    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
