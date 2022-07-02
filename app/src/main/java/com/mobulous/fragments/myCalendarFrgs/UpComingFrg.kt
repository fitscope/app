package com.mobulous.fragments.myCalendarFrgs

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mobulous.Adapter.myCalendarAptrs.UpcomingAptr
import com.mobulous.Repo.calender.CalenderRepo
import com.mobulous.ViewModelFactory.dashboard.CalenderVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.activity.Schedule.ScheduleActivity
import com.mobulous.fitscope.databinding.FragmentUpComingFrgBinding
import com.mobulous.helper.*
import com.mobulous.listner.UpComingNdHistoryLisntr
import com.mobulous.pojo.PreviouslySchedulePojo
import com.mobulous.pojo.schedule.UpComingScheduleDataItem
import com.mobulous.viewModels.calender.CalenderViewModel
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder

class UpComingFrg : Fragment(), UpComingNdHistoryLisntr {
    lateinit var upcomingAptr: UpcomingAptr
    lateinit var broadcastReceiver: BroadcastReceiver
    lateinit var viewmodel: CalenderViewModel
    private var selectedPos = 0
    private var selectedId = ""
    private var userName = ""
    private var userId = ""
    private var token = ""
    private var scheduleID = ""
    private var chapterID = ""
    private var chapterDuration = ""
    private var chapterTitle = ""

    private var upcomingLst = ArrayList<UpComingScheduleDataItem?>()
    lateinit var bin: FragmentUpComingFrgBinding
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
        bin = FragmentUpComingFrgBinding.inflate(layoutInflater)
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
                            upcomingLst.removeAt(selectedPos)
                            upcomingAptr.notifyItemRemoved(selectedPos)
                            upcomingAptr.notifyItemRangeRemoved(selectedPos, upcomingLst.size)
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
                            upcomingLst.clear()
                            upcomingLst.addAll(dataObj.data ?: arrayListOf())
                            upcomingAptr.notifyDataSetChanged()
                        }
                    }
                }

                is NetworkReponse.Error -> {
                    Uitls.showToast(requireContext(), it.errorMessage)
                }

            }
        })

    }

    private fun initView() {
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

        upcomingAptr = UpcomingAptr(upcomingLst, this).apply {
            bin.upComingRv.layoutManager = LinearLayoutManager(this@UpComingFrg.requireContext())
            bin.upComingRv.adapter = this
        }
    }

    override fun onResume() {
        super.onResume()

        Uitls.showProgree(true, requireContext())
        viewmodel.getUpComingScheduleLst(Enums.Upcoming.toString())

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                p1?.let { intent ->
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
                                ).putExtra(Enums.ID.toString(),scheduleID)
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
        position: Int, id: String,
        chapterID: String,
        chapterName: String,
        chapterDuration: String, scheduleID: String
    ) {
        selectedPos = position
        selectedId = id
        this.chapterID = chapterID
        this.chapterDuration = chapterDuration
        this.chapterTitle = chapterName
        this.scheduleID = scheduleID
        requireContext().showMoreOptionForSchedule()
    }
}