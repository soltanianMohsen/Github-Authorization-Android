package com.mydigipay.challenge.domain.repository.oauth

import com.mydigipay.challenge.data.base.Resource
import com.mydigipay.challenge.data.model.*
import com.mydigipay.challenge.data.remote.GithubApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubApiClientImpl(private val githubApi: GithubApi) : GithubApiClient {

    override fun accessToken(requestAccessToken: RequestAccessToken) = githubApi.accessToken(requestAccessToken)

    override suspend fun getUsersList(page: Int, pageSize: Int): Resource<GithubUsersModel> = withContext(
        Dispatchers.IO) {
        try {
            val response = githubApi.getUsersList(page, pageSize)
            if (response.isSuccessful) {
                Resource.success(response.body())

            } else {
                Resource.error(response.message())
            }
        } catch (ex: Throwable) {
            Resource.error<GithubUsersModel>("${ex.message}")
        }
    }

    override suspend fun getUserInfo(username: String): Resource<GithubUserModel> = withContext(
        Dispatchers.IO) {
        try {
            val response = githubApi.getUserInfo(username)
            if (response.isSuccessful) {
                Resource.success(response.body())

            } else {
                Resource.error(response.message())
            }
        } catch (ex: Throwable) {
            Resource.error<GithubUserModel>("${ex.message}")
        }
    }

    override suspend fun getUsers(username: String): Resource<GithubUsersModel> = withContext(
        Dispatchers.IO) {
        try {
            val response = githubApi.getUsers(username)
            if (response.isSuccessful) {
                Resource.success(response.body())

            } else {
                Resource.error(response.message())
            }
        } catch (ex: Throwable) {
            Resource.error<GithubUsersModel>("${ex.message}")
        }
    }


    override suspend fun getRepos(login: String?): Resource<List<GithubRepoModel>> = withContext(
        Dispatchers.IO) {
        try {
            val response = githubApi.getRepos(login)
            if (response.isSuccessful) {
                Resource.success(response.body())

            } else {
                Resource.error(response.message())
            }
        } catch (ex: Throwable) {
            Resource.error<List<GithubRepoModel>>("${ex.message}")
        }
    }

    override suspend fun getBranches(name: String?,repo: String?): Resource<List<GithubBranchesModel>> = withContext(
        Dispatchers.IO) {
        try {
            val response = githubApi.getBranches(name,repo)
            if (response.isSuccessful) {
                Resource.success(response.body())

            } else {
                Resource.error(response.message())
            }
        } catch (ex: Throwable) {
            Resource.error<List<GithubBranchesModel>>("${ex.message}")
        }
    }
}