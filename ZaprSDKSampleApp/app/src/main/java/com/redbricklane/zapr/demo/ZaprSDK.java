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
        // Note: Logging should be disabled in production
        Log.setLogLevel(Log.LOG_LEVEL.debug);

        // Initialize Banner Ad
        mBannerAd = (ZaprBannerAd) findViewById(R.id.zaprAdView);
        if (mBannerAd != null) {
            // NOTE: This is test ad unit id. You should NOT use this id in your app
            mBannerAd.setAdUnitId("9d4cd9fc-91e4-4b10-9a0d-92ad872a3894");
            mBannerAd.setBannerAdEventListener(mBannerAdEventListener);
            mBannerAd.setUserInfo(new UserInfo(1990, "M")); // Add user information
        }

        // Initialize Video Ad
        mVideoAd = new ZaprVideoAd(mContext);
        mVideoAd.setZaprVideoAdEventListener(mVideoAdEventListener);
        // NOTE: This is test ad unit id. You should NOT use this id in your app
        mVideoAd.setAdUnitId("b39b63f5-87a8-4390-8216-12a4fb713427");
        mVideoAd.setMaxDuration(300);
        mVideoAd.setMinDuration(5);
        mVideoAd.setUserInfo(new UserInfo(1990, "F")); // Add user information
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


    /**
     * Zapr Banner Ad Event Listener to get common Banner ad lifecycle callbacks
     */
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
            /*
             *You can take required actions here like hiding Banner ad view if necessary.
             * mBannerAd.setVisibility(View.GONE);
             */
        }
    };

    /**
     * Zapr Video Ad Event Listener to get common Video ad lifecycle callbacks
     */
    private ZaprVideoAdEventListener mVideoAdEventListener = new ZaprVideoAdEventListener() {
        @Override
        public void onVideoAdError(int errorCode, String errorMessage) {
            mShowVideoAdButton.setEnabled(false);
            showToast("ErrorCode: " + errorCode + "\nError: " + errorMessage);
        }

        @Override
        public void onAdReady(VideoAdResponse videoAdResponse, String vastXml) {
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
