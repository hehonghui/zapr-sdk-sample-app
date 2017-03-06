package com.redbricklane.zapr.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.redbricklane.zapr.bannersdk.ZaprBannerAd;
import com.redbricklane.zapr.bannersdk.ZaprBannerAdEventListener;
import com.redbricklane.zapr.bannersdk.ZaprInterstitialAd;
import com.redbricklane.zapr.bannersdk.ZaprInterstitialAdEventListener;
import com.redbricklane.zapr.basesdk.Log;
import com.redbricklane.zapr.basesdk.model.UserInfo;
import com.redbricklane.zapr.videosdk.ZaprVideoAd;
import com.redbricklane.zapr.videosdk.ZaprVideoAdEventListener;
import com.redbricklane.zapr.videosdk.net.VideoAdResponse;
import com.redbricklane.zaprSdkBase.Zapr;

public class ZaprSDK extends AppCompatActivity {

    private Context mContext = this;
    private Button mShowVideoAdButton;
    private Button mShowInterstitialButton;
    private ZaprBannerAd mBannerAd;
    private ZaprVideoAd mVideoAd;
    private ZaprInterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zapr_sdk);
        mShowVideoAdButton = (Button) findViewById(R.id.showVideoAdButton);
        mShowInterstitialButton = (Button) findViewById(R.id.showInterstitial);

        // Set log level in Zapr Ad SDK (Optional. Default log level is error)
        // Note: Logging should be disabled in production
        Log.setLogLevel(Log.LOG_LEVEL.debug);

        // Initialize Banner Ad
        mBannerAd = (ZaprBannerAd) findViewById(R.id.zaprAdView);
        if (mBannerAd != null) {
            // NOTE: This is test ad unit id. You should NOT use this id in your app
            mBannerAd.setAdUnitId("9d4cd9fc-91e4-4b10-9a0d-92ad872a3894");
            // Set listener for Banner ad events
            mBannerAd.setBannerAdEventListener(mBannerAdEventListener);
            // Ad Auto reload after every 30 sec
            mBannerAd.setAdRefreshTime(30);
            // Auto retry ad request if some error happens
            mBannerAd.enableAutoRetryOnError(true);

            // Add user information (optional)
            // Note: Add user info only if you have correct info. Else do not add any user info.
            mBannerAd.setUserInfo(new UserInfo(1990, "M"));
        }

        // Initialize Video Ad
        mVideoAd = new ZaprVideoAd(mContext);
        mVideoAd.setZaprVideoAdEventListener(mVideoAdEventListener);
        // NOTE: This is test ad unit id. You should NOT use this id in your app
        mVideoAd.setAdUnitId("b39b63f5-87a8-4390-8216-12a4fb713427");
        // Set optional video ad parameters in request
        mVideoAd.setMaxDuration(300); // Optional
        mVideoAd.setMinDuration(5); // Optional

        // Add user information (optional)
        // Note: Add user info only if you have correct info. Else do not add any user info.
        mVideoAd.setUserInfo(new UserInfo(1991, "F"));

        // Initialize Interstitial Ad
        mInterstitialAd = new ZaprInterstitialAd(mContext);
        // Set listener
        mInterstitialAd.setInterstitialAdEventListener(mInterstitialAdEventListener);
        // NOTE: This is test ad unit id. You should NOT use this id in your app
        mInterstitialAd.setAdUnitId("c548b3db-593e-4222-9684-cbd72f0832eb");

        // Add user information (optional)
        // Note: Add user info only if you have correct info. Else do not add any user info.
        mInterstitialAd.setUserInfo(new UserInfo(1992, "M"));

        // Optional: Change background color of interstitial ad
        mInterstitialAd.setInterstitialAdBackgroundColor(0xCC111111);

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
        if (mInterstitialAd != null) {
            mInterstitialAd.destroy();
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
        public void onResponseReceived(VideoAdResponse videoAdResponse) {
            // Ad response received.
            // No action required here unless you are using video ad cache hold functionality
        }

        @Override
        public void onAdReady(VideoAdResponse videoAdResponse, String vastXml) {
            // Video ad is ready to play
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
            mShowVideoAdButton.setEnabled(false);
        }

    };

    /**
     * Zapr Interstitial Ad Event Listener to get common Interstitial ad lifecycle callbacks
     */
    private ZaprInterstitialAdEventListener mInterstitialAdEventListener = new ZaprInterstitialAdEventListener() {
        @Override
        public void onInterstitialAdLoaded() {
            mShowInterstitialButton.setEnabled(true);
            showToast("Interstitial Ad is ready to be displayed");
        }

        @Override
        public void onInterstitialAdShown() {
            // interstitial ad is displayed on screen
        }

        @Override
        public void onInterstitialAdClicked() {
            showToast("Interstitial Ad is clicked");
        }

        @Override
        public void onInterstitialAdClosed() {
            mShowInterstitialButton.setEnabled(false);
        }

        @Override
        public void onFailedToLoadInterstitialAd(int errorCode, String errorMessage) {
            mShowInterstitialButton.setEnabled(false);
            showToast("ErrorCode: " + errorCode + "\nError: " + errorMessage);
        }
    };

    // Utility method to show toast
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    // Button click callbacks

    public void loadAdButtonClicked(View view) {
        if (mBannerAd != null) {
            mBannerAd.loadAd();
        }
    }

    public void loadVideoAdButtonClicked(View view) {
        mShowVideoAdButton.setEnabled(false);
        mVideoAd.loadAd();
    }

    public void showVideoAdButtonClicked(View view) {
        mVideoAd.showVideoAd();
    }

    public void loadInterstitialAdButtonClicked(View view) {
        mShowInterstitialButton.setEnabled(false);
        mInterstitialAd.loadInterstitialAd();
    }

    public void showInterstitialAdButtonClicked(View view) {
        mInterstitialAd.showInterstitialAd();
    }

    public void startDataSdkButtonClicked(View view) {
        // Optional
        // Enable policy dialog with your Privacy Policy link url
        Zapr.enablePolicyDialogWithPolicyLink("http://zapr.in/privacy/");

        // Optional
        // Enable policy dialog with Policy message
        //Zapr.enablePolicyDialogWithMessage("Policy terms and conditions text...");

        // Note: If policy dialog is enabled, then Zapr service will only start after getting acceptance from user.

        // Optional
        // Uncomment to disable Runtime permission check and permission request for Android M+ devices.
        // Note: Disable permission check only if you are explicitly requesting required permissions in your app.
        // Zapr.setRequestForPermissionsEnabled(false);

        // Start Zapr service
        Zapr.start(mContext);
    }
}
