package uz.behad.Ads.networkPart

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConnectManager {
    companion object {

        private var retrofit: Retrofit? = null

        fun initRetrofit(context: Context) {
            val okHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(ChuckerInterceptor.Builder(context).build())
                .build()

            retrofit = Retrofit.Builder().baseUrl("https://ads.adstar.uz")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getAdsApi(): AdsApi = retrofit!!.create(AdsApi::class.java)
    }
}