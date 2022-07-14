package com.mobulous.fragments.myCalendarFrgs

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mobulous.Adapter.myCalendarAptrs.HistoryAptr
import com.mobulous.Repo.calender.CalenderRepo
import com.mobulous.ViewModelFactory.dashboard.CalenderVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.activity.Schedule.ScheduleActivity
import com.mobulous.fitscope.databinding.FragmentHistoryFrgBinding
import com.mobulous.helper.*
import com.mobulous.listner.UpComingNdHistoryLisntr
import com.mobulous.pojo.PreviouslySchedulePojo
import com.mobulous.pojo.schedule.UpComingScheduleDataItem
import com.mobulous.viewModels.calender.CalenderViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder


class HistoryFrg : Fragment(), UpComingNdHistoryLisntr {

    private val TAG = "HistoryFrg"
    lateinit var historyAptr: HistoryAptr
    lateinit var viewmodel: CalenderViewModel
    private var selectedPos = 0
    private var selectedId = ""
    private var userName = ""
    private var scheduleID = ""
    private var userId = ""
    private var token = ""
    lateinit var broadcastReceiver: BroadcastReceiver
    private var chapterID = ""
    private var chapterDuration = ""
    private var chapterTitle = ""
    private var historyLst = ArrayList<UpComingScheduleDataItem?>()
    lateinit var bin: FragmentHistoryFrgBinding
    private var scheduleResultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data?.getBooleanExtra(
                        Constants.isPreviouslyComplete,
                        false
                    ) == true
                ) {
                    /*code here*/
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentHistoryFrgBinding.inflate(layoutInflater)

        initView()
        observer()

        return bin.root
    }

    private fun observer() {
        viewmodel.removeFromSchedule.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            when (it) {

                is NetworkReponse.Success -> {

                    it.data?.let { dataObj ->
                        if (dataObj.data?.n == 1) {
                            historyLst.removeAt(selectedPos)
                            historyAptr.notifyItemRemoved(selectedPos)
                            historyAptr.notifyItemRangeRemoved(selectedPos, historyLst.size)


                        } else {
                            Uitls.showToast(
                                requireContext(),
                                getString(R.string.no_able_to_process_api)
                            )
                        }

                    }
                }

                is NetworkReponse.Error -> {
                    Uitls.showToast(requireContext(), it.errorMessage)
                }

            }
        })
        viewmodel.getUpcomingSchduleLst.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            when (it) {
                is NetworkReponse.Success -> {
                    it.data?.let { dataObj ->
                        if (dataObj.status == 200) {
                            historyLst.clear()
                            historyLst.addAll(dataObj.data ?: arrayListOf())
                            historyAptr.notifyDataSetChanged()
                        }

                    }

                }

                is NetworkReponse.Error -> {
                    Uitls.showToast(requireContext(), it.errorMessage)
                }
            }
        })
    }

    fun initView() {
        PrefUtils.with(requireContext()).apply {
            userName = getString(Enums.UserName.toString(), "") ?: ""
            userId = getString(Enums.UserID.toString(), "") ?: ""
            token = getString(Enums.UserToken.toString(), "") ?: ""
        }
        viewmodel = ViewModelProvider(
            this,
            CalenderVMFactory(
                CalenderRepo(
                    ServiceBuilder.mobulousBuildServiceToken(
                        ApiInterface::class.java,
                        requireContext()
                    )
                )
            )
        ).get(CalenderViewModel::class.java)

        Log.d(TAG, "initView: ........"+historyLst.size)
        historyAptr = HistoryAptr(historyLst, this).apply {
            bin.historyRv.layoutManager = LinearLayoutManager(this@HistoryFrg.requireContext())
            bin.historyRv.adapter = this
        }
    }

    override fun onResume() {
        super.onResume()
        Uitls.showProgree(true, requireContext())
        viewmodel.getUpComingScheduleLst(Enums.History.toString())

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                p1?.let { intent ->
                    println("-------->${intent.getStringExtra(Constants.Type)}")

                    when (intent.getStringExtra(Constants.Type)) {
                        Enums.REMOVE_FROM_SCHEDULE.toString() -> {
                            Uitls.showProgree(true, requireContext())
                            viewmodel.removeFromSchedule(
                                selectedId
                            )
                        }
                        Enums.ReSCHEDULE.toString() -> {
                            scheduleResultContract.launch(
                                Intent(requireContext(), ScheduleActivity::class.java).putExtra(
                                    Constants.Case,
                                    Constants.ReSchedule
                                ).putExtra(
                                    Constants.Data,
                                    Gson().toJson(
                                        PreviouslySchedulePojo(
                                            userName = userName,
                                            userID = userId,
                                            chapterID = chapterID,
                                            chapterName = chapterTitle,
                                            token = token,
                                            chapterDuration = chapterDuration
                                        )
                                    )
                                ).putExtra(Enums.ID.toString(), scheduleID)
                            )
                        }
                    }

                }
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            broadcastReceiver,
            IntentFilter(Constants.explicitBroadCastAction)
        )
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver)
    }

    override fun onMoreOptionSelected(
        position: Int,
        id: String,
        chapterID: String,
        chapterName: String,
        chapterDuration: String, scheduleID: String
    ) {
        this.scheduleID = scheduleID
        selectedPos = position
        selectedId = id
        this.chapterID = chapterID
        this.chapterDuration = chapterDuration
        this.chapterTitle = chapterName
        requireContext().showMoreOptionForSchedule()
    }

}