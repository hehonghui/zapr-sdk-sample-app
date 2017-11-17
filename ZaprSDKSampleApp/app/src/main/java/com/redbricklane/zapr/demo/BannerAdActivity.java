package com.redbricklane.zapr.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.redbricklane.zapr.bannersdk.ZaprBannerAd;
import com.redbricklane.zapr.bannersdk.ZaprBannerAdEventListener;
import com.redbricklane.zapr.basesdk.model.UserInfo;

/**
 * todo : Zapr sdk bugs
 *
 * BannerAdActivity will cause memory leak 100%. Please use LeakCanary and MAT to find out the reasons, and fix it.
 *
 * Created by mrsimple on 17/11/17.
 */

public class BannerAdActivity extends AppCompatActivity {

    private ZaprBannerAd mBannerAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ad);

        // Initialize Banner Ad
        mBannerAd = (ZaprBannerAd) findViewById(R.id.zapr_ad_view);
        if (mBannerAd != null) {
            // NOTE: This is test ad unit id. You should NOT use this id in your app
            mBannerAd.setAdUnitId("9d4cd9fc-91e4-4b10-9a0d-92ad872a3894");
            // Set listener for Banner ad events
            mBannerAd.setBannerAdEventListener(mBannerAdEventListener);
            // Ad Auto reload after every 30 sec
            mBannerAd.setAdRefreshTime(30);
            // Auto retry ad request if some error happens. Set false to disable auto retry
            mBannerAd.enableAutoRetryOnError(false);
            // Add user information (optional)
            // Note: Add user info only if you have correct info. Else do not add any user info.
            mBannerAd.setUserInfo(new UserInfo(1990, "M"));
            // load ad
            mBannerAd.loadAd();

            showToast("loading banner ads ");
        }
    }


    /**
     * Zapr Banner Ad Event Listener to get common Banner ad lifecycle callbacks
     */
    private ZaprBannerAdEventListener mBannerAdEventListener = new ZaprBannerAdEventListener() {
        @Override
        public void onBannerAdLoaded() {
            showToast("Banner Ad Loaded -> " + BannerAdActivity.this.getClass().getSimpleName());
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


    // Utility method to show toast
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( mBannerAd != null ) {
            mBannerAd.destroy();
        }
    }
}
