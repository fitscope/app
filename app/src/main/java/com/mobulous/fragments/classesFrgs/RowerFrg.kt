package com.mobulous.fragments.classesFrgs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobulous.Adapter.ClassesAdptrs.RowerAdptr
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.ViewModelFactory.dashboard.classes.ClassRowerVMFactory
import com.mobulous.fitscope.databinding.FragmentRowerFrgBinding
import com.mobulous.helper.Uitls
import com.mobulous.listner.RowerViewAllListnr
import com.mobulous.pojo.dashboard.CommonChaptersItem
import com.mobulous.pojo.homePojo
import com.mobulous.viewModels.dashboard.classVMs.ClassRowerViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder
import kotlinx.coroutines.launch

class RowerFrg : Fragment(), RowerViewAllListnr {
    //    private var lst = ArrayList<bikePojo>()
    private var lst = ArrayList<homePojo>()
    lateinit var viewmodel: ClassRowerViewModel

    lateinit var bin: FragmentRowerFrgBinding
    lateinit var lstrn: RowerViewAllListnr
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentRowerFrgBinding.inflate(layoutInflater)
        initviews()
        observer()
        return bin.root
    }

    fun observer() {
        lifecycleScope.launch {
            viewmodel.rowerData.observe(viewLifecycleOwner, {
                Uitls.showProgree(false,requireContext())
                it?.let {
                    if (it.status == 200) {
                        it.data?.let { dataitemLst ->
                            if (dataitemLst.size > 0) {
                                dataitemLst[0]?.categoryData?.let {
                                    RowerAdptr(requireContext(), it, this@RowerFrg).apply {
                                        bin.bikesRv.layoutManager =
                                            LinearLayoutManager(this@RowerFrg.requireContext())
                                        bin.bikesRv.adapter = this
                                    }
                                }
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
            ClassRowerVMFactory(
                ClassRepo(
                    ServiceBuilder.mobulousBuildServiceToken(
                        ApiInterface::class.java,
                        requireContext()
                    )
                )
            )
        ).get(ClassRowerViewModel::class.java)


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            lstrn = activity as RowerViewAllListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }

    override fun onRowerViewAllClick(lbl: String, obj: ArrayList<CommonChaptersItem?>) {
        lstrn.onRowerViewAllClick(lbl, obj)
    }

    override fun onResume() {
        super.onResume()
        Uitls.showProgree(true,requireContext())
        viewmodel.getRowersData()
    }

}