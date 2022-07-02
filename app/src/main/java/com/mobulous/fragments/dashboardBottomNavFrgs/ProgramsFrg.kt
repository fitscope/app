package com.mobulous.fragments.dashboardBottomNavFrgs

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentProgramsFrgBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.Uitls
import com.mobulous.pojo.dashboard.ProgramPostPojo
import com.mobulous.pojo.dashboard.SeniorPostPjo
import com.mobulous.pojo.dashboard.program.SeniorWorkOutByIDRes
import com.mobulous.pojo.dashboard.program.WeightLossRes
import com.mobulous.pojo.dashboard.program.seniorWorkOut.SeniorChildHorizontalDataItem
import com.mobulous.pojo.dashboard.program.seniorWorkOut.SeniorWorkOutPojo
import com.mobulous.viewPagers.dashboardViewPager.ProgramsVPAdptr
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val programs_arry = arrayOf("Weight Loss", "Prenatal", "Senior Workout", "BOOTCAMPS")
val programs = arrayOf("Weight loss", "Prenatal", "Senior Workouts", "Bootcamps")

class ProgramsFrg : Fragment() {
    lateinit var apiInterface: ApiInterface
    private var otherSeniorWorkOutHashmap = linkedMapOf<String, String>().apply {
        put("97794", "Recumbent")
        put("78413", "Power Walking")
        /* put("96082", "Tai Chi")*/

    }
    private var totalHitsCount = 0
    lateinit var bin: FragmentProgramsFrgBinding
    private var seniorMainLst = ArrayList<SeniorWorkOutPojo>()
    private var seniorMainHorizontalLst = ArrayList<SeniorChildHorizontalDataItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentProgramsFrgBinding.inflate(layoutInflater)
        initViews()
        listnr()
        return bin.root
    }


    fun initViews() {
        apiInterface =
            ServiceBuilder.mobulousBuildServiceToken(ApiInterface::class.java, requireContext())
        val tabletSize = resources.getBoolean(R.bool.isTablet)
        if (tabletSize) {
            bin.programTabLay.tabMode = TabLayout.MODE_FIXED

        } else {
            bin.programTabLay.tabMode = TabLayout.MODE_SCROLLABLE
        }

        ProgramsVPAdptr(
            this@ProgramsFrg.childFragmentManager, this@ProgramsFrg
                .lifecycle
        ).apply {
            bin.viewpagr.adapter = this
            bin.viewpagr.isUserInputEnabled = false
            TabLayoutMediator(
                bin.programTabLay,
                bin.viewpagr
            ) { tab, position ->
                tab.text = programs_arry[position]
            }.attach()
            Constants.isProgramSectionViewed = true
        }

//        if (!Constants.isProgramSectionViewed) {
//            requireActivity().runOnUiThread {
//                programs.forEachIndexed { index, element ->
//                    getProgramInnerTabsData(element, index)
//                }
//            }
//
//        } else {
//            //showProgramView()
//            ProgramsVPAdptr(
//                this@ProgramsFrg.childFragmentManager, this@ProgramsFrg
//                    .lifecycle
//            ).apply {
//                bin.viewpagr.adapter = this
//                bin.viewpagr.isUserInputEnabled = false
//                TabLayoutMediator(
//                    bin.programTabLay,
//                    bin.viewpagr
//                ) { tab, position ->
//                    tab.text = programs_arry[position]
//                }.attach()
//                Constants.isProgramSectionViewed = true
//            }
//            Uitls.showProgree(false, requireContext())
//        }


    }

    fun listnr() {
        bin.ProgramToolbar.lbl.text = "Programs"
    }

    fun showProgramView() {
        requireActivity().runOnUiThread {
            totalHitsCount = otherSeniorWorkOutHashmap.size
            otherSeniorWorkOutHashmap.entries.forEachIndexed { index, it ->
                println("====order===>${it.value}")
                getOtherSeniorWorkOuts(it, index)
            }
        }


    }


    fun getOtherSeniorWorkOuts(set: MutableMap.MutableEntry<String, String>, index: Int) {
        println("==ss=>${set.value}")
        Uitls.showProgree(isShow = true, requireContext())
        val call = apiInterface.getSeniorWorkOutByID(SeniorPostPjo(id = set.key))
        call.enqueue(object : Callback<SeniorWorkOutByIDRes> {
            override fun onResponse(
                call: Call<SeniorWorkOutByIDRes>,
                response: Response<SeniorWorkOutByIDRes>
            ) {
                println("============>${set.value}")
                if (response.body() != null && response.isSuccessful) {

                    when (set.value) {
                        Constants.Recumbent -> {
                            seniorMainHorizontalLst = ArrayList()
                            response.body()!!.data?.forEach {
                                seniorMainHorizontalLst.add(
                                    SeniorChildHorizontalDataItem(
                                        image_url = it?.horizontalPreview ?: "",
                                        duration = it?.chapters?.get(0)?.duration.toString(),
                                        name = it?.title ?: "",
                                        nestedChildrens = it?.chapters!!

                                    )
                                )
                            }
                            seniorMainLst.add(
                                SeniorWorkOutPojo(
                                    parent_lbl = set.value,
                                    parent_object = seniorMainHorizontalLst
                                )
                            )

                        }
                        Constants.PowerWalking -> {
                            seniorMainHorizontalLst = ArrayList()
                            response.body()!!.data?.forEach {
                                seniorMainHorizontalLst.add(
                                    SeniorChildHorizontalDataItem(
                                        image_url = it?.horizontalPreview ?: "",
                                        duration = it?.chapters?.get(0)?.duration.toString(),
                                        name = it?.title ?: "",
                                        nestedChildrens = it?.chapters!!

                                    )
                                )
                            }
                            seniorMainLst.add(
                                SeniorWorkOutPojo(
                                    parent_lbl = set.value,
                                    parent_object = seniorMainHorizontalLst
                                )
                            )

                        }
                        Constants.TaiChi -> {
                            seniorMainHorizontalLst = ArrayList()
                            response.body()!!.data?.forEach {
                                seniorMainHorizontalLst.add(
                                    SeniorChildHorizontalDataItem(
                                        image_url = it?.horizontalPreview ?: "",
                                        duration = it?.chapters?.get(0)?.duration.toString(),
                                        name = it?.title ?: "",
                                        nestedChildrens = it?.chapters!!

                                    )
                                )
                            }
                            seniorMainLst.add(
                                SeniorWorkOutPojo(
                                    parent_lbl = set.value,
                                    parent_object = seniorMainHorizontalLst
                                )
                            )
                        }

                    }

                    if (index == totalHitsCount - 1) {
                        Handler(Looper.myLooper()!!).postDelayed({
                            Constants.seniorWorkOutJson2 = Gson().toJson(seniorMainLst)
                            ProgramsVPAdptr(
                                this@ProgramsFrg.childFragmentManager, this@ProgramsFrg
                                    .lifecycle
                            ).apply {
                                bin.viewpagr.adapter = this
                                bin.viewpagr.isUserInputEnabled = false
                                TabLayoutMediator(
                                    bin.programTabLay,
                                    bin.viewpagr
                                ) { tab, position ->
                                    tab.text = programs_arry[position]
                                }.attach()
                                Constants.isProgramSectionViewed = true
                            }
                            Uitls.showProgree(false, requireContext())
                        }, 1000)
                    }


                } else {
                    println("============>${set.value}")
                    Uitls.onUnSuccessResponse(response.code(), requireContext())
                }
            }

            override fun onFailure(call: Call<SeniorWorkOutByIDRes>, t: Throwable) {
                println("============>${set.value}")
                Uitls.showProgree(false, requireContext())
                Uitls.handlerError(requireContext(), t)
            }
        })
    }


    fun getProgramInnerTabsData(type: String, index: Int) {
        Uitls.showProgree(true, requireContext())
        val call =
            apiInterface.getProgramEachTab(ProgramPostPojo(type = type))
        call.enqueue(object : Callback<WeightLossRes> {
            override fun onResponse(call: Call<WeightLossRes>, response: Response<WeightLossRes>) {
//                Uitls.showProgree(false, requireContext())
                if (response.body() != null && response.isSuccessful) {
                    when (type) {
                        Constants.WeightLoss -> {
                            Constants.weightLossJson = Gson().toJson(response.body()!!.data)
                            if (index == 3) {
                                showProgramView()
                            }
                        }
                        Constants.Prenatal -> {
                            Constants.prenatalJson = Gson().toJson(response.body()!!.data)
                            if (index == 3) {
                                showProgramView()
                            }
                        }
                        Constants.SeniorWorkouts -> {
                            Constants.seniorWorkOutJson = Gson().toJson(response.body()!!.data)
                            if (index == 3) {
                                showProgramView()
                            }
                        }
                        Constants.Bootcamps -> {
                            Constants.bootcampsJson = Gson().toJson(response.body()!!.data)
                            if (index == 3) {
                                showProgramView()
                            }
                        }
                    }

//                    if (index == 3 && Constants.weightLossJson.isNotEmpty()
//                        && Constants.prenatalJson.isNotEmpty() && Constants.seniorWorkOutJson.isNotEmpty()
//                        && Constants.bootcampsJson.isNotEmpty()){
//
//                        println("============now========")
//                    }

                } else {
                    Uitls.showProgree(false, requireContext())
                    Uitls.onUnSuccessResponse(response.code(), requireContext())
                }
            }

            override fun onFailure(call: Call<WeightLossRes>, t: Throwable) {
                Uitls.showProgree(false, requireContext())
                Uitls.handlerError(requireContext(), t)
            }
        })
    }

}