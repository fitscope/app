package com.mobulous.fragments.programFrgs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobulous.Adapter.programsAdptrs.prenatalAptrs.PrenatalParentAdptr
import com.mobulous.Repo.dashboard.ProgramRepo
import com.mobulous.ViewModelFactory.dashboard.program.ProgramVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentPrenatalFrgBinding
import com.mobulous.helper.NetworkReponse
import com.mobulous.helper.Uitls
import com.mobulous.helper.showToast
import com.mobulous.listner.PrenatalViewAllListnr
import com.mobulous.viewModels.dashboard.programVMs.ProgramViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder

class PrenatalFrg : Fragment(), PrenatalViewAllListnr {
    lateinit var listnr: PrenatalViewAllListnr
    lateinit var viewmodel: ProgramViewModel
    lateinit var bin: FragmentPrenatalFrgBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentPrenatalFrgBinding.inflate(layoutInflater)
        initViews()
        observer()
        return bin.root
    }

    fun observer() {
        viewmodel.prenatalData.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            it?.let {
                when (it) {
                    is NetworkReponse.Success -> {
                        if (it.data?.status == 200) {
                            PrenatalParentAdptr(
                                this.requireContext(),
                                it.data.data ?: arrayListOf(),
                                this
                            ).apply {
                                bin.rv.layoutManager =
                                    LinearLayoutManager(this@PrenatalFrg.requireContext())
                                bin.rv.adapter = this
                            }
                        } else {
                            requireActivity().showToast(it.data?.message ?: "")
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
        viewmodel = ViewModelProvider(
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
        try {
//            prenatalMainLst.clear()
//            prenatalMainHorizontalLst.clear()
//            val jsonObj = Gson().fromJson<ArrayList<DataItem>>(
//                Constants.prenatalJson,
//                object : TypeToken<ArrayList<DataItem>>() {}.type
//            )
//            jsonObj?.forEach {
//                prenatalMainHorizontalLst = ArrayList()
//                it.chapters?.forEach { chapterObj ->
//                    prenatalMainHorizontalLst.add(
//                        ChildHorizontalDataItem(
//                            image_url = chapterObj?.previewImage ?: "",
//                            duration = chapterObj?.duration.toString(),
//                            name = chapterObj?.title ?: "",
//                            id  = chapterObj?.id.toString()
//                        )
//                    )
//                }
//                prenatalMainLst.add(homePojo(it.title ?: "", prenatalMainHorizontalLst))
//            }
//
//            PrenatalParentAdptr(this.requireContext(), prenatalMainLst, this).apply {
//                bin.rv.layoutManager = LinearLayoutManager(this@PrenatalFrg.requireContext())
//                bin.rv.adapter = this
//            }


        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listnr = activity as PrenatalViewAllListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }


    override fun onPrenatalViewAllClick(lbl: String, data: String) {
        listnr.onPrenatalViewAllClick(lbl, data = data)
    }

    override fun onResume() {
        super.onResume()
        Uitls.showProgree(true, requireContext())
        viewmodel.getPrenatalData()
    }

}