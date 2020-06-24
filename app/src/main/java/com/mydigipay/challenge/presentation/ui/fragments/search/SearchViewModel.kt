package com.mydigipay.challenge.presentation.ui.fragments.search

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mydigipay.challenge.data.base.Status
import com.mydigipay.challenge.data.model.GithubUsersModel
import com.mydigipay.challenge.domain.repository.oauth.GithubApiClient
import com.mydigipay.challenge.utils.viewModel.SingleLiveEvent
import kotlinx.coroutines.launch

class SearchViewModel(private val githubApiClient: GithubApiClient) : ViewModel() {

    var usersData: SingleLiveEvent<GithubUsersModel> = SingleLiveEvent()
    val isWaiting: ObservableField<Boolean> = ObservableField()
    val errorMessage: ObservableField<String> = ObservableField()

    init {
        isWaiting.set(false)
        errorMessage.set(null)
    }

    fun getUsersByUsername(query: String) {
        isWaiting.set(true)
        viewModelScope.launch {
            val result = githubApiClient.getUsers(query)
            if (result.status == Status.SUCCESS) {
                usersData.value = result.data
                errorMessage.set(null)

            } else {
                usersData.value = null
                errorMessage.set(result.message)
            }

            isWaiting.set(false)
        }
    }

}
