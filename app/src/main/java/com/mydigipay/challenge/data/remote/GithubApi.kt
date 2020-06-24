package com.mydigipay.challenge.data.remote

import com.mydigipay.challenge.data.model.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface GithubApi {

    @Headers("Accept:application/json")
    @POST("https://github.com/login/oauth/access_token")
    fun accessToken(
        @Body requestAccessToken: RequestAccessToken
    ): Deferred<ResponseAccessToken>

    @GET("search/users?q=repos:>1")
    suspend fun getUsersList(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): Response<GithubUsersModel>

    @GET("users/{username}")
    suspend fun getUserInfo(
        @Path("username") username: String
    ): Response<GithubUserModel>

    @GET("search/users")
    suspend fun getUsers(
        @Query("q") query: String
    ): Response<GithubUsersModel>

    @GET("users/{login}/repos")
    suspend fun getRepos(
        @Path("login") login: String?
    ): Response<List<GithubRepoModel>>

    @GET("repos/{user}/{repo}/branches")
    suspend fun getBranches(
        @Path("user") user: String?,
        @Path("repo") repo: String?
    ): Response<List<GithubBranchesModel>>

}