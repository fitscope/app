package com.mobulous.Adapter.myCalendarAptrs

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobulous.fitscope.activity.video.AboutVideoActivity
import com.mobulous.fitscope.databinding.UpcomingCalendatDataItemBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.Uitls
import com.mobulous.helper.formatDate
import com.mobulous.helper.loadNormalPhoto_Dimens400
import com.mobulous.listner.UpComingNdHistoryLisntr
import com.mobulous.pojo.schedule.UpComingScheduleDataItem
import java.util.*

class UpcomingAptr(
    val upcomingLst: ArrayList<UpComingScheduleDataItem?>,
    val lisntr: UpComingNdHistoryLisntr
) :
    RecyclerView.Adapter<UpcomingAptr.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UpcomingCalendatDataItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mView.img.loadNormalPhoto_Dimens400(
            upcomingLst[position]?.chapterDetails?.enrollImage ?: ""
        )
        holder.mView.textView36.text =
            (if (upcomingLst[position]?.timeInMint != null) upcomingLst[position]?.timeInMint else 0)?.let {
                Uitls.getTimeStampHMS(
                    it
                )
            }
        holder.mView.lbl.text = upcomingLst[position]?.chapterDetails?.title ?: ""
        if (position == 0) {
            holder.mView.dateLbl.text =
                upcomingLst[position]?.dateValue?.formatDate(
                    Constants.yMd,
                    Constants.upcoming_date_format
                )
        }
        if (position > 0) {
            if (upcomingLst[position]?.dateValue != upcomingLst[position - 1]?.dateValue) {
                holder.mView.dateLbl.visibility = View.VISIBLE
                holder.mView.dateLbl.text =
                    upcomingLst[position]?.dateValue?.formatDate(
                        Constants.yMd,
                        Constants.upcoming_date_format
                    )
            } else {
                holder.mView.dateLbl.visibility = View.GONE
            }
        }
        holder.mView.lbl.setOnClickListener {
            holder.itemView.context.startActivity(
                Intent(holder.itemView.context, AboutVideoActivity::class.java).putExtra(
                    Constants.Data, upcomingLst[position]?.chapterDetails?.id.toString()
                ).putExtra(Constants.InnerObj, "")
            )
        }

        holder.mView.MoreBtn.setOnClickListener {
            lisntr.onMoreOptionSelected(
                position,
                id = upcomingLst[position]?.id ?: "",
                chapterID = upcomingLst[position]?.chapterDetails?.id.toString(),
                chapterDuration = upcomingLst[position]?.chapterDetails?.duration.toString(),
                chapterName = upcomingLst[position]?.chapterDetails?.title ?: "",
                scheduleID = upcomingLst[position]?.id ?: ""
            )
            //  holder.itemView.context.showMoreOptionForSchedule(upcomingLst[position]?.id)
        }
    }

    override fun getItemCount(): Int {
        return upcomingLst.size
    }

    inner class ViewHolder(val mView: UpcomingCalendatDataItemBinding) :
        RecyclerView.ViewHolder(mView.root)

}