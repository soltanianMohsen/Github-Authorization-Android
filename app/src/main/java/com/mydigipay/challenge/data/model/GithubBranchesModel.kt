package com.mydigipay.challenge.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class GithubBranchesModel(
    @SerializedName("name") var name: String,
    @SerializedName("protected") var protected: Boolean,
    @SerializedName("commit") var commit: Commit

)

data class Commit(
    @SerializedName("sha") var sha: String,
    @SerializedName("url") var url: String
)
