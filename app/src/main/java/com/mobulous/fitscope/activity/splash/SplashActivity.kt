package com.mobulous.fitscope.activity.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.mobulous.BaseAc
import com.mobulous.fitscope.R
import com.mobulous.fitscope.activity.main.MainActivity
import com.mobulous.fitscope.activity.walkthrough.WalkthroughActivity
import com.mobulous.fitscope.databinding.ActivitySplashBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.Enums
import com.mobulous.helper.PrefUtils
import com.mobulous.webservices.ApiConstants

class SplashActivity : BaseAc<ActivitySplashBinding>() {
    override fun getViewBinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    override fun listnr() {

    }

    override fun initViews() {
        Constants.isUserLogined =
            PrefUtils.with(this).getString(Enums.isLogin.toString(), "false") == "true"
        if (!Constants.isUserLogined) splashNextIntent(WalkthroughActivity::class.java) else splashNextIntent(
            MainActivity::class.java
        )
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("OnAccessTokenRev", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            ApiConstants.AccessToken = task.result
            Log.e("firebaseDeviceToken", task.result.toString())
        })
    }

    override fun observers() {

    }

    private fun <A> splashNextIntent(activity: Class<A>) {
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this@SplashActivity, activity))
            finish()
        }, 3000)
    }
}