package com.mobulous.Adapter.programsAdptrs.prenatalAptrs

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mobulous.fitscope.databinding.ParentHorizontalRvViewAllBinding
import com.mobulous.helper.Constants
import com.mobulous.listner.PrenatalViewAllListnr
import com.mobulous.pojo.dashboard.program.PrenatalDataItem

class PrenatalParentAdptr(
    val con: Context,
    val lst: ArrayList<PrenatalDataItem?>,
    val lisntr: PrenatalViewAllListnr
) :
    RecyclerView.Adapter<PrenatalParentAdptr.ViewHolder>() {
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
                PrenatalChildAdptr(
                    holder.mView.innerHorizonRv.context,
                    lst[position]?.chapters ?: arrayListOf()
                )
            setRecycledViewPool(viewPool)
        }
        holder.mView.lbl1.text = lst[position]?.id ?: ""
        holder.mView.lbl2.setOnClickListener {
            // Constants.weightLossChaptersJson = Gson().toJson(lst[position])

            Constants.isAlreadyAddedToFav = lst[position]?.isFav ?: false
            Constants.isAlreadyAddedToSave = lst[position]?.isSave ?: false
            Constants.programID = lst[position]?.programId.toString()

            lisntr.onPrenatalViewAllClick(
                lst[position]?.id ?: "",
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