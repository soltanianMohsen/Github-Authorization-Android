package com.mydigipay.challenge.presentation.ui.fragments.profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.content.Intent
import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.mydigipay.challenge.R

import com.mydigipay.challenge.databinding.LayoutProfileBinding

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModel()
    private lateinit var binding: LayoutProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.layout_profile, container, false)
        this.binding.viewModel = profileViewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.binding.lblsearch.setOnClickListener {
            gotoSearchFragment()
        }
        this.binding.lblLogout.setOnClickListener(View.OnClickListener {
            activity?.let { it.finish() }
        })
        observeData()
    }


    private fun observeData() {
        this.profileViewModel.getUserInfoByUsername("soltanianMohsen")
        this.profileViewModel.pageUrl.observe(viewLifecycleOwner, Observer {
                openInBrowser(it)

        })
    }


    private fun openInBrowser(pageUrl: String?) {
        pageUrl?.let {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
        }
    }

    object BindingLayoutUtils {
        @JvmStatic
        @BindingAdapter("avatar")
        fun loadAvatar(view: AppCompatImageView, imageUrl: String?) {
            imageUrl?.let {
                Glide.with(view.context)
                    .load(it)
                    .into(view)
            }
        }
    }


    private fun gotoSearchFragment() {
        NavHostFragment.findNavController(this@ProfileFragment)
            .navigate(R.id.action_profile_to_search)
    }
}
