package com.mydigipay.challenge.presentation.ui.fragments.repo

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mydigipay.challenge.data.base.Status
import com.mydigipay.challenge.data.model.GithubRepoModel
import com.mydigipay.challenge.domain.repository.oauth.GithubApiClient
import com.mydigipay.challenge.utils.viewModel.SingleLiveEvent
import kotlinx.coroutines.launch

class RepoViewModel(private val githubApiClient: GithubApiClient) : ViewModel() {

    var reposData: SingleLiveEvent<List<GithubRepoModel>> = SingleLiveEvent()
    val isWaiting: ObservableField<Boolean> = ObservableField()
    val errorMessage: ObservableField<String> = ObservableField()

    init {
        isWaiting.set(false)
        errorMessage.set(null)
    }

    fun getRepos(login: String?) {
        isWaiting.set(true)
        viewModelScope.launch {
            val result = githubApiClient.getRepos(login)
            if (result.status == Status.SUCCESS) {
                reposData.value = result.data
                errorMessage.set(null)

            } else {
                reposData.value = null
                errorMessage.set(result.message)
            }

            isWaiting.set(false)
        }
    }

}
