package com.mydigipay.challenge.github

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mydigipay.challenge.BuildConfig
import com.mydigipay.challenge.R
import com.mydigipay.challenge.dialog.WaitDialog
import com.mydigipay.challenge.github.oauth.GithubOauth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    companion object {
        const val CLIENT_ID = BuildConfig.CLIENT_ID
        const val CLIENT_SECRET = BuildConfig.CLIENT_SECRET
        const val APPLICATION_ID  = BuildConfig.APPLICATION_ID
        const val REDIRECT_URI  = BuildConfig.REDIRECT_URI
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var waitDialog = WaitDialog(this)

        authorize.setOnClickListener { view ->
            GithubOauth
                .Builder()
                .withClientId(CLIENT_ID)
                .withClientSecret(CLIENT_SECRET)
                .withContext(applicationContext)
                .packageName(APPLICATION_ID)
                .nextActivity("com.mydigipay.challenge.github.LoginUriActivity")
                .debug(true)
                .execute()
        }
    }
}
