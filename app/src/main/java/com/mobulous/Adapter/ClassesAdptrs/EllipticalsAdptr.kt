package com.mobulous.Adapter.ClassesAdptrs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mobulous.fitscope.databinding.VerticalRvDataItemBinding
import com.mobulous.helper.Constants
import com.mobulous.helper.loadNormalPhoto_Dimens300
import com.mobulous.listner.EllipticalViewAllListnr
import com.mobulous.pojo.dashboard.classes.Ellipticals.EllipticalPojo
import com.mobulous.pojo.dashboard.classes.Ellipticals.HomeEllipticalsDataItems

class EllipticalsAdptr(
    val con: Context,
    val lst: ArrayList<HomeEllipticalsDataItems?>,
    val lstnr: EllipticalViewAllListnr
) :
    RecyclerView.Adapter<EllipticalsAdptr.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
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
        holder.mView.imge.loadNormalPhoto_Dimens300(lst[position]?.cateImage)
        holder.mView.textView36.visibility = if(holder.mView.textView36.text.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        holder.mView.lbl2.text = lst[position]?.category
        holder.itemView.setOnClickListener {
            lstnr.onEllipticalViewAllClick(
                lst[position]?.category?:"", Gson().toJson(lst[position]?.categoryData)
            )
        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: VerticalRvDataItemBinding) :
        RecyclerView.ViewHolder(mView.root)
//            init {
//                mView.mainVerticalRvDataItem.setOnClickListener(this)
//            }
//
//        override fun onClick(v: View?) {
//            when(v!!.id){
//                R.id.main_Vertical_rv_dataItem ->{
//                    con.startActivity(Intent(con,ViewAllFrg::class.java))
//                }
//            }
//        }

}