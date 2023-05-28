package uz.behad.Ads.adLoaders

import android.view.View

object AdConstants {

    const val BANNER_TYPE = "banner"
    const val INTERS_TYPE = "inters"

    var deviceId: String = "null"

    @JvmName("setDeviceId1")
    fun setDeviceId(id: String) {
        deviceId = id
    }

//     "5b72ef1c-0c05-49f2-8dae-75adfc6d82a0"

//    fun View.gone() = run { visibility = View.GONE }

    fun View.visible() = run { visibility = View.VISIBLE }

    fun View.invisible() = run { visibility = View.INVISIBLE }


}