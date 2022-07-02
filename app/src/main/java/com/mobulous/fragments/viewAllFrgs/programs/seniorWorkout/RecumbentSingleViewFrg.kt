package com.mobulous.fragments.viewAllFrgs.programs.seniorWorkout

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobulous.Adapter.viewAllAptrs.SingleViewAptr
import com.mobulous.Repo.dashboard.ProgramRepo
import com.mobulous.ViewModelFactory.dashboard.program.ProgramVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.RecumentSingleViewFragmentBinding
import com.mobulous.helper.*
import com.mobulous.pojo.dashboard.ProgramChaptersDataItem
import com.mobulous.viewModels.dashboard.programVMs.ProgramViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder
import kotlinx.coroutines.launch


class RecumbentSingleViewFrg : Fragment() {
    lateinit var bin: RecumentSingleViewFragmentBinding
    lateinit var viewmodel: ProgramViewModel
    lateinit var broadcastReceiver: BroadcastReceiver
    val args: RecumbentSingleViewFrgArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = RecumentSingleViewFragmentBinding.inflate(layoutInflater)
        initview()
        listrn()
        observers()

        return bin.root
    }

    private fun observers() {
        lifecycleScope.launch {
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
                            println("-----> callledd")
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
    }

    fun initview() {
        viewmodel = ViewModelProvider(
            this,
            ProgramVMFactory(
                ProgramRepo(
                    ServiceBuilder.mobulousBuildServiceToken(
                        ApiInterface::class.java,
                        requireContext()
                    )
                )
            )
        ).get(ProgramViewModel::class.java)
        bin.ViewAllToolbar.tvTitle.text = args.categoryLbl
        try {
            Gson().fromJson<ArrayList<ProgramChaptersDataItem?>>(
                args.objectString,
                object : TypeToken<ArrayList<ProgramChaptersDataItem?>>() {}.type
            ).apply {
                SingleViewAptr(requireContext(), this).apply {
                    bin.singleViewRv.layoutManager =
                        if (!resources.getBoolean(R.bool.isTablet)) LinearLayoutManager(this@RecumbentSingleViewFrg.requireContext()) else GridLayoutManager(
                            this@RecumbentSingleViewFrg.requireContext(), 2
                        )
                    bin.singleViewRv.adapter = this
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
        bin.ViewAllToolbar.ivback.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainLogo.visibility = View.GONE
        bin.ViewAllToolbar.ivDrawer.visibility = View.GONE
        bin.ViewAllToolbar.tvTitle.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainNotification.visibility = View.GONE
        bin.ViewAllToolbar.ivDots.visibility = View.VISIBLE
//      bin.ViewAllToolbar.tvTitle.text = "Himanshu"
        bin.ViewAllToolbar.ivback.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        bin.ViewAllToolbar.ivDots.setOnClickListener {
            requireContext().showMoreOption(
                Constants.isAlreadyAddedToSave,
                Constants.isAlreadyAddedToFav
            )
        }

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