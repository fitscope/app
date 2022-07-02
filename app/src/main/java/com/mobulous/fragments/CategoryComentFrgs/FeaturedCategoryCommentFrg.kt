package com.mobulous.fragments.CategoryComentFrgs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentViewAllFrgBinding
import com.mobulous.fragments.dashboardBottomNavFrgs.viewAllarry
import com.mobulous.helper.Constants
import com.mobulous.helper.loadNormalPhoto_Dimens400
import com.mobulous.helper.showMoreOption
import com.mobulous.pojo.homePojo
import com.mobulous.viewModels.calender.CalenderViewModel
import com.mobulous.viewPagers.ViewAll_VPAdptr


/*classes ----> bike section ---> itemClick ---> viewAll*/
class FeaturedCategoryCommentFrg : Fragment() {
    lateinit var bin: FragmentViewAllFrgBinding
    val args: BikesCategoryCommentFrgArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentViewAllFrgBinding.inflate(layoutInflater)
        initview()
        listrn()

        return bin.root
    }


    fun initview() {

//        val itemList = Gson().fromJson<ArrayList<homePojo>>(
//            args.objectString,
//            object : TypeToken<ArrayList<homePojo>>() {}.type
//        )

        bin.viewAllTopBnnr.visibility = View.GONE
        bin.lbl.visibility = View.GONE
        try {
            val itemList = Gson().fromJson<homePojo>(args.objectString, homePojo::class.java)
            bin.ViewAllToolbar.tvTitle.text = itemList.parent_lbl
            bin.viewAllTopBnnr.loadNormalPhoto_Dimens400(itemList.parent_object[0].image_url)
            Constants.nonHomeTabCategoryJson = Gson().toJson(itemList)

        } catch (e: Exception) {
            e.printStackTrace()
        }


//

        ViewAll_VPAdptr(
            this.childFragmentManager, this
                .lifecycle
        ).apply {
            bin.viewAllViewpagr.adapter = this
//            bin.labViewpagr.offscreenPageLimit = 8
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
            findNavController().navigate(R.id.action_viewAll_to_classes)
        }


        bin.ViewAllToolbar.ivDots.setOnClickListener {
            requireContext().showMoreOption(
                Constants.isAlreadyAddedToSave,
                Constants.isAlreadyAddedToFav
            )
//            BottomSheetDialog(
//                this.requireContext(),
//                R.style.NewCustomBottomSheetDialogTheme
//            ).apply {
//                val btnView =
//                    DialogAddToFavLayBinding.inflate(LayoutInflater.from(this@FeaturedCategoryCommentFrg.requireContext()))
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