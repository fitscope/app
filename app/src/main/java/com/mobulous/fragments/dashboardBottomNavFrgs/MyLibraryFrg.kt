package com.mobulous.fragments.dashboardBottomNavFrgs


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.mobulous.fitscope.databinding.FragmentMyLibraryFrgBinding
import com.mobulous.viewPagers.dashboardViewPager.LibraryVPAdptr

class MyLibraryFrg : Fragment() {
    lateinit var bin: FragmentMyLibraryFrgBinding
    val args: MyLibraryFrgArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentMyLibraryFrgBinding.inflate(layoutInflater)
        initViews()
        listnr()

        return bin.root
    }


    fun initViews() {
        LibraryVPAdptr(
            this.childFragmentManager, this
                .lifecycle
        ).apply {
            bin.viewpagr.adapter = this
            bin.viewpagr.isUserInputEnabled = false
//            bin.labViewpagr.offscreenPageLimit = 8
            bin.viewpagr.setCurrentItem(args.postion?.toInt() ?: 0, true)
            TabLayoutMediator(bin.tabLay, bin.viewpagr) { tab, position ->
                tab.text = arrayOf("Favorites", "My List", "Downloads")[position]
            }.attach()
        }
    }

    fun listnr() {
        bin.LibraryToolbar.lbl.text = "My Library"
        bin.LibraryToolbar.calSideMenuBtn.visibility = View.GONE

    }


}