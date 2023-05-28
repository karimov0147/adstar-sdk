package uz.behad.Ads.InterDialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnShowListener
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Browser
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.devbrackets.android.exomedia.ui.widget.VideoView
import com.google.android.material.progressindicator.CircularProgressIndicator
import uz.behad.Ads.InterCallBack.InterLoadCallback
import uz.behad.Ads.InterCallBack.OnCancelClickedListener
import uz.behad.Ads.R
import uz.behad.Ads.adLoaders.AdConstants
import uz.behad.Ads.adLoaders.AdConstants.invisible
import uz.behad.Ads.adLoaders.AdConstants.visible
import uz.behad.Ads.adLoaders.InterAdsBuilder
import uz.behad.Ads.networkPart.CallbacksSender
import uz.behad.Ads.responses.AdResponse

class AdDialog(
    context: Context,
    val data: AdResponse,
    var interLoadCallback: InterLoadCallback,
    var interAdsBuilder: InterAdsBuilder,
    private var interId: String
) : Dialog(context, R.style.DialogTheme), OnShowListener {

    private var cancelClickedListener: OnCancelClickedListener? = null
    private var videoView: VideoView? = null
    private var timer: CountDownTimer? = null


    override fun onStart() {
        super.onStart()
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
    }

    fun setOnCancelListener(l: OnCancelClickedListener) {
        cancelClickedListener = l
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnShowListener(this)
        when (data.mediaType.lowercase()) {
            "video" -> {
                val view = View.inflate(context, R.layout.dialog_video, null)
                setContentView(view)
                setUpVideoIntersType(view)
            }
            "image" -> {
                val view = View.inflate(context, R.layout.dialog_image, null)
                setContentView(view)
                setUpImageIntersType(view)
            }
            "gif" -> {
                val view = View.inflate(context, R.layout.dialog_image, null)
                setContentView(view)
                setUpImageIntersType(view)
            }
            "website" -> {
                val view = View.inflate(context, R.layout.dialog_webview, null)
                setContentView(view)
                setUpWebSiteIntersType(view)
            }
            "webview" -> {
                val view = View.inflate(context, R.layout.dialog_webview, null)
                setContentView(view)
                setUpWebViewIntersType(view)
            }
            "app" -> {
                val view = View.inflate(context, R.layout.dialog_webview, null)
                setContentView(view)
                setUpApplicationIntersType(view)
            }
            else -> {
                return
            }
        }
    }

    private fun setUpImageIntersType(view: View) {
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val closeBtn = view.findViewById<AppCompatButton>(R.id.cancelBtn)
        val actionBtn = view.findViewById<CardView>(R.id.installBtn)
        val actionBtnText = view.findViewById<TextView>(R.id.installBtnText)
        actionBtnText.text = data.actionText
        Glide.with(imageView)
            .load(data.link)
            .override(
                Resources.getSystem().displayMetrics.widthPixels,
                Resources.getSystem().displayMetrics.widthPixels
            )
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
                    interLoadCallback.onAdLoaded(interAdsBuilder)
                    return false
                }
            })
            .into(imageView)

        closeBtn.setOnClickListener {
            dismiss()
            if (cancelClickedListener != null) if (cancelClickedListener != null) cancelClickedListener?.onCancelClicked()
        }

        imageView.setOnClickListener { onClickAdvertisement(data, view); dismiss() }
        actionBtn.setOnClickListener { onClickAdvertisement(data, view); dismiss() }


    }

    private fun setUpVideoIntersType(view: View) {
//        val videoView: VideoView
        val cancelBtn: AppCompatButton
        val progressBar: CircularProgressIndicator
        val counter: TextView
        val actionBtn: CardView
        val videoLayout: LinearLayout
        val actionBtnText: TextView
        view.apply {
            cancelBtn = findViewById<AppCompatButton>(R.id.cancelBtn).apply { invisible() }
            videoView = findViewById<VideoView>(R.id.videoView)
            progressBar = findViewById<CircularProgressIndicator>(R.id.progress_circular)
            counter = findViewById<TextView>(R.id.counter)
            videoLayout = findViewById<LinearLayout>(R.id.videoLayout)
            actionBtn = findViewById<CardView>(R.id.installBtn)
            actionBtnText = findViewById(R.id.installBtnText)
        }

        timer = object : CountDownTimer(6000, 1000) {
            override fun onTick(sec: Long) {
                progressBar.progress = 6 - (sec / 1000).toInt()
                Log.d("timer_jalap", "onTick: $sec ")
                counter.text = (sec / 1000).toString()
            }

            override fun onFinish() {
                counter.invisible()
                progressBar.invisible()
                cancelBtn.visible()
            }
        }
        videoView?.setVideoPath(data.link)
        videoView?.setOnPreparedListener {
//            Toast.makeText(view.context, "video started", Toast.LENGTH_SHORT).show()
//
            interLoadCallback.onAdLoaded(interAdsBuilder)
        }

        actionBtnText.text = data.actionText

        cancelBtn.setOnClickListener {
            dismiss()
            if (cancelClickedListener != null) if (cancelClickedListener != null) cancelClickedListener?.onCancelClicked()
        }

        videoView?.setOnClickListener { onClickAdvertisement(data, view); dismiss() }
        videoLayout.setOnClickListener { onClickAdvertisement(data, view); dismiss() }
        actionBtn.setOnClickListener { onClickAdvertisement(data, view); dismiss() }


    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    private fun setUpWebSiteIntersType(view: View) {
        val webView: WebView
        val cancelBtn: AppCompatButton
        view.apply {
            webView = findViewById<WebView>(R.id.webView).apply { visible() }
            cancelBtn = findViewById(R.id.cancelBtn)
        }


        webView.loadUrl(data.link)
        interLoadCallback.onAdLoaded(interAdsBuilder)
        webView.settings.javaScriptEnabled = true
        cancelBtn.setOnClickListener {
            dismiss()
            if (cancelClickedListener != null) if (cancelClickedListener != null) cancelClickedListener?.onCancelClicked()
        }
        webView.setOnTouchListener { _, _ ->
            onClickAdvertisement(data, view); dismiss()
            false
        }
    }

    @SuppressLint("ClickableViewAccessibility", "SetJavaScriptEnabled")
    private fun setUpWebViewIntersType(view: View) {
        val webView: WebView
        val cancelBtn: AppCompatButton
        view.apply {
            webView = findViewById<WebView>(R.id.webView).apply { visible() }
            cancelBtn = findViewById(R.id.cancelBtn)
        }


        webView.loadData(data.link, null, null)
        interLoadCallback.onAdLoaded(interAdsBuilder)
        webView.settings.javaScriptEnabled = true
        cancelBtn.setOnClickListener {
            dismiss()
            if (cancelClickedListener != null) cancelClickedListener?.onCancelClicked()
        }
        webView.setOnTouchListener { _, _ ->
            onClickAdvertisement(data, view); dismiss()
            false
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpApplicationIntersType(view: View) {
        val webView: WebView
        val cancelBtn: AppCompatButton
        view.apply {
            webView = findViewById<WebView>(R.id.webView).apply { visible() }
            cancelBtn = findViewById(R.id.cancelBtn)
        }
//        webView.loadDataWithBaseURL(null  ,"https://site.adstar.uz/${data.link}/${data.campaignId}/${interId}/${AdConstants.deviceId}", "text/html", "UTF-8" , null)
        webView.loadUrl("https://site.adstar.uz/${data.link}/${data.campaignId}/${interId}/${AdConstants.deviceId}")
        interLoadCallback.onAdLoaded(interAdsBuilder)
        webView.settings.javaScriptEnabled = true
        cancelBtn.setOnClickListener {
            dismiss()
            if (cancelClickedListener != null) cancelClickedListener?.onCancelClicked()
        }
    }

    private fun onClickAdvertisement(data: AdResponse, view: View) {
        if (cancelClickedListener != null) cancelClickedListener?.onCancelClicked()
        val urlString =
            "https://redirect.adstar.uz/redirect/${data.campaignId}/${interId}/${AdConstants.deviceId}"
        val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build()
        builder.setShowTitle(true)
        val headers = Bundle()
        customTabsIntent.intent.putExtra(Browser.EXTRA_HEADERS, headers)
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        customTabsIntent.launchUrl(view.context, Uri.parse(urlString))
    }

    override fun onShow(dialog: DialogInterface?) {
        sendClickEventToBackEnd(data.campaignId)
        if (timer != null && videoView != null) {
            videoView!!.start()
            timer!!.start()
        }

    }

    private fun sendClickEventToBackEnd(campaignId: String) {
        CallbacksSender.sendCallViewBack(interId, campaignId)
    }


}