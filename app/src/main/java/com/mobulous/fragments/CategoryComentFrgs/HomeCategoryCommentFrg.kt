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
import com.google.gson.reflect.TypeToken
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.DialogAddToFavLayBinding
import com.mobulous.fitscope.databinding.FragmentViewAllFrgBinding
import com.mobulous.fragments.dashboardBottomNavFrgs.viewAllarry
import com.mobulous.helper.*
import com.mobulous.pojo.homePojo
import com.mobulous.viewPagers.ViewAll_VPAdptr


class HomeCategoryCommentFrg : Fragment() {
    lateinit var bin: FragmentViewAllFrgBinding
   // val args: HomeCategoryCommentFrgArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentViewAllFrgBinding.inflate(layoutInflater)
        initview()
        listrn()
        println("HomeCategoryCommentFrg invoked....")
        return bin.root
    }


    fun initview() {
        bin.ViewAllToolbar.tvTitle.text = "args.titles"
        PrefUtils.with(requireContext()).apply {
            val itemList = Gson().fromJson<ArrayList<homePojo>>(
                getString(Enums.HomeParentData.toString(), ""),
                object : TypeToken<ArrayList<homePojo>>() {}.type
            )
            itemList.forEach {
                if (it.parent_lbl == Constants.ViewAllParentLabel) {
                    bin.viewAllTopBnnr.loadNormalPhoto_Dimens400(it.parent_object[0].image_url)
                    return@forEach
                }
            }
        }
        ViewAll_VPAdptr(
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
            Navigation.findNavController(it).navigate(R.id.action_viewAll_to_classes)
        }

        bin.ViewAllToolbar.ivDots.setOnClickListener {
            requireContext().showMoreOption(Constants.isAlreadyAddedToSave,Constants.isAlreadyAddedToFav)
//            BottomSheetDialog(
//                this.requireContext(),
//                R.style.NewCustomBottomSheetDialogTheme
//            ).apply {
//                val btnView =
//                    DialogAddToFavLayBinding.inflate(LayoutInflater.from(this@HomeCategoryCommentFrg.requireContext()))
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