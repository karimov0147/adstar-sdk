package uz.behad.Ads.networkPart

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.behad.Ads.adLoaders.AdConstants
import uz.behad.Ads.responses.AdEventRequest
import uz.behad.Ads.responses.StatusResponse

object CallbacksSender {
    val api = ConnectManager.getAdsApi()
    fun sendCallViewBack(interId: String, campaignId: String) {
        api.sendEvent(AdEventRequest(interId, campaignId, AdConstants.deviceId)).enqueue(object :
            Callback<StatusResponse<Any>> {
            override fun onResponse(
                call: Call<StatusResponse<Any>>,
                response: Response<StatusResponse<Any>>
            ) {
            }

            override fun onFailure(call: Call<StatusResponse<Any>>, t: Throwable) {
            }
        })
    }
}