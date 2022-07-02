package com.mobulous.Adapter.programsAdptrs.seniorAptr

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mobulous.fitscope.databinding.ParentHorizontalRvViewAllBinding
import com.mobulous.listner.RecumbentCategoryCommentListnr
import com.mobulous.listner.SeniorWrkoutHorizontalViewAllListnr
import com.mobulous.listner.SeniorWrkoutViewAllListnr
import com.mobulous.pojo.dashboard.program.SeniorWorkoutDataItemPojo


class SeniorWrkoutParentAdptr(
    val con: Context,
    val lst: ArrayList<SeniorWorkoutDataItemPojo?>,
    val lisntr: SeniorWrkoutViewAllListnr,
    val lisntrHorizontal: SeniorWrkoutHorizontalViewAllListnr,
    val childlistnr: RecumbentCategoryCommentListnr

) :
    RecyclerView.Adapter<SeniorWrkoutParentAdptr.ViewHolder>() {
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
                SeniorWrkoutChildAdptr(
                    holder.mView.innerHorizonRv.context,
                    lst[position]?.categoryData ?: arrayListOf(),
                    listnr = childlistnr
                )
            setRecycledViewPool(viewPool)
        }
        holder.mView.lbl1.text = lst[position]?.category ?: ""
        holder.mView.lbl2.setOnClickListener {
            lisntrHorizontal.onSeniorWrkoutHorizontalViewAllClick(
                lbl = lst[position]?.category ?: "",
                data = Gson().toJson(lst[position]?.categoryData?.filter { it?.chapters?.size!! > 1 })
            )
//            if (lst[position]?.category ?: "" == Constants.Recumbent || lst[position]?.category ?: "" == Constants.PowerWalking) {
//
//            }
//            else {
//                lisntr.onSeniorWrkoutViewAllClick(lst[position].parent_lbl, data = Gson().toJson(lst[position]))
//            }

        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    inner class ViewHolder(val mView: ParentHorizontalRvViewAllBinding) :
        RecyclerView.ViewHolder(mView.root)
}