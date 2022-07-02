package com.mobulous.fragments.programFrgs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobulous.Adapter.programsAdptrs.BootCampAptrs.BootCampParentAdptr
import com.mobulous.Repo.dashboard.ProgramRepo
import com.mobulous.ViewModelFactory.dashboard.program.ProgramVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentBuildStrengthFrgBinding
import com.mobulous.helper.NetworkReponse
import com.mobulous.helper.Uitls
import com.mobulous.helper.showToast
import com.mobulous.listner.BuildStrengthViewAllListnr
import com.mobulous.pojo.ChildHorizontalDataItem
import com.mobulous.pojo.homePojo
import com.mobulous.viewModels.dashboard.programVMs.ProgramViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder


class BootCampFrg : Fragment(), BuildStrengthViewAllListnr {
    private var bootMainLst = ArrayList<homePojo>()
    private var bootMainLst_duplicate = ArrayList<homePojo>()
    private var bootMainHorizontalLst = ArrayList<ChildHorizontalDataItem>()
    lateinit var listnr: BuildStrengthViewAllListnr
    lateinit var viewModel: ProgramViewModel
    lateinit var bin: FragmentBuildStrengthFrgBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentBuildStrengthFrgBinding.inflate(layoutInflater)
        initViews()
        observer()
        return bin.root
    }

    fun observer() {
        viewModel.bootcampData.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        it.data?.let { dataObj ->
                            if (dataObj.status == 200) {
                                BootCampParentAdptr(
                                    this.requireContext(),
                                    dataObj.data?: arrayListOf(),
                                    this
                                ).apply {
                                    bin.rv.layoutManager =
                                        LinearLayoutManager(this@BootCampFrg.requireContext())
                                    bin.rv.adapter = this
                                }
                            } else {
                                requireActivity().showToast(dataObj.message ?: "")
                            }

                        }
                    }
                    is NetworkReponse.Error -> {
                        requireActivity().showToast(it.errorMessage)
                    }

                    else -> {

                    }
                }

            } ?: requireActivity().showToast(getString(R.string.no_able_to_process_api))
        })
    }

    fun initViews() {
        try {
            viewModel = ViewModelProvider(
                requireActivity(),
                ProgramVMFactory(
                    ProgramRepo(
                        ServiceBuilder.mobulousBuildServiceToken(
                            ApiInterface::class.java,
                            requireContext()
                        )
                    )
                )
            ).get(ProgramViewModel::class.java).apply {
                Uitls.showProgree(true, requireContext())
                getBootCampData()
            }

//            bootMainLst.clear()
//            bootMainLst_duplicate.clear()
//            bootMainHorizontalLst.clear()
//            val jsonObj = Gson().fromJson<ArrayList<DataItem>>(
//                Constants.bootcampsJson,
//                object : TypeToken<ArrayList<DataItem>>() {}.type
//            )
//            jsonObj?.forEach {
//                bootMainHorizontalLst = ArrayList()
//                it.chapters?.forEach { chapterObj ->
//                    bootMainHorizontalLst.add(
//                        ChildHorizontalDataItem(
//                            image_url = chapterObj?.previewImage ?: "",
//                            duration = chapterObj?.duration.toString(),
//                            name = chapterObj?.title ?: "",
//                            id = chapterObj?.id.toString()
//                        )
//                    )
//                }
//                bootMainLst.add(homePojo(it.title ?: "", bootMainHorizontalLst))
//                bootMainLst_duplicate.add(homePojo(it.title ?: "", bootMainHorizontalLst))
//
//            }
//
//
//            bootMainLst_duplicate.forEach {
//                when (it.parent_lbl) {
//                    "Cycling Bootcamp" -> {
//                        if (bootMainLst.size > 0) {
//                            bootMainLst[0] = it
//                        }
//
//                    }
//                    "Elliptical Bootcamp" -> {
//                        if (bootMainLst.size > 1) {
//                            bootMainLst[1] = it
//                        }
//
//                    }
//                    "Rowing Bootcamp" -> {
//                        if (bootMainLst.size > 2) {
//                            bootMainLst[2] = it
//                        }
//
//                    }
//                    "Running Bootcamp" -> {
//                        if (bootMainLst.size > 3) {
//                            bootMainLst[3] = it
//                        }
//
//                    }
//                    "Recumbent Cross Training" -> {
//                        if (bootMainLst.size > 4) {
//                            bootMainLst[4] = it
//                        }
//
//                    }
//                    "Airbike Bootcamp" -> {
//                        if (bootMainLst.size > 5) {
//                            bootMainLst[5] = it
//                        }
//
//                    }
//                    "Walking Bootcamp" -> {
//                        if (bootMainLst.size > 6) {
//                            bootMainLst[6] = it
//                        }
//
//                    }
//                }
//            }
//
//            BuildStrengthParentAdptr(this.requireContext(), bootMainLst, this).apply {
//                bin.rv.layoutManager = LinearLayoutManager(this@BootCampFrg.requireContext())
//                bin.rv.adapter = this
//            }


        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listnr = activity as BuildStrengthViewAllListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }

    override fun onBuildStrengthViewAllClick(lbl: String, data: String) {
        listnr.onBuildStrengthViewAllClick(lbl = lbl, data)
    }

}