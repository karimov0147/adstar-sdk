package uz.behad.Ads.adLoaders

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.behad.Ads.InterCallBack.InterLoadCallback
import uz.behad.Ads.InterCallBack.OnCancelClickedListener
import uz.behad.Ads.InterDialog.AdDialog
import uz.behad.Ads.networkPart.AdsApi
import uz.behad.Ads.responses.AdResponse
import uz.behad.Ads.responses.StatusResponse

class InterAdsBuilder(private var dialog: AdDialog? = null) {

    companion object {
        var interAdsBuilder: InterAdsBuilder = InterAdsBuilder()

        fun load(
            context: Context,
            interId: String,
            adsApi: AdsApi,
            interLoadCallback: InterLoadCallback
        ) {
            adsApi.getAd(AdConstants.deviceId, interId, AdConstants.INTERS_TYPE)
                .enqueue(object : Callback<StatusResponse<AdResponse>> {
                    override fun onResponse(
                        call: Call<StatusResponse<AdResponse>>,
                        response: Response<StatusResponse<AdResponse>>
                    ) {
                        if (response.isSuccessful) {
                            interAdsBuilder.dialog = AdDialog(
                                context,
                                response.body()?.data ?: return,
                                interLoadCallback,
                                interAdsBuilder,
                                interId
                            )
                            interAdsBuilder.dialog?.create()
                        }
                    }

                    override fun onFailure(call: Call<StatusResponse<AdResponse>>, t: Throwable) {
                        interLoadCallback.onAdFailedToLoad()
                    }

                })
        }

    }

    fun show(onCancelClickedListener: OnCancelClickedListener? = null) {
        dialog?.show()
        if (onCancelClickedListener != null) dialog?.setOnCancelListener(onCancelClickedListener)
    }

}


