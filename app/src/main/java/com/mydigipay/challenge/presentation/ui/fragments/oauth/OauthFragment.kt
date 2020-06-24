package com.mydigipay.challenge.presentation.ui.fragments.oauth

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.mydigipay.challenge.R
import com.mydigipay.challenge.domain.repository.oauth.GithubApiClient
import com.mydigipay.challenge.domain.repository.token.TokenRepository
import com.wang.avi.AVLoadingIndicatorView
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONException
import org.json.JSONObject
import org.koin.android.ext.android.inject
import java.io.IOException
import java.lang.Exception

class OauthFragment : Fragment() {
    //private var spinner: ProgressBar? = null
    private val tokenRepository: TokenRepository by inject()
    private val githubApiClient: GithubApiClient by inject()
    private var webview: WebView? = null
    private var avi: AVLoadingIndicatorView? = null
    private val c: Class<*>? = null
    private var debug = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_oauth,container,false)

        getArgument()
        webview = view.findViewById<WebView>(R.id.webview)
        avi = view.findViewById<AVLoadingIndicatorView>(R.id.avi)
        webview!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean {
                super.shouldOverrideUrlLoading(view, url)
                avi!!.visibility = View.GONE
                try{
                    CODE = url.substring(url.lastIndexOf("?code=") + 1)

                    val token_code =
                        CODE.split("=").toTypedArray()

                    val tokenFetchedIs = token_code[1]
                    val cleanToken =
                        tokenFetchedIs.split("&").toTypedArray()

                    fetchOauthTokenWithCode(cleanToken[0])
                }catch (e:Exception){
                    e.printStackTrace()
                }

                return false
            }
        }
        val url_load =
            "$GITHUB_URL?client_id=$CLIENT_ID"
        webview!!.loadUrl(url_load)


        return view
    }

    private fun getArgument(){
        arguments?.let {
            CLIENT_ID = OauthFragmentArgs.fromBundle(it).clientId
            CLIENT_SECRET = OauthFragmentArgs.fromBundle(it).clientSecret
        } ?: run {
            CLIENT_ID = " "
            CLIENT_SECRET = " "
            Log.e(TAG, "intent extras null")
        }
    }

    private fun fetchOauthTokenWithCode(code: String) {
        val client = OkHttpClient()
        val url = GITHUB_OAUTH.toHttpUrlOrNull()!!.newBuilder()
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
                    val JsonData = response.body!!.string()

                    try {
                        val jsonObject = JSONObject(JsonData)
                        val auth_token = jsonObject.getString("access_token")

                        storeToSharedPreference(auth_token)
                    } catch (exp: JSONException) {
                         Log.e(
                            TAG,
                            "json exception :" + exp.message)

                    }
                } else {
                    Log.e(
                        TAG,
                        "onResponse: not success : " + response.message
                    )
                }
            }
        })
    }

    private fun storeToSharedPreference(auth_token: String) {
        val prefs = activity?.getSharedPreferences("github_prefs", MODE_PRIVATE)
        val edit = prefs?.edit()
        edit?.putString("oauth_token", auth_token)
        edit?.apply()
         avi!!.visibility = View.GONE
        gotoProfileFragment()
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

    }

    private fun gotoProfileFragment(){
        try {
            NavHostFragment.findNavController(this@OauthFragment).navigate(R.id.action_oauth_to_profile)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    companion object {
        const val GITHUB_URL = "https://github.com/login/oauth/authorize"
        const val GITHUB_OAUTH = "https://github.com/login/oauth/access_token"
        private const val TAG = "github-oauth"
        var CODE = ""
        var CLIENT_ID = ""
        var CLIENT_SECRET = ""
    }
}
