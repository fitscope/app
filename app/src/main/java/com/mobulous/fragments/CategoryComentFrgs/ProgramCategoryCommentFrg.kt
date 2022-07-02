package com.mobulous.fragments.CategoryComentFrgs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.DialogAddToFavLayBinding
import com.mobulous.fitscope.databinding.ProgramViewAllFragmentBinding
import com.mobulous.fragments.dashboardBottomNavFrgs.viewAllarry
import com.mobulous.helper.Constants
import com.mobulous.helper.Uitls
import com.mobulous.helper.loadNormalPhoto_Dimens400
import com.mobulous.helper.showMoreOption
import com.mobulous.pojo.homePojo
import com.mobulous.viewPagers.programVp.ProgramViewAll_VPAdptr


class ProgramCategoryCommentFrg : Fragment() {
    lateinit var bin: ProgramViewAllFragmentBinding
    //val args: HomeCategoryCommentFrgArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = ProgramViewAllFragmentBinding.inflate(layoutInflater)
        initview()
        listrn()

        return bin.root
    }


    fun initview() {
        bin.ViewAllToolbar.tvTitle.text = "args.titles"

        try {
            val itemList2 = Gson().fromJson<homePojo>(
                Constants.weightLossChaptersJson,
                homePojo::class.java
            )
            bin.viewAllTopBnnr.loadNormalPhoto_Dimens400(itemList2.parent_object[0].image_url)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        ProgramViewAll_VPAdptr(
            this.childFragmentManager, this
                .lifecycle
        ).apply {
            bin.viewAllViewpagr.adapter = this
//            bin.labViewpagr.offscreenPageLimit = 8
            bin.viewAllViewpagr.isUserInputEnabled = false
            TabLayoutMediator(bin.viewallTabLay, bin.viewAllViewpagr) { tab, position ->
                tab.text = viewAllarry[position]
            }.attach()
        }

    }

    fun listrn() {
        bin.ViewAllToolbar.ivback.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainLogo.visibility = View.GONE
        bin.ViewAllToolbar.ivDrawer.visibility = View.GONE
        bin.ViewAllToolbar.tvTitle.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainNotification.visibility = View.GONE
        bin.ViewAllToolbar.ivDots.visibility = View.VISIBLE
//      bin.ViewAllToolbar.tvTitle.text = "Himanshu"
        bin.ViewAllToolbar.ivback.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        bin.ViewAllToolbar.ivDots.setOnClickListener {
           requireContext().showMoreOption(Constants.isAlreadyAddedToSave,Constants.isAlreadyAddedToFav)
//            BottomSheetDialog(
//                this.requireContext(),
//                R.style.NewCustomBottomSheetDialogTheme
//            ).apply {
//                val btnView =
//                    DialogAddToFavLayBinding.inflate(LayoutInflater.from(this@ProgramCategoryCommentFrg.requireContext()))
//                setCanceledOnTouchOutside(true)
//                setContentView(btnView.root)
//
//                btnView.tvAddtoFavDialogAddTpFavItem.setOnClickListener {
//                    dismiss()
//                }
//
//                btnView.tvAddtoMyListDialogAddtoFavItem.setOnClickListener {
//                    dismiss()
//
//                }
//                btnView.tvCancel.setOnClickListener {
//                    dismiss()
//
//                }
//                show()
//            }
        }

    }

}