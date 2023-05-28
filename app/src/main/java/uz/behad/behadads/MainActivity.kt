package uz.behad.behadads

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import uz.behad.Ads.adLoaders.InterAdsBuilder
import uz.behad.Ads.networkPart.ConnectManager

class MainActivity : AppCompatActivity() {
    val adsApi = ConnectManager.getAdsApi()
    var adsBuilder: InterAdsBuilder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var button: AppCompatButton = findViewById<AppCompatButton>(R.id.reloadBtn)
//        AdConstants.setDeviceId("5b72ef1c-0c05-49f2-8dae-75adfc6d82a0")
//        BannerLoader.getInstance().loadBanner(findViewById(R.id.banner))
//        load()


//        button.setOnClickListener {
//            adsBuilder?.show(
//                object :OnCancelClickedListener{
//                override fun onCancelClicked() {
//                    load()
//                    Toast.makeText(this@MainActivity, "reload ishladi", Toast.LENGTH_SHORT).show()
//                }
//
//            }
//            )
////            load()
//        }


    }
//    private fun load(){
//        InterAdsBuilder.load(this , "IN-nif7gipxt2tjlurt" ,adsApi , object  : InterLoadCallback {
//            override fun onAdFailedToLoad() {
//                adsBuilder = null
//                Toast.makeText(this@MainActivity, "yuklanmadi network mavjud emas", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onAdLoaded(interAdsBuilder: InterAdsBuilder) {
//                adsBuilder = interAdsBuilder
//                Toast.makeText(this@MainActivity, "yuklandi button bosilsa korsatiladi", Toast.LENGTH_SHORT).show()
//            }
//
//        })
//    }


}