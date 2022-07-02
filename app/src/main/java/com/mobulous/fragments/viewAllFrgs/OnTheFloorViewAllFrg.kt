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
import com.mobulous.Adapter.ClassesAdptrs.onTheFloorAdptrs.OnTheFloorHorizontalAdptr
import com.mobulous.Adapter.ClassesAdptrs.onTheFloorAdptrs.OnTheFloorSingleHorizontalAdptr
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.ViewModelFactory.dashboard.classes.ClassOnTheFloorVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentBikeViewAllFrgBinding
import com.mobulous.helper.*
import com.mobulous.listner.OnTheFloorHorizontalViewAllListnr
import com.mobulous.pojo.ChildHorizontalDataItem
import com.mobulous.pojo.dashboard.CommonCategoryDataItem
import com.mobulous.pojo.dashboard.CommonChaptersItem
import com.mobulous.pojo.homePojo
import com.mobulous.viewModels.dashboard.classVMs.ClassOnTheFloorViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder


class OnTheFloorViewAllFrg : Fragment(), OnTheFloorHorizontalViewAllListnr {
    lateinit var bin: FragmentBikeViewAllFrgBinding
    lateinit var viewmodel: ClassOnTheFloorViewModel
    lateinit var broadcastReceiver: BroadcastReceiver
    private var homeMainLst = ArrayList<homePojo>()
    private var homeMainHorizontalLst = ArrayList<ChildHorizontalDataItem>()
    lateinit var lstrn: OnTheFloorHorizontalViewAllListnr
    val agrs: BikeViewAllFrgArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentBikeViewAllFrgBinding.inflate(layoutInflater)
        initView()
        listnr()
        observers()
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

    fun initView() {
        viewmodel = ViewModelProvider(
            this,
            ClassOnTheFloorVMFactory(
                ClassRepo(
                    ServiceBuilder.mobulousBuildServiceToken(
                        ApiInterface::class.java,
                        requireContext()
                    )
                )
            )
        ).get(ClassOnTheFloorViewModel::class.java)
        bin.ViewAllToolbar.ivback.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainLogo.visibility = View.GONE
        bin.ViewAllToolbar.ivDrawer.visibility = View.GONE
        bin.ViewAllToolbar.tvTitle.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainNotification.visibility = View.GONE

        bin.ViewAllToolbar.ivback.setOnClickListener {
            // NavController(requireContext()).popBackStack()
            Navigation.findNavController(it).navigateUp()
        }
        bin.ViewAllToolbar.tvTitle.text = agrs.categoryName ?: ""
        //mInterface = ServiceBuilder.buildServiceToken(ApiInterface::class.java, requireContext())
//        requireActivity().runOnUiThread {
//            getDataByCategory(agrs.id)
//        }


        try {
            val lst = Gson().fromJson<ArrayList<CommonCategoryDataItem?>>(
                agrs.dataToPopulate,
                object : TypeToken<ArrayList<CommonCategoryDataItem>>() {}.type
            )

            if (lst.size > 1 && lst[0]?.chapters?.size!! > 1) {
                OnTheFloorHorizontalAdptr(
                    requireContext(),
                    lst,
                    this@OnTheFloorViewAllFrg
                ).apply {
                    bin.bikesViewAlRv.layoutManager =
                        LinearLayoutManager(this@OnTheFloorViewAllFrg.requireContext())
                    bin.bikesViewAlRv.adapter = this
                }
            } else {
                bin.ViewAllToolbar.ivDots.visibility = View.VISIBLE
                OnTheFloorSingleHorizontalAdptr(
                    requireContext(),
                    lst
                ).apply {
                    bin.bikesViewAlRv.layoutManager =
                        if (resources.getBoolean(R.bool.isTablet))
                            GridLayoutManager(
                                this@OnTheFloorViewAllFrg.requireContext(),
                                2
                            ) else LinearLayoutManager(this@OnTheFloorViewAllFrg.requireContext())
                    bin.bikesViewAlRv.adapter = this
                }
            }
            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(p0: Context?, intent: Intent?) {
                    println("--typeReceived-->${intent?.getStringExtra(Constants.Type)}")
                    println("---Id--->${Constants.programID}")
                    intent?.let { i ->
                        /**for features program id is holding category id*/
                        when (i.getStringExtra(Constants.Type)) {
                            Enums.REMOVE_FROM_FAV.toString() -> {
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
    }

    fun listnr() {
        bin.ViewAllToolbar.ivDots.setOnClickListener {
            requireContext().showMoreOption(
                Constants.isAlreadyAddedToSave,
                Constants.isAlreadyAddedToFav
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            lstrn = activity as OnTheFloorHorizontalViewAllListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }

    override fun onTheFloorHorizontalViewAllClick(
        lbl: String,
        obj: ArrayList<CommonChaptersItem?>
    ) {
        lstrn.onTheFloorHorizontalViewAllClick(lbl, obj)
//        if (agrs.categoryName == Constants.STRENGTH_TRAINING) {
//
//        } else {
//            println("===========>${obj.parent_object.size}")
//            startActivity(
//                Intent(this.requireContext(), AboutVideoActivity::class.java).putExtra(
//                    Constants.Data,
//                    Gson().toJson(obj)
//                )
//            )
//        }

    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(broadcastReceiver)
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(
            broadcastReceiver,
            IntentFilter(Constants.explicitBroadCastAction)
        )
    }
}