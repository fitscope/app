package com.mobulous.fragments.viewAllFrgs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobulous.Adapter.ClassesAdptrs.BikeAdptrs.BikesHorizontalAdptr
import com.mobulous.fitscope.databinding.FragmentBikeViewAllFrgBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.showMoreOption
import com.mobulous.listner.BikeHorizontalViewAllListnr
import com.mobulous.pojo.ChildHorizontalDataItem
import com.mobulous.pojo.dashboard.CommonCategoryDataItem
import com.mobulous.pojo.dashboard.CommonChaptersItem
import com.mobulous.pojo.homePojo
import com.mobulous.webservices.ApiInterface


class BikeViewAllFrg : Fragment(), BikeHorizontalViewAllListnr {
    lateinit var bin: FragmentBikeViewAllFrgBinding
    lateinit var mInterface: ApiInterface
    private var homeMainLst = ArrayList<homePojo>()
    private var homeMainHorizontalLst = ArrayList<ChildHorizontalDataItem>()
    lateinit var lstrn: BikeHorizontalViewAllListnr
    val agrs: BikeViewAllFrgArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentBikeViewAllFrgBinding.inflate(layoutInflater)
        initView()
        return bin.root
    }

    fun initView() {
        println("BikeViewAllFrg invoked....")
        bin.ViewAllToolbar.ivback.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainLogo.visibility = View.GONE
        bin.ViewAllToolbar.ivDrawer.visibility = View.GONE
        bin.ViewAllToolbar.tvTitle.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainNotification.visibility = View.GONE
        //bin.ViewAllToolbar.ivDots.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivback.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
            //   Navigation.findNavController(it).navigate(R.id.action_viewAll_to_classes)
        }
        bin.ViewAllToolbar.tvTitle.text = agrs.categoryName
        try {
            Gson().fromJson<ArrayList<CommonCategoryDataItem?>>(
                agrs.dataToPopulate,
                object : TypeToken<ArrayList<CommonCategoryDataItem?>>() {}.type
            ).apply {
                BikesHorizontalAdptr(requireContext(), this, this@BikeViewAllFrg).apply {
                    bin.bikesViewAlRv.layoutManager =
                        LinearLayoutManager(this@BikeViewAllFrg.requireContext())
                    bin.bikesViewAlRv.adapter = this
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // mInterface = ServiceBuilder.buildServiceToken(ApiInterface::class.java, requireContext())
//        requireActivity().runOnUiThread {
//            getDataByCategory(agrs.id)
//        }


    }


//    fun getDataByCategory(id: String?) {
//        homeMainLst.clear()
//        homeMainHorizontalLst.clear()
//        Uitls.showProgree(true, requireContext())
//        val call = mInterface.getCategoryDetailByID(
//            categoryID = id ?: "",
//            perPageCount = Constants.bikesIndexingCountMapping[agrs.categoryName] ?: 1,
//            pageCout = 1
//        )
//        call.enqueue(object : Callback<ArrayList<HomeCategoryDetailByIdResItem>> {
//            override fun onResponse(
//                call: Call<ArrayList<HomeCategoryDetailByIdResItem>>,
//                response: Response<ArrayList<HomeCategoryDetailByIdResItem>>
//            ) {
//                Uitls.showProgree(false, this@BikeViewAllFrg.requireContext())
//                if (response.body() != null && response.isSuccessful) {
//                    response.body()!!.forEach {
//                        homeMainHorizontalLst = ArrayList()
//                        it.chapters?.forEach { chapterObj ->
//                            if (chapterObj?.type == Enums.video.toString()) {
//                                homeMainHorizontalLst.add(
//                                    ChildHorizontalDataItem(
//                                        image_url = chapterObj.previewImage ?: "",
//                                        duration = chapterObj.duration.toString(),
//                                        name = chapterObj.title ?: "",
//                                        id = chapterObj.id ?: ""
//                                    )
//                                )
//                            }
//
//                        }
//                        homeMainLst.add(homePojo(it.title ?: "", homeMainHorizontalLst))
//
//                    }
//                    BikesHorizontalAdptr(requireContext(), homeMainLst, this@BikeViewAllFrg).apply {
//                        bin.bikesViewAlRv.layoutManager =
//                            LinearLayoutManager(this@BikeViewAllFrg.requireContext())
//                        bin.bikesViewAlRv.adapter = this
//                    }
//
//
//                }
//            }
//
//            override fun onFailure(
//                call: Call<ArrayList<HomeCategoryDetailByIdResItem>>,
//                t: Throwable
//            ) {
//                Uitls.showProgree(false, this@BikeViewAllFrg.requireContext())
//                Uitls.handlerError(this@BikeViewAllFrg.requireContext(), t)
//            }
//        })
//
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            lstrn = activity as BikeHorizontalViewAllListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }


    override fun onBikeHorizontalViewAllClick(lbl: String, obj: ArrayList<CommonChaptersItem?>) {
        lstrn.onBikeHorizontalViewAllClick(lbl, obj)
    }

}