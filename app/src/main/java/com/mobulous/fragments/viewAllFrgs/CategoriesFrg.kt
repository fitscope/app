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
import com.mobulous.Adapter.viewAllAptrs.CategoriesAptr
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.ViewModelFactory.dashboard.classes.ClassHomeVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentCategoriesFrgBinding
import com.mobulous.helper.*
import com.mobulous.pojo.dashboard.CommonChaptersItem
import com.mobulous.viewModels.dashboard.classVMs.ClassHomeViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder

class CategoriesFrg : Fragment() {
    lateinit var viewmodel: ClassHomeViewModel
    lateinit var broadcastReceiver: BroadcastReceiver
    lateinit var bin: FragmentCategoriesFrgBinding
    val agrs: CategoriesFrgArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentCategoriesFrgBinding.inflate(layoutInflater)
        initViews()
        listrn()
        observers()
        return bin.root
    }

    fun initViews() {
        println("CategoriesFrg invoked...")

        try {
            Gson().fromJson<ArrayList<CommonChaptersItem?>>(
                agrs.dataToPopulate,
                object : TypeToken<ArrayList<CommonChaptersItem?>>() {}.type
            ).apply {
                CategoriesAptr(requireContext(), this).apply {
                    bin.categoriesRv.layoutManager =
                        if (resources.getBoolean(R.bool.isTablet))
                            GridLayoutManager(
                                this@CategoriesFrg.requireContext(),
                                2
                            ) else LinearLayoutManager(
                            this@CategoriesFrg.requireContext()
                        )
                    bin.categoriesRv.adapter = this
                }
            }

            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(p0: Context?, intent: Intent?) {
                    println("--typeReceived-->${intent?.getStringExtra(Constants.Type)}")
                    println("---Id--->${Constants.programID}")
                    intent?.let { i ->
                        when (i.getStringExtra(Constants.Type)) {
                            Enums.REMOVE_FROM_FAV.toString() -> {
                                Uitls.showProgree(true, requireContext())
                                viewmodel.removeProgramFromFav(
                                    userID = PrefUtils.with(requireContext())
                                        .getString(Enums.UserID.toString(), "")
                                        ?: Constants.savedUserID,
                                    programID = Constants.programID,
                                    categoryID = null
                                )

                            }
                            Enums.REMOVE_FROM_SAVE.toString() -> {
                                /**for features program id is holding category id*/
                                Uitls.showProgree(true, requireContext())
                                viewmodel.removeProgramFromSave(
                                    userID = PrefUtils.with(requireContext())
                                        .getString(Enums.UserID.toString(), "")
                                        ?: Constants.savedUserID,
                                    programID = Constants.programID,
                                    categoryID = null
                                )
                            }
                            Enums.ADD_TO_SAVE.toString() -> {
                                Uitls.showProgree(true, requireContext())
                                viewmodel.addProgramToSave(
                                    userID = PrefUtils.with(requireContext())
                                        .getString(Enums.UserID.toString(), "")
                                        ?: Constants.savedUserID,
                                    programID = Constants.programID,
                                    categoryID = null
                                )

                            }
                            Enums.ADD_TO_FAV.toString() -> {
                                Uitls.showProgree(true, requireContext())
                                viewmodel.addProgramTofav(
                                    userid = PrefUtils.with(requireContext())
                                        .getString(Enums.UserID.toString(), "")
                                        ?: Constants.savedUserID,
                                    programID = Constants.programID,
                                    categoryId = null
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

    fun listrn() {
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
        bin.ViewAllToolbar.tvTitle.text = agrs.parentName
        bin.ViewAllToolbar.ivback.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(CategoriesFrgDirections.actionViewAllToClasses().actionId)
        }

        bin.ViewAllToolbar.ivDots.setOnClickListener {
            requireContext().showMoreOption(
                isSave = Constants.isAlreadyAddedToSave,
                isFav = Constants.isAlreadyAddedToFav
            )
        }

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

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(
            broadcastReceiver,
            IntentFilter(Constants.explicitBroadCastAction)
        )
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(broadcastReceiver)
    }


}