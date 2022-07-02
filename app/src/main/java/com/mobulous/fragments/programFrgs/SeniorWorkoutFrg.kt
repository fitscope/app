package com.mobulous.fragments.programFrgs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobulous.Adapter.programsAdptrs.seniorAptr.SeniorWrkoutParentAdptr
import com.mobulous.Repo.dashboard.ProgramRepo
import com.mobulous.ViewModelFactory.dashboard.program.ProgramVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentSeniorWorkoutFrgBinding
import com.mobulous.helper.NetworkReponse
import com.mobulous.helper.Uitls
import com.mobulous.helper.showToast
import com.mobulous.listner.RecumbentCategoryCommentListnr
import com.mobulous.listner.SeniorWrkoutHorizontalViewAllListnr
import com.mobulous.listner.SeniorWrkoutViewAllListnr
import com.mobulous.pojo.dashboard.program.seniorWorkOut.SeniorChildHorizontalDataItem
import com.mobulous.pojo.dashboard.program.seniorWorkOut.SeniorWorkOutPojo
import com.mobulous.viewModels.dashboard.programVMs.ProgramViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder

class SeniorWorkoutFrg : Fragment(), SeniorWrkoutViewAllListnr,
    SeniorWrkoutHorizontalViewAllListnr, RecumbentCategoryCommentListnr {
    lateinit var listnr: SeniorWrkoutViewAllListnr
    lateinit var recumbentListnr: RecumbentCategoryCommentListnr
    lateinit var listnr_horizontal: SeniorWrkoutHorizontalViewAllListnr
    private var seniorMainLst_toBeShown = ArrayList<SeniorWorkOutPojo>()
    private var seniorMainLst = ArrayList<SeniorWorkOutPojo>()
    private var seniorMainHorizontalLst = ArrayList<SeniorChildHorizontalDataItem>()
    lateinit var bin: FragmentSeniorWorkoutFrgBinding
    lateinit var viewModel: ProgramViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentSeniorWorkoutFrgBinding.inflate(layoutInflater)
        initViews()
        observer()
        return bin.root
    }

    fun observer() {
        viewModel.seniorWorkOutData.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        it.data?.let { dataObj ->
                            if (dataObj.status == 200) {
                                SeniorWrkoutParentAdptr(
                                    this.requireContext(),
                                    dataObj.data ?: arrayListOf(),
                                    this,
                                    this, this
                                ).apply {
                                    bin.rv.layoutManager =
                                        LinearLayoutManager(this@SeniorWorkoutFrg.requireContext())
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
                    else -> {}
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
            ).get(ProgramViewModel::class.java)


//            seniorMainLst_toBeShown.clear()
//            seniorMainHorizontalLst.clear()
//            seniorMainLst.clear()
//            val jsonObj = Gson().fromJson<ArrayList<DataItem>>(
//                Constants.seniorWorkOutJson,
//                object : TypeToken<ArrayList<DataItem>>() {}.type
//            )
//            val jsonObj2 = Gson().fromJson<ArrayList<SeniorWorkOutPojo>>(
//                Constants.seniorWorkOutJson2,
//                object : TypeToken<ArrayList<SeniorWorkOutPojo>>() {}.type
//            )
//
//            println("${jsonObj.size}--------${jsonObj2.size}")
//
//            jsonObj?.forEach {
//                seniorMainHorizontalLst = ArrayList()
//                it.chapters?.forEach { chapterObj ->
//                    seniorMainHorizontalLst.add(
//                        SeniorChildHorizontalDataItem(
//                            image_url = chapterObj?.previewImage ?: "",
//                            duration = chapterObj?.duration.toString(),
//                            name = chapterObj?.title ?: "",
//                            nestedChildrens = arrayListOf()
//                        )
//                    )
//                }
//                seniorMainLst.add(
//                    SeniorWorkOutPojo(
//                        parent_lbl = it.title ?: "",
//                        parent_object = seniorMainHorizontalLst
//                    )
//                )
//                seniorMainLst_toBeShown.add(
//                    SeniorWorkOutPojo(
//                        parent_lbl = it.title ?: "",
//                        parent_object = seniorMainHorizontalLst
//                    )
//                )
//            }
//            jsonObj2?.forEach {
//                seniorMainLst.add(it)
//                seniorMainLst_toBeShown.add(it)
//
//            }
//
//
//            seniorMainLst.forEach {
//                when (it.parent_lbl) {
//                    Constants.Recumbent -> {
//                        if (seniorMainLst_toBeShown.size > 0) {
//                            seniorMainLst_toBeShown[0] = it
//                        }
//
//                    }
//                    Constants.PowerWalking -> {
//                        if (seniorMainLst_toBeShown.size > 1) {
//                            seniorMainLst_toBeShown[1] = it
//                        }
//
//                    }
//                    Constants.TaiChi -> {
//                        if (seniorMainHorizontalLst.size > 2) {
//                            seniorMainLst_toBeShown[2] = it
//                        }
//
//                    }
//                }
//            }
//
//            SeniorWrkoutParentAdptr(
//                this.requireContext(),
//                seniorMainLst_toBeShown,
//                this,
//                this, this
//            ).apply {
//                bin.rv.layoutManager = LinearLayoutManager(this@SeniorWorkoutFrg.requireContext())
//                bin.rv.adapter = this
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listnr = activity as SeniorWrkoutViewAllListnr
            listnr_horizontal = activity as SeniorWrkoutHorizontalViewAllListnr
            recumbentListnr = activity as RecumbentCategoryCommentListnr

        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }


    override fun onSeniorWrkoutViewAllClick(lbl: String, data: String) {
        listnr.onSeniorWrkoutViewAllClick(lbl, data)
    }

    override fun onSeniorWrkoutHorizontalViewAllClick(lbl: String, data: String) {
        listnr_horizontal.onSeniorWrkoutHorizontalViewAllClick(lbl, data)
    }

    override fun onRecumbentClick(data: String, parentLbl: String) {
        recumbentListnr.onRecumbentClick(data = data, parentLbl = parentLbl)

    }

    override fun onResume() {
        super.onResume()
        Uitls.showProgree(true, requireContext())
        viewModel.getSeniorWorkOutData()
    }
}