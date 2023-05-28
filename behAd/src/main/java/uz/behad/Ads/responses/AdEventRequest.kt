package uz.behad.Ads.responses

import com.google.gson.annotations.SerializedName

data class AdEventRequest(
    @SerializedName("app_ads_id")
    val adId: String,
    @SerializedName("campaign_id")
    val campaignId: String,
    @SerializedName("device_id")
    val deviceId: String,
    @SerializedName("action")
    val action: Int = 2
)