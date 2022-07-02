package com.mobulous.Controllers

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import com.mobulous.ViewController.DayViewContainer
import com.mobulous.ViewController.MonthViewContainer
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.CustomCalenderLayBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.Enums
import com.mobulous.helper.Uitls
import com.mobulous.listner.DateListnr
import com.mobulous.pojo.ScheduleDataItem
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
class CustomCalenderViewController(
    val context: Activity,
    val bin: CustomCalenderLayBinding
) : DateListnr {

    private var selectedDate = ""
    var scheduleDates = ArrayList<ScheduleDataItem?>()

    fun scheduleDatesBindings(scheduleDates: ArrayList<ScheduleDataItem?>) {
        this.scheduleDates.clear()
        this.scheduleDates.addAll(scheduleDates)
        bin.exFiveCalendarSchedule.notifyCalendarChanged()
//        bin.exFiveCalendarSchedule.notifyDateChanged(LocalDate.parse(""))
    }


    fun calenderViewBindinds(selectedDate: String) {
        this.selectedDate = selectedDate
        bin.tvCalendarLblSchedule.text = getFormattedDate(date = selectedDate)
        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        bin.exFiveCalendarSchedule.setup(firstMonth, lastMonth, firstDayOfWeek)
        bin.exFiveCalendarSchedule.scrollToMonth(currentMonth)

        /*view binding*/
        bin.exFiveCalendarSchedule.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) =
                DayViewContainer(view, this@CustomCalenderViewController)

            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.binding.dateLbl
//                val layout = container.binding.exFiveDayLayout
                textView.text = day.date.dayOfMonth.toString()
                if (day.owner == DayOwner.THIS_MONTH) {
                    when (day.date.toString()) {
                        this@CustomCalenderViewController.selectedDate -> {
                            // If this is the selected date, show a round background and change the text color.
                            textView.setTextColor(Color.WHITE)
                            textView.setBackgroundResource(R.drawable.selected_date_bg)
                        }

                        else -> {
                            // If this is NOT the selected date, remove the background and reset the text color.
                            textView.setTextColor(Color.BLACK)
                            textView.background = null
                        }
                    }
                    scheduleDates.find { it?.dateValue == day.date.toString() }?.let {
                        println("-----dates--${day.date.toString()}-----type--${it.type}")
                        when (it.type) {
                            Enums.Complete.toString() -> {
                                container.binding.ivScheduleType.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        this@CustomCalenderViewController.context,
                                        R.drawable.completed_ic
                                    )
                                )
                            }
                            Enums.Schedule.toString() -> {
                                container.binding.ivScheduleType.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        this@CustomCalenderViewController.context,
                                        R.drawable.pending_ic
                                    )
                                )
                            }
                        }
                    } ?: run {
                        container.binding.ivScheduleType.setImageDrawable(
                            null
                        )
                    }


//                    scheduleDates.forEach {
//                        if (it?.dateValue == day.date.toString()) {

//                        } else {
//                            println("----non matching dates---${day.date.toString()}")
//                                    container.binding.ivScheduleType.setImageDrawable(
//                                null
//                            )
//                        }
//                    }


                } else {
                    textView.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.light_grey
                        )
                    )
                }
            }
        }


        bin.exFiveCalendarSchedule.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }
                        .forEachIndexed { index, tv ->
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                        }
                    month.yearMonth
                }
            }
        }
        bin.exFiveCalendarSchedule.monthScrollListener = { month ->
            val title =
                "${Uitls.getmonthTitleFormatter().format(month.yearMonth)} ${month.year}"
            bin.tvCalendarLblSchedule.text = title
        }


        bin.ivNextMonthImageSchedule.setOnClickListener {
            bin.exFiveCalendarSchedule.findFirstVisibleMonth()?.let {
                bin.exFiveCalendarSchedule.smoothScrollToMonth(it.yearMonth.next)
            }
        }

        bin.PreviousMonthImage.setOnClickListener {
            bin.exFiveCalendarSchedule.findFirstVisibleMonth()?.let {
                bin.exFiveCalendarSchedule.smoothScrollToMonth(it.yearMonth.previous)
            }
        }

//        bin.tvSaveSchedule.setOnClickListener {
//            listnr.onChapterScheduleSaveClick(selectedDate = this.selectedDate)
//        }

    }

    private fun getFormattedDate(date: String): String {
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

    override fun onDateClick(date: String) {
        selectedDate = date
        bin.exFiveCalendarSchedule.notifyCalendarChanged()
//        calenderViewBindinds(date)
        LocalBroadcastManager.getInstance(context).sendBroadcast(Intent().apply {
            action = Constants.explicitBroadCastAction
            putExtra(Enums.SelectedDate.toString(), date)
            putExtra(Constants.Type, Enums.CalenderDateClick.toString())
        })

    }


}