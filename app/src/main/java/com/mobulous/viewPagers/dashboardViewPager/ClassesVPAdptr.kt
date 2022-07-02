package com.mobulous.viewPagers.dashboardViewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mobulous.fragments.classesFrgs.*
import com.mobulous.helper.Constants

class ClassesVPAdptr(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return Constants.CLASSES_NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> return ClassesHomeFrg()
            1 -> return BikesFrg()
            2 -> return EllipticalsFrg()
            3 -> return RowerFrg()
            4-> return TreadmillFrg()
            else -> OnTheFloorFrg()
        }

    }




}