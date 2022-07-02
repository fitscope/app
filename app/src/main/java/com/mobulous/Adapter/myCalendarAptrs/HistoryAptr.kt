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

class HistoryAptr(
    val lst: ArrayList<UpComingScheduleDataItem?>,
    val lisntr: UpComingNdHistoryLisntr
) :
    RecyclerView.Adapter<HistoryAptr.ViewHolder>() {
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
            lst[position]?.chapterDetails?.enrollImage ?: ""
        )
        holder.mView.textView36.text =
            (if (lst[position]?.timeInMint != null) lst[position]?.timeInMint else 0)?.let {
                Uitls.getTimeStampHMS(
                    it
                )
            }
        holder.mView.lbl.text = lst[position]?.chapterDetails?.title ?: ""
        if (position == 0) {
            holder.mView.dateLbl.text =
                lst[position]?.dateValue?.formatDate(Constants.yMd, Constants.upcoming_date_format)
        }
        if (position > 0) {
            if (lst[position]?.dateValue != lst[position - 1]?.dateValue) {
                holder.mView.dateLbl.visibility = View.VISIBLE
                holder.mView.dateLbl.text =
                    lst[position]?.dateValue?.formatDate(
                        Constants.yMd,
                        Constants.upcoming_date_format
                    )
            } else {
                holder.mView.dateLbl.visibility = View.GONE
            }
        }
        holder.mView.MoreBtn.setOnClickListener {
            lisntr.onMoreOptionSelected(
                position,
                id = lst[position]?.id ?: "",
                chapterID = lst[position]?.chapterDetails?.id.toString(),
                chapterDuration = lst[position]?.chapterDetails?.duration.toString(),
                chapterName = lst[position]?.chapterDetails?.title ?: "", scheduleID = lst[position]?.id?:""
            )
        }
        holder.mView.lbl.setOnClickListener {
            holder.itemView.context.startActivity(
                Intent(holder.itemView.context, AboutVideoActivity::class.java).putExtra(
                    Constants.Data, lst[position]?.chapterDetails?.id.toString()
                ).putExtra(Constants.InnerObj, "")
            )
        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: UpcomingCalendatDataItemBinding) :
        RecyclerView.ViewHolder(mView.root)

}