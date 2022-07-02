package com.mobulous.viewPagers.dashboardViewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mobulous.fragments.programFrgs.BootCampFrg
import com.mobulous.fragments.programFrgs.PrenatalFrg
import com.mobulous.fragments.programFrgs.SeniorWorkoutFrg
import com.mobulous.fragments.programFrgs.WeightLossFrg
import com.mobulous.helper.Constants

class ProgramsVPAdptr(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return Constants.PROGRAMS_NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return WeightLossFrg()
            1 -> return PrenatalFrg()
            2 -> return SeniorWorkoutFrg()
        }
        return BootCampFrg()
    }


}