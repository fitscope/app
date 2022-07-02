package com.mobulous.fragments.classesFrgs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobulous.Adapter.ClassesAdptrs.TreadMillAdptr
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.ViewModelFactory.dashboard.classes.ClassTreadMilVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentTreadmillFrgBinding
import com.mobulous.helper.Uitls
import com.mobulous.listner.TreadmillViewAllListnr
import com.mobulous.pojo.dashboard.classes.treadmil.TreadMilPojo
import com.mobulous.viewModels.dashboard.classVMs.ClassTreadMilViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder
import kotlinx.coroutines.launch

class TreadmillFrg : Fragment(), TreadmillViewAllListnr {
    private var treadMilMainLst = ArrayList<TreadMilPojo>()
    lateinit var bin: FragmentTreadmillFrgBinding
    lateinit var viewmodel: ClassTreadMilViewModel
    lateinit var lstnr: TreadmillViewAllListnr
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentTreadmillFrgBinding.inflate(layoutInflater)
        initviews()
        observer()
        return bin.root
    }

    fun observer() {
        lifecycleScope.launch {
            viewmodel.treadmilData.observe(viewLifecycleOwner, {
                Uitls.showProgree(false, requireContext())
                it?.let {
                    if (it.status == 200) {
                        it.data?.let { dataitemLst ->
                            TreadMillAdptr(requireContext(), dataitemLst, this@TreadmillFrg).apply {
                                bin.bikesRv.layoutManager =
                                    if (resources.getBoolean(R.bool.isTablet))
                                        GridLayoutManager(
                                            this@TreadmillFrg.requireContext(),
                                            2
                                        ) else LinearLayoutManager(this@TreadmillFrg.requireContext())
                                bin.bikesRv.adapter = this
                            }
                        }
                    }
                }
            })
        }
    }

    fun initviews() {
        viewmodel = ViewModelProvider(
            requireActivity(),
            ClassTreadMilVMFactory(
                ClassRepo(
                    ServiceBuilder.mobulousBuildServiceToken(
                        ApiInterface::class.java,
                        requireContext()
                    )
                )
            )
        ).get(ClassTreadMilViewModel::class.java)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            lstnr = activity as TreadmillViewAllListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }

    override fun onTreadMillViewAllClick(lbl: String, data: String) {
        lstnr.onTreadMillViewAllClick(lbl, data)
    }

    override fun onResume() {
        super.onResume()
        Uitls.showProgree(true, requireContext())
        viewmodel.getTreadmilData()
    }

}