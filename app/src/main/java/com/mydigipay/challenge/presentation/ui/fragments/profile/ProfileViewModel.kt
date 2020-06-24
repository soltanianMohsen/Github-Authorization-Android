package com.mydigipay.challenge.presentation.ui.fragments.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewModelScope
import com.mydigipay.challenge.data.base.Status
import com.mydigipay.challenge.data.model.GithubUserModel
import com.mydigipay.challenge.domain.repository.oauth.GithubApiClient
import com.mydigipay.challenge.utils.viewModel.SingleLiveEvent
import kotlinx.coroutines.launch

class ProfileViewModel(private val githubApiClient: GithubApiClient) : ViewModel() {

    val isWaiting: ObservableField<Boolean> = ObservableField()
    val errorMessage: ObservableField<String> = ObservableField()
    val githubUserModel: ObservableField<GithubUserModel> = ObservableField()
    val pageUrl: SingleLiveEvent<String> = SingleLiveEvent()

    init {
        isWaiting.set(true)
        errorMessage.set(null)
    }

    fun getUserInfoByUsername(username: String) {
        viewModelScope.launch {
            val result = githubApiClient.getUserInfo(username)
            if (result.status == Status.SUCCESS) {
                githubUserModel.set(result.data)
                errorMessage.set(null)

            } else {
                //githubUserModel.value = null
                errorMessage.set(result.message)
            }

            isWaiting.set(false)
        }
    }

    fun openInBrowser(pageUrl: String?) {
        this.pageUrl.value = pageUrl
    }


}
