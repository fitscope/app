package com.mobulous.fragments.AuthFragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentSubscriptionBinding
import com.mobulous.helper.Constants
import com.mobulous.listner.SubcriptionFromSideMenuListnr


class SubscriptionFragment : Fragment() {
    lateinit var bin: FragmentSubscriptionBinding
    lateinit var subscriptionFromSideMnyListnr: SubcriptionFromSideMenuListnr
    val args: SubscriptionFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentSubscriptionBinding.inflate(layoutInflater)
        initView()
        lstrn()
        return bin.root
    }

    fun initView() {

    }

    fun lstrn() {
        bin.constraintLayout.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(
                    SubscriptionFragmentDirections.actionSubscriptionFragmentToSignupFragment(
                        isFromWelcomeFrg = args.isFromWelcomeFrg
                    )
                )
        }
        bin.constraintLayout1.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(
                    SubscriptionFragmentDirections.actionSubscriptionFragmentToSignupFragment(
                        isFromWelcomeFrg = args.isFromWelcomeFrg
                    )
                )
        }
        bin.tvTermConditionSubScription.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TERMS_ND_CONDITION))
            startActivity(intent)
        }
        bin.tvPrivacyPolicySubScription.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.PRIVACY_URL))
            startActivity(intent)
        }
        bin.ivbackSubScription.setOnClickListener {
            if (args.isFromWelcomeFrg) {
                Navigation.findNavController(it).navigate(
                    SubscriptionFragmentDirections.actionSubscriptionFragmentToWelcomeFragment().actionId,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.welcomeFragment, true)
                        .build()
                )
            } else {
                subscriptionFromSideMnyListnr.onSubcriptionfromSideMenu()
            }

        }
        bin.tvContactUsSubscription.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.CONTACT_US_URL))
            startActivity(intent)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            subscriptionFromSideMnyListnr = activity as SubcriptionFromSideMenuListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }


}