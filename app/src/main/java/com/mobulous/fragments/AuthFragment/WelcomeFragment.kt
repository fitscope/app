package com.mobulous.fragments.AuthFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mobulous.fitscope.activity.main.MainActivity
import com.mobulous.fitscope.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    lateinit var bin: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentWelcomeBinding.inflate(layoutInflater)
        initView()
        lstrn()
        return bin.root
    }

    fun initView() {

    }

    fun lstrn() {
        bin.trlBtnLoginOptin.setOnClickListener {
            Navigation.findNavController(it).navigate(
                WelcomeFragmentDirections.actionWelcomeFragmentToSubscriptionFragment(
                    isFromWelcomeFrg = true
                )
            )
        }
        bin.tvTkLoolLoginOption.setOnClickListener {
            startActivity(
                Intent(
                    this.requireContext(),
                    MainActivity::class.java
                )
            )
            requireActivity().finish()
        }
        bin.tvAlreadySignLoginOption.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(
                    WelcomeFragmentDirections.actionWelcomeFragmentToSignInFragment(
                        isFromWelcomeFrg = true
                    )
                )
        }
    }


}