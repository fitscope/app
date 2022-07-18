package com.mobulous.fragments.VideoFrgs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobulous.Adapter.VideoAdapter.UpNextAdapter
import com.mobulous.fitscope.databinding.FragmentUpNextBinding
import com.mobulous.pojo.dashboard.CommonCategoryDataItem
import com.mobulous.pojo.dashboard.CommonChaptersItem

class UpNextFragment(val upNextDataObj: String, val isCategoryType: Boolean) : Fragment() {
    lateinit var bin: FragmentUpNextBinding
    private val upNextLst = ArrayList<CommonChaptersItem?>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentUpNextBinding.inflate(layoutInflater)
//        return inflater.inflate(R.layout.fragment_up_next, container, false)
        initViews()
        lisnr()
        return bin.root
    }

    fun initViews() {
        try {
            when (isCategoryType) {
                true -> {
                    Gson().fromJson<ArrayList<CommonCategoryDataItem?>>(
                        upNextDataObj,
                        object : TypeToken<ArrayList<CommonCategoryDataItem?>>() {}.type
                    )?.onEach {
                        it?.let { obj ->
                            upNextLst.addAll(obj.chapters!!)
                        }
                    }
                }
                false -> {
                    Gson().fromJson<ArrayList<CommonChaptersItem?>>(
                        upNextDataObj,
                        object : TypeToken<ArrayList<CommonChaptersItem?>>() {}.type
                    )?.apply {
                        upNextLst.addAll(this)
                    }
                }
            }

            UpNextAdapter(this@UpNextFragment.requireContext(), upNextLst, isCategoryType).apply {
                bin.rvUPNext.layoutManager =
                    LinearLayoutManager(this@UpNextFragment.requireContext())
                bin.rvUPNext.adapter = this

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun lisnr() {

    }
}