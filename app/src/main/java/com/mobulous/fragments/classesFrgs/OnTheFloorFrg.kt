package com.mobulous.fragments.classesFrgs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobulous.Adapter.ClassesAdptrs.onTheFloorAdptrs.OnTheFloorAdptr
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.ViewModelFactory.dashboard.classes.ClassOnTheFloorVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentTreadmillFrgBinding
import com.mobulous.helper.Uitls
import com.mobulous.listner.OnTheFloorViewAllListnr
import com.mobulous.pojo.dashboard.classes.onTheFloor.OnTheFloorPojo
import com.mobulous.viewModels.dashboard.classVMs.ClassOnTheFloorViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder

class OnTheFloorFrg : Fragment(), OnTheFloorViewAllListnr {
    private var onTheFloorMainLst = ArrayList<OnTheFloorPojo>()
    lateinit var listnr: OnTheFloorViewAllListnr
    lateinit var viewmodel: ClassOnTheFloorViewModel
    lateinit var bin: FragmentTreadmillFrgBinding
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
        viewmodel.onTheFloordata.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            it?.let {
                if (it.status == 200) {
                    it.data?.let { dataitemLst ->
                        OnTheFloorAdptr(
                            requireActivity().applicationContext,
                            dataitemLst,
                            this
                        ).apply {
                            bin.bikesRv.layoutManager =
                                if (resources.getBoolean(R.bool.isTablet))
                                    GridLayoutManager(
                                        this@OnTheFloorFrg.requireContext(),
                                        2
                                    ) else LinearLayoutManager(this@OnTheFloorFrg.requireContext())
                            bin.bikesRv.adapter = this
                        }
                    }
                }
            }
        })
    }

    fun initviews() {
        viewmodel = ViewModelProvider(
            requireActivity(),
            ClassOnTheFloorVMFactory(
                ClassRepo(
                    ServiceBuilder.mobulousBuildServiceToken(
                        ApiInterface::class.java,
                        requireContext()
                    )
                )
            )
        ).get(ClassOnTheFloorViewModel::class.java)

    }

    override fun onTheFloorViewAllClick(lbl: String, data: String) {
        listnr.onTheFloorViewAllClick(lbl, data)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listnr = activity as OnTheFloorViewAllListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }

    override fun onResume() {
        super.onResume()
        Uitls.showProgree(true, requireContext())
        viewmodel.getOnTheFloorData()
    }
}