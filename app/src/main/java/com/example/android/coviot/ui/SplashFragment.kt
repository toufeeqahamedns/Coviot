package com.example.android.coviot.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.android.coviot.R
import com.example.android.coviot.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentSplashBinding>(
            inflater, R.layout.fragment_splash, container, false
        )

        // Delaying transition, nothing crazy here
        Handler(Looper.getMainLooper()).postDelayed({
            Navigation.findNavController(activity!!, this.id)
                .navigate(SplashFragmentDirections.actionSplashFragmentToChatFragment())
        }, 800)

        return binding.root
    }
}