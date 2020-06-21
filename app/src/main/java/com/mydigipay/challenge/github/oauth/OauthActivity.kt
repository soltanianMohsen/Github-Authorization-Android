package com.mydigipay.challenge.github.oauth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.mydigipay.challenge.R
import com.mydigipay.challenge.github.MainActivity
import com.mydigipay.challenge.network.oauth.RequestAccessToken
import com.mydigipay.challenge.repository.oauth.AccessTokenDataSource
import com.mydigipay.challenge.repository.token.TokenRepository
import kotlinx.android.synthetic.main.login_uri_activity.*
import kotlinx.coroutines.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import org.koin.android.ext.android.inject
import java.io.IOException

class OauthActivity : AppCompatActivity() {
    //private var spinner: ProgressBar? = null
    private val tokenRepository: TokenRepository by inject()
    private val accessTokenDataSource: AccessTokenDataSource by inject()
    private var webview: WebView? = null
    private val c: Class<*>? = null
    private var debug = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oauth)
        if (intent.extras != null) {
            CLIENT_ID = intent.getStringExtra("id")
            CLIENT_SECRET = intent.getStringExtra("secret")
            ACTIVITY_NAME = intent.getStringExtra("activity")
            debug = intent.getBooleanExtra("debug", false)
            PACKAGE = intent.getStringExtra("package")
        } else {
            Log.d(TAG, "intent extras null")
        }
        if (debug) Log.d(
            TAG,
            "intent recieved is -client id :$CLIENT_ID-secret:$CLIENT_SECRET-activit : $ACTIVITY_NAME-Package : $PACKAGE"
        )
        //        try {
//            c = Class.forName(ACTIVITY_NAME);
//        } catch (ClassNotFoundException exp) {
//            if (debug)Log.d(TAG, "error :" + exp.getMessage());
//        }
        //spinner = findViewById(R.id.progressBar) as ProgressBar
        //spinner!!.visibility = View.VISIBLE
        webview = findViewById(R.id.webview) as WebView
        webview!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean {
                super.shouldOverrideUrlLoading(view, url)
                CODE = url.substring(url.lastIndexOf("?code=") + 1)
                if (debug) Log.d(
                    TAG,
                    "code fetched is :$CODE"
                )
                val token_code =
                    CODE.split("=").toTypedArray()
                if (debug) Log.d(
                    TAG,
                    "code token :" + token_code[1]
                )
                val tokenFetchedIs = token_code[1]
                val cleanToken =
                    tokenFetchedIs.split("&").toTypedArray()
                if (debug) Log.d(
                    TAG,
                    "token cleaned is :" + cleanToken[0]
                )
                fetchOauthTokenWithCode(cleanToken[0])
                return false
            }
        }
        val url_load =
            "$GITHUB_URL?client_id=$CLIENT_ID"
        webview!!.loadUrl(url_load)
    }

    private fun fetchOauthTokenWithCode(code: String) {
        val client = OkHttpClient()
        val url = HttpUrl.parse(GITHUB_OAUTH)!!.newBuilder()
        url.addQueryParameter("client_id", CLIENT_ID)
        url.addQueryParameter("client_secret", CLIENT_SECRET)
        url.addQueryParameter("code", code)
        val url_oauth = url.build().toString()
        val request = Request.Builder()
            .header("Accept", "application/json")
            .url(url_oauth)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (debug) Log.d(
                    TAG,
                    "IOException :" + e.message
                )
            }

            @Throws(IOException::class)
            override fun onResponse(
                call: Call,
                response: Response
            ) {
                if (response.isSuccessful) {
                    val JsonData = response.body()!!.string()
                    if (debug) Log.d(
                        TAG,
                        "response is:$JsonData"
                    )
                    try {
                        val jsonObject = JSONObject(JsonData)
                        val auth_token = jsonObject.getString("access_token")
                        if (debug) Log.d(
                            TAG,
                            "token is :$auth_token"
                        )
                        storeToSharedPreference(auth_token)
                    } catch (exp: JSONException) {
                        if (debug) Log.d(
                            TAG,
                            "json exception :" + exp.message
                        )
                    }
                } else {
                    Log.d(
                        TAG,
                        "onResponse: not success : " + response.message()
                    )
                }
            }
        })
    }

    private fun storeToSharedPreference(auth_token: String) {
        val prefs = getSharedPreferences("github_prefs", MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString("oauth_token", auth_token)
        edit.commit()


//        val accessTokenJob = CoroutineScope(Dispatchers.IO).launch {
//            val response = accessTokenDataSource.accessToken(
//                RequestAccessToken(
//                    MainActivity.CLIENT_ID,
//                    MainActivity.CLIENT_SECRET,
//                    "",
//                    MainActivity.REDIRECT_URI,
//                    "0"
//                )
//            ).await()
//
//            tokenRepository.saveToken(response.accessToken).await()
//        }
//
//        accessTokenJob.invokeOnCompletion {
//            CoroutineScope(Dispatchers.Main).launch {
//                token.text = tokenRepository.readToken().await()
//                this.cancel()
//                accessTokenJob.cancelAndJoin()
//            }
//        }

        val intent = Intent()
        intent.setClassName(PACKAGE, ACTIVITY_NAME)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    companion object {
        const val GITHUB_URL = "https://github.com/login/oauth/authorize"
        const val GITHUB_OAUTH = "https://github.com/login/oauth/access_token"
        private const val TAG = "github-oauth"
        var CODE = ""
        var CLIENT_ID = ""
        var CLIENT_SECRET = ""
        var ACTIVITY_NAME = ""
        var PACKAGE = ""
    }
}
