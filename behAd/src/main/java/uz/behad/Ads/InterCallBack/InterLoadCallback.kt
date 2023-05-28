package uz.behad.Ads.InterCallBack

import uz.behad.Ads.adLoaders.InterAdsBuilder

interface InterLoadCallback {
    fun onAdFailedToLoad()
    fun onAdLoaded(interAdsBuilder: InterAdsBuilder)
}