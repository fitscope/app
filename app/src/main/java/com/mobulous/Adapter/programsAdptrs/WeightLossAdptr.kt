package com.mobulous.adapter.ClassesAdptrs

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobulous.fitscope.databinding.VerticalRvDataItemBinding
import com.mobulous.pojo.dashboard.classes.bikes.bikePojo

class WeightLossAdptr(val con: Context, val lst: ArrayList<bikePojo>) :
    RecyclerView.Adapter<WeightLossAdptr.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightLossAdptr.ViewHolder {
        return ViewHolder(
            VerticalRvDataItemBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeightLossAdptr.ViewHolder, position: Int) {
      //  holder.mView.lbl1.text = lst[position].name
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: VerticalRvDataItemBinding) :
        RecyclerView.ViewHolder(mView.root)
}