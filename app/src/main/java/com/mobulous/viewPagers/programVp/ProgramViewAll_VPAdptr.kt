package com.mobulous.viewPagers.programVp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mobulous.fragments.viewAllFrgs.CategoriesFrg
import com.mobulous.fragments.viewAllFrgs.CommentsFrg
import com.mobulous.fragments.viewAllFrgs.WeightLossCategoriesFrg
import com.mobulous.helper.Constants

class ProgramViewAll_VPAdptr(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return Constants.VIEWALL_NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return WeightLossCategoriesFrg()
        }
        return CommentsFrg()
    }
}