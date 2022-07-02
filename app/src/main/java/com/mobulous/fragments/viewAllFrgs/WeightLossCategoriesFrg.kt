package com.mobulous.fragments.viewAllFrgs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobulous.Adapter.viewAllAptrs.CategoriesAptr
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentCategoriesFrgBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.Enums
import com.mobulous.helper.PrefUtils
import com.mobulous.pojo.ChildHorizontalDataItem
import com.mobulous.pojo.homePojo

class WeightLossCategoriesFrg : Fragment() {
    private var lst = ArrayList<ChildHorizontalDataItem>()
    lateinit var bin: FragmentCategoriesFrgBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentCategoriesFrgBinding.inflate(layoutInflater)
        initViews()

        return bin.root
    }

    fun initViews() {
        println("------> called WeightLossCategoryFrg")
//        PrefUtils.with(requireContext()).apply {
//            val itemList = Gson().fromJson<ArrayList<homePojo>>(
//                getString(Enums.HomeParentData.toString(), ""),
//                object : TypeToken<ArrayList<homePojo>>() {}.type
//            )
//            itemList.forEach {
//                if (it.parent_lbl == Constants.ViewAllParentLabel) {
//                    lst.addAll(it.parent_object)
//                    return@forEach
//                }
//            }
//            if (lst.isEmpty()) {
//                try {
//                    val itemList2 = Gson().fromJson<homePojo>(
//                        Constants.weightLossChaptersJson,
//                        homePojo::class.java
//                    )
//                    lst.addAll(itemList2.parent_object)
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//
//            }
//
//            CategoriesAptr(requireContext(), lst).apply {
//                bin.categoriesRv.layoutManager =
//                    if (!resources.getBoolean(R.bool.isTablet)) LinearLayoutManager(this@WeightLossCategoriesFrg.requireContext()) else GridLayoutManager(
//                        this@WeightLossCategoriesFrg.requireContext(),
//                        2
//                    )
//                bin.categoriesRv.adapter = this
//            }
//
//
//        }


    }


}