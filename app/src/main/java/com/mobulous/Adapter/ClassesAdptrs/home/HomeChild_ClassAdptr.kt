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
import com.mobulous.fitscope.databinding.ChildHorizontalRvBinding
import com.mobulous.helper.*
import com.mobulous.pojo.dashboard.CommonChaptersItem

class HomeChild_ClassAdptr(val con: Context, val lst: ArrayList<CommonChaptersItem?>) :
    RecyclerView.Adapter<HomeChild_ClassAdptr.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ChildHorizontalRvBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mView.imgLbl.text = lst[position]?.title
        holder.mView.imageView9.visibility =
            if (Constants.isUserLogined) View.INVISIBLE else View.VISIBLE
        holder.mView.textView36.text =
            (if (lst[position]?.duration != "null") lst[position]?.duration?.toInt() else 0)?.let {
                Uitls.getTimeStampHMS(
                    it
                )
            }
        holder.mView.imgView.loadNormalPhoto_Dimens300(lst[position]?.enrollImage ?: "")
        holder.itemView.setOnClickListener {
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
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: ChildHorizontalRvBinding) :
        RecyclerView.ViewHolder(mView.root)
}