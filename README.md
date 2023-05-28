# behad-adstar

Behad advertisement (simple)

 how to add to project : 
 
 Step 1. Add the JitPack repository to your build file

 Add it in your root settings.gradle at the end of repositories:

	 allprojects {
		 repositories {
		 	 ...
		  	maven { url 'https://jitpack.io' }
		 }
	 }
  
   Step 2. Add the dependency

	 dependencies {
	        implementation 'com.github.karimov0147:adstar-sdk:1.0.0'
	 }
	 
	 
# First you need to add these codes
	 
In App class add this code: 

	override fun onCreate() {
		super.onCreate()
		ConnectManager.initRetrofit(this.applicationContext)
		...
	}
	 
 And in Main Activity : 
 
  	override fun onCreate(savedInstanceState: Bundle?) {
        	super.onCreate(savedInstanceState)
        	setContentView(R.layout.activity_main)
 	 	AdConstants.setDeviceId("DEVICE_ID")
		...
	}
	 
# Banner

Step 1. Add to app class this code : 

	override fun onCreate() {
		super.onCreate()
		ConnectManager.initRetrofit(this.applicationContext)
		BannerLoader.initBannerLoader("BANNER_ID")
	}
	
Step 2. In your xml file  : 

 	 <ImageView
         android:id="@+id/banner"
         android:layout_width="match_parent"
         android:layout_height="60dp"
         android:adjustViewBounds="true"
         android:background="@color/white"
         android:visibility="gone"
         app:layout_constraintBottom_toBottomOf="parent"
         app:layout_constraintEnd_toEndOf="parent"
         app:layout_constraintStart_toStartOf="parent" />
	 
Step 3. in kotlin or java file (inside onCreate) : 

	 BannerLoader.getInstance().loadBanner(findViewById(R.id.banner))
	 
# Interstitial

Step 1. Create global objects in your activity : 

	
	class MainActivity : AppCompatActivity() {
	...
   		val adsApi = ConnectManager.getAdsApi()
    		var adsBuilder: InterAdsBuilder? = null
	...
	
Step 2. Write this function in your activity : 

	private fun load(){
       		 InterAdsBuilder.load(Context , "INTERSTIAL_ID" , adsApi , object  : InterLoadCallback {
            		override fun onAdFailedToLoad() {
                		adsBuilder = null
            		}

            		override fun onAdLoaded(interAdsBuilder: InterAdsBuilder) {
               		 	adsBuilder = interAdsBuilder
            		}
        	})
    }
	
Step 3. In some action you can write the following code	for show interstial ad (example on click button) : 

	button.setOnClickListener {
            adsBuilder?.show(object : OnCancelClickedListener {
          		override fun onCancelClicked() {
                   		load()
			}
		})
	}
		
Step 4. And don't forget to revise load() once in oncreate() so that it loads ads for the first time 

	override fun onCreate(savedInstanceState: Bundle?) {
        	super.onCreate(savedInstanceState)
        	setContentView(R.layout.activity_main)
		...
		load()
		...
	}
	 
	 

