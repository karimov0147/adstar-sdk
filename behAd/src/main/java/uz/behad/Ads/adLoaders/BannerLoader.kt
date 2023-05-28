package uz.behad.behadads.adLoaders

import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.Browser
import android.view.View
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsIntent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.behad.Ads.adLoaders.AdConstants
import uz.behad.Ads.networkPart.ConnectManager
import uz.behad.Ads.responses.AdResponse
import uz.behad.Ads.responses.StatusResponse


class BannerLoader(private val bannerId: String) {

    private val api = ConnectManager.getAdsApi()

    companion object {
        private var bannerLoader: BannerLoader? = null

        fun initBannerLoader(bannerId: String) {
            bannerLoader = BannerLoader(bannerId)
        }

        fun getInstance() = bannerLoader!!
    }


    fun loadBanner(imageView: ImageView) {
        api.getAd(AdConstants.deviceId, bannerId, AdConstants.BANNER_TYPE)
            .enqueue(object : Callback<StatusResponse<AdResponse>> {
                override fun onResponse(
                    call: Call<StatusResponse<AdResponse>>,
                    response: Response<StatusResponse<AdResponse>>
                ) {
                    if (response.isSuccessful) {
                        setBanner(response.body()?.data ?: return, imageView)
                    }
                }

                override fun onFailure(call: Call<StatusResponse<AdResponse>>, t: Throwable) {

                }

            })
    }

    private fun setBanner(data: AdResponse, imageView: ImageView) {
        Glide.with(imageView)
            .load(data.link)
            .override(Resources.getSystem().displayMetrics.widthPixels, dpToPx(60))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean = false


                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageView.visibility = View.VISIBLE
                    onClickBanner(data, imageView)
                    return false
                }

            })
            .into(imageView)
    }

    private fun onClickBanner(data: AdResponse, view: ImageView) {
        view.setOnClickListener {
            val urlString =
                "https://redirect.adstar.uz/redirect/${data.campaignId}/${bannerId}/${AdConstants.deviceId}"
            val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
            val customTabsIntent: CustomTabsIntent = builder.build()
            builder.setShowTitle(true)
            val headers = Bundle()
            customTabsIntent.intent.putExtra(Browser.EXTRA_HEADERS, headers)
            customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            customTabsIntent.launchUrl(view.context, Uri.parse(urlString))
        }
    }


    private fun dpToPx(dp: Int): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()


}