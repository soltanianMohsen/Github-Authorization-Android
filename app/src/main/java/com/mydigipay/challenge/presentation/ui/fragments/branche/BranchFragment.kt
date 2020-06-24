package com.mydigipay.challenge.presentation.ui.fragments.branche


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mydigipay.challenge.R
import com.mydigipay.challenge.databinding.LayoutBranchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import runAnimation

class BranchFragment : Fragment(), BranchAdapter.BranchAdapterInteraction {

    private val branchViewModel: BranchViewModel by viewModel()

    private lateinit var binding: LayoutBranchBinding
    private lateinit var branchAdapter: BranchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_branch, container, false)
        binding.viewModel = branchViewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments()
        initRecyclerView()
        observeData()
    }

    private fun arguments(){
        var name: String
        var repo: String
        arguments?.let {
            name = BranchFragmentArgs.fromBundle(it) .name
            repo = BranchFragmentArgs.fromBundle(it) .repo
            branchViewModel.getBranches(name,repo)
        } ?: run {
            name = " "
            repo = " "
            branchViewModel.getBranches(name,repo)
        }
    }

    private fun initRecyclerView() {
        branchAdapter = BranchAdapter(this)
        binding.rvMain.adapter = branchAdapter
        binding.rvMain.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

    }

    private fun observeData() {
        branchViewModel.branchesData.observe(viewLifecycleOwner, Observer {
           if(it != null){
               if(it.isEmpty()) {
                   binding.llLbl.visibility = View.VISIBLE
               }else {
                   binding.llLbl.visibility = View.GONE
                   branchAdapter.updateBranches(it)
                   binding.rvMain.runAnimation()
               }

           }else
               binding.llLbl.visibility = View.VISIBLE

        })
    }


    override fun onBranchItemClick(url: String) {
        openInBrowser(url)
    }

    private fun openInBrowser(pageUrl: String?) {
        pageUrl?.let {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
        }
    }

}
