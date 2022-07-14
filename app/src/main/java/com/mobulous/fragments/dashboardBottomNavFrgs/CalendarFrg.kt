package com.mobulous.fragments.dashboardBottomNavFrgs

import android.animation.ValueAnimator
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.yearMonth
import com.mobulous.Adapter.myCalendarAptrs.MyCalendarAptr
import com.mobulous.Controllers.CustomCalenderViewController
import com.mobulous.Repo.calender.CalenderRepo
import com.mobulous.ViewModelFactory.dashboard.CalenderVMFactory
import com.mobulous.fitscope.R
import com.mobulous.fitscope.activity.Schedule.ScheduleActivity
import com.mobulous.fitscope.databinding.FragmentCalendarFrgBinding
import com.mobulous.helper.*
import com.mobulous.helper.Uitls.Companion.daysOfWeekFromLocale
import com.mobulous.listner.DateListnr
import com.mobulous.listner.RemoveScheduleLisntr
import com.mobulous.pojo.PreviouslySchedulePojo
import com.mobulous.pojo.ScheduleDataItem
import com.mobulous.pojo.schedule.ScheduleByDateDataItem
import com.mobulous.viewModels.calender.CalenderViewModel
import com.mobulous.viewPagers.MyCalendarVPAdptr
import com.mobulous.webservices.ApiInterface
import com.mobulous.webservices.ServiceBuilder
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.*
import kotlin.collections.ArrayList

val mycalndar_arry = arrayOf("Coming Up", "History")

@RequiresApi(Build.VERSION_CODES.O)
class CalendarFrg : Fragment(), DateListnr, RemoveScheduleLisntr {
    lateinit var bin: FragmentCalendarFrgBinding
    val daysOfWeek = daysOfWeekFromLocale()
    var imgClick = true
    private var userName = ""
    private var userId = ""
    private var token = ""
    lateinit var broadcastReceiver: BroadcastReceiver
    private var toRemovePos = 0
    lateinit var scheduleWorkoutsVPAdptr: MyCalendarAptr
    private var scheduled_workout_lst = ArrayList<ScheduleByDateDataItem?>()
    private var scheduled_calender_lst = ArrayList<ScheduleDataItem?>()
    lateinit var customCalenderViewController: CustomCalenderViewController
    lateinit var viewmodel: CalenderViewModel
    var selectedDate = ""

