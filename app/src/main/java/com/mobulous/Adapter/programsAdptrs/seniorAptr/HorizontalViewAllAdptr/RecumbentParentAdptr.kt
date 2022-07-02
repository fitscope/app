package com.mobulous.Adapter.programsAdptrs.seniorAptr.HorizontalViewAllAdptr

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mobulous.fitscope.databinding.ParentHorizontalRvViewAllBinding
import com.mobulous.helper.Constants
import com.mobulous.listner.RecumbentViewAllListnr
import com.mobulous.pojo.dashboard.ProgramCategoryDataItem

class RecumbentParentAdptr(
    val con: Context, val lst: ArrayList<ProgramCategoryDataItem?>,
    val lisntr: RecumbentViewAllListnr
) :
    RecyclerView.Adapter<RecumbentParentAdptr.ViewHolder>() {
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
                RecumbentChildAdptr(
                    holder.mView.innerHorizonRv.context,
                    lst[position]?.chapters ?: arrayListOf()
                )
            setRecycledViewPool(viewPool)
        }
        holder.mView.lbl1.text = lst[position]?.programTitle ?: ""

        holder.mView.lbl2.setOnClickListener {

            Constants.isAlreadyAddedToSave = lst[position]?.isSave?:false
            Constants.isAlreadyAddedToFav = lst[position]?.isFav?:false
            Constants.programID = lst[position]?.programId.toString()
            lisntr.onRecumbentViewAllClick(
                lst[position]?.programTitle ?: "",
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