package com.mydigipay.challenge.github.oauth

import android.content.Context
import android.content.Intent

class GithubOauth {
    var client_id: String? = null
    var client_secret: String? = null
    var nextActivity: String? = null
    var appContext: Context? = null
    var isDebug = false
    var packageName: String? = null

    fun withContext(context: Context?): GithubOauth {
        appContext = context
        return this
    }

    fun withClientId(client_id: String?): GithubOauth {
        this@GithubOauth.client_id = client_id
        return this
    }

    fun withClientSecret(client_secret: String?): GithubOauth {
        this@GithubOauth.client_secret = client_secret
        return this
    }

    fun nextActivity(activity: String?): GithubOauth {
        nextActivity = activity
        return this
    }

    fun debug(active: Boolean): GithubOauth {
        isDebug = active
        return this
    }

    fun packageName(packageName: String?): GithubOauth {
        this@GithubOauth.packageName = packageName
        return this
    }

    /**
     * This method will execute the instance created , client_id ,
     * client_secret , packageName and activity fully qualified is must
     */
    fun execute() {
        val intent = Intent(appContext, OauthActivity::class.java)
        intent.putExtra("id", client_id)
        intent.putExtra("secret", client_secret)
        intent.putExtra("debug", isDebug)
        intent.putExtra("package", packageName)
        intent.putExtra("activity", nextActivity)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        appContext!!.startActivity(intent)
    }

    companion object {
        fun Builder(): GithubOauth {
            return GithubOauth()
        }
    }
}