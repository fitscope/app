package com.mobulous.ViewController

import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import com.mobulous.fitscope.databinding.CalendarDayLayoutBinding
import com.mobulous.listner.DateListnr


class DayViewContainer(view: View, lisntr: DateListnr) : ViewContainer(view) {

    lateinit var day: CalendarDay // Will be set when this container is bound.
    val binding = CalendarDayLayoutBinding.bind(view)

    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                println("-------->${day.date}")
                lisntr.onDateClick(date = day.date.toString())
//                if (selectedDate != day.date) {
//                    val oldDate = selectedDate
//                    selectedDate = day.date
//                    val binding = this@Example5Fragment.binding
//                    binding.exFiveCalendar.notifyDateChanged(day.date)
//                    oldDate?.let { binding.exFiveCalendar.notifyDateChanged(it) }
//                    updateAdapterForDate(day.date)
//                }
                
            }
        }
    }
}