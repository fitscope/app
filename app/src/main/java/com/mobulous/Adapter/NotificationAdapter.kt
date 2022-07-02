package com.mobulous.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobulous.fitscope.databinding.NotificationItemLayBinding
import com.mobulous.pojo.notification.DocsItem

class NotificationAdapter(var con: Context, var lst: List<DocsItem?>) :
    RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            NotificationItemLayBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemNoti.notifictionTitleItem.text = lst[position]?.title
        //holder.itemNoti.notificationoDateItem.text = lst[position].date
        holder.itemNoti.notifiactionDesITem.text = lst[position]?.message

    }

    override fun getItemCount(): Int {
        return lst.size
    }

    class MyViewHolder(val itemNoti: NotificationItemLayBinding) :
        RecyclerView.ViewHolder(itemNoti.root) {

    }

}