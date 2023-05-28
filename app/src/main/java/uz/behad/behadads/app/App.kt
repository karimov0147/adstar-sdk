package uz.behad.behadads.app

import android.app.Application
import uz.behad.Ads.networkPart.ConnectManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ConnectManager.initRetrofit(this.applicationContext)
//        BannerLoader.initBannerLoader("BN-5665566")
//        InterLoader.initBannerLoader("IN-nif7gipxt2tjlurt") eski add ishlidi faqat listenrlar yo
//        InterAd.initInterAd("IN-nif7gipxt2tjlurt")
    }
}