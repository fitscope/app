package com.mobulous.Adapter.ClassesAdptrs.BikeAdptrs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mobulous.fitscope.databinding.VerticalRvDataItemBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.loadNormalPhoto_Dimens600
import com.mobulous.listner.BikeViewAllListnr
import com.mobulous.pojo.dashboard.classes.ClassBike.ClassBikeDataItems

class BikesClassAdptr(
    val con: Context,
    val lst: ArrayList<ClassBikeDataItems?>,
    val lstnr: BikeViewAllListnr
) :
    RecyclerView.Adapter<BikesClassAdptr.ViewHolder>() {
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
        holder.mView.imge.loadNormalPhoto_Dimens600(lst[position]?.cateImage ?: "")
        holder.mView.textView36.visibility =
            if (holder.mView.textView36.text.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        holder.mView.lbl2.text = lst[position]?.category
        holder.itemView.setOnClickListener {
            lstnr.onBikeViewAllClick(
                lst[position]?.category ?: "",
                Gson().toJson(lst[position]?.categoryData)
            )
        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: VerticalRvDataItemBinding) :
        RecyclerView.ViewHolder(mView.root)
}