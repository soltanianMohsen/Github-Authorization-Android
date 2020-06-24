package com.mydigipay.challenge.presentation.di


import com.mydigipay.challenge.presentation.ui.fragments.branche.BranchViewModel
import com.mydigipay.challenge.presentation.ui.fragments.profile.ProfileViewModel
import com.mydigipay.challenge.presentation.ui.fragments.repo.RepoViewModel
import com.mydigipay.challenge.presentation.ui.fragments.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
    
val viewModels = module {
    viewModel { SearchViewModel(get()) }

    viewModel { ProfileViewModel(get()) }

    viewModel { RepoViewModel(get()) }

    viewModel { BranchViewModel(get()) }

}
