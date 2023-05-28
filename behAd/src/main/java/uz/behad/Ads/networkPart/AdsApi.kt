package uz.behad.Ads.networkPart

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import uz.behad.Ads.responses.AdEventRequest
import uz.behad.Ads.responses.AdResponse
import uz.behad.Ads.responses.StatusResponse

interface AdsApi {

    @GET("/api/v1/filterAd")
    fun getAd(
        @Query("deviceId") deviceId: String,
        @Query("adId") adId: String,
        @Query("type") type: String,
    ): Call<StatusResponse<AdResponse>>

    @POST("/api/v1/addAction")
    fun sendEvent(
        @Body request: AdEventRequest
    ): Call<StatusResponse<Any>>

}