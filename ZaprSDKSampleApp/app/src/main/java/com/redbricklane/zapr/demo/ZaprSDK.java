package com.redbricklane.zapr.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.redbricklane.zapr.bannersdk.ZaprBannerAd;
import com.redbricklane.zapr.bannersdk.ZaprBannerAdEventListener;
import com.redbricklane.zapr.basesdk.Log;
import com.redbricklane.zapr.basesdk.model.UserInfo;
import com.redbricklane.zapr.videosdk.ZaprVideoAd;
import com.redbricklane.zapr.videosdk.ZaprVideoAdEventListener;
import com.redbricklane.zapr.videosdk.net.VideoAdResponse;
import com.redbricklane.zaprSdkBase.Zapr;

public class ZaprSDK extends AppCompatActivity {

    private Context mContext = this;
    private Button mShowVideoAdButton;
    private ZaprBannerAd mBannerAd;
    private ZaprVideoAd mVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zapr_sdk);
        mShowVideoAdButton = (Button) findViewById(R.id.showVideoAdButton);

        // Set log level in Zapr Ad SDK (Optional. Default log level is error)
        Log.setLogLevel(Log.LOG_LEVEL.verbose);

        // Initialize Banner Ad
        mBannerAd = (ZaprBannerAd) findViewById(R.id.zaprAdView);
        if (mBannerAd != null) {
            mBannerAd.setAdUnitId("abcd1234banner"); // Add your Ad Unit ID here
            mBannerAd.setBannerAdEventListener(mBannerAdEventListener);
            mBannerAd.setUserInfo(new UserInfo(1980, "M"));
        }

        // Initialize Video Ad
        mVideoAd = new ZaprVideoAd(mContext);
        mVideoAd.setZaprVideoAdEventListener(mVideoAdEventListener);
        mVideoAd.setAdUnitId("abcd1234video"); // Add your Ad Unit ID here
        mVideoAd.setBlockSkippableAds(true);
        mVideoAd.setMaxDuration(300);
        mVideoAd.setMinDuration(30);
        mVideoAd.setUserInfo(new UserInfo(1980, "M"));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBannerAd != null) {
            mBannerAd.destroy();
        }
        if (mVideoAd != null) {
            mVideoAd.destroy();
        }
    }

    public void loadAdButtonClicked(View view) {
        if (mBannerAd != null) {
            mBannerAd.loadAd();
        }
    }


    private ZaprBannerAdEventListener mBannerAdEventListener = new ZaprBannerAdEventListener() {
        @Override
        public void onBannerAdLoaded() {
            showToast("Banner Ad Loaded");
        }

        @Override
        public void onBannerAdClicked() {
            showToast("Banner Ad Clicked");
        }

        @Override
        public void onFailedToLoadBannerAd(int errorCode, String errorMessage) {
            showToast("Error Code: " + errorCode + "\nError: " + errorMessage);
        }
    };

    private ZaprVideoAdEventListener mVideoAdEventListener = new ZaprVideoAdEventListener() {
        @Override
        public void onVideoAdError(int errorCode, String errorMessage) {
            mShowVideoAdButton.setEnabled(false);
            showToast("ErrorCode: " + errorCode + "\nError: " + errorMessage);
        }

        @Override
        public void onAdReady(VideoAdResponse videoAdResponse) {
            mShowVideoAdButton.setEnabled(true);
            showToast("Video Ad is ready to play");
        }

        @Override
        public void onVideoAdStarted() {
            // Ad Started
        }

        @Override
        public void onVideoAdClicked() {
            showToast("Video Ad clicked");
        }

        @Override
        public void onVideoAdFinished() {
            // Video ad finished
        }

        @Override
        public void onVideoPlayerClosed() {
            // Video ad player is closed
        }

    };

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void loadVideoAdButtonClicked(View view) {
        mShowVideoAdButton.setEnabled(false);
        mVideoAd.loadAd();
    }

    public void showVideoAdButtonClicked(View view) {
        mVideoAd.showVideoAd();
    }

    public void startDataSdkButtonClicked(View view) {
        Zapr.start(mContext);
    }

}
