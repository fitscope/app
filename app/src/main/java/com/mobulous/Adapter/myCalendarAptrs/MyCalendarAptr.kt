package com.mobulous.Adapter.myCalendarAptrs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobulous.fitscope.databinding.CalendarBottomDataitemBinding
import com.mobulous.helper.loadNormalPhoto_Dimens400
import com.mobulous.listner.RemoveScheduleLisntr
import com.mobulous.pojo.schedule.ScheduleByDateDataItem

class MyCalendarAptr(
    val workoutLst: ArrayList<ScheduleByDateDataItem?>,
    val lisntr: RemoveScheduleLisntr
) :
    RecyclerView.Adapter<MyCalendarAptr.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CalendarBottomDataitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mView.img.loadNormalPhoto_Dimens400(
            workoutLst[position]?.chapterDetails?.enrollImage ?: ""
        )
        holder.mView.exTitleLbl.text = workoutLst[position]?.chapterDetails?.title ?: ""

        holder.mView.deleteLbl.setOnClickListener {
            lisntr.onScheduleClicked(position, workoutLst[position]?.id ?: "", obj = null)
        }

        holder.mView.rescheduleLbl.setOnClickListener {
            lisntr.onScheduleClicked(
                position,
                scheduleID = workoutLst[position]?.id ?: "",
                obj = workoutLst[position]
            )
        }




    }

    override fun getItemCount(): Int {
        return workoutLst.size
    }

    inner class ViewHolder(val mView: CalendarBottomDataitemBinding) :
        RecyclerView.ViewHolder(mView.root)
}