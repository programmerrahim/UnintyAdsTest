package com.greenlove.unitytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.IUnityBannerListener;
import com.unity3d.services.banners.UnityBanners;

public class MainActivity extends AppCompatActivity {

    private String GameID = "4376387";
    private boolean testMode = true;
    private String bannerAdsPlacement = "Banner_Android";
    private String interestitialAdsPlacement = "Interstitial_Android";

    private Button showInterstialBtn;
    private Button showBannerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UnityAds.initialize(MainActivity.this, GameID, testMode);


// Unity Interestitial Ads Listner Start
        IUnityAdsListener unityAdsListener = new IUnityAdsListener() {
            @Override
            public void onUnityAdsReady(String s) {
                Toast.makeText(MainActivity.this, "Interestitial Ads Ready", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnityAdsStart(String s) {
                Toast.makeText(MainActivity.this, "Interestitial Ads Playing", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
                Toast.makeText(MainActivity.this, "Interestitial Ads Finished", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
                Toast.makeText(MainActivity.this, unityAdsError.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        UnityAds.setListener(unityAdsListener);

        //Inside Activity start
        if (UnityAds.isInitialized()) {
            UnityAds.load(interestitialAdsPlacement);
            UnityBanners.loadBanner(MainActivity.this, bannerAdsPlacement);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DisplayInterestitalAds();
                }
            }, 5000);
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    UnityAds.load(interestitialAdsPlacement);


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            DisplayInterestitalAds();
                            UnityBanners.loadBanner(MainActivity.this, bannerAdsPlacement);
                        }
                    }, 5000);
                }
            }, 5000);

        }
        //Inside Activity Ends

        // Unity Interestitial Ads Listner Ends

        // Unity Banner Ads Start

        IUnityBannerListener bannerListener = new IUnityBannerListener() {
            @Override
            public void onUnityBannerLoaded(String s, View view) {
                ((ViewGroup) findViewById(R.id.bannerAdsLayoutId)).removeView(view);
                ((ViewGroup) findViewById(R.id.bannerAdsLayoutId)).addView(view);
            }

            @Override
            public void onUnityBannerUnloaded(String s) {

            }

            @Override
            public void onUnityBannerShow(String s) {

            }

            @Override
            public void onUnityBannerClick(String s) {

            }

            @Override
            public void onUnityBannerHide(String s) {

            }

            @Override
            public void onUnityBannerError(String s) {

            }
        };

        UnityBanners.setBannerListener(bannerListener);
        // Unity Banner Ads Ends

        showInterstialBtn = findViewById(R.id.btnInsId);
        showBannerBtn = findViewById(R.id.btnBannerId);

        showInterstialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnityAds.load(interestitialAdsPlacement);
                DisplayInterestitalAds();
            }
        });

        showBannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnityBanners.loadBanner(MainActivity.this, bannerAdsPlacement);
            }
        });
    }

    // Disply Interestitial Ads Start
    private void DisplayInterestitalAds() {
        if (UnityAds.isReady(interestitialAdsPlacement)) {
            UnityAds.show(MainActivity.this, interestitialAdsPlacement);
        }
    }

    // Disply Interestitial Ads Ends
}