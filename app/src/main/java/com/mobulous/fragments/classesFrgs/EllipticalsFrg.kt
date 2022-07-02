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
import com.mobulous.Adapter.ClassesAdptrs.EllipticalsAdptr
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.ViewModelFactory.dashboard.classes.ClassEllipticalVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentEllipticalsFrgBinding
import com.mobulous.helper.Uitls
import com.mobulous.listner.EllipticalViewAllListnr
import com.mobulous.pojo.dashboard.classes.Ellipticals.HomeEllipticalsDataItems
import com.mobulous.viewModels.dashboard.classVMs.ClassEllipticalsViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder

class EllipticalsFrg : Fragment(), EllipticalViewAllListnr {
    private var ellipticalsMainLst = ArrayList<HomeEllipticalsDataItems?>()
    lateinit var lstnr: EllipticalViewAllListnr
    lateinit var viewModel: ClassEllipticalsViewModel
    lateinit var bin: FragmentEllipticalsFrgBinding
    lateinit var mInterface: ApiInterface
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentEllipticalsFrgBinding.inflate(layoutInflater)
        initviews()
        observers()
        return bin.root
    }

    fun observers() {
        viewModel.ellipticalData.observe(viewLifecycleOwner, {
            Uitls.showProgree(false,requireContext())
            it?.let {
                if (it.status == 200) {
                    it.data?.let { dataItmsLst ->
                        ellipticalsMainLst.clear()
                        if (ellipticalsMainLst.addAll(dataItmsLst)) {
                            EllipticalsAdptr(
                                this.requireContext(),
                                ellipticalsMainLst,
                                this
                            ).apply {
                                bin.bikesRv.layoutManager =
                                    if (resources.getBoolean(R.bool.isTablet))
                                        GridLayoutManager(
                                            this@EllipticalsFrg.requireContext(),
                                            2
                                        ) else LinearLayoutManager(this@EllipticalsFrg.requireContext())
                                bin.bikesRv.adapter = this

                            }
                        }
                    }
                }
            }
        })
    }

    fun initviews() {
        mInterface =
            ServiceBuilder.mobulousBuildServiceToken(ApiInterface::class.java, requireContext())
        viewModel = ViewModelProvider(
            requireActivity(),
            ClassEllipticalVMFactory(ClassRepo(mInterface))
        ).get(ClassEllipticalsViewModel::class.java)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            lstnr = activity as EllipticalViewAllListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }

    override fun onEllipticalViewAllClick(lbl: String, data: String) {
        lstnr.onEllipticalViewAllClick(lbl, data)
    }

    override fun onResume() {
        super.onResume()
        Uitls.showProgree(true,requireContext())
        viewModel.getEllipticalsData()
    }

}