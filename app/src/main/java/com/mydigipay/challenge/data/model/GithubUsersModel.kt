package com.mydigipay.challenge.data.model

import com.google.gson.annotations.SerializedName

data class GithubUsersModel(
    @SerializedName("total_count") var totalCount: Long,
    @SerializedName("incomplete_results") var incompleteResults: Boolean,
    @SerializedName("items") var items: List<GithubUserModel>
)