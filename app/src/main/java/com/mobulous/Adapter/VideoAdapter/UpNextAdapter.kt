package com.mobulous.Adapter.VideoAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mobulous.fitscope.activity.video.AboutVideoActivity
import com.mobulous.fitscope.databinding.SearchResultsRvDataItemBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.Enums
import com.mobulous.helper.loadNormalPhoto_Dimens600
import com.mobulous.pojo.dashboard.CommonChaptersItem

class UpNextAdapter(
    var con: Context,
    var upnextLst: ArrayList<CommonChaptersItem?>,
    var isCategoryType: Boolean
) :
    RecyclerView.Adapter<UpNextAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpNextAdapter.MyViewHolder {
        return MyViewHolder(
            SearchResultsRvDataItemBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.upNextView.img.loadNormalPhoto_Dimens600(upnextLst[position]?.enrollImage ?: "")
        holder.upNextView.lbl.text = upnextLst[position]?.title
        holder.upNextView.textView36.text = upnextLst[position]?.duration
        holder.itemView.setOnClickListener {
            con.startActivity(
                Intent(con, AboutVideoActivity::class.java).putExtra(
                    Constants.Data, upnextLst[position]?.id.toString()
                ).putExtra(Constants.NewChapterDataLst, Gson().toJson(upnextLst.drop(position + 1)))
//                    .putExtra(
//                        Constants.Type,
//                        if (isCategoryType) Enums.CATEGORY_DATA_ITEM.toString() else ""
//                    )
//                /*since its category data so we will parse this type of data differently in UpNext FRagment*/
            )
        }
    }

    override fun getItemCount(): Int {
        return upnextLst.size
    }

    inner class MyViewHolder(val upNextView: SearchResultsRvDataItemBinding) :
        RecyclerView.ViewHolder(upNextView.root) {

    }
}