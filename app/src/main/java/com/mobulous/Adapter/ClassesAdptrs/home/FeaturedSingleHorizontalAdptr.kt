package com.mobulous.Adapter.ClassesAdptrs.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mobulous.fitscope.activity.auth.BaseActivity
import com.mobulous.fitscope.activity.video.AboutVideoActivity
import com.mobulous.fitscope.databinding.VerticalRvDataItemBinding
import com.mobulous.helper.*
import com.mobulous.pojo.dashboard.CommonChaptersItem

class FeaturedSingleHorizontalAdptr(
    val con: Context, val lst: ArrayList<CommonChaptersItem?>
) :
    RecyclerView.Adapter<FeaturedSingleHorizontalAdptr.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            VerticalRvDataItemBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mView.lbl2.text = lst[position]?.title
        holder.mView.textView36.text =
            Uitls.getTimeStampHMS(
                if (lst[position]?.duration != "null") lst[position]?.duration?.toInt() ?: 0 else 0
            )
        holder.mView.textView36.visibility =
            if (holder.mView.textView36.text.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        holder.mView.lbl2.setOnClickListener {
            if (PrefUtils.with(con).getString(Enums.isLogin.toString(), "false") == "true") {
                con.startActivity(
                    Intent(con, AboutVideoActivity::class.java).putExtra(
                        Constants.Data, lst[position]?.id.toString()
                    ).putExtra(Constants.InnerObj, Gson().toJson(lst.drop(position + 1)))
                )
            } else {
                con.startActivity(
                    Intent(con, BaseActivity::class.java).putExtra(
                        Constants.From, Enums.NORMAL.toString()
                    )
                )
            }

        }
        holder.mView.imge.loadNormalPhoto_Dimens300(lst[position]?.enrollImage ?: "")

    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: VerticalRvDataItemBinding) :
        RecyclerView.ViewHolder(mView.root)
}