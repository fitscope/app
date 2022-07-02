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
import com.mobulous.Adapter.ClassesAdptrs.BikeAdptrs.BikesClassAdptr
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.ViewModelFactory.dashboard.classes.ClassBikeVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.FragmentBikesFrgBinding
import com.mobulous.helper.Uitls
import com.mobulous.listner.BikeViewAllListnr
import com.mobulous.pojo.dashboard.classes.ClassBike.ClassBikeDataItems
import com.mobulous.viewModels.dashboard.classVMs.ClassBikeViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder
import kotlinx.coroutines.launch


class BikesFrg : Fragment(), BikeViewAllListnr {
    private var bikeMainLst = ArrayList<ClassBikeDataItems?>()
    lateinit var lstnr: BikeViewAllListnr
    lateinit var mInterface: ApiInterface
    lateinit var adptr: BikesClassAdptr
    lateinit var bike_viewmodel: ClassBikeViewModel
    lateinit var bin: FragmentBikesFrgBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentBikesFrgBinding.inflate(layoutInflater)
        initviews()
        observers()
        return bin.root

    }

    fun observers() {
        lifecycleScope.launch {
            bike_viewmodel.bikedata.observe(viewLifecycleOwner, {
                Uitls.showProgree(false,requireContext())
                it?.let {
                    it.data?.let { dataItmsLst ->
                        bikeMainLst.clear()
                        if (bikeMainLst.addAll(dataItmsLst)) {
                            adptr.notifyDataSetChanged()
                        }
                    }

                }
            })
        }
    }

    fun initviews() {
        mInterface =
            ServiceBuilder.mobulousBuildServiceToken(ApiInterface::class.java, requireContext())
        bike_viewmodel = ViewModelProvider(this, ClassBikeVMFactory(ClassRepo(mInterface))).get(
            ClassBikeViewModel::class.java
        )

        adptr = BikesClassAdptr(requireContext(), bikeMainLst, this@BikesFrg).apply {
            bin.bikesRv.layoutManager =
                if (resources.getBoolean(R.bool.isTablet))
                    GridLayoutManager(
                        this@BikesFrg.requireContext(),
                        2
                    ) else LinearLayoutManager(this@BikesFrg.requireContext())
            bin.bikesRv.adapter = this
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            lstnr = activity as BikeViewAllListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }


    override fun onBikeViewAllClick(lbl: String, id: String) {
        lstnr.onBikeViewAllClick(lbl, id)
    }

    override fun onResume() {
        super.onResume()
        Uitls.showProgree(true, requireContext())
        bike_viewmodel.getClassBikeData()
    }

}