package com.mobulous.Adapter.viewAllAptrs

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

class CategoriesAptr(val con: Context, val lst: ArrayList<CommonChaptersItem?>) :
    RecyclerView.Adapter<CategoriesAptr.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAptr.ViewHolder {
        return ViewHolder(
            VerticalRvDataItemBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoriesAptr.ViewHolder, position: Int) {
        holder.mView.lbl2.text = lst[position]?.title
        holder.mView.imge.loadNormalPhoto_Dimens300(lst[position]?.enrollImage?:"")
        holder.mView.textView36.text =
            (if (lst[position]?.duration != "null") lst[position]?.duration?.toInt() else 0)?.let {
                Uitls.getTimeStampHMS(
                    it
                )
            }

        holder.mView.textView36.visibility = if(holder.mView.textView36.text.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        holder.itemView.setOnClickListener {

            if (PrefUtils.with(con).getString(Enums.isLogin.toString(), "false") == "true") {
                con.startActivity(
                    Intent(con, AboutVideoActivity::class.java).putExtra(
                        Constants.Data,
                        lst[position]?.id.toString()
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
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: VerticalRvDataItemBinding) :
        RecyclerView.ViewHolder(mView.root)
}