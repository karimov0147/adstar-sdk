package uz.behad.Ads.responses

import com.google.gson.annotations.SerializedName

data class AdResponse(
    @SerializedName("campaign_id")
    val campaignId: String,
    @SerializedName("advertisement_media_type")
    val mediaType: String,
    @SerializedName("advertisement_link")
    val link: String,
    @SerializedName("advertisement_action_text")
    val actionText: String
)