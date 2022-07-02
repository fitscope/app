package com.mobulous.Adapter.programsAdptrs.BootCampAptrs

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mobulous.fitscope.databinding.ParentHorizontalRvViewAllBinding
import com.mobulous.helper.Constants
import com.mobulous.listner.BuildStrengthViewAllListnr
import com.mobulous.pojo.dashboard.program.BootCampDataItem

class BootCampParentAdptr(
    val con: Context,
    val lst: ArrayList<BootCampDataItem?>,
    val lisntr: BuildStrengthViewAllListnr
) :
    RecyclerView.Adapter<BootCampParentAdptr.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ParentHorizontalRvViewAllBinding.inflate(
                LayoutInflater.from(con),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
                BootCampChildAdptr(
                    holder.mView.innerHorizonRv.context,
                    lst[position]?.chapters ?: arrayListOf()
                )
            setRecycledViewPool(viewPool)
        }
        holder.mView.lbl1.text = lst[position]?.categoryTitle ?: ""
        holder.mView.lbl2.setOnClickListener {
            Constants.isAlreadyAddedToFav = lst[position]?.isFav ?: false
            Constants.isAlreadyAddedToSave = lst[position]?.isSave ?: false
            Constants.programID = lst[position]?.programId.toString()
            lisntr.onBuildStrengthViewAllClick(
                lst[position]?.categoryTitle ?: "",
                data = Gson().toJson(lst[position]?.chapters)
            )
        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: ParentHorizontalRvViewAllBinding) :
        RecyclerView.ViewHolder(mView.root)
}