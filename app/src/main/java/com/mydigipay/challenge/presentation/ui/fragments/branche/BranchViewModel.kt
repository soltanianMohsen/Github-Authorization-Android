package com.mydigipay.challenge.presentation.ui.fragments.branche

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mydigipay.challenge.data.base.Status
import com.mydigipay.challenge.data.model.GithubBranchesModel
import com.mydigipay.challenge.domain.repository.oauth.GithubApiClient
import com.mydigipay.challenge.utils.viewModel.SingleLiveEvent
import kotlinx.coroutines.launch

class BranchViewModel(private val githubApiClient: GithubApiClient) : ViewModel() {

    var branchesData: SingleLiveEvent<List<GithubBranchesModel>> = SingleLiveEvent()
    val isWaiting: ObservableField<Boolean> = ObservableField()
    val errorMessage: ObservableField<String> = ObservableField()

    init {
        isWaiting.set(false)
        errorMessage.set(null)
    }

    fun getBranches(name: String? , repo: String?) {
        isWaiting.set(true)
        viewModelScope.launch {
            val result = githubApiClient.getBranches(name,repo)
            if (result.status == Status.SUCCESS) {
                branchesData.value = result.data
                errorMessage.set(null)

            } else {
                branchesData.value = null
                errorMessage.set(result.message)
            }

            isWaiting.set(false)
        }
    }

}
