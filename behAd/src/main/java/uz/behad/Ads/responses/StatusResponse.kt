package uz.behad.Ads.responses

import com.google.gson.annotations.SerializedName

data class StatusResponse<T>(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T
)