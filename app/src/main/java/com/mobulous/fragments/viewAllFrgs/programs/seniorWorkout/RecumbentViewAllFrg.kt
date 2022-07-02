package com.mobulous.fragments.viewAllFrgs.programs.seniorWorkout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobulous.Adapter.programsAdptrs.seniorAptr.HorizontalViewAllAdptr.RecumbentParentAdptr
import com.mobulous.fitscope.databinding.RecumbentViewAllFrgBinding
import com.mobulous.helper.Constants
import com.mobulous.listner.RecumbentViewAllListnr
import com.mobulous.pojo.ChildHorizontalDataItem
import com.mobulous.pojo.dashboard.ProgramCategoryDataItem
import com.mobulous.pojo.dashboard.program.seniorWorkOut.SeniorWorkOutPojo
import com.mobulous.pojo.homePojo
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder


class RecumbentViewAllFrg : Fragment(), RecumbentViewAllListnr {
    lateinit var bin: RecumbentViewAllFrgBinding
    lateinit var jsonLst: SeniorWorkOutPojo
    lateinit var mInterface: ApiInterface
    private var homeMainLst = ArrayList<homePojo>()
    private var homeMainHorizontalLst = ArrayList<ChildHorizontalDataItem>()
    lateinit var lstrn: RecumbentViewAllListnr
    val agrs: RecumbentViewAllFrgArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = RecumbentViewAllFrgBinding.inflate(layoutInflater)
        initView()
        return bin.root
    }


    fun initView() {
        bin.ViewAllToolbar.ivback.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainLogo.visibility = View.GONE
        bin.ViewAllToolbar.ivDrawer.visibility = View.GONE
        bin.ViewAllToolbar.tvTitle.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivMainNotification.visibility = View.GONE
        // bin.ViewAllToolbar.ivDots.visibility = View.VISIBLE
        bin.ViewAllToolbar.ivback.setOnClickListener {

            Navigation.findNavController(it).navigateUp()
//            Navigation.findNavController(it)
//                .navigate(RecumbentViewAllFrgDirections.actionViewAllToProgram())
        }
        bin.ViewAllToolbar.tvTitle.text = agrs.categoryLbl
        mInterface = ServiceBuilder.buildServiceToken(ApiInterface::class.java, requireContext())

        try {
//            if (Constants.seniorWorkOutPojo.isNotEmpty()) {
//                jsonLst = Gson().fromJson<SeniorWorkOutPojo>(
//                    Constants.seniorWorkOutPojo,
//                    SeniorWorkOutPojo::class.java
//                )
//            } else {
//                jsonLst = Gson().fromJson<SeniorWorkOutPojo>(
//                    agrs.objectString,
//                    SeniorWorkOutPojo::class.java
//                )
//                Constants.seniorWorkOutPojo = Gson().toJson(jsonLst)
//            }

            Gson().fromJson<ArrayList<ProgramCategoryDataItem?>>(
                agrs.objectString,
                object : TypeToken<ArrayList<ProgramCategoryDataItem?>>() {}.type
            ).apply {
                RecumbentParentAdptr(
                    this@RecumbentViewAllFrg.requireContext(), this, this@RecumbentViewAllFrg
                ).apply {
                    bin.recumbentViewAlRv.layoutManager =
                        LinearLayoutManager(this@RecumbentViewAllFrg.requireContext())
                    bin.recumbentViewAlRv.adapter = this
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            lstrn = activity as RecumbentViewAllListnr
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onSomeEventListener")
        }
    }

    override fun onRecumbentViewAllClick(
        lbl: String, data: String
    ) {
        lstrn.onRecumbentViewAllClick(data = data, lbl = lbl)
    }

    override fun onDetach() {
        super.onDetach()
        Constants.seniorWorkOutPojo = ""
    }
}