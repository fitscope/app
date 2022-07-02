package com.mobulous.fragments.viewAllFrgs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobulous.Adapter.ClassesAdptrs.home.FeaturedSingleHorizontalAdptr
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.ViewModelFactory.dashboard.classes.ClassHomeVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentBikeViewAllFrgBinding
import com.mobulous.helper.*
import com.mobulous.pojo.ChildHorizontalDataItem
import com.mobulous.pojo.dashboard.CommonChaptersItem
import com.mobulous.pojo.homePojo
import com.mobulous.viewModels.dashboard.classVMs.ClassHomeViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder


class FeaturedViewAllFrg : Fragment() {
    lateinit var bin: FragmentBikeViewAllFrgBinding
    lateinit var viewmodel: ClassHomeViewModel
    lateinit var broadcastReceiver: BroadcastReceiver
    private var featuredMainLst = ArrayList<homePojo>()
    private var featuredMainHorizontalLst = ArrayList<ChildHorizontalDataItem>()
    val agrs: FeaturedViewAllFrgArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentBikeViewAllFrgBinding.inflate(layoutInflater)
        initView()
        observers()
        listnr()
        return bin.root
    }

    private fun observers() {
        viewmodel.addProgramToFav.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        it.data?.let { res ->
                            if (res.status == 200) {
                                Constants.isAlreadyAddedToFav = true
                            }
                            requireContext().showToast(res.message ?: "")

                        }
                    }

                    is NetworkReponse.Error -> {
                        requireContext().showToast(it.errorMessage)
                    }
                    else -> {

                    }
                }
            } ?: requireContext().showToast(getString(R.string.no_able_to_process_api))
        })
        viewmodel.removeProgramFromFav.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        it.data?.let { res ->
                            if (res.status == 200) {
                                Constants.isAlreadyAddedToFav = false
                            }
                            requireContext().showToast(res.message ?: "")

                        }
                    }

                    is NetworkReponse.Error -> {
                        requireContext().showToast(it.errorMessage)
                    }
                    else -> {

                    }
                }
            } ?: requireContext().showToast(getString(R.string.no_able_to_process_api))
        })

        viewmodel.removeProgramFromSave.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        it.data?.let { res ->
                            if (res.status == 200) {
                                Constants.isAlreadyAddedToSave = false
                            }
                            requireContext().showToast(res.message ?: "")

                        }
                    }

                    is NetworkReponse.Error -> {
                        requireContext().showToast(it.errorMessage)
                    }
                    else -> {

                    }
                }
            } ?: requireContext().showToast(getString(R.string.no_able_to_process_api))
        })
        viewmodel.addProgramToSave.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        it.data?.let { res ->
                            if (res.status == 200) {
                                Constants.isAlreadyAddedToSave = true
                            }
                            requireContext().showToast(res.message ?: "")

                        }
                    }

                    is NetworkReponse.Error -> {
                        requireContext().showToast(it.errorMessage)
                    }
                    else -> {

                    }
                }
            } ?: requireContext().showToast(getString(R.string.no_able_to_process_api))
        })
    }

    fun listnr() {
        bin.ViewAllToolbar.ivDots.setOnClickListener {
            requireActivity().showMoreOption(
                isSave = Constants.isAlreadyAddedToSave,
                isFav = Constants.isAlreadyAddedToFav
            )
        }

        // This callback will only be called when MyFragment is at least Started.
        // This callback will only be called when MyFragment is at least Started.

//        requireActivity().onBackPressedDispatcher.addCallback(
//            this.requireActivity(),
//            object : OnBackPressedCallback(true /* enabled by default */) {
//                override fun handleOnBackPressed() {
//                    // Handle the back button event
//                    println("her ")
//                    Navigation.findNavController(bin.ViewAllToolbar.ivback).navigateUp()
//                }
//            })


    }

    fun initView() {
        viewmodel = ViewModelProvider(
            this,
            ClassHomeVMFactory(
                ClassRepo(
                    ServiceBuilder.mobulousBuildServiceToken(
                        ApiInterface::class.java,
                        requireContext()
                    )
                )
            )
        ).get(ClassHomeViewModel::class.java)
        bin.ViewAllToolbar.ivback.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainLogo.visibility = View.GONE
        bin.ViewAllToolbar.ivDrawer.visibility = View.GONE
        bin.ViewAllToolbar.tvTitle.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainNotification.visibility = View.GONE
        bin.ViewAllToolbar.ivDots.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivback.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.actionToBackToClassesSection)
        }
        bin.ViewAllToolbar.tvTitle.text = agrs.categoryName
        try {
            /*ArrayList<ClassHomeChaptersItem?>?*/
            val bannerObjLst = Gson().fromJson<ArrayList<CommonChaptersItem?>>(
                Constants.feauredViewAllJson,
                object : TypeToken<ArrayList<CommonChaptersItem?>>() {}.type
            )

            FeaturedSingleHorizontalAdptr(
                requireContext(),
                bannerObjLst
            ).apply {
                bin.bikesViewAlRv.layoutManager =
                    if (!resources.getBoolean(R.bool.isTablet)) LinearLayoutManager(this@FeaturedViewAllFrg.requireContext()) else GridLayoutManager(
                        this@FeaturedViewAllFrg.requireContext(), 2
                    )
                bin.bikesViewAlRv.adapter = this
            }


            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(p0: Context?, intent: Intent?) {
                    println("--typeReceived-->${intent?.getStringExtra(Constants.Type)}")
                    println("---Id--->${Constants.programID}")
                    intent?.let { i ->
                        when (i.getStringExtra(Constants.Type)) {
                            Enums.REMOVE_FROM_FAV.toString() -> {
                                /**for features program id is holding category id*/
                                Uitls.showProgree(true, requireContext())
                                viewmodel.removeProgramFromFav(
                                    userID = PrefUtils.with(requireContext())
                                        .getString(Enums.UserID.toString(), "")
                                        ?: Constants.savedUserID,
                                    programID = null,
                                    categoryID = Constants.programID
                                )

                            }
                            Enums.REMOVE_FROM_SAVE.toString() -> {
                                /**for features program id is holding category id*/
                                Uitls.showProgree(true, requireContext())
                                viewmodel.removeProgramFromSave(
                                    userID = PrefUtils.with(requireContext())
                                        .getString(Enums.UserID.toString(), "")
                                        ?: Constants.savedUserID,
                                    programID = null,
                                    categoryID = Constants.programID
                                )
                            }
                            Enums.ADD_TO_SAVE.toString() -> {
                                /**for features program id is holding category id*/
                                Uitls.showProgree(true, requireContext())
                                viewmodel.addProgramToSave(
                                    userID = PrefUtils.with(requireContext())
                                        .getString(Enums.UserID.toString(), "")
                                        ?: Constants.savedUserID,
                                    programID = null,
                                    categoryID = Constants.programID
                                )

                            }
                            Enums.ADD_TO_FAV.toString() -> {
                                /**for features program id is holding category id*/
                                Uitls.showProgree(true, requireContext())
                                viewmodel.addProgramTofav(
                                    userid = PrefUtils.with(requireContext())
                                        .getString(Enums.UserID.toString(), "")
                                        ?: Constants.savedUserID,
                                    programID = null,
                                    categoryId = Constants.programID
                                )
                            }
                        }
                    }
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }


        /*
        * OnTheFloorSingleHorizontalAdptr(
                            requireContext(),
                            homeMainLst,
                            this@FeaturedViewAllFrg
                        ).apply {
                            bin.bikesViewAlRv.layoutManager =
                                LinearLayoutManager(this@FeaturedViewAllFrg.requireContext())
                            bin.bikesViewAlRv.adapter = this
                        }
        * */


    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(
            broadcastReceiver,
            IntentFilter(Constants.explicitBroadCastAction)
        )
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(broadcastReceiver)
    }

}