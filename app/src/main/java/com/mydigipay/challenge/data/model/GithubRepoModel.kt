package com.mydigipay.challenge.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class GithubRepoModel(
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("language") var language: String,
    @SerializedName("html_url") var htmlUrl: String,
    @SerializedName("owner") var owner: GithubUserModel

)