package com.mobulous.Adapter.ClassesAdptrs

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobulous.Adapter.ClassesAdptrs.home.HomeChild_ClassAdptr
import com.mobulous.fitscope.databinding.ParentHorizontalRvViewAllBinding
import com.mobulous.helper.Constants
import com.mobulous.listner.RowerViewAllListnr
import com.mobulous.pojo.dashboard.CommonCategoryDataItem

class RowerAdptr(
    val con: Context, val lst: ArrayList<CommonCategoryDataItem?>,
    val lisntr: RowerViewAllListnr
) :
    RecyclerView.Adapter<RowerAdptr.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
//            VerticalRvDataItemBinding.inflate(
//                LayoutInflater.from(con),
//                parent,
//                false
//            )
            ParentHorizontalRvViewAllBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.mView.lbl1.text = lst[position].
        val childLayoutManager =
            LinearLayoutManager(
                holder.mView.innerHorizonRv.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        // childLayoutManager.initialPrefetchItemCount = 4
        holder.mView.innerHorizonRv.apply {
            layoutManager = childLayoutManager
            adapter =
                HomeChild_ClassAdptr(
                    holder.mView.innerHorizonRv.context,
                    lst[position]?.chapters ?: arrayListOf()
                )
            setRecycledViewPool(viewPool)
        }
        holder.mView.lbl1.text = lst[position]?.id
        holder.mView.lbl2.setOnClickListener {
            Constants.isAlreadyAddedToFav = lst[position]?.isFav ?: false
            Constants.isAlreadyAddedToSave = lst[position]?.isSave ?: false
            Constants.programID = lst[position]?.programId.toString()
            lisntr.onRowerViewAllClick(
                lst[position]?.id ?: "",
                lst[position]?.chapters ?: arrayListOf()
            )
        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: ParentHorizontalRvViewAllBinding) :
        RecyclerView.ViewHolder(mView.root)
}