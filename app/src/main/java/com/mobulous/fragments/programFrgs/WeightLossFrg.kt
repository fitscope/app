package com.mobulous.fragments.programFrgs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobulous.Adapter.programsAdptrs.weightLossAptrs.WeightLossParentAdptr
import com.mobulous.Repo.dashboard.ProgramRepo
import com.mobulous.ViewModelFactory.dashboard.program.ProgramVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentWeightLossFrgBinding
import com.mobulous.helper.NetworkReponse
import com.mobulous.helper.Uitls
import com.mobulous.helper.showToast
import com.mobulous.listner.WeightLossViewAllListnr
import com.mobulous.pojo.ChildHorizontalDataItem
import com.mobulous.pojo.homePojo
import com.mobulous.viewModels.dashboard.programVMs.ProgramViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder

class WeightLossFrg : Fragment(), WeightLossViewAllListnr {
    lateinit var listnr: WeightLossViewAllListnr
    private var weightMainLst = ArrayList<homePojo>()
    private var weightMainHorizontalLst = ArrayList<ChildHorizontalDataItem>()
    lateinit var viewModel: ProgramViewModel
    lateinit var bin: FragmentWeightLossFrgBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentWeightLossFrgBinding.inflate(layoutInflater)
        initViews()
        observer()
        return bin.root
    }

    fun observer() {
        viewModel.weightLossData.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        it.data?.let { dataobj ->
                            if (dataobj.status == 200) {
                                dataobj.data?.let { dataitemsLst ->
                                    Uitls.showProgree(false, requireContext())
                                    WeightLossParentAdptr(
                                        this.requireContext(),
                                        dataitemsLst,
                                        this
                                    ).apply {
                                        bin.rv.layoutManager =
                                            LinearLayoutManager(this@WeightLossFrg.requireContext())
                                        bin.rv.adapter = this

                                    }
                                }

                            } else {
                                requireActivity().showToast(dataobj.message ?: "")
                            }


                        } ?: requireActivity().showToast(getString(R.string.no_able_to_process_api))

                    }
                    is NetworkReponse.Error -> {
                        requireActivity().showToast(it.errorMessage)
                    }

                }

            }
        })
    }

    fun initViews() {
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
//        try {
//
////            weightMainLst.clear()
////            weightMainHorizontalLst.clear()
////            val jsonObj = Gson().fromJson<ArrayList<DataItem>>(
////                Constants.weightLossJson,
////                object : TypeToken<ArrayList<DataItem>>() {}.type
////            )
////            jsonObj?.forEach {
////                weightMainHorizontalLst = ArrayList()
////                it.chapters?.forEach { chapterObj ->
////                    weightMainHorizontalLst.add(
////                        ChildHorizontalDataItem(
////                            image_url = chapterObj?.previewImage ?: "",
////                            duration = chapterObj?.duration.toString(),
////                            name = chapterObj?.title ?: "",
////                            id = chapterObj?.id.toString()
////                        )
////                    )
////                }
////                weightMainLst.add(homePojo(it.title ?: "", weightMainHorizontalLst))
////            }
////            WeightLossParentAdptr(this.requireContext(), weightMainLst, this).apply {
////                bin.rv.layoutManager = LinearLayoutManager(this@WeightLossFrg.requireContext())
////                bin.rv.adapter = this
////            }
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listnr = activity as WeightLossViewAllListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }

    override fun onWeightLossViewAllClick(lbl: String, data: String) {
        listnr.onWeightLossViewAllClick(lbl, data = data)
    }

    override fun onResume() {
        super.onResume()
        Uitls.showProgree(true, requireContext())
        viewModel.getWeightLoss()
    }
}