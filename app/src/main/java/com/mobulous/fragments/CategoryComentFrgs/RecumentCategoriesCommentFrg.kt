package com.mobulous.fragments.CategoryComentFrgs

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
import com.mobulous.Adapter.viewAllAptrs.RecumentCategoriesAptr
import com.mobulous.Repo.dashboard.ProgramRepo
import com.mobulous.ViewModelFactory.dashboard.program.ProgramVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.RecumbentCategoryCommentFrgBinding
import com.mobulous.helper.*
import com.mobulous.pojo.ChildHorizontalDataItem
import com.mobulous.pojo.dashboard.ProgramChaptersDataItem
import com.mobulous.viewModels.dashboard.programVMs.ProgramViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder
import kotlinx.coroutines.launch


class RecumentCategoriesCommentFrg : Fragment() {
    private var lst = ArrayList<ChildHorizontalDataItem>()
    lateinit var viewmodel: ProgramViewModel
    lateinit var broadcastReceiver: BroadcastReceiver
    val agrs: RecumentCategoriesCommentFrgArgs by navArgs()
    lateinit var bin: RecumbentCategoryCommentFrgBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = RecumbentCategoryCommentFrgBinding.inflate(layoutInflater)
        initViews()
        listnr()
        observers()
        return bin.root
    }

    fun listnr() {
        bin.ViewAllToolbar.ivDots.setOnClickListener {
            requireContext().showMoreOption(
                Constants.isAlreadyAddedToSave,
                Constants.isAlreadyAddedToFav
            )
        }
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

    fun initViews() {
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

        bin.ViewAllToolbar.ivback.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainLogo.visibility = View.GONE
        bin.ViewAllToolbar.ivDrawer.visibility = View.GONE
        bin.ViewAllToolbar.tvTitle.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainNotification.visibility = View.GONE
        bin.ViewAllToolbar.ivDots.visibility = View.VISIBLE

        bin.ViewAllToolbar.ivback.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
            // Navigation.findNavController(it).navigate(R.id.actionToProgram)
        }
        try {
            bin.ViewAllToolbar.tvTitle.text = agrs.categoryName
            val jsonLst =
                Gson().fromJson<ArrayList<ProgramChaptersDataItem?>>(
                    agrs.dataToPopulate,
                    object : TypeToken<ArrayList<ProgramChaptersDataItem?>>() {}.type
                )
            RecumentCategoriesAptr(requireContext(), jsonLst).apply {
                bin.rv.layoutManager =
                    if (!resources.getBoolean(R.bool.isTablet)) LinearLayoutManager(this@RecumentCategoriesCommentFrg.requireContext()) else GridLayoutManager(
                        this@RecumentCategoriesCommentFrg.requireContext(),
                        2
                    )
                bin.rv.adapter = this
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