package com.mobulous.Adapter

import android.app.PendingIntent.getActivity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mobulous.fitscope.R
import com.mobulous.fitscope.databinding.BluetoothItemLayBinding
import com.mobulous.model.BluetoothDeviceModel
import java.util.ArrayList

class BluetoothDeviceAdapter(var con: Context, var lst: ArrayList<BluetoothDeviceModel>) :
    RecyclerView.Adapter<BluetoothDeviceAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            BluetoothItemLayBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }
    var click :Boolean= true
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemBluetooth.ivBlutoothItem.setImageResource(lst[position].img)
        holder.itemBluetooth.tvTitleBlutoothItem.text = lst[position].title
        holder.itemBluetooth.tvGpsBlutoothItem.text = lst[position].gps
        holder.itemBluetooth.tvDesBlutoothItem.text = lst[position].des
        holder.itemBluetooth.tvPairedBlutoothItem.text = lst[position].paired


    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class MyViewHolder(val itemBluetooth: BluetoothItemLayBinding) :
        RecyclerView.ViewHolder(itemBluetooth.root),
        View.OnClickListener {

        init {
            itemBluetooth.mainBluetothDeviceItemLayout.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            when (v!!.id) {
                R.id.main_BluetothDeviceItem_layout -> {
                    if (click == true){
                        itemBluetooth.mainBluetothDeviceItemLayout.setCardBackgroundColor(
                            ContextCompat.getColor(
                                con,
                                R.color.blue
                            )
                        )
                        click = false
                    }else{
                        click = true
                        itemBluetooth.mainBluetothDeviceItemLayout.setCardBackgroundColor(
                            ContextCompat.getColor(
                                con,
                                R.color.white
                            )
                        )
                    }

                }
            }
        }

    }
}