    private var scheduleResultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data?.getBooleanExtra(
                        Constants.isPreviouslyComplete,
                        false
                    ) == true
                ) {
                    // UpdateIconState(isChecked = true, position = 3)
                    scheduled_workout_lst.removeAt(toRemovePos)
                    scheduleWorkoutsVPAdptr.notifyItemRemoved(toRemovePos)
                    scheduleWorkoutsVPAdptr.notifyItemRangeRemoved(
                        toRemovePos,
                        scheduled_workout_lst.size
                    )

//                    if (scheduled_workout_lst.isEmpty()) {
//                        println(selectedDate)
//                        println("~~~${scheduled_calender_lst.find { calenderLst -> calenderLst?.dateValue == selectedDate }}")
//                        scheduled_calender_lst.indexOf(scheduled_calender_lst.find { calenderLst -> calenderLst?.dateValue == selectedDate })
//                            .also {
//                                println("~~~~~index---$it----${scheduled_calender_lst.size}")
//                                println("~~~~~in---${scheduled_calender_lst[it]}")
//
//                            }
//
//                        with(customCalenderViewController) {
//                            scheduleDatesBindings(scheduled_calender_lst)
//                        }
//                    }


                    Uitls.showProgree(true, requireContext())
                    viewmodel.getScheduleLst()


                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bin.calendarCusAppbar.lbl.text = "My Calendar"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bin = FragmentCalendarFrgBinding.inflate(layoutInflater)
        initview()
        listnr()
        myCalendarSideMenuInit()
        observer()
        return bin.root
    }

    private fun initview() {
        PrefUtils.with(requireContext()).apply {
            userName = getString(Enums.UserName.toString(), "") ?: ""
            userId = getString(Enums.UserID.toString(), "") ?: ""
            token = getString(Enums.UserToken.toString(), "") ?: ""
        }
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent?) {
                intent?.let { intentObj ->
                    if (intentObj.getStringExtra(Constants.Type) == Enums.CalenderDateClick.toString()) {
                        // selectedDate = it.getStringExtra(Enums.SelectedDate.toString()) ?: ""

                        showScheduleLstByDate(
                            scheduled_workout_lst.find {
                                it?.dateValue == intentObj.getStringExtra(
                                    Enums.SelectedDate.toString()
                                ) ?: ""
                            }?.type ?: "", date = intentObj.getStringExtra(
                                Enums.SelectedDate.toString()
                            ) ?: ""
                        )

                        selectedDate = intentObj.getStringExtra(
                            Enums.SelectedDate.toString()
                        ) ?: ""

//                        println(
//                            "------${
//                                scheduled_workout_lst.find {
//                                    it?.dateValue == intentObj.getStringExtra(
//                                        Enums.SelectedDate.toString()
//                                    ) ?: ""
//                                }?.type
//                            }-------\n--------------"
//                        )

                    }


                }
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            broadcastReceiver,
            IntentFilter(Constants.explicitBroadCastAction)
        )
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
        ).get(CalenderViewModel::class.java).apply {
            Uitls.showProgree(true, requireContext())
            getScheduleLst()
        }

        selectedDate = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).format(Date()).toString()
        customCalenderViewController =
            CustomCalenderViewController(requireActivity(), bin.customCalenderLay).apply {
                calenderViewBindinds(selectedDate)
            }

        scheduleWorkoutsVPAdptr = MyCalendarAptr(scheduled_workout_lst, this).apply {
            bin.exFiveRv.layoutManager = LinearLayoutManager(requireContext())
            bin.exFiveRv.adapter = this
        }

    }

    private fun observer() {
        viewmodel.removeFromSchedule.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            when (it) {
                is NetworkReponse.Success -> {
                    it.data?.let { dataObj ->
                        if (dataObj.data?.n == 1) {
                            scheduled_workout_lst.removeAt(toRemovePos)
                            scheduleWorkoutsVPAdptr.notifyItemRemoved(toRemovePos)
                            scheduleWorkoutsVPAdptr.notifyItemRangeRemoved(
                                toRemovePos,
                                scheduled_workout_lst.size
                            )

                            if (scheduled_workout_lst.isEmpty()) {
                                scheduled_calender_lst.removeIf { calenderLst -> calenderLst?.dateValue == selectedDate }

                                with(customCalenderViewController) {
                                    scheduleDatesBindings(scheduled_calender_lst)
                                }
                            }

                            //                            /*reloading the views of calender*/


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

        viewmodel.getScheduleLst.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            when (it) {
                is NetworkReponse.Success -> {
                    it.data?.let { dataObj ->
                        if (dataObj.status == 200) {
                            dataObj.data?.let { scheduleLst ->
                                scheduled_calender_lst.clear()
                                scheduled_calender_lst.addAll(scheduleLst)

                                with(customCalenderViewController) {
                                    scheduleDatesBindings(scheduled_calender_lst)
                                }
                                //  changeCalenderView(true)
                                showScheduleLstByDate(scheduled_calender_lst.find {
                                    it?.dateValue == selectedDate
                                }?.type ?: "", selectedDate)
                            }


                        } else {
                           // Uitls.showToast(requireContext(), dataObj.message ?: "")
                        }
                    }
                }
                is NetworkReponse.Error -> {
                    Uitls.showToast(requireContext(), it.errorMessage)
                }

            }
        })

        viewmodel.getScheduleByDateLst.observe(viewLifecycleOwner, {
            Uitls.showProgree(false, requireContext())
            when (it) {
                is NetworkReponse.Success -> {
                    it.data?.let { dataObj ->
                        if (dataObj.status == 200) {
                            bin.monthLbl.text = selectedDate
                            scheduled_workout_lst.clear()
                            scheduled_workout_lst.addAll(dataObj.data ?: arrayListOf())
                            scheduleWorkoutsVPAdptr.notifyDataSetChanged()
//                            with(customCalenderViewController) {
//                                scheduleDatesBindings(scheduled_calender_lst)
//                            }
//                            scheduleWorkoutsVPAdptr.notifyItemRangeInserted(
//                                0,
//                                scheduled_workout_lst.size
//                            )


                        } else {
                            Uitls.showToast(requireContext(), dataObj.message ?: "")
                        }
                    }
                }
                is NetworkReponse.Error -> {
                    Uitls.showToast(requireContext(), it.errorMessage)
                }

            }
        })
    }

    fun myCalendarSideMenuInit() {
        MyCalendarVPAdptr(
            this.childFragmentManager, this
                .lifecycle
        ).apply {
            bin.mycalendatSideMenuLay.viewpagr.adapter = this
//            bin.labViewpagr.offscreenPageLimit = 8
            TabLayoutMediator(
                bin.mycalendatSideMenuLay.tabLay,
                bin.mycalendatSideMenuLay.viewpagr
            ) { tab, position ->
                tab.text = mycalndar_arry[position]
            }.attach()
        }
    }


    fun listnr() {

        bin.calModeChanger.setOnCheckedChangeListener { _, monthToWeek ->
            changeCalenderView(monthToWeek)
        }
        bin.calendarCusAppbar.calSideMenuBtn.visibility = View.VISIBLE

        bin.calendarCusAppbar.calSideMenuBtn.setOnClickListener {
            if (imgClick) {
                bin.calendarCusAppbar.calSideMenuBtn.setImageDrawable(
                    this.requireContext().getDrawable(R.drawable.new_clndr)
                )
                imgClick = false
            } else {
                imgClick = true
                bin.calendarCusAppbar.calSideMenuBtn.setImageDrawable(
                    this.requireContext().getDrawable(
                        R.drawable.clndr
                    )
                )
                Uitls.showProgree(true, requireContext())
                viewmodel.getScheduleLst()
            }

            bin.mycalendatSideMenuLay.root.visibility =
                if (bin.mycalendatSideMenuLay.root.isVisible) View.GONE else View.VISIBLE

            bin.calendarLay.visibility =
                if (bin.mycalendatSideMenuLay.root.isVisible) View.GONE else View.VISIBLE

        }
    }

    private fun showScheduleLstByDate(type: String, date: String) {
        Uitls.showProgree(true, requireContext())
        viewmodel.getScheduleByDateLst(type, date)

    }

    private fun changeCalenderView(monthToWeek: Boolean) {
        bin.calModeChanger.text = if (monthToWeek) "Show months" else "Show week"
        val firstDate =
            bin.customCalenderLay.exFiveCalendarSchedule.findFirstVisibleDay()?.date
                ?: return
        val lastDate =
            bin.customCalenderLay.exFiveCalendarSchedule.findLastVisibleDay()?.date
                ?: return

        val oneWeekHeight = bin.customCalenderLay.exFiveCalendarSchedule.daySize.height
        val oneMonthHeight = oneWeekHeight * 6

        val oldHeight = if (monthToWeek) oneMonthHeight else oneWeekHeight
        val newHeight = if (monthToWeek) oneWeekHeight else oneMonthHeight

        // Animate calendar height changes.
        val animator = ValueAnimator.ofInt(oldHeight, newHeight)
        animator.addUpdateListener { animator ->
            bin.customCalenderLay.exFiveCalendarSchedule.updateLayoutParams {
                height = animator.animatedValue as Int
            }
        }

        // When changing from month to week mode, we change the calendar's
        // config at the end of the animation(doOnEnd) but when changing
        // from week to month mode, we change the calendar's config at
        // the start of the animation(doOnStart). This is so that the change
        // in height is visible. You can do this whichever way you prefer.

        animator.doOnStart {
            if (!monthToWeek) {
                bin.customCalenderLay.exFiveCalendarSchedule.updateMonthConfiguration(
                    inDateStyle = InDateStyle.ALL_MONTHS,
                    maxRowCount = 6,
                    hasBoundaries = true
                )
            }
        }
        animator.doOnEnd {
            if (monthToWeek) {
                bin.customCalenderLay.exFiveCalendarSchedule.updateMonthConfiguration(
                    inDateStyle = InDateStyle.FIRST_MONTH,
                    maxRowCount = 1,
                    hasBoundaries = true
                )
            }

            if (monthToWeek) {
                // We want the first visible day to remain
                // visible when we change to week mode.
                bin.customCalenderLay.exFiveCalendarSchedule.scrollToDate(LocalDate.now())
                // bin.customCalenderLay.exFiveCalendarSchedule.scrollToMonth(firstDate.yearMonth)
            } else {
                // When changing to month mode, we choose current
                // month if it is the only one in the current frame.
                // if we have multiple months in one frame, we prefer
                // the second one unless it's an outDate in the last index.
                if (firstDate.yearMonth == lastDate.yearMonth) {
                    bin.customCalenderLay.exFiveCalendarSchedule.scrollToMonth(firstDate.yearMonth)
                } else {
                    // We compare the next with the last month on the calendar so we don't go over.
                    bin.customCalenderLay.exFiveCalendarSchedule.scrollToMonth(
                        minOf(
                            firstDate.yearMonth.next,
                            YearMonth.now().apply {
                                plusMonths(10)
                            }
                        )
                    )
                }

            }
        }
        animator.duration = 250
        animator.start()

    }


    override fun onDateClick(date: String) {
        bin.monthLbl.text = getFormattedDate(date = date)
        selectedDate = date
        //  bin.exFiveCalendarSchedule.notifyCalendarChanged()
    }


    fun getFormattedDate(date: String): String {
        try {
            SimpleDateFormat("dd MMMM", Locale.getDefault()).format(
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                ).parse(date) ?: ""
            ).apply {
                return this
            }
        } catch (e: Exception) {
            return ""
        }
    }

    override fun onScheduleClicked(
        position: Int,
        scheduleID: String?,
        scheduleObj: ScheduleByDateDataItem?
    ) {
        toRemovePos = position
        if (scheduleObj != null) {
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
                            chapterID = scheduleObj.chapterId.toString(),
                            chapterName = scheduleObj.chapterDetails?.programTitle ?: "",
                            token = token,
                            chapterDuration = scheduleObj.timeInMint.toString()
                        )
                    )
                ).putExtra(Enums.ID.toString(), scheduleObj.id)
                /**scheduleId*/
            )
        } else {
            Uitls.showProgree(true, requireContext())
            viewmodel.removeFromSchedule(scheduleID!!)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver)
    }
}