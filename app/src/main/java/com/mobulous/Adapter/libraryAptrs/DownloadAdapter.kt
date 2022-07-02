package com.mobulous.Adapter.libraryAptrs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mobulous.fitscope.activity.auth.BaseActivity
import com.mobulous.fitscope.activity.video.AboutVideoActivity
import com.mobulous.fitscope.databinding.LibraryVpAdapterBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.Enums
import com.mobulous.helper.PrefUtils
import com.mobulous.helper.loadNormalPhoto_Dimens600
import com.mobulous.pojo.video.VideoDataObj

class DownloadAdapter(var con: Context, var list: List<VideoDataObj>, var chapterID: String) :
    RecyclerView.Adapter<DownloadAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            LibraryVpAdapterBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemNoti.lbl.text = list[position].title

        holder.itemNoti.img.loadNormalPhoto_Dimens600(list[position].enrollImage?:"")

        holder.itemView.setOnClickListener {
            if (PrefUtils.with(con).getString(Enums.isLogin.toString(), "false") == "true") {
                con.startActivity(
                    Intent(con, AboutVideoActivity::class.java).putExtra(
                        Constants.Data,
                        Gson().toJson(list[position])
                    ).putExtra(Enums.ChapterID.toString(), chapterID)
                        .putExtra(Constants.From, Enums.FromOfflineDownloads.toString())
                        .putExtra(Constants.InnerObj, Gson().toJson(list.drop(position + 1)))
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
        return list.size
    }

    class MyViewHolder(val itemNoti: LibraryVpAdapterBinding) :
        RecyclerView.ViewHolder(itemNoti.root)

}