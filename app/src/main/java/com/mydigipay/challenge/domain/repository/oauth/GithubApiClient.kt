package com.mydigipay.challenge.domain.repository.oauth

import com.mydigipay.challenge.data.base.Resource
import com.mydigipay.challenge.data.model.*
import kotlinx.coroutines.Deferred

interface GithubApiClient {

    fun accessToken(requestAccessToken: RequestAccessToken): Deferred<ResponseAccessToken>

    suspend fun getUsersList(page: Int, pageSize: Int): Resource<GithubUsersModel>

    suspend fun getUserInfo(username: String): Resource<GithubUserModel>

    suspend fun getUsers(username: String): Resource<GithubUsersModel>

    suspend fun getRepos(login: String?): Resource<List<GithubRepoModel>>

    suspend fun getBranches(name: String?,repo: String?): Resource<List<GithubBranchesModel>>
}