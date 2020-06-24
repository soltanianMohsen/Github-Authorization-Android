package com.mydigipay.challenge.presentation.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.mydigipay.challenge.BuildConfig
import com.mydigipay.challenge.R

class MainFragment: Fragment() {

    companion object {
        const val CLIENT_ID = BuildConfig.CLIENT_ID
        const val CLIENT_SECRET = BuildConfig.CLIENT_SECRET
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main,container,false)

        view.findViewById<RelativeLayout>(R.id.authorize).setOnClickListener(View.OnClickListener {
            val direction = MainFragmentDirections.actionMainToOauth(clientId = CLIENT_ID,clientSecret = CLIENT_SECRET)
            NavHostFragment.findNavController(this@MainFragment).navigate(direction)
        })

        return view
    }

}