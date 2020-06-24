package com.mydigipay.challenge.presentation.ui.fragments.repo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mydigipay.challenge.R
import com.mydigipay.challenge.databinding.LayoutRepoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import runAnimation

class RepoFragment : Fragment(), RepoAdapter.RepoAdapterInteraction {

    private val repoViewModel: RepoViewModel by viewModel()

    private lateinit var binding: LayoutRepoBinding
    private lateinit var repoAdapter: RepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_repo, container, false)
        binding.viewModel = repoViewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments()
        initRecyclerView()
        observeData()
    }



    private fun arguments(){
        var login: String
        arguments?.let {
            login = RepoFragmentArgs.fromBundle(it) .login
            repoViewModel.getRepos(login)
        } ?: run {
            login = " "
            repoViewModel.getRepos(login)
        }
    }

    private fun initRecyclerView() {
        repoAdapter = RepoAdapter(this)
        binding.rvMain.adapter = repoAdapter
        binding.rvMain.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

    }

    private fun observeData() {
        repoViewModel.reposData.observe(viewLifecycleOwner, Observer {
           if(it != null){
               if(it.isEmpty()) {
                   binding.llLbl.visibility = View.VISIBLE
               }else {
                   binding.llLbl.visibility = View.GONE
                   repoAdapter.updateRepo(it)
                   binding.rvMain.runAnimation()
               }

           }else
               binding.llLbl.visibility = View.VISIBLE

        })
    }


    override fun onRepoItemClick(name: String, repo: String) {
       val direction = RepoFragmentDirections.actionRepoToBranches(name,repo)
        NavHostFragment.findNavController(this@RepoFragment).navigate(direction)
    }

}
