package com.mydigipay.challenge.presentation.ui.fragments.search


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.RxTextView
import com.mydigipay.challenge.R
import com.mydigipay.challenge.databinding.LayoutSearchBinding
import hideKeyboard
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import runAnimation
import showKeyboard
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment(), SearchAdapter.SearchAdapterInteraction {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var binding: LayoutSearchBinding
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding =  DataBindingUtil.inflate(inflater, R.layout.layout_search, container, false)
        this.binding.viewModel = searchViewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { showKeyboard(it) }
        initRecyclerView()
        observeData()
        textWatcher()
    }

    private fun initRecyclerView(){
        this.searchAdapter = SearchAdapter(this)
        this.binding.rvMain.adapter = searchAdapter
        this.binding.rvMain.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

    }

    private fun observeData(){
        this.searchViewModel.usersData.observe(viewLifecycleOwner, Observer {
            searchAdapter.updateUsers(it.items)
            this.binding.rvMain.runAnimation()
        })
    }

    @SuppressLint("CheckResult")
    private fun textWatcher(){
        RxTextView.textChanges(binding.edtSearch)
            .map { it.toString() }
            .debounce(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isEmpty()) {
                    this.binding.rvMain.visibility = View.GONE
                    this.binding.llLbl.visibility = View.VISIBLE
                } else {
                    this.searchViewModel.getUsersByUsername(it)
                    this.binding.rvMain.visibility = View.VISIBLE
                    this.binding.llLbl.visibility = View.GONE
                }
            }, {
                it.printStackTrace()
            })

    }

    override fun onUserItemClick(login: String) {
        activity?.let { hideKeyboard(it,binding.edtSearch) }
        val direction = SearchFragmentDirections.actionSearchToRepo(login)
        NavHostFragment.findNavController(this@SearchFragment).navigate(direction)
    }



}
