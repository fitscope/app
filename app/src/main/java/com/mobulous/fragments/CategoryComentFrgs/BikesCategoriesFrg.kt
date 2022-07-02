//package com.mobulous.fragments.CategoryComentFrgs
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import com.mobulous.Adapter.viewAllAptrs.CategoriesAptr
//import com.mobulous.fitscope.databinding.FragmentCategoriesFrgBinding
//import com.mobulous.helper.Constants
//import com.mobulous.helper.Enums
//import com.mobulous.helper.PrefUtils
//import com.mobulous.pojo.ChildHorizontalDataItem
//import com.mobulous.pojo.dashboard.classes.bikes.bikePojo
//import com.mobulous.pojo.homePojo
//
//class BikesCategoriesFrg : Fragment() {
//    private var lst = ArrayList<ChildHorizontalDataItem>()
//    lateinit var bin: FragmentCategoriesFrgBinding
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        bin = FragmentCategoriesFrgBinding.inflate(layoutInflater)
//        initViews()
//
//        return bin.root
//    }
//
//    fun initViews() {
//        println("------> Bikes CategoryFrg")
//
//
//
////        CategoriesAptr(requireContext(), lst).apply {
////            bin.categoriesRv.layoutManager =
////                LinearLayoutManager(this@BikesCategoriesFrg.requireContext())
////            bin.categoriesRv.adapter = this
////        }
//
//    }
//
//
//
//
//}