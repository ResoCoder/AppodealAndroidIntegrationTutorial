package com.resocoder.appodealprep

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.appodeal.ads.Appodeal
import com.appodeal.ads.RewardedVideoCallbacks
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //change this app key
        val appKey = "64dba8c39bcae63c9d8529fe144186375774cbecccfd09a0"
        Appodeal.disableLocationPermissionCheck()
        Appodeal.setTesting(true)
        Appodeal.setBannerViewId(R.id.appodeal_banner_view)
        Appodeal.initialize(this, appKey, Appodeal.INTERSTITIAL or Appodeal.REWARDED_VIDEO
                or Appodeal.BANNER or Appodeal.NATIVE)

        //banners
        btn_show_banner.setOnClickListener {
            Appodeal.show(this, Appodeal.BANNER_VIEW)
        }

        btn_hide_banner.setOnClickListener {
            Appodeal.hide(this, Appodeal.BANNER)
        }

        //interstitials
        btn_show_interstitial.setOnClickListener {
            Appodeal.show(this, Appodeal.INTERSTITIAL)
        }

        //you can add callbacks to any ad format, not just Rewarded videos
        Appodeal.setRewardedVideoCallbacks(object : RewardedVideoCallbacks{
            override fun onRewardedVideoFinished(p0: Int, p1: String?) {
                showToastWithText("Rewarded Video Finished, added 30 coins to the inventory.")
            }

            override fun onRewardedVideoClosed(p0: Boolean) {
                showToastWithText("Rewarded Video Closed")
            }

            override fun onRewardedVideoLoaded() {
                showToastWithText("Rewarded Video Loaded")
            }

            override fun onRewardedVideoFailedToLoad() {
                showToastWithText("Rewarded Video Failed to Load")
            }

            override fun onRewardedVideoShown() {
                showToastWithText("Rewarded Video Shown")
            }

        })

        btn_show_rewarded.setOnClickListener {
            Appodeal.show(this, Appodeal.REWARDED_VIDEO)
        }

        //native ads
        Appodeal.cache(this, Appodeal.NATIVE)

        btn_show_native.setOnClickListener {
            val nativeAd = Appodeal.getNativeAds(1)[0]
            native_ad_view.setNativeAd(nativeAd)
            Appodeal.cache(this, Appodeal.NATIVE)
        }

        //you can also build a completely custom looking native ad
        //let's get the title of a native ad and display it
        btn_show_native_title.setOnClickListener {
            val nativeAd = Appodeal.getNativeAds(1)[0]
            textView_native_ad_title.text = nativeAd.title
            Appodeal.cache(this, Appodeal.NATIVE)
        }
    }

    public override fun onResume() {
        super.onResume()
        Appodeal.onResume(this, Appodeal.BANNER)
    }

    private fun showToastWithText(text: String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}
